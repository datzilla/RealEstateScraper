package datamodel.propertylisting;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Agency {

	private String mID;
	private String mName;
	private String mWebSiteURL;
	private String mProfileURL;
	private String mPhone;
	private String mStreetAddress;
	private String mAddressLocality;
	private String mAddressRegion;
	private String mAddressPostCode;
	private String mFacebookURL;
	private String mTwitterURL;
	private String mLinkedInURL;
	
	public Agency (String pID) {
		mID = pID;
	}
	
	public Agency () {
		
	}
	
	public String getAgencyProfileURL() {
		return mProfileURL;
	}

	public void setAgencyProfileURL(String pProfileURL) {
		mProfileURL = pProfileURL;
	}

	public String getAgencyStreetAddress() {
		return mStreetAddress;
	}

	public void setAgencyStreetAddress(String pStreetAddress) {
		mStreetAddress = pStreetAddress;
	}

	public String getAgencyAddressLocality() {
		return mAddressLocality;
	}

	public void setAgencyAddressLocality(String pAddressLocality) {
		mAddressLocality = pAddressLocality;
	}

	public String getAgencyAddressRegion() {
		return mAddressRegion;
	}

	public void setAgencyAddressRegion(String pAddressRegion) {
		mAddressRegion = pAddressRegion;
	}

	public String getAgencyAddressPostCode() {
		return mAddressPostCode;
	}

	public void setAgencyAddressPostCode(String pAddressPostCode) {
		mAddressPostCode = pAddressPostCode;
	}

	public String getAgencyFacebookURL() {
		return mFacebookURL;
	}

	public void setAgencyFacebookURL(String pFacebookURL) {
		mFacebookURL = pFacebookURL;
	}

	public String getAgencyTwitterURL() {
		return mTwitterURL;
	}

	public void setAgencyTwitterURL(String pTwitterURL) {
		mTwitterURL = pTwitterURL;
	}

	public String getAgencyLinkedInURL() {
		return mLinkedInURL;
	}

	public void setAgencyLinkedInURL(String pLinkedInURL) {
		mLinkedInURL = pLinkedInURL;
	}

	public void setAgencyID(String pID) {
		mID = pID;
	}

	public String getAgencyName() {
		return mName;
	}

	public void setAgencyName(String pName) {
		mName = pName;
	}

	public String getAgencyWebSiteURL() {
		return mWebSiteURL;
	}

	public void setAgencyWebSiteURL(String pWebSiteURL) {
		mWebSiteURL = pWebSiteURL;
	}

	public String getPhone () {
		return mPhone;
	}

	public void setPhone (String pPhone) {
		mPhone = pPhone;
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
