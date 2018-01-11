package cscie97.asn3.housemate.controller;

import cscie97.asn2.housemate.model.DeviceType;
import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn2.housemate.model.MessagePacket;

public class ControllerCommandFactoryImpl extends ControllerCommandFactory {

	/**
     * Default constructor
     * Uses package visibility to restrict creation of the ControllerCommandFactoryImpl class.
     */
	ControllerCommandFactoryImpl() {
		super();
		
	}

	@Override
	ControllerCommand createControllerCommand(Object arg, HouseMateModelService modelService) throws NoRuleExistsForCommandException{
		ControllerCommand command = null;
		if(arg instanceof MessagePacket){
			MessagePacket packet = (MessagePacket) arg;
			if(packet.getOriginatingDeviceType() == DeviceType.AVA)
			{
				if(packet.getCommandIdentifier().equalsIgnoreCase("open_door")){
					command = new DoorOpenCommand(packet, modelService);
				}else if(packet.getCommandIdentifier().equalsIgnoreCase("close_door")){
					command = new DoorCloseCommand(packet, modelService);
				}else if(packet.getCommandIdentifier().equalsIgnoreCase("lights_on")){
					command = new LightOnCommand(packet, modelService);
				}else if(packet.getCommandIdentifier().equalsIgnoreCase("lights_off")){
					command = new LightOffCommand(packet, modelService);
				}else if(packet.getCommandIdentifier().equalsIgnoreCase("yes_order_beer")){
					command = new BeerOrderCommand(packet, modelService);
				}else if(packet.isGenericAvaCommand()){
					command = new ApplianceAndAvaSendResponseCommand(packet, modelService);
				}else if(packet.isAvaQuestion()){
					command = new AvaSendResponseCommand(packet, modelService);
				}
			}else if(packet.getOriginatingDeviceType() == DeviceType.CAMERA){
				if(packet.getCommandIdentifier().equalsIgnoreCase("statusDetected")){
					command = new OccupantDetectedCommand(packet, modelService);
				}else if(packet.getCommandIdentifier().equalsIgnoreCase("statusLeaving")){
					command = new OccupantLeavingCommand(packet, modelService);
				}else if(packet.getCommandIdentifier().equalsIgnoreCase("statusActive")){
					command = new OccupantActiveCommand(packet, modelService);
				}else if(packet.getCommandIdentifier().equalsIgnoreCase("statusInactive")){
					command = new OccupantInactiveCommand(packet, modelService);
				}
			}else if(packet.getOriginatingDeviceType() == DeviceType.SMOKE_DETECTOR){
				if(packet.getCommandIdentifier().equalsIgnoreCase("statusSmokeDetector")){
					command = new SmokeDetectorFireCommand(packet, modelService);
				}
			}else if(packet.getOriginatingDeviceType() == DeviceType.OVEN){
				if(packet.getApplianceStatusName().equalsIgnoreCase("timeToCook")){
					command = new OvenTimeToCookCommand(packet, modelService);
				}
			}else if(packet.getOriginatingDeviceType() == DeviceType.REFRIGERATOR){
				if(packet.getApplianceStatusName().equalsIgnoreCase("beerCount")){
					command = new RefrigeratorBeerChangeCommand(packet, modelService);
				}
			}
			
			if(command == null){
				NoRuleExistsForCommandException he = new NoRuleExistsForCommandException();
				he.setDescription("No rule exists for the command");
				he.setCommandContext(packet.getCommandIdentifier());
				throw he;
			}
		}
		
		return command;
	}

}
