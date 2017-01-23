package datamodel.propertylisting;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Agent {
	private String mID;
	private String mName;
	private String mPosition;
	private String mProfileURL;
	private String mPhone;
	private String mTwitter;
	private String mFacebook;
	private String mLinkedin;
	private String mEmail;
	private String mDescription;
	private String mAgencyID;
	private String mAgencyName;
	
	public Agent () {
		
	}
	
	public Agent (String pID) {
		mID = pID;
	}

	public void setID(String pID) {
		mID = pID;
	}

	public String getAgencyName() {
		return mAgencyName;
	}

	public void setAgencyName(String pAgencyName) {
		mAgencyName = pAgencyName;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public String getPosition() {
		return mPosition;
	}

	public void setPosition(String pPosition) {
		mPosition = pPosition;
	}

	public String getProfileURL() {
		return mProfileURL;
	}

	public void setProfileURL(String pProfileURL) {
		mProfileURL = pProfileURL;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String pMobile) {
		mPhone = pMobile;
	}

	public String getTwitter() {
		return mTwitter;
	}

	public void setTwitter(String pTwitter) {
		mTwitter = pTwitter;
	}

	public String getFacebook() {
		return mFacebook;
	}

	public void setFacebook(String pFacebook) {
		mFacebook = pFacebook;
	}

	public String getLinkedin() {
		return mLinkedin;
	}

	public void setLinkedin(String pLinkedin) {
		mLinkedin = pLinkedin;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String pEmail) {
		mEmail = pEmail;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String pDescription) {
		mDescription = pDescription;
	}

	public String getAgencyID () {
		return mAgencyID;
	}

	public void setAgencyID (String pAgencyID) {
		mAgencyID = pAgencyID;
	}

	public String getID() {
		return mID;
	}
	
	public String toString () {
		String myString = "";
		for (Field m : this.getClass().getDeclaredFields()) {

			if (m.getType().equals(String.class)) {
				try {
					if (m.get(this) != null) {
						if (myString.length()==0) {

							myString = m.getName().substring(1,m.getName().length()) + ":" + m.get(this);

						}
						else {
							myString = myString + ", " + m.getName().substring(1,m.getName().length()) + ":" + m.get(this);
						}
					}
				}
				catch (Exception e){
					//do not need to print anything
				}
			}
		}
		
		return myString;
	}
}
