package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Timer extends Thread {
	
	private ArrayList <Pair <Object, Date>> mStartTimes;
	private ArrayList <Pair <Object, Date>> mStopTimes;
	private Timer mTimer;
	
	private Timer () {
		mStartTimes = new ArrayList <Pair <Object, Date>> ();
		mStopTimes = new ArrayList <Pair <Object, Date>> ();		
	}
	
	public Timer getTimer () {
		if (mTimer == null) {
			mTimer = new Timer();
		}
		return mTimer;
	}
	
	public void resetTime (Object pObject) {
		if (mStartTimes != null) {
			int itemPosition = getObjectPosition (pObject, mStartTimes); 
			if (itemPosition >= 0) {
				mStartTimes.get(itemPosition).setSecond(new Date());
			}	
		}
		
		
		if (mStopTimes != null) {
			int itemPosition = getObjectPosition (pObject, mStopTimes); 
			if (itemPosition >= 0) {
				mStopTimes.remove(itemPosition);
			}
		}
	}
	
	private int getObjectPosition (Object pObject, List pList) {
		int ItemPosition = -1;
		if (pList !=null) {
			for (int i=0; i < pList.size(); i++) {
				if (pList.get(i).equals(pObject)) {
					ItemPosition=i;
					break;
				}
			}
		}
		return ItemPosition;
	}
	
	public void timeStart (Object pObject) {
		if (mStartTimes != null) {
			int ItemPosition = getObjectPosition (pObject, mStartTimes);
			if (ItemPosition < 0) {
				mStartTimes.add(new Pair <Object, Date> (pObject, new Date()));
			}	
		}
		
	}
	
	public void timeStop (Object pObject) {
		if (mStartTimes != null && mStopTimes != null) {
			int StartItemPosition = getObjectPosition (pObject, mStartTimes);
			if (StartItemPosition >= 0) {
				int StopItemPosition = getObjectPosition (pObject, mStopTimes);
				if (StopItemPosition < 0) {
					mStopTimes.add(new Pair <Object, Date> (pObject, new Date ()));
				}
			}
		}
	}
	
	public long getRunTime (Object pObject) {
		int startItemPosition = getObjectPosition (pObject, mStartTimes);
		int stopItemPosition = getObjectPosition (pObject, mStopTimes);
		long runTime =-1;
		
		if (startItemPosition >= 0) {
			long startTime = mStartTimes.get(startItemPosition).getSecond().getTime();
			long stopTime;
			if (stopItemPosition >= 0) {
				stopTime = mStopTimes.get(stopItemPosition).getSecond().getTime();
			} else {
				stopTime = new Date ().getTime();
			}
			runTime = stopTime - startTime;
		}
		
		return runTime;
	}
	
	public Date getStartTime (Object pObject) {
		int startItemPosition = getObjectPosition (pObject, mStartTimes);
		Date startTime = null;
		if (startItemPosition >=0) {
			startTime = mStartTimes.get(startItemPosition).getSecond();
		}
		return startTime;
	}
	
	public Date getStopTime (Object pObject) {
		int stopItemPosition = getObjectPosition (pObject, mStopTimes);
		Date stopTime = null;
		if (stopItemPosition >=0) {
			stopTime = mStopTimes.get(stopItemPosition).getSecond();
		}
		return stopTime;
		
	}
	
	public void removeTime (Object pObject) {
		int startItemPosition = getObjectPosition (pObject, mStartTimes);
		int stopItemPosition = getObjectPosition (pObject, mStopTimes);
		
		if (startItemPosition >= 0) {
			mStartTimes.remove(startItemPosition);
		}
		if (stopItemPosition >= 0) {
			mStopTimes.remove(stopItemPosition);
		}
	}
	
	public void resetTimer () {
		if (mStartTimes != null) mStartTimes.clear();
		if (mStopTimes != null) mStopTimes.clear();
	}
}


