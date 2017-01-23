package main;

import java.util.ArrayList;

import utils.Pair;

public class Progress <V> {
	
	private ArrayList <Pair <V, Status>> mProgress;
	private int mTotal;
	
	public Progress (int pTotal) {
		mTotal = pTotal;
	}
	
	public int getTotal() {
		return mTotal;
	}

	public void setTotal(int pTotal) {
		mTotal = pTotal;
	}

	public void addValue (V pValue, Status pStatus) {
		
	}
	
	public void setCompleted (V pValue) {
		
	}
	
	public int getProgress () {
		
		int totalCompleted = 0;
		// mProgress is not null;
		if (mProgress != null) {
			for (int i = 0; i < mProgress.size(); i++) {
				if (mProgress.get(i).getSecond().is(Status.COMPELETED)) {
					totalCompleted++;
				}
			}
		}
		//cater for mTotal = 0; this will cause error on division.
		if (totalCompleted==0 || mTotal!=0) {
			return 0;
		} else {
			return Math.round((totalCompleted/mTotal));
		}
	}
	
	public void setValueStatus (V pValue, Status pStatus) {
		if (mProgress != null) {
			for (int i = 0; i < mProgress.size(); i++) {
				if (mProgress.get(i).getFirst().equals(pValue)) {
					mProgress.get(i).setSecond(pStatus);
					break;
				}
			}
		}
	}
}
