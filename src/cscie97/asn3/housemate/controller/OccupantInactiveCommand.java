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

public final class OccupantInactiveCommand implements ControllerCommand {

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the OccupantInactiveCommand class.
     */
	OccupantInactiveCommand(MessagePacket packet, HouseMateModelService service){
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
		String occupantName = this.packet.getTargetIdentifier();
		System.out.println("Camera detects the occupant " + occupantName + " inactive in the room" );
		
		try{
			HouseMateEntitlementService houseMateEntitlementService = HouseMateEntitlementServiceFactory.getInstance();
			auth_token = houseMateEntitlementService.loginAuthentication("HMCS", "HMCS");
			
			Set<String> OccupantsSet = modelService.getOccupantsInRoom(auth_token, roomName);
			if(OccupantsSet.size() == 1){
				System.out.println("Only 1 inactive occupant in the room: " + roomName);
				//Turn OFF the lights
				Set<String> lightsSet = modelService.getAllDevicesOfRoomForType(auth_token, roomName, DeviceType.LIGHT);
				for(String lightIdentifier:lightsSet){
					System.out.println("Setting Lights Dim with intensity 10 to: " + lightIdentifier);
					modelService.setApplianceStatus(auth_token, lightIdentifier, "intensity", "10", "");
				}
				//Update the occupant state in KG
				System.out.println("Updating state to resting for occupant " + occupantName);
				modelService.updateOccupantState(auth_token, occupantName, roomName, "resting");
				
				//Logout HMCS to invalidate the existing auth token.
				houseMateEntitlementService.logout(auth_token);
			}
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
