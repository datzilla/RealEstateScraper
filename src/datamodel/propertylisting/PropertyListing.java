package datamodel.propertylisting;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import main.Status;


public class PropertyListing {
	private static final Logger log = Logger.getLogger( PropertyListing.class.getName() );
	private String mID;
	private String mListingURL;
	private String mListingName;
	private String mDescriptionTitle;
	private String mDescription;
	private ListingType mListingType;
	private String mFeatureStatus;
	private String mPropertyType;
	private String mSellingMethod;
	private AgentList mAgents;
	private Agency mListingAgency;
	private Date mLastSeenDate;
	private String mPriceText;
	private String mSoldDate;
	private String mFeatureType;
	private String mPropertyStreetAddress;
	private InspectionTimes mInspectionTimes;
	private String mAuctionTime;
	private String mPropertyAddressLocality;
	private String mPropertyAddressRegion;
	private String mPropertyAddressPostCode;
	//private String mPageHTML;
	private String mVirtualTourURL;
	private ArrayList<String> mThumbnailURLs;
	private String mListingVisits;
	private PropertyFeatures mPropertyFeatures;
	private String mPaymentFrequency;
	private String mAvailableDate;
		
	public PropertyListing (String pID) {
		mID = pID;
		mLastSeenDate = new Date();
	}
	
	public String getPropertyAddressPostCode() {
		return mPropertyAddressPostCode;
	}

	public void setPropertyAddressPostCode(String pPropertyAddressPostCode) {
		mPropertyAddressPostCode = pPropertyAddressPostCode;
	}

//	public ArrayList<String> getThumbnailURLs() {
//		return mThumbnailURLs;
//	}
	
	public String getThumbnailURLs() {
		if (mThumbnailURLs != null) {
			return String.join(",", mThumbnailURLs);
		}
		else {
			return null;
		}
	}
	
	public void addThumbnailURL (String pThumbnailURL) {
		if (mThumbnailURLs == null) {
			mThumbnailURLs = new ArrayList<String>();
		}
		mThumbnailURLs.add(pThumbnailURL);
	}
	
	public String getVirtualTourURL() {
		return mVirtualTourURL;
	}

	public void setVirtualTourURL(String pVirtualTourURL) {
		mVirtualTourURL = pVirtualTourURL;
	}
	
	public String getPriceText() {
		return mPriceText;
	}


	public void setPriceText(String pPriceText) {
		mPriceText = pPriceText;
	}


//	public String getPageHTML() {
//		return mPageHTML;
//	}
//
//
//	public void setPageHTML(String pPageHTML) {
//		mPageHTML = pPageHTML;
//	}

	public String getAuctionTime() {
		return mAuctionTime;
	}


	public void setAuctionTime(String pAuctionTime) {
		mAuctionTime = pAuctionTime;
	}


	public String getPropertyAddressLocality() {
		return mPropertyAddressLocality;
	}


	public void setPropertyAddressLocality(String pAddressLocality) {
		mPropertyAddressLocality = pAddressLocality;
	}


	public String getPropertyAddressRegion() {
		return mPropertyAddressRegion;
	}


	public void setPropertyAddressRegion(String pAddressRegion) {
		mPropertyAddressRegion = pAddressRegion;
	}


	public void setInspectionTimes(InspectionTimes pInspectionTimes) {
		mInspectionTimes = pInspectionTimes;
	}


	public String getDescriptionTitle() {
		return mDescriptionTitle;
	}


	public void setDescriptionTitle(String pDescriptionTitle) {
		if (mDescriptionTitle != null) {
			mDescriptionTitle = mDescriptionTitle + ", " + pDescriptionTitle;
		} else {
			mDescriptionTitle = pDescriptionTitle;
		}	
	}


	public String getDescription() {
		if (mDescription != null) {
			return mDescription;
		} else {
			return null;
		}
	}


	public void setDescription(String pDescription) {
		if (mDescription != null) {
			mDescription = mDescription + "\t" + pDescription;
		} else {
			mDescription = pDescription;
		}	
	}


//	public InspectionTimes getInspectionTimes() {
//		return mInspectionTimes;
//	}
	
	public String getInspectionTime () {
		if (mInspectionTimes !=null) {
			return mInspectionTimes.toString();
		}else {
			return null;
		}
		
	}


	public void setInspectionTimes(String pStartTime, String pEndTime) {
		if (mInspectionTimes == null) {
			mInspectionTimes = new InspectionTimes (pStartTime, pEndTime);
		} else {
			mInspectionTimes.addSession(pStartTime, pEndTime);
		}
	}
	
	public String getPropertyStreetAddress() {
		if (mPropertyStreetAddress != null) {
			return mPropertyStreetAddress;
		} else {
			return null;
		}
		
	}


	public void setPropertyStreetAddress(String pPropertyAddress) {
		mPropertyStreetAddress = pPropertyAddress;
	}


	public String getFeatureStatus() {
		return mFeatureStatus;
	}

	public String getFeatureType() {
		return mFeatureType;
	}


	public void setFeatureType(String pFeatureType) {
		mFeatureType = pFeatureType;
	}

	public void setFeatureStatus(String pFeatureStatus) {
		mFeatureStatus = pFeatureStatus;
	}


	public String getPropertyType() {
		return mPropertyType;
	}


	public void setPropertyType(String pPropertyType) {
		mPropertyType = pPropertyType;
	}


	public String getSellingMethod() {
		return mSellingMethod;
	}


	public void setSellingMethod(String pSellingMethod) {
		mSellingMethod = pSellingMethod;
	}


	public void setAgents(AgentList pAgents) {
		mAgents = pAgents;
	}


	public String getID() {
	    return mID;
	}

	public void setID(String pID) {
	    mID = pID;
	}

	public String getLastSeenDate() {
	    return mLastSeenDate.toString();
	}

	public void setLastSeenDate(Date pLastSeenDate) {
	    mLastSeenDate = pLastSeenDate;
	}

	public String getSoldDate() {
	    return mSoldDate;
	}

	public void setSoldDate(String pSoldDate) {
	    mSoldDate = pSoldDate;
	}

	public String getListingURL() {
		return mListingURL;
	}

	public void setListingURL(String pListingURL) {
		mListingURL = pListingURL;
	}

	public String getListingName() {
		return mListingName;
	}

	public void setListingName(String pListingName) {
		mListingName = pListingName;
	}

	public String getListingType() {
		return mListingType.getType();
	}

	public void setListingType(ListingType pListingType) {
		mListingType = pListingType;
	}
	
	public void clearAgents () {
		mAgents.clearList();
	}

	public String getHandlingAgents() {
		if (mAgents !=null) {
			return mAgents.toString();
		}
		else {
			return null;
		}
	}

	public String getPaymentFrequency() {
		return mPaymentFrequency;
	}

	public void setPaymentFrequency(String pPaymentFrequency) {
		mPaymentFrequency = pPaymentFrequency;
	}

	public String getAvailableDate() {
		return mAvailableDate;
	}

	public void setAvailableDate(String pAvailableDate) {
		mAvailableDate = pAvailableDate;
	}

	public void removeAgent (Agent pAgent) {
		mAgents.removeAgent(pAgent);
	}
	
	public void addAgent(Agent pAgent) {
		if (mAgents == null) {
			mAgents = new AgentList();
		}
		mAgents.addAgent(pAgent);
	}
	
	public String getListingAgency() {
		if (mListingAgency != null) {
			return mListingAgency.toString();
		}
		else {
			return null;
		}	
	}

	public void setListingAgency(Agency pListingAgency) {
		mListingAgency = pListingAgency;
		if (mAgents != null && mListingAgency != null) {
			for (Agent agent : mAgents.getAgentList()) {
				agent.setAgencyID(mListingAgency.getID());
				agent.setAgencyName(mListingAgency.getAgencyName());				
			}
		}
	}
	
	public String getListingVisits() {
		return mListingVisits;
	}

	public void setListingVisits(String pPropertyListingVisits) {
		mListingVisits = pPropertyListingVisits;
	}

//	public PropertyFeatures getPropertyFeatures() {
//		return mPropertyFeatures;
//	}
	
	public String getPropertyFeatures() {
		if (mPropertyFeatures != null) {
			return mPropertyFeatures.toString();
		} else {
			return null;
		}
	}

	public void setPropertyFeatures(PropertyFeatures pPropertyFeatures) {
		mPropertyFeatures = pPropertyFeatures;
	}
	
	public String toString () {
		String myString = "";
		for (Method m : this.getClass().getDeclaredMethods()) {
			if (m.getName().startsWith("get") && m.getReturnType().equals(String.class)) {
				try {
					String methodResult = (String) m.invoke(this);
					if (methodResult !=null) {
						if (!methodResult.equals("")) {
							if (myString.length()==0) {
								myString = m.getName().substring(3,m.getName().length()) + "=" + methodResult;
							}
							else {
								myString = myString + "\t" + m.getName().substring(3,m.getName().length()) + "=" + methodResult;
							}
						}

					}					 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return myString;
	}
	
}
