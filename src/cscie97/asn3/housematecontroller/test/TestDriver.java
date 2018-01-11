package cscie97.asn3.housematecontroller.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import cscie97.asn2.housemate.model.ApplianceNotExistsException;
import cscie97.asn2.housemate.model.EmptyOrNUllAuthTokenException;
import cscie97.asn2.housemate.model.EntityDefineException;
import cscie97.asn2.housemate.model.HouseAlreadyExistsException;
import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn2.housemate.model.HouseMateModelServiceFactory;
import cscie97.asn2.housemate.model.HouseNotExistsException;
import cscie97.asn2.housemate.model.OccupantAlreadyExistsException;
import cscie97.asn2.housemate.model.RoomAlreadyExistsException;
import cscie97.asn2.housemate.model.RoomNotExistsException;
import cscie97.asn2.housemate.model.SensorNotExistsException;
import cscie97.asn3.housemate.controller.HouseMateControllerService;
import cscie97.asn3.housemate.controller.HouseMateControllerServiceFactory;
import cscie97.asn4.housemate.entitlement.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.InvalidAccessTokenException;
import cscie97.asn4.housemate.entitlement.ResourceAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.ResourceRoleAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.UserAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.UserCredentialOptionNotExistsException;

public class TestDriver {

	public static void main(String[] args) {
		HouseMateModelService houseMateService = HouseMateModelServiceFactory.getInstance();
		Observable obs = (Observable)houseMateService;
		HouseMateControllerService houseMateControllerService = HouseMateControllerServiceFactory.getInstance(obs);
		
		
		if(args.length == 0)
		{
			System.out.println("No house configuration setup file provided");
			return;
		}
		String houseConfiguration = args[0];
		int lineNumber = 0;
		
		String auth_token = ""; //Currently empty string to be filled later on.
		 try (BufferedReader br = new BufferedReader(new FileReader(houseConfiguration))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            	try{
		            	if(line.equals("")) continue;
						if(line.startsWith("#")) continue;
						String[] parts = line.split("\\s+");
					
						int numArgs = parts.length;
						String command = parts[0];
						lineNumber++;
						
						//Trim extra leading and trailing white spaces.
						command.trim();
						switch(command){
							case "define":
								String houseEntity = parts[1];
								if(houseEntity.equals("house")){
									int commaIndex = line.indexOf("address");
									String address = line.substring(commaIndex+9,line.length()-1);
									houseMateService.createHouse(auth_token, parts[2], address);
								}else if(houseEntity.equals("room")){
									houseMateService.createRoom(auth_token, parts[2],Integer.parseInt(parts[4]), parts[6], parts[8], Integer.parseInt(parts[10]));
								}else if(houseEntity.equals("occupant")){
									houseMateService.createOccupant(auth_token, parts[2], parts[4]);
								}else if(houseEntity.equals("sensor")){
									houseMateService.createSensor(auth_token, parts[2], parts[4], parts[6]);
								}else if(houseEntity.equals("appliance")){
									houseMateService.createAppliance(auth_token, parts[2], parts[4], parts[6]);
								}else{
									EntityDefineException de = new EntityDefineException();
									de.setDescription("Incorrect entity type to create.");
									de.setEntityNameContext(houseEntity);
									throw de;
								}
								break;
							case "add":
								String occupantEntity = parts[1];
								if(occupantEntity.equals("occupant")){
									houseMateService.addOccupantToHouse(auth_token, parts[2], parts[4]);
								}else{
									EntityDefineException de = new EntityDefineException();
									de.setDescription("Incorrect entity type to add.");
									de.setEntityNameContext(occupantEntity);
									throw de;
								}
								break;
							case "set":
								System.out.println("");
								System.out.println("**Command: " + line);
								String entity = parts[1];
								if(entity.equals("sensor")){
									houseMateService.setSensorStatus(auth_token, parts[2], parts[4], parts[6]);
								}else if(entity.equals("appliance")){
									houseMateService.setApplianceStatus(auth_token, parts[2], parts[4], parts[6], "");
								}else if(entity.equals("occupant")){
									houseMateService.updateOccupantLocation(auth_token, parts[2], parts[4]);
								}else{
									EntityDefineException de = new EntityDefineException();
									de.setDescription("Incorrect entity type to set.");
									de.setEntityNameContext(entity);
									throw de;
								}
								break;
							case "show":
								String entityName = parts[1];
								
								if(numArgs == 5){
									if(entityName.equals("sensor")){
										String value = houseMateService.getSensorStatus(auth_token, parts[2], parts[4]);
										System.out.println(value);
									}else if(entityName.equals("appliance")){
										String value = houseMateService.getApplianceStatus(auth_token, parts[2], parts[4]);
										System.out.println("Status of " + entityName + " " + parts[2] + " Status " + parts[4]);
										System.out.println(value);
									}else{
										EntityDefineException de = new EntityDefineException();
										de.setDescription("Incorrect entity type to show value.");
										de.setEntityNameContext(entityName);
										throw de;
									}
								}else{
									if(entityName.equals("sensor") || entityName.equals("appliance")){
										Map<String, String> statusValueMap = houseMateService.getAllStatusOfSensorOrAppliance(auth_token, parts[2]);
										System.out.println("All status of " + entityName + " " + parts[2]);
										for (Map.Entry<String, String> entry : statusValueMap.entrySet())
										{
										    System.out.println(entry.getKey() + ":" + entry.getValue());
										}
										
									}else if(entityName.equals("configuration")){
										if(numArgs == 2){
											System.out.println("*****Configurations of All Houses*****");
											Set<LinkedHashSet<String>> allConfigSet = houseMateService.getAllHousesConfiguration(auth_token);
											for (LinkedHashSet<String> configSet : allConfigSet) {
												for (String config : configSet) {
													System.out.println(config);
												}
											}
										}else if(parts[2].equals("house")){
											System.out.println("*****Configurations of House*****");
											Set<String> houseConfigSet = houseMateService.getHouseConfiguration(auth_token, parts[3]);
											if (houseConfigSet == null || houseConfigSet.size() == 0 ) {
												System.out.println("<null>");
											}
											else{
												for (String config : houseConfigSet) {
													System.out.println(config);
												}
											}
								        }
										else if(parts[2].equals("room")){
											System.out.println("*****Configuration of Room*****");
											Set<String> roomConfigSet = houseMateService.getRoomConfiguration(auth_token, parts[3]);
											if (roomConfigSet == null || roomConfigSet.size() == 0 ) {
												System.out.println("<null>");
											}
											else{
												for (String config : roomConfigSet) {
													System.out.println(config);
												}
											}
										}
									}else{
										EntityDefineException de = new EntityDefineException();
										de.setDescription("Incorrect entity type to show configuration.");
										de.setEntityNameContext(entityName);
										throw de;
									}
								}
								break;
								
							default:
								EntityDefineException de = new EntityDefineException();
								de.setDescription("Incorrect command");
								de.setEntityNameContext(command);
								throw de;
								
						}
	            	} catch(EntityDefineException de){
	            		de.setFileName(houseConfiguration);
	            		de.setCommandContext(line);
	            		de.setLineNumber(lineNumber);
						System.out.println(de);
	            	} catch (HouseAlreadyExistsException he) {
	            		he.setFileName(houseConfiguration);
	            		he.setCommandContext(line);
	            		he.setLineNumber(lineNumber);
						System.out.println(he);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (RoomAlreadyExistsException re) {
						re.setFileName(houseConfiguration);
						re.setCommandContext(line);
						re.setLineNumber(lineNumber);
						System.out.println(re);
					} catch (OccupantAlreadyExistsException oe) {
						oe.setFileName(houseConfiguration);
						oe.setCommandContext(line);
						oe.setLineNumber(lineNumber);
						System.out.println(oe);
					} catch (RoomNotExistsException re) {
						re.setFileName(houseConfiguration);
						re.setCommandContext(line);
						re.setLineNumber(lineNumber);
						System.out.println(re);
					} catch (HouseNotExistsException he) {
						he.setFileName(houseConfiguration);
	            		he.setCommandContext(line);
	            		he.setLineNumber(lineNumber);
						System.out.println(he);
					} catch (SensorNotExistsException se) {
						se.setFileName(houseConfiguration);
						se.setCommandContext(line);
						se.setLineNumber(lineNumber);
						System.out.println(se);
					} catch (ApplianceNotExistsException ae) {
						ae.setFileName(houseConfiguration);
						ae.setCommandContext(line);
						ae.setLineNumber(lineNumber);
						System.out.println(ae);
					} catch (EmptyOrNUllAuthTokenException ae) {
						ae.setFileName(houseConfiguration);
						ae.setCommandContext(line);
						ae.setLineNumber(lineNumber);
						System.out.println(ae);
					} catch (InvalidAccessTokenException e) {
						e.setFileName(houseConfiguration);
						e.setCommandContext(line);
						e.setLineNumber(lineNumber);
						System.out.println(e);
					} catch (AccessDeniedException e) {
						e.setFileName(houseConfiguration);
						e.setCommandContext(line);
						e.setLineNumber(lineNumber);
						System.out.println(e);
					} catch (UserCredentialOptionNotExistsException e) {
						e.setFileName(houseConfiguration);
						e.setCommandContext(line);
						e.setLineNumber(lineNumber);
						System.out.println(e);
					} catch (UserAlreadyExistsException e) {
						e.setFileName(houseConfiguration);
						e.setCommandContext(line);
						e.setLineNumber(lineNumber);
						System.out.println(e);
					} catch (ResourceAlreadyExistsException e) {
						e.setFileName(houseConfiguration);
						e.setCommandContext(line);
						e.setLineNumber(lineNumber);
						System.out.println(e);
					} catch (ResourceRoleAlreadyExistsException e) {
						e.setFileName(houseConfiguration);
						e.setCommandContext(line);
						e.setLineNumber(lineNumber);
						System.out.println(e);
					}
					
	            }
		 }
		 catch (FileNotFoundException e) {
	        	System.out.println("House setup file not found: " + e.getMessage());
		 }
		 catch (IOException e) {
	        	System.out.println("File not found: " + e);
		 }

	}

}
