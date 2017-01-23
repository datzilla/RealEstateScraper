package datamodel.propertylisting;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class AgencyList {
	
	private ArrayList <Agency> mAgencyList;
	private AgencyList mAgencies;
	
	public AgencyList () {		
		mAgencyList = new ArrayList<Agency>();
	}
	
	public AgencyList getInstance () {
		if (mAgencies == null) {
			mAgencies = new AgencyList ();
		}
		
		return mAgencies;
		
	}

	public boolean checkAgent (String pID) {
		boolean found = false; 
		for (Agency a : mAgencyList ) {
			if (a.getID().equalsIgnoreCase(pID)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	public void addAgent (Agency pItem) {
		mAgencyList.add(pItem);
	}
	
	public void removeAgent (Agency pItem) {
		mAgencyList.remove (pItem);
	}
	
	public void clearList () {
		mAgencyList.clear();
	}
	
//	public Agency getAgency (String pID) {
//		Agency agency = null;
//		for (Agency a : mAgencyList ) {
//			if (a.getID().equalsIgnoreCase(pID)) {
//				agency = a;
//				break;
//			}
//		}
//		return agency;
//	}
	
	public String toString () {
		if (mAgencyList !=null){
			ArrayList <String> tempString = new ArrayList <String> ();
			for (Agency a: mAgencyList) {
				tempString.add(a.toString());
			}
			return String.join(" || ", tempString);
		} else {
			return null;
		}
	}
}
