package cscie97.asn3.housemate.controller;

import java.util.Map;
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

public final class SmokeDetectorFireCommand implements ControllerCommand {

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the SmokeDetectorFireCommand class.
     */
	SmokeDetectorFireCommand(MessagePacket packet, HouseMateModelService service){
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
		String houseName = parts[0];
		
		System.out.println("Smoke detector fire alarm in the room: " + roomName);
		try{
			HouseMateEntitlementService houseMateEntitlementService = HouseMateEntitlementServiceFactory.getInstance();
			auth_token = houseMateEntitlementService.loginAuthentication("HMCS", "HMCS");
			
			Set<String> avaSet = modelService.getAllDevicesOfRoomForType(auth_token, roomName, DeviceType.AVA);
			String avaIdentifier = "";
			if(avaSet.size() == 1) {
				for(String str:avaSet){
					avaIdentifier = str;
					break;
				}
			}
			
			Set<String> OccupantsSet = modelService.getOccupantsInHouse(auth_token, houseName);
			if(OccupantsSet.size() > 0){
				System.out.println("There are occupants in the house");
				//Turn ON the lights
				Set<String> lightsSet = modelService.getAllDevicesOfHouseForType(auth_token, houseName, DeviceType.LIGHT);
				for(String lightIdentifier:lightsSet){
					System.out.println("Setting Light status ON and intensity 20 to: " + lightIdentifier);
					modelService.setApplianceStatus(auth_token, lightIdentifier, "power", "On", "");
					modelService.setApplianceStatus(auth_token, lightIdentifier, "intensity", "100", "");
				}
				
				Map<String, Integer> floorWindowMap =  modelService.getFloorAndWindowCountForRoom(auth_token, roomName);
				String responseStr = "Fire in the " +  roomName + ", please leave the house immediately";
				if(floorWindowMap != null){
					if(floorWindowMap.get("floor") == 1 && floorWindowMap.get("window") >= 1){
						responseStr = "Please leave the house using the " + roomName + " room window";
					}
				}
				
				System.out.println("Setting Ava response to: " + responseStr);
				modelService.setApplianceStatus(auth_token, avaIdentifier, "statusResponse", responseStr, "");
				
				System.out.println("Call 911");
				
			}
			//Logout HMCS to invalidate the existing auth token.
			houseMateEntitlementService.logout(auth_token);
		}catch (HouseNotExistsException e) {
			System.out.println(e.toString());
		} catch (ApplianceNotExistsException e) {
			System.out.println(e.toString());
		} catch (RoomNotExistsException e) {
			System.out.println(e.toString());
		} catch (EmptyOrNUllAuthTokenException e) {
			System.out.println(e.toString());
		} catch (InvalidAccessTokenException e) {
			System.out.println(e.toString());
		} catch (AccessDeniedException e) {
			System.out.println(e.toString());
		} catch (AccessTokenNotExistsException e) {
			System.out.println(e.toString());
		} catch (UserNotAuthenticatedException e) {
			System.out.println(e.toString());
		}
	}

}
