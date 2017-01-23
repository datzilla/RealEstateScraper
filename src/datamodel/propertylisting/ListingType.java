package datamodel.propertylisting;

public class ListingType {
	public static final String BUY = "buy";
	public static final String SOLD = "sold";
	public static final String LEASE = "rent";
	public static final String INVEST = "invest";
	public static final String BUSINESS = "business";
	public static final String AGENT = "agent";
	public static final String AGENCY = "find-agent";
	public static final String SHARE = "share";
	
	public String mType;
	
	public ListingType (String pType) {
		mType = pType;
	}

	public String getType() {
		return mType;
	}

	public void setType(String pType) {
		mType = pType;
	}
	
}
