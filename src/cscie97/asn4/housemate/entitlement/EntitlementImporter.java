package cscie97.asn4.housemate.entitlement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class EntitlementImporter {
	/**
	 * Public method for importing the entitlement related specification.
	 * Checks for valid input file name. 
	 * @param fileName name of input file
	 * @throws EntitlementImportException on error accessing or processing the input File.
	 * @return int linenumber
	 *
	 */
	public int importEntitlementSpecification(String fileName) throws EntitlementImportException{
		
		HouseMateEntitlementService houseMateEntitlementService = HouseMateEntitlementServiceFactory.getInstance();
		
		System.out.println("********Entitlement service setup start********");
		int lineNumber = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            	lineNumber++;
	            	if(line.equals("")) continue;
	            	if(line.equals("#ENTITLEMENT_DATA_END")) {
	            		System.out.println("********Entitlement service setup end********");
	            		return lineNumber;
	            	}
					String[] parts = line.split(Pattern.quote(","));
					int numArgs = parts.length;
					String command = parts[0];
					//Trim extra leading and trailing white spaces.
					command.trim();
					switch(command){
						case "define_permission":
							if(numArgs != 4) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("Define permission invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							
							System.out.println("Creating permission with id: " + parts[1].trim() + " and name: " + parts[2].trim());
							houseMateEntitlementService.createPermission(parts[1].trim(), parts[2].trim(), parts[3].trim());
							break;
							
						case "define_role":
							if(numArgs != 4) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("Define role invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							System.out.println("Creating role with id: " + parts[1].trim() + " and name: " + parts[2].trim());
							houseMateEntitlementService.createRole(parts[1].trim(), parts[2].trim(), parts[3].trim());
							break;
						case "add_entitlement_to_role":
							if(numArgs != 3) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("add_entitlement_to_role invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							System.out.println("Adding entitlement: " + parts[2].trim() + " to role: " + parts[1].trim());
							houseMateEntitlementService.addEntitlementToRole(parts[1].trim(), parts[2].trim());
							break;
						case "create_user":
							if(numArgs != 3) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("create_user invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							System.out.println("Creating user with id: " + parts[1].trim() + " and name: " + parts[2].trim());
							houseMateEntitlementService.createUser(parts[1].trim(), parts[2].trim());
							break;
						case "add_user_credential":
							if(numArgs != 5) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("add_user_credential invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							houseMateEntitlementService.addUserCredential(parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim());
							break;
						case "add_role_to_user":
							if(numArgs != 3) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("add_role_to_user invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							System.out.println("Adding role: " + parts[2].trim() + " to user: " + parts[1].trim());
							houseMateEntitlementService.addRoleToUser(parts[1].trim(), parts[2].trim());
							break;
						case "create_resource":
							if(numArgs != 3) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("create_resource invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							houseMateEntitlementService.createResource(parts[1].trim(), parts[2].trim());
							break;
						case "create_resource_role":
							if(numArgs != 4) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("create_resource_role invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							houseMateEntitlementService.createResourceRole(parts[1].trim(), parts[2].trim(), parts[3].trim());
							break;
						case "add_resource_role_to_user":
							if(numArgs != 3) {
								EntitlementImportException eI = new EntitlementImportException();
								eI.setDescription("create_resource_role invalid arguments");
								eI.setCommandContext(line);
								throw eI;
							}
							houseMateEntitlementService.addResourceRoleToUser(parts[1].trim(), parts[2].trim());
							break;
					}
	            }
		 }
		catch (FileNotFoundException e) {
	    	System.out.println("File not found: " + e);
	    	e.printStackTrace();
		}
		catch (EntitlementImportException e1) {
			e1.setFileName(fileName);
			e1.setLineNumber(lineNumber);
			System.out.println(e1);
		}
		catch (IOException e) {
	    	System.out.println("File not found: " + e);
	    	e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (UserCredentialOptionNotExistsException e1) {
			e1.setFileName(fileName);
			e1.setLineNumber(lineNumber);
			System.out.println(e1);
		} catch (RoleAlreadyExistsException e1) {
			e1.setFileName(fileName);
			e1.setLineNumber(lineNumber);
			System.out.println(e1);
		} catch (PermissionAlreadyExistsException e1) {
			e1.setFileName(fileName);
			e1.setLineNumber(lineNumber);
			System.out.println(e1);
		} catch (UserAlreadyExistsException e1) {
			e1.setFileName(fileName);
			e1.setLineNumber(lineNumber);
			System.out.println(e1);
		} catch (ResourceAlreadyExistsException e1) {
			e1.setFileName(fileName);
			e1.setLineNumber(lineNumber);
			System.out.println(e1);
		} catch (ResourceRoleAlreadyExistsException e1) {
			e1.setFileName(fileName);
			e1.setLineNumber(lineNumber);
			System.out.println(e1);
		}
		return lineNumber;
	}
}
