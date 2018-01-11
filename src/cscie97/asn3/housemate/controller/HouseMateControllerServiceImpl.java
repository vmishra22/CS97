package cscie97.asn3.housemate.controller;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Queue;
import cscie97.asn2.housemate.model.HouseMateModelService;

public class HouseMateControllerServiceImpl implements HouseMateControllerService {

	@SuppressWarnings("unused")
	private Observable observable;
	private Queue<ControllerCommand> commandsQueue = new LinkedList<ControllerCommand>();
	private ControllerCommandFactory commandFactory = new ControllerCommandFactoryImpl();
	
	public HouseMateControllerServiceImpl(Observable obs){
		this.observable = obs;
		obs.addObserver(this);
	}
	
	@Override
	public void update(Observable obs, Object arg) {
		if(obs instanceof HouseMateModelService){
			ControllerCommand command = null;
			try {
				command = commandFactory.createControllerCommand(arg, (HouseMateModelService)obs);
			} catch (NoRuleExistsForCommandException e) {
				//System.out.println(e.toString());
			}
			if(command != null){
				commandsQueue.add(command);
				executeCommandFromQueue();
			}
		}
	}

	private void executeCommandFromQueue() {
		try{
			ControllerCommand headCommand = commandsQueue.remove();
			if(headCommand != null)
				headCommand.execute();
		}catch(NoSuchElementException e){
			System.out.println(e.getMessage());
		}
		
	}

}
