package proxy;
/*
 *  Author:      Dat Q Nguyen
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ProxyManager {
	
	private ArrayList <Proxy> mProxyList;
	private String mProxyListURL = "http://proxylist.hidemyass.com";
	private InetAddress mPublicIP;
	private String mCheckIPURL = "http://checkip.dyndns.org";
	private int mLastPage = 1;
	private int mReadTimeout = 0;
	private int mConnectTimeout = 0;

	ProxyManager () throws Exception {
	}
	
	public InetAddress getPublicIP (Document pJoupDocument) throws IOException {
		if (mPublicIP == null) {
			System.out.println("Determining public ip...");
			Pattern p = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})", Pattern.MULTILINE | Pattern.DOTALL);
			Matcher m = p.matcher(pJoupDocument.html());
			while (m.find()) {
				mPublicIP = InetAddress.getByName(m.group(1));
			}
		}
		return mPublicIP;
	}
	
	private boolean verifyProxy (Proxy pProxy) {
		System.out.println("Testing Proxy: " + pProxy.address().toString());
		try {
			Document doc = getDocument (mCheckIPURL, pProxy);
			doc = null;
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public int getProxyCount () {
		if (mProxyList != null) {
			return mProxyList.size();
		} else {
			return 0;
		}
	}
	
	public Document getDocument (String pURL, Proxy pProxy) throws MalformedURLException, IOException {
		URL url = new URL(pURL);
		HttpURLConnection uc = (HttpURLConnection) url.openConnection(pProxy);
		if (mConnectTimeout > 0) uc.setConnectTimeout(mConnectTimeout);
		if (mReadTimeout > 0) uc.setReadTimeout(mReadTimeout);
		uc.connect();
		String line = null;
		StringBuffer tmp = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	    while ((line = in.readLine()) != null) {
	      tmp.append(line);
	    }
		 return Jsoup.parse(String.valueOf(tmp));
	}
	
	public ArrayList <Proxy> getProxyList (String pProxyListURL) throws Exception {
		if (mProxyList!=null) {
			return mProxyList;			
		} else {
			mProxyListURL = pProxyListURL;
			loadProxy();
			return mProxyList;
		}
	}
	
	public ArrayList <Proxy> getProxyList () throws Exception {
		if (mProxyList!=null) {
			return mProxyList;			
		} else {
			loadProxy();
			return mProxyList;
		}
	}
	
	public synchronized void checkProxyList () {
		ArrayList <Proxy> toRemove = new ArrayList<Proxy>(); 
		synchronized (mProxyList) {
			if (mProxyList != null) {
				for (Proxy p : mProxyList) {
					if (!verifyProxy(p)) {
						System.out.println("Deleting " + p.address().toString() + " from list...");
						toRemove.add(p);
					}
				}
			}
		}
		
		if (toRemove.size() > 0) {
			for (Proxy p : toRemove) {
				mProxyList.remove(p);
			}
			toRemove.clear();
		}
		toRemove=null;
	}
	
	private synchronized void clearProxyList () {
		if (mProxyList != null) mProxyList.clear();
	}
		
	private void loadProxy () throws IOException {
		Connection jsc = Jsoup.connect (mProxyListURL).timeout(5000);
		Document hmasite = jsc.get();
		
		//get the last page
		for (Element e : hmasite.select(".pagination.ng-scope")) {
			Pattern p = Pattern.compile("([0-9]+)$", Pattern.MULTILINE);
			Matcher m;
			ArrayList <Integer> pagenum = new ArrayList <Integer> ();
			
			for (Element at : e.select ("a[href]")) {
				m = p.matcher(at.text());
				if (m.find()) {
					//System.out.println(m.group(1));
					pagenum.add(Integer.parseInt(m.group(1)));
				}
			}
			
			mLastPage = pagenum.get(pagenum.size()-1);
			
			pagenum = null;
			p = null;
			m = null;
		}

		jsc = null;
		hmasite = null;

		mProxyList = new ArrayList<Proxy>();
		for (int lp = 1; lp <= mLastPage; lp++) {
			// create a connection to each page
			jsc = Jsoup.connect (mProxyListURL + "/" + Integer.toString(lp) + "#listable").timeout(5000);
			hmasite = jsc.get();
			
			for (Element proxyTable : hmasite.select (".hma-table")) {
				int i = 0;
				String ip = "";
				String port = "";
				String type = "";
				
				for (Element td : proxyTable.select("td")) {
					// Second column of the proxy list table contains the IP of the proxy
					if (i == 1) {
						ArrayList <HMAStylePair> stylepairs = new ArrayList <HMAStylePair> ();
						
						String code = td.html();
						
						Pattern p = Pattern.compile("\\.(\\w+)\\{\\w+:(\\w+)}", Pattern.MULTILINE | Pattern.DOTALL);
						Matcher m = p.matcher(code);
						while (m.find()) {
							stylepairs.add(new HMAStylePair(m.group(1), m.group (2)));
						}
						
						//remove all white spaces
						code = code.replaceAll("[\\r\\n]", "");
						code = code.replaceAll("<style>.*?</style>", "");
						
						// clear the style that has none for display
						for (HMAStylePair sp : stylepairs) {
							if (sp.isDisplayFlag() == false) {
								code = code.replaceAll ("<span class=\"" + sp.getKey() +"\">.*?</span>", "");
							}
						}
											
						// clear all span tags with attribute value display:none
						code = code.replaceAll("<span style=\"display:none\">.*?</span>", "");
						
						
						// clear all div tags with attribute value display:none
						code = code.replaceAll("<div style=\"display:none\">.*?</div>", "");
						
						//replace all span tags without any values
						code = code.replaceAll("<span></span>", "");
												
						// remove spaces
						code = code.replaceAll("\\s", "");
											
						p = Pattern.compile(">([\\.0-9]+)<", Pattern.MULTILINE | Pattern.DOTALL);
						m = p.matcher(code);
						while (m.find()) {
							ip = ip + m.group(1);
						}			
						
					}
					
					if (i==2) {
						// Third column of the proxy list table contains the Port of the proxy
						port = td.text();
					} 	
					
					if (i==6) {
						type = td.text();
					}
					
					// reset the column count including ips and all the other variables
					if (i > 6) {				
						// add proxy to the list
						if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}") && port.matches("^[0-9]+$") && type.matches("HTTP[S]*"))  {
							Proxy p = new Proxy (Proxy.Type.HTTP, new InetSocketAddress(ip, Integer.parseInt(port)));
							//if (verifyProxy(p)) {
								System.out.println("Adding proxy: " + ip + ":" + port + " to the proxy list...");
								mProxyList.add(p);
							//} else {
								//System.out.println("Excluded proxy: " + ip + ":" + port + " from the proxy list...");
								//p = null;
							//}
						} else {
							System.out.println("Excluded proxy: " + ip + ":" + port + " from the proxy list...");
						}

						i = 0;
						ip = "";
						port = "";
						type = "";
					} else {
						i++;
					}
				}
			}
			
			jsc = null;
			hmasite = null;
		}	
	}
	
	public Proxy getProxy () {
		if (mProxyList!=null) {
			boolean foundGoodProxy=false;
			Random random = new Random();
			Proxy p = null;
			while (foundGoodProxy) {
				p = mProxyList.get(random.nextInt(mProxyList.size()));
				if (verifyProxy(p)) {
					foundGoodProxy = true;
				}
			}
			return p;
		} else {
			return null;
		}
	}
	
	public int getReadTimeout() {
		return mReadTimeout;
	}

	public int getConnectTimeout() {
		return mConnectTimeout;
	}

	public void setReadTimeout(int pReadTimeout) {
		mReadTimeout = pReadTimeout;
	}

	public void setConnectTimeout(int pConnectTimeout) {
		mConnectTimeout = pConnectTimeout;
	}

	public static void main(String[] args) {
		try {
			// Create instance
			ProxyManager pm = new ProxyManager();
			
			//load the proxies
			long starttime = System.nanoTime();
			System.out.println("Extract Proxies from HMA and put into a list...");
			ArrayList <Proxy> proxylist = pm.getProxyList();
			long endtime = System.nanoTime();
			long duration = (long) ((endtime - starttime)/1000000000);
			System.out.println("Loaded proxies in " + duration + " seconds");
			System.out.println("Total proxies in list: " + pm.getProxyCount());
			System.out.println("Purging the proxy list...");
			
			//clean the proxy list
			
			// set the default connection time and read time to filter out the slow proxies
			pm.setConnectTimeout(500); //500 msec max to connect
			pm.setReadTimeout(3000); //3 sec max to read the page
			
			starttime = System.nanoTime();
			pm.checkProxyList();
			endtime = System.nanoTime();
			duration = (long) ((endtime - starttime)/1000000000);
			System.out.println("Purged proxies in " + duration + " seconds");
			System.out.println("Total Proxies in list: " + pm.getProxyCount());
			
			pm.clearProxyList();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
