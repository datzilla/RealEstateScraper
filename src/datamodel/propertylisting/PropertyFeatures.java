package datamodel.propertylisting;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyFeatures {
    private String mBedrooms=null;
    private String mBathrooms=null;
    private String mGarageSpaces=null;
    private String mCarportSpaces=null;
    private String mToilets=null;
    private String mPropertyType=null;
    private String mBuildingSize=null;
    private String mLandSize=null;
    private String mEnsuiteRooms=null;
    private String mLivingAreas=null;
    private String mERR=null;
    
    private boolean mRumpusRoom=false;
    private boolean mDuctedSystems=false;
    private boolean mRemoteGarage=false;
    private boolean mCloseToSchools=false;
    private boolean mCloseToTransport=false;
    private boolean mCloseToShops=false;
    private boolean mOutdoorLiving=false;
    private boolean mShed=false;
    private boolean mAirconditioning=false;
    private boolean mBuiltinWardrobes=false;
    private boolean mSecureParking=false;
    private boolean mSolarPanels=false;
    private boolean mSolarHotWater=false;
    private boolean mWaterTank=false;
    private boolean mGreyWaterSystem=false;
    private boolean mHighEnergyEfficiency=false;
    private boolean mMediumEnergyEfficiency=false;
    private boolean mLowEnergyEfficiency=false;
    private boolean mOpenCarSpaces=false;
    private boolean mSwimmingPoolAbove=false;
    private boolean mSwimmingPoolUnder=false;
    private boolean mSwimmingPool=false;
    private boolean mOutsideSpa=false;
    private boolean mTennisCourt=false;
    private boolean mBalcony=false;
    private boolean mDeck=false;
    private boolean mCourtyard=false;
    private boolean mFullyFenced=false;
    private boolean mAlarmSystem=false;
    private boolean mStudyRoom=false;
    private boolean mEnsuite=false;
    private boolean mIntercom=false;
    private boolean mGarden=false;
    private boolean mDishWasher=false;
    private boolean mSplitSystem=false;
    private boolean mFloorboards=false;
    private boolean mGym=false;
    private boolean mIndoorSpa=false;
    private boolean mWorkshop=false;
    private boolean mBroadbandInternet=false;
    private boolean mPayTV=false;
    private boolean mGasHeating=false;
    private boolean mEvaporativeCooling=false;
    private boolean mHydronicHeating=false;
    private boolean mWashingMachine=false;
    private boolean mSharedLaundry=false;
    private boolean mPrivateLaundry=false;
    
    public PropertyFeatures () {
	
    }

	public String getLivingAreas() {
		return mLivingAreas;
	}

	public boolean isGym() {
		return mGym;
	}

	public void setGym(boolean pGym) {
		mGym = pGym;
	}

	public boolean isFloorboards() {
		return mFloorboards;
	}

	public void setFloorboards(boolean pFloorboards) {
		mFloorboards = pFloorboards;
	}

	public void setLivingAreas(String pLivingAreas) {
		mLivingAreas = pLivingAreas;
	}

	public String getERR() {
		return mERR;
	}

	public void setERR(String pERR) {
		mERR = pERR;
	}

	public String getEnsuiteRooms() {
		return mEnsuiteRooms;
	}

	public void setEnsuiteRooms(String pEnsuiteRooms) {
		mEnsuiteRooms = pEnsuiteRooms;
	}

	public boolean isPayTV() {
		return mPayTV;
	}

	public void setPayTV(boolean pPayTV) {
		mPayTV = pPayTV;
	}

	public boolean isDuctedSystems() {
		return mDuctedSystems;
	}

	public void setDuctedSystems(boolean pDuctedSystems) {
		mDuctedSystems = pDuctedSystems;
	}

	public String getBedrooms() {
		return mBedrooms;
	}

	public void setBedrooms(String pBedrooms) {
		mBedrooms = pBedrooms;
	}

	public String getBathrooms() {
		return mBathrooms;
	}

	public boolean isWashingMachine() {
		return mWashingMachine;
	}

	public void setWashingMachine(boolean pWashingMachine) {
		mWashingMachine = pWashingMachine;
	}

	public boolean isSharedLaundry() {
		return mSharedLaundry;
	}

	public void setSharedLaundry(boolean pSharedLaundry) {
		mSharedLaundry = pSharedLaundry;
	}

	public boolean isPrivateLaundry() {
		return mPrivateLaundry;
	}

	public void setPrivateLaundry(boolean pPrivateLaundry) {
		mPrivateLaundry = pPrivateLaundry;
	}

	public void setBathrooms(String pBathrooms) {
		mBathrooms = pBathrooms;
	}

	public String getGarageSpaces() {
		return mGarageSpaces;
	}

	public void setGarageSpaces(String pGarageSpaces) {
		mGarageSpaces = pGarageSpaces;
	}

	public String getCarportSpaces() {
		return mCarportSpaces;
	}

	public void setCarportSpaces(String pCarportSpaces) {
		mCarportSpaces = pCarportSpaces;
	}

	public String getToilets() {
		return mToilets;
	}

	public boolean isGarden() {
		return mGarden;
	}

	public void setGarden(boolean pGarden) {
		mGarden = pGarden;
	}

	public boolean isWorkshop() {
		return mWorkshop;
	}

	public void setWorkshop(boolean pWorkshop) {
		mWorkshop = pWorkshop;
	}

	public boolean isDishWasher() {
		return mDishWasher;
	}

	public void setDishWasher(boolean pDishWasher) {
		mDishWasher = pDishWasher;
	}

	public void setToilets(String pToilets) {
		mToilets = pToilets;
	}

	public String getPropertyType() {
		return mPropertyType;
	}

	public void setPropertyType(String pPropertyType) {
		mPropertyType = pPropertyType;
	}

	public boolean isIndoorSpa() {
		return mIndoorSpa;
	}

	public void setIndoorSpa(boolean pIndoorSpa) {
		mIndoorSpa = pIndoorSpa;
	}

	public String getBuildingSize() {
		return mBuildingSize;
	}

	public void setBuildingSize(String pBuildingSize) {
		mBuildingSize = pBuildingSize;
	}

	public String getLandSize() {
		return mLandSize;
	}

	public boolean isSplitSystem() {
		return mSplitSystem;
	}

	public void setSplitSystem(boolean pSplitSystem) {
		mSplitSystem = pSplitSystem;
	}

	public boolean isGasHeating() {
		return mGasHeating;
	}

	public void setGasHeating(boolean pGasHeating) {
		mGasHeating = pGasHeating;
	}

	public boolean isEvaporativeCooling() {
		return mEvaporativeCooling;
	}

	public void setEvaporativeCooling(boolean pEvaporativeCooling) {
		mEvaporativeCooling = pEvaporativeCooling;
	}

	public boolean isHydronicHeating() {
		return mHydronicHeating;
	}

	public void setHydronicHeating(boolean pHydronicHeating) {
		mHydronicHeating = pHydronicHeating;
	}

	public void setLandSize(String pLandSize) {
		mLandSize = pLandSize;
	}

	public boolean isRemoteGarage() {
		return mRemoteGarage;
	}

	public void setRemoteGarage(boolean pRemoteGarage) {
		mRemoteGarage = pRemoteGarage;
	}

	public boolean isCloseToSchools() {
		return mCloseToSchools;
	}

	public boolean isBroadbandInternet() {
		return mBroadbandInternet;
	}

	public void setBroadbandInternet(boolean pBroadbandInternet) {
		mBroadbandInternet = pBroadbandInternet;
	}

	public void setCloseToSchools(boolean pCloseToSchools) {
		mCloseToSchools = pCloseToSchools;
	}

	public boolean isCloseToTransport() {
		return mCloseToTransport;
	}

	public void setCloseToTransport(boolean pCloseToTransport) {
		mCloseToTransport = pCloseToTransport;
	}

	public boolean isCloseToShops() {
		return mCloseToShops;
	}

	public void setCloseToShops(boolean pCloseToShops) {
		mCloseToShops = pCloseToShops;
	}

	public boolean isOutdoorLiving() {
		return mOutdoorLiving;
	}

	public void setOutdoorLiving(boolean pOutdoorLiving) {
		mOutdoorLiving = pOutdoorLiving;
	}

	public boolean isShed() {
		return mShed;
	}

	public void setShed(boolean pShed) {
		mShed = pShed;
	}

	public boolean isAirconditioning() {
		return mAirconditioning;
	}

	public void setAirconditioning(boolean pAirconditioning) {
		mAirconditioning = pAirconditioning;
	}

	public boolean isBuiltinWardrobes() {
		return mBuiltinWardrobes;
	}

	public void setBuiltinWardrobes(boolean pBuiltinWardrobes) {
		mBuiltinWardrobes = pBuiltinWardrobes;
	}

	public boolean isSecureParking() {
		return mSecureParking;
	}

	public void setSecureParking(boolean pSecureParking) {
		mSecureParking = pSecureParking;
	}

	public boolean isSolarPanels() {
		return mSolarPanels;
	}

	public void setSolarPanels(boolean pSolarPanels) {
		mSolarPanels = pSolarPanels;
	}

	public boolean isRumpusRoom() {
		return mRumpusRoom;
	}

	public void setRumpusRoom(boolean pRumpusRoom) {
		mRumpusRoom = pRumpusRoom;
	}

	public boolean isSolarHotWater() {
		return mSolarHotWater;
	}

	public void setSolarHotWater(boolean pSolarHotWater) {
		mSolarHotWater = pSolarHotWater;
	}

	public boolean isWaterTank() {
		return mWaterTank;
	}

	public void setWaterTank(boolean pWaterTank) {
		mWaterTank = pWaterTank;
	}

	public boolean isGreyWaterSystem() {
		return mGreyWaterSystem;
	}

	public void setGreyWaterSystem(boolean pGreyWaterSystem) {
		mGreyWaterSystem = pGreyWaterSystem;
	}

	public boolean isHighEnergyEfficiency() {
		return mHighEnergyEfficiency;
	}

	public void setHighEnergyEfficiency(boolean pHighEnergyEfficiency) {
		mHighEnergyEfficiency = pHighEnergyEfficiency;
	}

	public boolean isMediumEnergyEfficiency() {
		return mMediumEnergyEfficiency;
	}

	public void setMediumEnergyEfficiency(boolean pMediumEnergyEfficiency) {
		mMediumEnergyEfficiency = pMediumEnergyEfficiency;
	}

	public boolean isLowEnergyEfficiency() {
		return mLowEnergyEfficiency;
	}

	public void setLowEnergyEfficiency(boolean pLowEnergyEfficiency) {
		mLowEnergyEfficiency = pLowEnergyEfficiency;
	}

	public boolean isOpenCarSpaces() {
		return mOpenCarSpaces;
	}

	public void setOpenCarSpaces(boolean pOpenCarSpaces) {
		mOpenCarSpaces = pOpenCarSpaces;
	}

	public boolean isSwimmingPoolAbove() {
		return mSwimmingPoolAbove;
	}

	public void setSwimmingPoolAbove(boolean pSwimmingPoolAbove) {
		mSwimmingPoolAbove = pSwimmingPoolAbove;
	}

	public boolean isSwimmingPoolUnder() {
		return mSwimmingPoolUnder;
	}

	public void setSwimmingPoolUnder(boolean pSwimmingPoolUnder) {
		mSwimmingPoolUnder = pSwimmingPoolUnder;
	}

	public boolean isSwimmingPool() {
		return mSwimmingPool;
	}

	public void setSwimmingPool(boolean pSwimmingPool) {
		mSwimmingPool = pSwimmingPool;
	}

	public boolean isOutsideSpa() {
		return mOutsideSpa;
	}

	public void setOutsideSpa(boolean pOutsideSpa) {
		mOutsideSpa = pOutsideSpa;
	}

	public boolean isTennisCourt() {
		return mTennisCourt;
	}

	public void setTennisCourt(boolean pTennisCourt) {
		mTennisCourt = pTennisCourt;
	}

	public boolean isBalcony() {
		return mBalcony;
	}

	public void setBalcony(boolean pBalcony) {
		mBalcony = pBalcony;
	}

	public boolean isDeck() {
		return mDeck;
	}

	public void setDeck(boolean pDeck) {
		mDeck = pDeck;
	}

	public boolean isCourtyard() {
		return mCourtyard;
	}

	public void setCourtyard(boolean pCourtyard) {
		mCourtyard = pCourtyard;
	}

	public boolean isFullyFenced() {
		return mFullyFenced;
	}

	public void setFullyFenced(boolean pFullyFenced) {
		mFullyFenced = pFullyFenced;
	}

	public boolean isAlarmSystem() {
		return mAlarmSystem;
	}

	public void setAlarmSystem(boolean pAlarmSystem) {
		mAlarmSystem = pAlarmSystem;
	}

	public boolean isStudyRoom() {
		return mStudyRoom;
	}

	public void setStudyRoom(boolean pStudyRoom) {
		mStudyRoom = pStudyRoom;
	}

	public boolean isEnsuite() {
		return mEnsuite;
	}

	public void setEnsuite(boolean pEnsuite) {
		mEnsuite = pEnsuite;
	}

	public boolean isIntercom() {
		return mIntercom;
	}

	public void setIntercom(boolean pIntercom) {
		mIntercom = pIntercom;
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
			else if (m.getType().equals(boolean.class)) {
				try {
					if (m.get(this).equals(true)) {
						if (myString.length()==0) {
							myString = m.getName().substring(1,m.getName().length());
						}
						else {
							myString = myString + ", " + m.getName().substring(1,m.getName().length());
						}
					}
				}catch (Exception e ){

				}

			}
		}
		
		return myString;
	}
    
}
