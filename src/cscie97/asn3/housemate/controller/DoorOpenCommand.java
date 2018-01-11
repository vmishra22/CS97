package cscie97.asn3.housemate.controller;

import java.util.regex.Pattern;

import cscie97.asn2.housemate.model.ApplianceNotExistsException;
import cscie97.asn2.housemate.model.EmptyOrNUllAuthTokenException;
import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn2.housemate.model.MessagePacket;
import cscie97.asn4.housemate.entitlement.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementService;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementServiceFactory;
import cscie97.asn4.housemate.entitlement.InvalidAccessTokenException;
import cscie97.asn4.housemate.entitlement.UserNotAuthenticatedException;

public final class DoorOpenCommand implements ControllerCommand {

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the DoorOpenCommand class.
     */
	DoorOpenCommand(MessagePacket packet, HouseMateModelService service){
		this.modelService = service;
		this.packet = packet;
	}
	
	/**
	 * 
	 * Interface method implementation for execute().
	 */
	@Override
	public void execute() {
		System.out.println("Command to Ava: " + this.packet.getCommandIdentifier());
		String[] parts = this.packet.getSubjectIdentifier().split(Pattern.quote(":"));
		String doorIdentifier = parts[0] + ":" + parts[1] + ":" + "door1";
		String voice_print = packet.getVoicePrint();
		HouseMateEntitlementService houseMateEntitlementService = HouseMateEntitlementServiceFactory.getInstance();
		
		try{
			auth_token = houseMateEntitlementService.voicePrintAuthentication(voice_print);
			System.out.println("Setting door status Open to: " + doorIdentifier);
			modelService.setApplianceStatus(auth_token, doorIdentifier, "doorStatus", "Open", "");
		}catch(ApplianceNotExistsException e){
			System.out.println(e.toString());
		} catch (EmptyOrNUllAuthTokenException e) {
			System.out.println(e.toString());
		} catch (UserNotAuthenticatedException e) {
			System.out.println(e.toString());
		} catch (InvalidAccessTokenException e) {
			System.out.println(e.toString());
		} catch (AccessDeniedException e) {
			System.out.println(e.toString());
		}

	}

}
