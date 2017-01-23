package main;

import java.util.logging.Logger;

public class RealestateScraper {

	private Logger mAppLogger;
	private static RealestateScraper mRealestateScraper;
	private String [] mArguments;
	
	RealestateScraper (String [] pArguments) {
		try {
			mAppLogger = LogManager.getClassLogger(this.getClass().getName());
			mArguments = pArguments;
		} catch (Exception e) {
			System.out.println("Webscrapper can't not create log instance for " + this.getClass().getName());
		}
	}
	
	public void run () {
		// run it from here.
		
	}
	
	
	
	// Public start method	
	public static void main (String [] pArguments) {
		RealestateScraper mRealestateScraper = new RealestateScraper(pArguments);
		mRealestateScraper.run();
	}
}
