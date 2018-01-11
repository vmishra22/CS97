package cscie97.asn3.housemate.controller;


import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn2.housemate.model.MessagePacket;

public class BeerOrderCommand implements ControllerCommand {

	HouseMateModelService modelService;
	String auth_token = "";
	MessagePacket packet = null;
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the BeerOrderCommand class.
     */
	BeerOrderCommand(MessagePacket packet, HouseMateModelService service){
		this.modelService = service;
		this.packet = packet;
	}
	
	/**
	 * 
	 * Interface method implementation for execute().
	 */
	@Override
	public void execute() {
		System.out.println("Email to store for ordering more beer");
	}

}
