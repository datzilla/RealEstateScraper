package datamodel.geographic;

import java.sql.ResultSet;

public class Suburb {
	
	private String mSuburb;
	private String mPostCode;
	private String mStateName;
	private String mStateCode;
	private String mCountryName;
	private String mCountryCode;
	private String mCouncilRegion;
	private String mLatitude;
	private String mLongitude;
	private String mSQLTable;
		
	public Suburb (String pSuburb, String pPostcode, String pStateCode, String pCountryName) {
		mSuburb = pSuburb;
		mPostCode = pPostcode;
		mStateCode = pStateCode;
		mCountryName = pCountryName;
	}
		
	public Suburb (String pSuburb, String pPostcode, String pStateCode) {
		mSuburb = pSuburb;
		mPostCode = pPostcode;
		mStateCode = pStateCode;
		//mCountryName = pCountryName;
	}
	
	public String getStateName() {
		return mStateName;
	}

	public void setStateName(String pStateName) {
		mStateName = pStateName;
	}

	public String getStateCode() {
		return mStateCode;
	}

	public void setStateCode(String pStateCode) {
		mStateCode = pStateCode;
	}

	public String getCountryName() {
		return mCountryName;
	}

	public void setCountryName(String pCountryName) {
		mCountryName = pCountryName;
	}

	public String getCountryCode() {
		return mCountryCode;
	}

	public void setCountryCode(String pCountryCode) {
		mCountryCode = pCountryCode;
	}

	public void setSuburbName(String pSuburb) {
		mSuburb = pSuburb;
	}

	public void setPostCode(String pPostCode) {
		mPostCode = pPostCode;
	}

	public String getSuburbName() {
		return mSuburb;
	}

	public String getPostCode() {
		return mPostCode;
	}
	
	public static Suburb translateResultSet (ResultSet pResultSet, int pRowID) throws Exception{
		pResultSet.absolute(pRowID);
		String postcode = pResultSet.getString(1);
		String suburb = pResultSet.getString(2);
		String stateabbrv = pResultSet.getString(3);
//		pResultSet.close();
//		pResultSet = null;
		return new Suburb (suburb, postcode, stateabbrv);
	}
}
