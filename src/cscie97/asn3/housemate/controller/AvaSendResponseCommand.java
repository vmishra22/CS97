package cscie97.asn3.housemate.controller;

import java.util.regex.Pattern;

import cscie97.asn2.housemate.model.ApplianceNotExistsException;
import cscie97.asn2.housemate.model.EmptyOrNUllAuthTokenException;
import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn2.housemate.model.MessagePacket;
import cscie97.asn2.housemate.model.OccupantNotExistsException;
import cscie97.asn4.housemate.entitlement.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.AccessTokenNotExistsException;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementService;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementServiceFactory;
import cscie97.asn4.housemate.entitlement.InvalidAccessTokenException;
import cscie97.asn4.housemate.entitlement.UserNotAuthenticatedException;

public final class AvaSendResponseCommand implements ControllerCommand {

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the AvaSendResponseCommand class.
     */
	AvaSendResponseCommand(MessagePacket packet, HouseMateModelService service){
		this.modelService = service;
		this.packet = packet;
	}
	
	/**
	 * 
	 * Interface method implementation for execute().
	 */
	@Override
	public void execute() {
		//Retrieve the occupant identifier from the command
		String[] commandParts = this.packet.getCommandIdentifier().split(Pattern.quote("_"));
		String occupantStr = commandParts[2] + "_" + commandParts[3];
		String[] occupantStrParts = occupantStr.split(Pattern.quote("?"));
    	String occupantName = occupantStrParts[0];
		
    	String responseStr = occupantName + " is_located_in ";
    	System.out.println("Command to Ava: " + this.packet.getCommandIdentifier());
    	
    	HouseMateEntitlementService houseMateEntitlementService = HouseMateEntitlementServiceFactory.getInstance();
		
		try {
			auth_token = houseMateEntitlementService.loginAuthentication("HMCS", "HMCS");
			String location = modelService.getOccupantLocation(auth_token, occupantName);
	    	responseStr += location;
	    	
	    	System.out.println("Setting Response to Ava " + responseStr);
			modelService.setApplianceStatus(auth_token, this.packet.getSubjectIdentifier(), "statusResponse", responseStr, "");
			houseMateEntitlementService.logout(auth_token);
		} catch (ApplianceNotExistsException e) {
			System.out.println(e.toString());
		} catch (OccupantNotExistsException e) {
			System.out.println(e.toString());
		} catch (EmptyOrNUllAuthTokenException e) {
			System.out.println(e.toString());
		} catch (UserNotAuthenticatedException e) {
			System.out.println(e.toString());
		} catch (InvalidAccessTokenException e) {
			System.out.println(e.toString());
		} catch (AccessDeniedException e) {
			System.out.println(e.toString());
		} catch (AccessTokenNotExistsException e) {
			System.out.println(e.toString());
		}
	}

}
