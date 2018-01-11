package cscie97.asn3.housemate.controller;

import cscie97.asn2.housemate.model.HouseMateModelService;

public abstract class ControllerCommandFactory {
	
	abstract ControllerCommand createControllerCommand(Object arg, HouseMateModelService modelService) throws NoRuleExistsForCommandException;
	
}
