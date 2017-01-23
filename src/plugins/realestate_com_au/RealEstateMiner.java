

import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.DataMiner;
import main.Status;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.MySQLHandler;
import utils.Variable;
import datamodel.core.DataCategory;
import datamodel.geographic.Suburb;
import datamodel.propertylisting.Agency;
import datamodel.propertylisting.Agent;
import datamodel.propertylisting.ListingSearchResults;
import datamodel.propertylisting.ListingType;
import datamodel.propertylisting.PropertyFeatures;
import datamodel.propertylisting.PropertyListing;

public class RealestateMiner extends Thread implements DataMiner {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private ListingSearchResults mPropertySearch;
	private int mID;
	private static int sTotalInstances=0;
	private String mName = "www.realestate.com.au";
	
	//private DataCategory mCategory = MinerCategory.REALESTATELISTING;
	
	private String mCurrentURL;
	private int mCurrentPageNumber = 0;
	private int mFirstPageNumber = 1;
	private Document mListingResultsHTML;
	private Document mListingHTML;
	private Status mMinerStatus;
	private ListingType mListingType;
	private Connection mDBConnection;
	private MySQLHandler mMySQLHandler;
	private String mSuburbMiningID;
	private Suburb mSuburb;
	private String mListingDataID;
	private String mMinerErrorMessage;
	private String mListingErrorMessage;
	
	@SuppressWarnings("deprecation")
	public RealestateMiner(Suburb pSuburb, ListingType pListingType) throws Exception {
		sTotalInstances++;
		mID = sTotalInstances;
		initiateFields (pSuburb, pListingType);
	}
	
	public RealestateMiner(Suburb pSuburb, ListingType pListingType, int pID) throws Exception {
		sTotalInstances++;
		mID = pID;
		initiateFields (pSuburb, pListingType);
	}
	
	private void initiateFields (Suburb pSuburb, ListingType pListingType) {
		mListingType = pListingType;
		mSuburb = pSuburb;
		mPropertySearch = new ListingSearchResults(pSuburb, mListingType.getType() );
		mCurrentPageNumber = mFirstPageNumber;
		mPropertySearch.setFirstPageNumber(mFirstPageNumber);
		mCurrentURL = "http://www.realestate.com.au/" + mListingType.getType()  + "/in-"
				+ URLEncoder.encode(pSuburb.getSuburbName()) + "+"
				+ URLEncoder.encode(pSuburb.getStateCode()) + "+"
				+ URLEncoder.encode(pSuburb.getPostCode()) + "/list-"
				+ URLEncoder.encode(Integer.toString(mFirstPageNumber))
				+ "?includeSurrounding=false";
		mPropertySearch.setListingsURL(mCurrentURL);
		mPropertySearch.setBaseURL("www.realestate.com.au");
		mMinerStatus = new Status (Status.INITIATED);
	}
	
	public void run() {
		
		
		try {
			setMinerStatus(Status.STARTED);
			connectDB();
			createDBRecords();
			getPageInfo();
		}catch (Exception e) {
			setMinerStatus(Status.ERROR);
			mMinerErrorMessage = e.getMessage();
			LOGGER.severe(mMinerErrorMessage);
		}

		if (!mMinerStatus.is(Status.ERROR))
		{
			do {
				minePage();
				incrementPage();
			} while (mCurrentPageNumber <= mPropertySearch.getLastPageNumber());
		}
		
		try {
			closeDBRecords();
			closeDB();
			
			if (!mMinerStatus.is(Status.ERROR)) {
				setMinerStatus (Status.COMPELETED);	
			}
		}
		catch (Exception e) {
			setMinerStatus(Status.ERROR);
			mMinerErrorMessage = e.getMessage();
			LOGGER.severe(mMinerErrorMessage);
		}
		
	}

	public Status getMinerStatus() {
		return mMinerStatus;
	}

	public void setMinerStatus(byte pMinerStatus) {
		if (mMinerStatus != null) {
			mMinerStatus.setStatus(pMinerStatus);
			
			if (mMySQLHandler != null && mDBConnection != null) {
				try {
					mMySQLHandler.insertIntValue(mDBConnection, "suburb_mining", "mining_status", pMinerStatus, "id", mSuburbMiningID);
				} catch (Exception e) {
					LOGGER.warning(e.getMessage());
				}		
			}			
		}
	}

	public int getID() {
		return mID;
	}

	public void setID(int pID) {
		mID = pID;
	}

	public int getFirstPageNumber() {
		return mFirstPageNumber;
	}

	public void setFirstPageNumber(int pFirstPageNumber) {
		mFirstPageNumber = pFirstPageNumber;
	}
	
	public void connectDB () {
		MySQLHandler handler = MySQLHandler.getHandler();
		if (handler != null) {
			mMySQLHandler = handler;
			try {
				mDBConnection = mMySQLHandler.getConnection(false, true); 
			}
			catch (Exception e) {
				LOGGER.severe(e.getMessage() + " - Thread on");
			}
		}
	}
	
	private void closeDB ()  {
		if (mDBConnection != null) {
			try {
				mDBConnection.close();
			}
			catch (Exception e) {
				LOGGER.warning(e.getMessage());
			}
			mDBConnection = null;
		}
	}
	
	private void createDBRecords () throws Exception {
			mSuburbMiningID = mMySQLHandler.autoIDGenerateNewRow(mDBConnection, "suburb_mining", "id");
			
			//set initial data
			mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "start_time", (new Date ()).toString(), "id", mSuburbMiningID);
			mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "suburb", mSuburb.getSuburbName(), "id", mSuburbMiningID);
			mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "postcode", mSuburb.getPostCode(), "id", mSuburbMiningID);
			mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "state", mSuburb.getStateCode(), "id", mSuburbMiningID);
	}
	
	private void closeDBRecords () throws Exception {
		mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "stop_time", (new Date ()).toString(), "id", mSuburbMiningID);
		System.out.println("finished " + mSuburb.getSuburbName());
	}
	
	protected void getPageInfo() throws Exception {
		mListingResultsHTML = getDocument(mPropertySearch.getListingsURL());
		Element resultInfo = mListingResultsHTML.getElementById("resultsInfo");
		
		//suburb mining record - get first page url and html
		mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "first_page_url", mPropertySearch.getListingsURL(), "id", mSuburbMiningID);
		
		//Might infringe with their copyright laws by keeping their html. also creates alot of storage space.
		//mMySQLHandler.insertTextValue(mDBConnection, "suburb_mining", "first_page_html", mListingResultsHTML.html(), "id", mSuburbMiningID);

		if (resultInfo != null) {
			Pattern p = Pattern.compile("^.*?(\\d+)\\sof\\s(\\d+)\\s.*?$");
			Matcher m = p.matcher(resultInfo.text());
			if (m.find()) {
				mPropertySearch.setTotalListings(Integer.parseInt(m.group(2)));
				mPropertySearch.setListingPerPage(Integer.parseInt(m.group(1)));
				mPropertySearch.setTotalPages();
				mPropertySearch.setLastPageNumber();
				
				//suburb mining record - page info
				mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "listing_per_page", Integer.toString(mPropertySearch.getListingPerPage()), "id", mSuburbMiningID);
				mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "total_listings", Integer.toString(mPropertySearch.getTotalListings()), "id", mSuburbMiningID);
				mMySQLHandler.insertStringValue(mDBConnection, "suburb_mining", "total_pages", Integer.toString(mPropertySearch.getTotalPages()), "id", mSuburbMiningID);
				
				System.out.println("Mining " + mSuburb.getSuburbName() + " - total listings: " + Integer.toString(mPropertySearch.getTotalListings()) + " total pages: " + Integer.toString(mPropertySearch.getTotalPages()));
				
			} else {
				String errormessageString = "Regex can't match any page info from: " + mCurrentURL;
				LOGGER.severe(errormessageString);
				throw new Exception(errormessageString);
			}
		} else {
			String errormessageString = "Can't read page info for URL: " + mCurrentURL;
			LOGGER.severe(errormessageString);
			throw new Exception(errormessageString);
		}
	}

	private Document getDocument(String pURL) throws Exception {
		return Jsoup.connect(pURL).get();
	}
	
	protected void minePage() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			LOGGER.warning(e.getMessage());
		}
		//Elements totalItemsFound = mPageSourceCode.getElementsByClass("vCard");
		Elements propertySearchResults = mListingResultsHTML.select(".resultBody");

		for (Element searchResult : propertySearchResults) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				LOGGER.warning(e.getMessage());
			}
			
			//Create a new Property Listing for every search result found
			// --------------------------------------------
			// Mine the return results from the search page
			// --------------------------------------------
			PropertyListing propertyListing = new PropertyListing(searchResult.attr("id"));
			propertyListing.setListingType(mListingType);
			propertyListing.setFeatureStatus(searchResult.attr("data-featured-status"));
			propertyListing.setFeatureType(searchResult.attr("data-content-type"));
			
			for (Element listingPreviewInfo : searchResult.select(".name,.type,.pricetext,.propertyType")) {
				if (listingPreviewInfo.className().startsWith("name")) {
					propertyListing.setListingURL(listingPreviewInfo.absUrl("href"));
					propertyListing.setListingName(listingPreviewInfo.text());
				} else if (listingPreviewInfo.className().startsWith("type")) {
					propertyListing.setSellingMethod(listingPreviewInfo.text());
				} else if (listingPreviewInfo.className().startsWith("pricetext")) {
					propertyListing.setPriceText(listingPreviewInfo.text());
				} else if (listingPreviewInfo.className().startsWith("propertyType")) {
					propertyListing.setPropertyType(listingPreviewInfo.text());
				}
			}
			
			//-----------------------------------------------------
			// Mine every property listing from the search results
			//-----------------------------------------------------		
			mCurrentURL = propertyListing.getListingURL();
			mListingHTML = null;
			try {
				mListingHTML = getDocument(mCurrentURL);
				
				//listing mining record
				mListingDataID = mMySQLHandler.autoIDGenerateNewRow(mDBConnection, "listing_data", "row_id");
				mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.INITIATED, "row_id", mListingDataID);
				mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "start_time", (new Date()).toString(), "row_id", mListingDataID);
				mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "suburb_mining_id", mSuburbMiningID, "row_id", mListingDataID);
				mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "listingURL", mCurrentURL, "row_id", mListingDataID);
				//mMySQLHandler.insertTextValue(mDBConnection, "listing_data", "listing_html", mListingHTML.html(), "row_id", mListingDataID);
			}
			catch (Exception e) {
				
				mListingErrorMessage = e.getMessage() + " - URL: " + mCurrentURL;

				
				if (e.getMessage().equalsIgnoreCase("connect timed out")) {
					try {
						Thread.sleep(5000);
						mListingHTML = getDocument(mCurrentURL);
						
						//listing mining record
						mListingDataID = mMySQLHandler.autoIDGenerateNewRow(mDBConnection, "listing_data", "row_id");
						mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.INITIATED, "row_id", mListingDataID);
						mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "start_time", (new Date()).toString(), "row_id", mListingDataID);
						mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "suburb_mining_id", mSuburbMiningID, "row_id", mListingDataID);
						mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "listingURL", mCurrentURL, "row_id", mListingDataID);
					}catch (Exception e1) {
						LOGGER.warning(e1.getMessage());
						return;
					}
				}
				else {
					LOGGER.warning(mListingErrorMessage);
					//error in reading the html for a single listing
					try {
						mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.ERROR, "row_id", mListingDataID);
						mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "stop_time", (new Date()).toString(), "row_id", mListingDataID);
						mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "error_message", mListingErrorMessage, "row_id", mListingDataID);
					} catch (Exception em) {
						LOGGER.warning(em.getMessage());
					}
					
					//move on to the next listing
					return;
				}
			}

			if (mListingHTML != null) {			
				try {
					Elements pageSections = mListingHTML.select (
							"div#baseInfo,"
							+ "div#photoViewer,"					
							+ "div#agentInfoExpanded,"
							+ "div#listing_info_secondary,"
							+ "div#description,"
							+ "div#propertyLinks,"
							+ "div#features");
					for (Element pageSection : pageSections) {
						if (pageSection.id().equalsIgnoreCase("baseinfo")) {
							// ----------------------------------------------------------
							// property address, price, auction details, virtual tour URL
							// -----------------------------------------------------------
							for (Element propBasicInfo : pageSection.select("span")) {
								if (propBasicInfo.attr("itemprop").equalsIgnoreCase("streetAddress")) {
									propertyListing.setPropertyStreetAddress(propBasicInfo.text());
								}
								else if (propBasicInfo.attr("itemprop").equalsIgnoreCase("addressLocality")) {
									propertyListing.setPropertyAddressLocality(propBasicInfo.text());
								}
								else if (propBasicInfo.attr("itemprop").equalsIgnoreCase("addressRegion")) {
									propertyListing.setPropertyAddressRegion(propBasicInfo.text());
								}
								else if (propBasicInfo.attr("itemprop").equalsIgnoreCase("postalCode")) {
									propertyListing.setPropertyAddressPostCode(propBasicInfo.text());
								}
								else if (propBasicInfo.attr("class").equalsIgnoreCase("propertyType")) {
									propertyListing.setPropertyType(propBasicInfo.text());
								}
								else if (propBasicInfo.attr("class").equalsIgnoreCase("priceText")) {
									//Price or Auction
									propertyListing.setPriceText(propBasicInfo.text());
								}
								
							}
							
							for (Element propBasicInfo : pageSection.select("div.auction_details,div.feature_links")){
								if (propBasicInfo.attr("class").equalsIgnoreCase("auction_details")) {
									//Auction start time
									for (Element metaData : propBasicInfo.select("meta")) {
										if (metaData.attr("itemprop").equalsIgnoreCase("startDate")){
											propertyListing.setAuctionTime(metaData.attr("content"));
										}
									}
									
								}
								else if (propBasicInfo.attr("class").equalsIgnoreCase("feature_links")) {
									// Virtual Tour Video URL
									propertyListing.setVirtualTourURL(propBasicInfo.getElementsByTag("a").first().absUrl("href"));
								}
							}					
						} 
						else if (pageSection.id().equalsIgnoreCase("listing_info_secondary")) {
							//------------------------------
							// For sold property, Rent and 
							// ------------------------------
							for (Element secondaryInfo : pageSection.select("div.sold_date,div.available_date")) {
								if (secondaryInfo.className().equalsIgnoreCase("sold_date")) {
									try {
										propertyListing.setSoldDate(grepFirstString("^Sold Date:\\s(.*)$", secondaryInfo.text()));
									}catch (Exception e) {
										mListingErrorMessage = "Error getting text from sold date - " + e.getMessage();
										LOGGER.warning(mListingErrorMessage);
										mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.ERROR, "row_id", mListingDataID);
										mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "error_message", mListingErrorMessage, "row_id", mListingDataID);
									}
								}
								else if (secondaryInfo.className().equalsIgnoreCase("available_date")) {
									try {
										propertyListing.setAvailableDate(grepFirstString("^Date Available:\\s(.*)$", secondaryInfo.text()));
									}catch (Exception e) {
										mListingErrorMessage = "Error getting text from sold date - " + e.getMessage();
										LOGGER.warning(mListingErrorMessage);
										mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.ERROR, "row_id", mListingDataID);
										mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "error_message", mListingErrorMessage, "row_id", mListingDataID);
									}
								}
							}
						}
						
						else if (pageSection.id().equalsIgnoreCase("photoviewer")) {
							//-----------------------------
							// Property photo thumbnail(s)
							//-----------------------------
							for (Element thumbnail : pageSection.select("img")) {
								if (thumbnail.className().startsWith("thumb")) {
									propertyListing.addThumbnailURL(thumbnail.absUrl("src"));
								}
							}
						}
						else if (pageSection.id().equalsIgnoreCase("agentInfoExpanded")) {
							//-----------------------
							// Agent & Agency Info
							//-----------------------
							for (Element agentInfo : pageSection.select(".agentContactInfo,.agencyDetails")) {
								if (agentInfo.className().equalsIgnoreCase("agentContactInfo")) {
									// Create a new Agent
									Agent agentSmith = new Agent ();
									
									//Agent Information
									for (Element agentContact : agentInfo.select(".agentName,.phone,"
											+ ".agentSocialProfile.agentFacebookProfile,"
											+ ".agentSocialProfile.agentTwitterProfile,"
											+ ".agentSocialProfile.agentLinkedInProfile,"
											+ ".agentProfile")) {
																												
										//Agent Name
										if (agentContact.className().equalsIgnoreCase("agentName")) {
											agentSmith.setName(agentContact.text());
										} 
										
										//Agent Phone
										else if (agentContact.className().equalsIgnoreCase("phone")) {
											agentSmith.setPhone(agentContact.text());
										}
										
										//Agent Facebook URL
										else if (agentContact.className().equalsIgnoreCase("agentSocialProfile agentFacebookProfile")) {
											agentSmith.setFacebook(agentContact.select("a").first().absUrl("href"));
										}
										
										//Agent Twitter Profile
										else if (agentContact.className().equalsIgnoreCase("agentSocialProfile agentTwitterProfile")) {
											agentSmith.setTwitter(agentContact.select("a").first().absUrl("href"));
										}
										
										//Agent LinkedInProfile
										else if (agentContact.className().equalsIgnoreCase("agentSocialProfile agentLinkedInProfile")) {
											agentSmith.setLinkedin(agentContact.select("a").first().absUrl("href"));
										}
										
										//Agent Profile Page
										else if (agentContact.className().equalsIgnoreCase("agentProfile")) {
											agentSmith.setProfileURL(agentContact.select("a").first().absUrl("href"));
											Pattern idPattern = Pattern.compile("^.*?(\\d+)$",Pattern.CASE_INSENSITIVE);
											Matcher idGrep = idPattern.matcher(agentContact.select("a").first().absUrl("href"));
											if (idGrep.find()) {
												agentSmith.setID(idGrep.group(1));
											}
										}
									}
									// add conditions later
									propertyListing.addAgent(agentSmith);
								}
								// Agency Information
								else if (agentInfo.className().equalsIgnoreCase("agencyDetails")) {
									Agency repAgency = new Agency ();
									
									repAgency.setAgencyName(agentInfo.getElementsByClass("agencyName").first().text());
									repAgency.setAgencyProfileURL(agentInfo.select("a").first().absUrl("href"));
									if (repAgency.getAgencyProfileURL()!= null) {
										repAgency.setAgencyID(grepFirstString("^.*?by\\-([a-z0-9]+)/\\S+$", repAgency.getAgencyProfileURL()));
									}
									repAgency.setAgencyStreetAddress(agentInfo.getElementsByClass("street-address").first().text());
									repAgency.setAgencyAddressLocality(agentInfo.getElementsByClass("locality").first().text());
									repAgency.setAgencyAddressRegion(agentInfo.getElementsByClass("region").first().text());
									repAgency.setAgencyAddressPostCode(agentInfo.getElementsByClass("postal-code").first().text());
																
									//Add to Property List
									propertyListing.setListingAgency(repAgency);
								}
							}

						}
						
						else if (pageSection.id().equalsIgnoreCase("description")) {
							//----------------------------------------
							//Property inspection times & Description
							//----------------------------------------
							for (Element it : pageSection.select("a")) {
								Elements sessionTimes = it.select("meta");
								if (sessionTimes.size()==2)
								if (sessionTimes.get(0).attr("itemprop").equalsIgnoreCase("startDate") &&
									sessionTimes.get(1).attr("itemprop").equalsIgnoreCase("endDate")) {
									propertyListing.setInspectionTimes(sessionTimes.get(0).attr("content"), sessionTimes.get(1).attr("content"));
								}
							}
							
							//Description
							propertyListing.setDescriptionTitle(pageSection.select(".title").text());
							propertyListing.setDescription(pageSection.select(".body").text());
						}
						else if (pageSection.id().equalsIgnoreCase("propertylinks")) {
							//----------------------------------------
							//Property List Page visit stats
							//----------------------------------------
							for (Element para : pageSection.select("p")) {
								if (para.text().startsWith("Page Visits")) {
									propertyListing.setListingVisits(grepFirstString ("^.*?(\\d+)$", para.text()));
								}
							}

						}
						else if (pageSection.id().equalsIgnoreCase("features")) {
							//------------------------------
							//Property feature information
							//------------------------------
							PropertyFeatures propertyFeatures = new PropertyFeatures();
							for (Element k : pageSection.select("li")) {
								String featureText = k.text().toLowerCase();
								if (featureText.startsWith("bedrooms:")) {
									propertyFeatures.setBedrooms(grepFirstString ("^.*?(\\d+)$", k.text()));
								}
								else if (featureText.startsWith("bathrooms:")) {
									propertyFeatures.setBathrooms(grepFirstString ("^.*?(\\d+)$", k.text()));
								}
								else if (featureText.startsWith("ensuite:")) {
									propertyFeatures.setEnsuite(true);
									propertyFeatures.setEnsuiteRooms(grepFirstString ("^.*?(\\d+)$", k.text()));
								}
								else if (featureText.startsWith("garage spaces:")) {
									propertyFeatures.setGarageSpaces(grepFirstString ("^.*?(\\d+)$", k.text()));
								}
								else if (featureText.startsWith("carport spaces:")) {
									propertyFeatures.setGarageSpaces(grepFirstString ("^.*?(\\d+)$", k.text()));
								}
								else if (featureText.startsWith("living areas:")) {
									propertyFeatures.setLivingAreas(grepFirstString ("^.*?(\\d+)$", k.text()));
								}
								else if (featureText.startsWith("toilets:")) {
									propertyFeatures.setGarageSpaces(grepFirstString ("^.*?(\\d+)$", k.text()));
								}
								else if (featureText.startsWith("land size:")) {
									propertyFeatures.setLandSize(grepFirstString ("^.*?(\\d+.*)$", k.text()));
								}
								else if (featureText.startsWith("building size:")) {
									propertyFeatures.setBuildingSize(grepFirstString ("^.*?(\\d+.*)$", k.text()));
								}
								else if (featureText.startsWith("ERR ")) {
									propertyFeatures.setERR( grepFirstString ("^.*?(\\d+).*$", k.text()));
								}
								
								// These could be on one line
								if (featureText.contains("close to schools")) {
									propertyFeatures.setCloseToSchools(true);
								}
								if (featureText.contains("alarm system")) {
									propertyFeatures.setAlarmSystem(true);
								}
								if (featureText.contains("air conditioning")) {
									propertyFeatures.setAirconditioning(true);
								}
								if (featureText.contains("outdoor entertaining area")) {
									propertyFeatures.setOutdoorLiving(true);
								}
								if (featureText.contains("outdoor living")) {
									propertyFeatures.setOutdoorLiving(true);
								}
								if (featureText.contains("built-in wardrobes")) {
									propertyFeatures.setBuiltinWardrobes(true);
								}
								if (featureText.contains("deck")) {
									propertyFeatures.setDeck(true);
								}
								if (featureText.contains("open car spaces")) {
									propertyFeatures.setOpenCarSpaces(true);
								}
								if (featureText.contains("pay tv access")) {
									propertyFeatures.setPayTV(true);
								}
								if (featureText.contains("hydronic heating")) {
									propertyFeatures.setHydronicHeating(true);
								}
								if (featureText.contains("evaporative cooling")) {
									propertyFeatures.setEvaporativeCooling(true);
								}
								if (featureText.contains("gas heating")) {
									propertyFeatures.setGasHeating(true);
								}
								if (featureText.contains("washing machine")) {
									propertyFeatures.setWashingMachine(true);
								}
								if (featureText.contains("water tank")) {
									propertyFeatures.setWaterTank(true);
								}
								if (featureText.contains("grey water system")) {
									propertyFeatures.setGreyWaterSystem(true);
								}
								if (featureText.contains("split-system")) {
									propertyFeatures.setSplitSystem (true);
								}
								if (featureText.contains("indoor spa")) {
									propertyFeatures.setIndoorSpa(true);
								}
								if (featureText.contains("outdoor spa")) {
									propertyFeatures.setOutsideSpa(true);
								}
								if (featureText.contains("floorboards")) {
									propertyFeatures.setFloorboards(true);
								}
								if (featureText.contains("gym")) {
									propertyFeatures.setGym(true);
								}
								if (featureText.contains("workshop")) {
									propertyFeatures.setWorkshop(true);
								}
								if (featureText.contains("broadband internet")) {
									propertyFeatures.setBroadbandInternet(true);
								}
								if (featureText.contains("fully fenced")) {
									propertyFeatures.setFullyFenced(true);
								}
								if (featureText.contains("secured parking")) {
									propertyFeatures.setSecureParking(true);
								}
								if (featureText.contains("swimming pool - inground")) {
									propertyFeatures.setSwimmingPoolUnder(true);
								}
								if (featureText.contains("close to transport")) {
									propertyFeatures.setCloseToTransport(true);
								}
								if (featureText.contains("close to shops")) {
									propertyFeatures.setCloseToShops(true);
								}
								if (featureText.contains("outdoor living")) {
									propertyFeatures.setOutdoorLiving(true);
								}
								if (featureText.contains("shed")) {
									propertyFeatures.setShed(true);
								}
								if (featureText.contains("study")) {
									propertyFeatures.setStudyRoom(true);
								}
								if (featureText.contains("garden")) {
									propertyFeatures.setGarden(true);
								}
								if (featureText.contains("intercom")) {
									propertyFeatures.setIntercom(true);
								}
								if (featureText.contains("ducted")) {
									propertyFeatures.setDuctedSystems(true);
								}
								if (featureText.contains("rumpus")) {
									propertyFeatures.setRumpusRoom(true);
								}
								if (featureText.contains("dishwasher")) {
									propertyFeatures.setDishWasher(true);
								}
								if (featureText.contains("solar hot water")) {
									propertyFeatures.setSolarHotWater(true);
								}
								if (featureText.contains("solar panels")) {
									propertyFeatures.setSolarPanels(true);
								}
								
							}
							propertyListing.setPropertyFeatures(propertyFeatures);
						}
					}					
				}catch (Exception e) {
					mListingErrorMessage = e.getMessage() + " - URL: " + mCurrentURL;
					LOGGER.warning(mListingErrorMessage);
					try {
						mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.ERROR, "row_id", mListingDataID);
						mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "error_message", mListingErrorMessage, "row_id", mListingDataID);
					}
					catch (Exception em){
						LOGGER.warning(em.getMessage());
					}
				
				}
				
				// change it to SQL
				if (mListingDataID != null) {
					try {
						listingDataToSQL (propertyListing);
						mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.COMPELETED, "row_id", mListingDataID);
						mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "stop_time", (new Date()).toString(), "row_id", mListingDataID);
					}catch (Exception e) {
						mListingErrorMessage = e.getMessage() + " - URL: " + mCurrentURL;
						LOGGER.warning(mListingErrorMessage);
						try {
							mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.ERROR, "row_id", mListingDataID);
							mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "error_message", mListingErrorMessage, "row_id", mListingDataID);
						}
						catch (Exception em){
							LOGGER.warning(em.getMessage());
						}
						return;
					}
				}
			}
			else {
				mListingErrorMessage = "Can not retreive HTML from : " + mCurrentURL;
				LOGGER.warning(mListingErrorMessage);
				try {
					mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.ERROR, "row_id", mListingDataID);
					mMySQLHandler.insertStringValue(mDBConnection, "listing_data", "error_message", mListingErrorMessage, "row_id", mListingDataID);
				}
				catch (Exception em){
					LOGGER.warning(em.getMessage());
				}
				return;
			}
		
		}
	}
	
	private void listingDataToSQL (PropertyListing pPropertyListing) {
		for (Method m : pPropertyListing.getClass().getDeclaredMethods()) {
			if (m.getName().startsWith("get") && m.getReturnType().equals(String.class)) {
				try {
					String methodResult = (String) m.invoke(pPropertyListing);
					if (methodResult != null) {
						if (methodResult.length() > 0) {
							String columnName = m.getName().substring(3, m.getName().length());
							mMySQLHandler.insertStringValue(mDBConnection, "listing_data", columnName, methodResult, "row_id", mListingDataID);
						}
					}	
				} catch (Exception e) {
					LOGGER.warning(m.getName() + " " + e.getMessage());
					LOGGER.warning(e.getMessage());
					try {
						mMySQLHandler.insertByteValue(mDBConnection, "listing_data", "mining_status", Status.ERROR, "row_id", mListingDataID);
					}catch (Exception e1) {
						LOGGER.warning(e1.getMessage());
					}
				}
			}

		}

	}
	
	private String grepFirstString (String pRegexPattern, String pSearchString) {
		String matchedString = null;
		Pattern tempPattern = Pattern.compile(pRegexPattern);
		Matcher tempMatcher = tempPattern.matcher(pSearchString);
		if (tempMatcher.find()) {
			matchedString = tempMatcher.group(1);
		}
		return matchedString;
	}

	protected void incrementPage() {
		mCurrentPageNumber++;
		mCurrentURL = "http://www.realestate.com.au/"
				+ mPropertySearch.getListCategory()
				+ "/in-"
				+ URLEncoder
						.encode(mPropertySearch.getSuburb().getSuburbName())
				+ "+"
				+ URLEncoder.encode(mPropertySearch.getSuburb().getStateCode())
				+ "+"
				+ URLEncoder.encode(mPropertySearch.getSuburb().getPostCode())
				+ "/list-"
				+ URLEncoder.encode(Integer.toString(mCurrentPageNumber))
				+ "?includeSurrounding=false";
	}	
}
