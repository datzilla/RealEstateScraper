package main;

import java.util.Date;

public class Status {

	public final static byte STOPPED = 0;
	public final static byte PAUSED = 1;
	public final static byte STARTED = 2;
	public final static byte COMPELETED = 3;
	public final static byte ERROR = 4;
	public final static byte KILLED = 5;
	public final static byte INITIATED = 6;
	public final static byte RESUMED = 7;
	public final static byte CLOSED = 8;
	public final static byte OPENNED = 9;
	public final static byte SAVED = 10;
	public final static byte UNSAVED = 11;
	public final static byte CREATED = 12;
		
	private byte mStatus;
/*	 at the moment we dont know why we would want the time
	 but just incase.*/
	private long mTime; 
	
	public Status (byte pStatus) {
		mStatus = pStatus;
	}
	
/*	public Date getTime () {
		return mTime;
	}*/

	public void setStatus(byte pStatus) {
		mStatus = pStatus;
	}
	
	public boolean is (byte pStatus) {
		if (mStatus == pStatus) {
			return true;
		}
		else {
			return false;
		}
	}
}