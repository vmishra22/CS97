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

public final class ApplianceAndAvaSendResponseCommand implements ControllerCommand{

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the ApplianceAndAvaSendResponseCommand class.
     */
	ApplianceAndAvaSendResponseCommand(MessagePacket packet, HouseMateModelService service){
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
		
		String[] commandParts = this.packet.getCommandIdentifier().split(Pattern.quote("_"));
		System.out.println("Command to Ava: " + commandParts[0] + " " + commandParts[1] + " " + commandParts[2]);
		
		String voice_print = packet.getVoicePrint();
		HouseMateEntitlementService houseMateEntitlementService = HouseMateEntitlementServiceFactory.getInstance();
		
		try{
			auth_token = houseMateEntitlementService.voicePrintAuthentication(voice_print);
			
			String auth_token_hmcs = houseMateEntitlementService.loginAuthentication("HMCS", "HMCS");
			DeviceType deviceType = modelService.getDeviceType(commandParts[0]);
			Set<String> applianceSet = modelService.getAllDevicesOfRoomForType(auth_token_hmcs, roomName, deviceType);
			for(String applianceIdentifier:applianceSet){
				System.out.println("Setting status " + commandParts[1]+" with value " + commandParts[2] + " to: " + applianceIdentifier);
				modelService.setApplianceStatus(auth_token, applianceIdentifier, commandParts[1], commandParts[2], "");
			}
			
			System.out.println("Setting Response to Ava " + this.packet.getCommandIdentifier());
			modelService.setApplianceStatus(auth_token_hmcs, this.packet.getSubjectIdentifier(), "statusResponse", this.packet.getCommandIdentifier(), "");
			houseMateEntitlementService.logout(auth_token_hmcs);
			
		}catch (HouseNotExistsException e) {
			
			System.out.println(e.toString());
		} catch (ApplianceNotExistsException e) {
			
			System.out.println(e.toString());
		} catch (EmptyOrNUllAuthTokenException e) {
			System.out.println(e.toString());
		} catch (InvalidAccessTokenException e) {
			System.out.println(e.toString());
		} catch (AccessDeniedException e) {
			System.out.println(e.toString());
		} catch (UserNotAuthenticatedException e) {
			System.out.println(e.toString());
		} catch (AccessTokenNotExistsException e) {
			System.out.println(e.toString());
		}

	}

}
