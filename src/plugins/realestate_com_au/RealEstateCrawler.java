package plugins.realestate_com_au;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

// read the structure of the page and put url on to a queue

public class RealEstateCrawler extends main.SiteCrawler {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private BlockingQueue<?> mQueue;
	
	RealEstateCrawler (BlockingQueue<?> pQueue) {
		mQueue = pQueue;
	}
	
		public void run () {
		// iterate through the site code
		//for (;;) {
	//	}
	}

	@Override
	public void incrementPage() {
		// TODO Auto-generated method stub
	}
		
}
