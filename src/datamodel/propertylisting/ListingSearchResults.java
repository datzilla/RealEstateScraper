package datamodel.propertylisting;

import java.util.Date;

import datamodel.geographic.Suburb;

public class ListingSearchResults {
    private Date mTodayDate; 
    private Suburb mSuburb;
    private int mTotalListings = 0;
    private int mTotalPages = 0;
    private int mListingPerPage = 0;
    private int mFirstPageNumber = 0;
    private int mLastPageNumber = 0;
    private String mListingsURL;
    private String mBaseURL;
    private String mListingCategory;
    
    
    public ListingSearchResults (Suburb pSuburb, String pCategory) {
	mTodayDate = new Date ();
	mSuburb = pSuburb;
	mListingCategory = pCategory;
    }

    public int getFirstPageNumber() {
        return mFirstPageNumber;
    }

    public void setFirstPageNumber(int pFirstPageNumber) {
        mFirstPageNumber = pFirstPageNumber;
    }

    public int getLastPageNumber() {
        return mLastPageNumber;
    }

    public void setLastPageNumber(int pLastPageNumber) {
        mLastPageNumber = pLastPageNumber;
    }
    
    public void setLastPageNumber() {
        if (mTotalPages != 0) {
            mLastPageNumber = mTotalPages;
        } else if (mTotalListings != 0 && mListingPerPage !=0) {
            if (mTotalListings% mListingPerPage != 0) {
        	mLastPageNumber = Math.round( mTotalListings / mListingPerPage ) + 1;
            } else {
        	mLastPageNumber = Math.round( mTotalListings / mListingPerPage );
            }
	}
    }

    public String getListingCategory() {
        return mListingCategory;
    }

    public void setListingCategory(String pListingCategory) {
        mListingCategory = pListingCategory;
    }

    public Date getTodayDate() {
        return mTodayDate;
    }

    public void setTodayDate(Date pTodayDate) {
        mTodayDate = pTodayDate;
    }

    public Suburb getSuburb() {
        return mSuburb;
    }

    public void setSuburb(Suburb pSuburb) {
        mSuburb = pSuburb;
    }

    public int getTotalListings () {
        return mTotalListings;
    }

    public void setTotalListings (int pTotalResult) {
        mTotalListings = pTotalResult;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int pTotalPages) {
        mTotalPages = pTotalPages;
    }
    
    public void setTotalPages () {
	if (mTotalListings != 0 && mListingPerPage !=0) {
            if (mTotalListings% mListingPerPage != 0) {
        	mTotalPages = Math.round( mTotalListings / mListingPerPage ) + 1;
            } else {
        	mTotalPages = Math.round( mTotalListings / mListingPerPage );
            }
	}
    }

    public int getListingPerPage() {
        return mListingPerPage;
    }

    public void setListingPerPage(int pListingPerPage) {
        mListingPerPage = pListingPerPage;
    }

    public String getListingsURL() {
        return mListingsURL;
    }

    public void setListingsURL(String pListingsURL) {
        mListingsURL = pListingsURL;
    }

    public String getBaseURL() {
        return mBaseURL;
    }

    public void setBaseURL(String pBaseURL) {
        mBaseURL = pBaseURL;
    }

    public String getListCategory() {
        return mListingCategory;
    }

    public void setListCategory(String pListCategory) {
	mListingCategory = pListCategory;
    }
}
