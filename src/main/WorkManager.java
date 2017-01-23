package main;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.BlockingQueue;

public class WorkManager <W, M, C> {
	private BlockingQueue <W> mQueue;
	private ArrayList <M> mMiners;
	private ArrayList <C> mCrawlers;
	private Class <W> mWorkClass;
	private Class <M> mMinersClass;
	private Class <C> mCrawlerClass;
	
	private int mQueueSize;
	private int mNoOfMiners;
	private int mNoOfCrawlers;
	
	@SuppressWarnings("unchecked")
	WorkManager (Class <W> pWorkClass, Class <M> pMinerClass, Class <C> pCrawlerClass, short pQueueSize, short pNoOfMiners, short pNoOfCrawlers) {
		mQueueSize = pQueueSize;
		mNoOfMiners = pNoOfMiners;
		mNoOfCrawlers = pNoOfCrawlers;
		mWorkClass = pWorkClass;
		mMinersClass = pMinerClass;
		mCrawlerClass = pCrawlerClass;
		
		mQueue = new LinkedBlockingDeque <W> (mQueueSize);
		mMiners = new ArrayList <M> ();
		mCrawlers = new ArrayList <C> ();

	}
	
	WorkManager (Class <W> pWorkClass, Class <M> pMinerClass, Class <C> pCrawlerClass, short pNoOfMiners, short pNoOfCrawlers) {
		mNoOfMiners = pNoOfMiners;
		mNoOfCrawlers = pNoOfCrawlers;
		mWorkClass = pWorkClass;
		mMinersClass = pMinerClass;
		mCrawlerClass = pCrawlerClass;
		
		mQueue = new LinkedBlockingDeque <W> ();
		mMiners = new ArrayList <M> ();
		mCrawlers = new ArrayList <C> ();
	}
	
	public void start () throws Exception {
		
		// start workers here
		if (mMiners != null) {
			for (short i=0; i < mNoOfMiners; i++) {
				mMiners.add(mMinersClass.getDeclaredConstructor (mMinersClass).newInstance(mQueue));
				Class <M> tempMiner = (Class<M>) mMiners.get(i);
				//tempMiner.start;
			}
		}
		
		// Start the crawler here
		synchronized (mQueue) {
			for (short i = 0; i < mNoOfCrawlers; i++) {
				mCrawlers.add(mCrawlerClass.getDeclaredConstructor(mMinersClass).newInstance(mQueue));
				Class <C> tempCrawler = (Class<C>) mCrawlers.get(i); 
				// tempCrawler.start;
			}
		}
	}
	
}
