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

public class OvenTimeToCookCommand implements ControllerCommand {

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the OvenTimeToCookCommand class.
     */
	OvenTimeToCookCommand(MessagePacket packet, HouseMateModelService service){
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
			
			String timeToCookValue = modelService.getApplianceStatus(auth_token, this.packet.getSubjectIdentifier(), "timeToCook");
			System.out.println("Oven has time to cook value: " + timeToCookValue);
			if(Integer.parseInt(timeToCookValue) == 0){
				String powerValue = modelService.getApplianceStatus(auth_token, this.packet.getSubjectIdentifier(), "power");
				if(powerValue.equalsIgnoreCase("On")){
					System.out.println("Oven is ON currently");
					System.out.println("Setting oven status to Off");
					modelService.setApplianceStatus(auth_token, this.packet.getSubjectIdentifier(), "power", "Off", "");
					
					String responseStr = "Food is ready";
					modelService.setApplianceStatus(auth_token, avaIdentifier, "statusResponse", responseStr, "");
					System.out.println("Setting ava response to: " + responseStr);
				}
			}
			//Logout HMCS to invalidate the existing auth token.
			houseMateEntitlementService.logout(auth_token);
		}catch (ApplianceNotExistsException e) {
			System.out.println(e.toString());
		} catch (HouseNotExistsException e) {
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
