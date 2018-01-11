package cscie97.asn4.housemate.entitlement;



public final class HouseMateEntitlementServiceFactory {
	private static HouseMateEntitlementServiceImpl instance;
	
	 /**
   * Private default constructor
   */
	private HouseMateEntitlementServiceFactory(){
		
	}
	
	/**
   * This method returns a reference to the single static instance of the HouseMateEntitlementService.
   *
   * @return single instance of HouseMateEntitlementService.
   */
	public static HouseMateEntitlementService getInstance(){
		if(instance == null) 
			instance = new HouseMateEntitlementServiceImpl();
		return instance;
	}
}
