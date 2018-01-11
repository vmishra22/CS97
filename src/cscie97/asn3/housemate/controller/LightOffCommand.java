package cscie97.asn3.housemate.controller;

import java.util.Set;
import java.util.regex.Pattern;

import cscie97.asn2.housemate.model.ApplianceNotExistsException;
import cscie97.asn2.housemate.model.DeviceType;
import cscie97.asn2.housemate.model.EmptyOrNUllAuthTokenException;
import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn2.housemate.model.HouseNotExistsException;
import cscie97.asn2.housemate.model.MessagePacket;
import cscie97.asn4.housemate.entitlement.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.AccessTokenNotExistsException;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementService;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementServiceFactory;
import cscie97.asn4.housemate.entitlement.InvalidAccessTokenException;
import cscie97.asn4.housemate.entitlement.UserNotAuthenticatedException;

public final class LightOffCommand implements ControllerCommand {

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the LightOffCommand class.
     */
	LightOffCommand(MessagePacket packet, HouseMateModelService service){
		this.modelService = service;
		this.packet = packet;
	}
	
	/**
	 * 
	 * Interface method implementation for execute().
	 */
	@Override
	public void execute() {
		String[] parts = this.packet.getSubjectIdentifier().split(Pattern.quote(":"));
		String roomName = parts[0] + ":" + parts[1];
		System.out.println("Command to Ava: " + this.packet.getCommandIdentifier());
		
		String voice_print = packet.getVoicePrint();
		HouseMateEntitlementService houseMateEntitlementService = HouseMateEntitlementServiceFactory.getInstance();
		
		try{
			String auth_token_HMCS = houseMateEntitlementService.loginAuthentication("HMCS", "HMCS");
			auth_token = houseMateEntitlementService.voicePrintAuthentication(voice_print);
			Set<String> lightsSet = modelService.getAllDevicesOfRoomForType(auth_token_HMCS, roomName, DeviceType.LIGHT);
			for(String lightIdentifier:lightsSet){
				System.out.println("Setting Light status Off and intensity 0 to: " + lightIdentifier);
				modelService.setApplianceStatus(auth_token, lightIdentifier, "power", "Off", "");
				modelService.setApplianceStatus(auth_token, lightIdentifier, "intensity", "0", "");
			}
			//Logout HMCS to invalidate the existing auth token.
			houseMateEntitlementService.logout(auth_token_HMCS);
			
		}catch (HouseNotExistsException e) {
			
			System.out.println(e.toString());
		} catch (ApplianceNotExistsException e) {
			
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
