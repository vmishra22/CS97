package cscie97.asn3.housemate.controller;

import java.util.Set;
import java.util.regex.Pattern;

import cscie97.asn2.housemate.model.ApplianceNotExistsException;
import cscie97.asn2.housemate.model.DeviceType;
import cscie97.asn2.housemate.model.EmptyOrNUllAuthTokenException;
import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn2.housemate.model.HouseNotExistsException;
import cscie97.asn2.housemate.model.MessagePacket;
import cscie97.asn2.housemate.model.RoomNotExistsException;
import cscie97.asn4.housemate.entitlement.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.AccessTokenNotExistsException;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementService;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementServiceFactory;
import cscie97.asn4.housemate.entitlement.InvalidAccessTokenException;
import cscie97.asn4.housemate.entitlement.UserNotAuthenticatedException;

public final class OccupantDetectedCommand implements ControllerCommand {

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the OccupantDetectedCommand class.
     */
	OccupantDetectedCommand(MessagePacket packet, HouseMateModelService service){
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
		
		//Retrieve the occupant identifier from the command
		String occupantName = this.packet.getTargetIdentifier();
		System.out.println("Camera detects the occupant entering the room: " + occupantName);
		
		try{
			//HMCS user admin token for executing actions in HMMS
			HouseMateEntitlementService houseMateEntitlementService = HouseMateEntitlementServiceFactory.getInstance();
			auth_token = houseMateEntitlementService.loginAuthentication("HMCS", "HMCS");
			
			//Turn ON the lights
			Set<String> lightsSet = modelService.getAllDevicesOfRoomForType(auth_token, roomName, DeviceType.LIGHT);
			for(String lightIdentifier:lightsSet){
				System.out.println("Setting Light status ON and intensity 20 to: " + lightIdentifier);
				modelService.setApplianceStatus(auth_token, lightIdentifier, "power", "On", "");
				modelService.setApplianceStatus(auth_token, lightIdentifier, "intensity", "20", "");
			}
			
			//Increase the thermostat temperature to 71F
			Set<String> thermostatSet = modelService.getAllDevicesOfRoomForType(auth_token, roomName, DeviceType.THERMOSTAT);
			for(String thermostatIdentifier:thermostatSet){
				System.out.println("Setting Thermostat temperature 71 to: " + thermostatIdentifier);
				modelService.setApplianceStatus(auth_token, thermostatIdentifier, "temperature", "71", "");
			}
			
			//Update the occupant location in KG
			System.out.println("Updating location to " + roomName + " for occupant " + occupantName);
			modelService.updateOccupantLocation(auth_token, occupantName, roomName);
			
			//Logout HMCS to invalidate the existing auth token.
			houseMateEntitlementService.logout(auth_token);
			
		}catch (HouseNotExistsException e) {
			System.out.println(e.toString());
		} catch (RoomNotExistsException e) {
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
