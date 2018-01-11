package cscie97.asn2.housemate.model;

public final class HouseMateModelServiceFactory {
	private static HouseMateModelServiceImpl instance;
	
	 /**
     * Private default constructor
     */
	private HouseMateModelServiceFactory(){
		
	}
	
	/**
     * This method returns a reference to the single static instance of the HouseMateModelService.
     *
     * @return single instance of HouseMateModelService.
     */
	public static HouseMateModelService getInstance(){
		if(instance == null) 
			instance = new HouseMateModelServiceImpl();
		return instance;
	}
}
