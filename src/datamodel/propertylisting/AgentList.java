package datamodel.propertylisting;

import java.util.ArrayList;

public class AgentList {
	private ArrayList <Agent> mAgentList;
	private AgentList mAgents;
	
	public AgentList () {		
		mAgentList = new ArrayList<Agent>();
	}
	
	public ArrayList<Agent> getAgentList() {
		return mAgentList;
	}

	public AgentList getInstance () {
		if (mAgents == null) {
			mAgents = new AgentList ();
		}
		
		return mAgents;
		
	}

	public boolean checkAgent (String pID) {
		boolean found = false; 
		for (Agent a : mAgentList ) {
			if (a.getID().equalsIgnoreCase(pID)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	public void addAgent (Agent pItem) {
		mAgentList.add(pItem);
	}
	
	public void removeAgent (Agent pItem) {
		mAgentList.remove (pItem);
	}
	
	public void clearList () {
		mAgentList.clear();
	}
	
	public Agent getAgent (String pID) {
		Agent agent = null;
		for (Agent a : mAgentList ) {
			if (a.getID().equalsIgnoreCase(pID)) {
				agent = a;
				break;
			}
		}
		return agent;
	}
	
	public String toString () {
		if (mAgentList !=null){
			ArrayList <String> tempString = new ArrayList <String> ();
			for (Agent a: mAgentList) {
				tempString.add(a.toString());
			}
			String returnString = String.join(" || ", tempString);
			tempString.clear();
			tempString = null;
			return returnString;
		} else {
			return null;
		}
	}
}
