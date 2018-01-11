package cscie97.asn4.housemate.entitlement;

/**
 * @author V1A
 *
 */
public interface HouseMateEntitlementElements {
	
	/**accept visitor function to be implemented by domain elements. 
	 * @param visitor
	 * @return string
	 */
	String acceptVisitor(HouseMateEntitlementElementVisitor visitor);
}
