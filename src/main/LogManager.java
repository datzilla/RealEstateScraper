package main;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import utils.Pair;

public class LogManager extends Logger {
	
	private static ArrayList <Pair<String, Logger>> sLoggerList = new ArrayList <Pair<String, Logger>> ();
	
	protected LogManager(String pName, String pResourceBundleName) {
		super(pName, pResourceBundleName);
	}

	public static void disableScreenOutput () {   
	    Logger rootlogger = Logger.getLogger("");
	    Handler [] handlers = rootlogger.getHandlers();
	    for (Handler handler : handlers) {
	    	if (handler instanceof ConsoleHandler ) {
		    	rootlogger.removeHandler(handler);
	    	}
	    }
	}
	
	public static Logger getClassLogger (String pClassName) throws Exception {
		boolean found = false;
		Logger logger = null;
		synchronized (sLoggerList) {
			for (Pair <String, Logger> l : sLoggerList) {
				try {
					if (l.getFirst().toLowerCase().equals(pClassName)) {
						logger = (Logger) l.getSecond();
						found = true;
					}
				} catch (Exception e) {
					// do nothing
				}
			}
			
			if (found == false) {
				logger = Logger.getLogger(pClassName);
				FileHandler logfileHandler = new FileHandler("logs/" + pClassName + ".txt");
				logfileHandler.setFormatter(new LogFormatter());
				logger.addHandler(logfileHandler);
				sLoggerList.add(new Pair <String, Logger> (pClassName, logger));
			}
		}
		return logger;
	}
	
	public static boolean killAppLogger (String pClassName) {
		boolean removed = false;
		synchronized (sLoggerList) {
			for (Pair <String, Logger> logger : sLoggerList) {
				try {
					if (logger.getFirst().toLowerCase().equals(pClassName)) {
						sLoggerList.remove(sLoggerList.indexOf(logger));
						removed = true;
					}
				} catch (Exception e) {
					// do nothing
				}
			}
		}
		return removed;
	}
}
