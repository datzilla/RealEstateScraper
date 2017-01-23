package datamodel.propertylisting;

import java.util.ArrayList;

import utils.Pair;

public class InspectionTimes {
	private ArrayList <Pair <String, String>> mInspectionSessions;
	
	public InspectionTimes () {
		mInspectionSessions = new ArrayList <Pair<String, String>> ();
	}
	
	public InspectionTimes (String pStartTime, String pEndTime) {
		if (mInspectionSessions == null) {
			mInspectionSessions = new ArrayList <Pair<String, String>> ();
		} else {
			addSession (pStartTime, pStartTime);
		}
	}
	
	public void addSession (String pStartTime, String pEndTime) {
		if (mInspectionSessions != null) {
			mInspectionSessions.add(new Pair (pStartTime, pEndTime));
		}
	}
	
	public void removeSession (String pStartTime, String pEndTime) {
		if (mInspectionSessions != null) {
			for (Pair p : mInspectionSessions) {
				if (p.getFirst().equals(pStartTime) && p.getSecond().equals(pEndTime)) {
					mInspectionSessions.remove(p);
				}
			}
		}
	}
	
	public void clearSessions () {
		mInspectionSessions.clear();
	}
	
	public String toString () {
		if (mInspectionSessions !=null){
			ArrayList <String> tempString = new ArrayList <String> ();
			for (Pair a: mInspectionSessions) {
				tempString.add(a.getFirst() + " - " + a.getSecond());
			}
			return String.join(", ", tempString);
		} else {
			return null;
		}
	}
}
