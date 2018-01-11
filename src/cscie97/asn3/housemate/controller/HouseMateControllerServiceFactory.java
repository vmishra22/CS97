package cscie97.asn3.housemate.controller;

import java.util.Observable;

public final class HouseMateControllerServiceFactory {
	private static HouseMateControllerServiceImpl instance;
	
	 /**
    * Private default constructor
    */
	private HouseMateControllerServiceFactory(){
		
	}
	
	/**
    * This method returns a reference to the single static instance of the HouseMateControllerService.
    *
    * @return single instance of HouseMateControllerService.
    */
	public static HouseMateControllerService getInstance(Observable obs){
		if(instance == null) 
			instance = new HouseMateControllerServiceImpl(obs);
		return instance;
	}
}
