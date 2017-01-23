package ouput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import utils.Pair;


// This approach will connect on server for java app for now.



public class HadoopHandler implements OutputHandler {
	
	private static HadoopHandler mHadoopHandler;
	private static Configuration mConfiguration;
	private static HashMap<Path, Pair<OutputStream, BufferedWriter>> mOutputables;
	
	private String mNNSocketAddress;
	private FileSystem mFileSystem;
	private UserGroupInformation mUGI;
	private OutputFormat mFormat; 
		
	
	
	private HadoopHandler (String pHDFSAddress, String pUsername) throws IOException, InterruptedException {
		if (mConfiguration==null) mConfiguration = new Configuration();
		mNNSocketAddress = pHDFSAddress;
		mConfiguration.set("fs.defaultFS", mNNSocketAddress);
		mUGI = UserGroupInformation.createRemoteUser(pUsername);
		mFileSystem = mUGI.doAs (new PrivilegedExceptionAction <FileSystem> () {
			public FileSystem run() throws Exception {
				return FileSystem.get(mConfiguration);
			}
		});
		mFormat = new OutputFormat();
	}
	
	public static HadoopHandler getHandler (String pNameNodeSocketAddress, String pUsername) throws IOException, InterruptedException {
		if (mHadoopHandler == null) {
			mHadoopHandler = new HadoopHandler(pNameNodeSocketAddress, pUsername);
		}
		
		return mHadoopHandler;
	}
	
	public static void setConfiguration (String pKeyName, String pValueName) {
		if (mConfiguration==null) mConfiguration = new Configuration();
		mConfiguration.set(pKeyName, pValueName);
	}
	
	public void writeOutput (Outputable pOutputable) throws IOException, InterruptedException {
		
		synchronized (pOutputable) {
			if (mOutputables == null) mOutputables = new HashMap<Path, Pair<OutputStream,BufferedWriter>>();
			
			OutputStream os = null;
			BufferedWriter bw = null;
			
			final Path fp = new Path(pOutputable.getPath());
			if (!mOutputables.containsKey(fp)) {
				//if there's no keys create a new one
				//always append to a file if it exists
				if (mFileSystem.exists(fp)) {			
					//append
					os = mUGI.doAs(new PrivilegedExceptionAction <OutputStream>() {

					@Override
					public OutputStream run() throws Exception {
							return mFileSystem.append(fp);
						}
						
					});	
					
					bw = new BufferedWriter(new OutputStreamWriter(os, mFormat.getEncoding()));
					
				} else {
					//create a new one
					os = mUGI.doAs(new PrivilegedExceptionAction <OutputStream>() {
						public OutputStream run() throws Exception {
							return mFileSystem.create(fp);
						}
						
					});
					
					bw = new BufferedWriter(new OutputStreamWriter(os));
				}
				
				if (bw!=null && os!=null) {
					Pair <OutputStream, BufferedWriter> tmpOutput = new Pair <OutputStream, BufferedWriter> (os, bw);
					mOutputables.put(fp, tmpOutput);
				}
			} else {
				//retrieve the os and bw
				Pair <OutputStream, BufferedWriter> tmpOutput = mOutputables.get(pOutputable.getPath());
				os = tmpOutput.getFirst();
				bw = tmpOutput.getSecond();
				
			}
			
			//write to the file
			if (bw!=null && os!=null) {
				bw.write(String.join(mFormat.getDelimiter(), pOutputable.toOutput()) + mFormat.getLineSeperator());
				bw.flush();
			}
			
		}
	}
	
	public void reset () throws IOException, InterruptedException {	
		synchronized (mOutputables) {
			if (mOutputables != null) {			
				for (Pair<OutputStream, BufferedWriter> p : mOutputables.values()) {
					p.getSecond().close();
					p.getFirst().close();
				}
			}
		}
		
		synchronized (mFileSystem) {
			mFileSystem.close();
			mFileSystem = mUGI.doAs (new PrivilegedExceptionAction <FileSystem> () {

				@Override
				public FileSystem run() throws Exception {
					return FileSystem.get(mConfiguration);
				}
			});
		}
	}
	
	public void close () throws IOException {
		
		synchronized (mOutputables) {
			if (mOutputables != null) {			
				for (Pair<OutputStream, BufferedWriter> p : mOutputables.values()) {
					p.getSecond().close();
					p.getFirst().close();
				}
			}
			mOutputables= null;
		}
		
		synchronized (mFileSystem) {
			if (mFileSystem != null) mFileSystem.close();
			mFileSystem = null;
		}
	}
}
