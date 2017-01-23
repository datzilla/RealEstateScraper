package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import main.LogManager;


//this is an iterator for urls. you will need some static elements so this class can generate the url.
//This is used for mining.

public class URLList implements URLIterator {
	private String mURL;
	private int mInsertPosition;
	private Variable<?> mVariable;
	private List <URL> mCachedURLs;
	private int mCurrentPosition;
	private Logger mLogger;

	URLList (String pURL, int pPos, Variable <?> pVariable) {
		mURL = pURL;
		mInsertPosition = pPos;
		mVariable = pVariable;
		mCurrentPosition = -1;
		try {
			mLogger = LogManager.getClassLogger(this.getClass().getName());
		} catch (Exception e) {
			mLogger = null;
		}
	}
	
	URLList (URL pURL, int pPos, Variable <?> pVariable) {
		mURL = pURL.toExternalForm();
		mInsertPosition = pPos;
		mVariable = pVariable;
		mCurrentPosition = -1;
		try {
			mLogger = LogManager.getClassLogger(this.getClass().getName());
		} catch (Exception e) {
			mLogger = null;
		}
	}
	
	public boolean hasNext () {
		return mVariable.hasNext();
	}
	
	public URL getCurrentURL () {
		if (mCachedURLs != null) {
			return mCachedURLs.get(mCurrentPosition);
		} else {
			return null;
		}
	}
	
	public int getCurrentPosition () {
		return mCurrentPosition;
	}
	
	public void setCurrentPosition (int pPosition) {
		if (mCachedURLs != null) {
			if (pPosition < mCachedURLs.size() && pPosition >=0) {
				mCurrentPosition = pPosition;
			}
		}
	}
	
	public List <URL> getNext (int pNumberOfURLs) {
		List <URL> tempList = new ArrayList<URL>();
		int pos = 0;
		while (mVariable.hasNext() || pos < pNumberOfURLs) {
			try {
				tempList.add(new URL(mURL.substring(0, mInsertPosition)
						+ mVariable.getNext()
						+ mURL.substring(mInsertPosition, mURL.length() - 1)));
			} catch (MalformedURLException e) {
				if (mLogger != null) {
					mLogger.severe("getNext() failed - URL malformed - error: " +e.toString());
				}
				
			}
			
			mCurrentPosition++;
			pos++;
		}
		return tempList;
	}
	
	public URL getNext () {
		if (mVariable.hasNext()) {
			try {
				mCachedURLs.add(
						new URL(mURL.substring(0, mInsertPosition)
						+ mVariable.getNext()
						+ mURL.substring(mInsertPosition, mURL.length() - 1)));
			} catch (MalformedURLException e) {
				if (mLogger != null) {
					mLogger.severe("getNext() failed - URL malformed - error: " +e.toString());
				}
				
			}
			mCurrentPosition++;
		}
		return mCachedURLs.get(mCurrentPosition);
	}
	
	public URL getURL (int pPosition) {
		if (pPosition < mCachedURLs.size()) {
			return mCachedURLs.get(pPosition);
		} else {
			if (mLogger != null) {
				mLogger.severe("called getURL() with position: " + Integer.toString(pPosition) + " which is bigger than the size of mCachedURLs: " + Integer.toString(mCachedURLs.size()));
			}
			
			return null;
		}
	}

	@Override
	public void clearCache() {
		if (mCachedURLs != null) {
			mCachedURLs.clear();
		}
	}
}
