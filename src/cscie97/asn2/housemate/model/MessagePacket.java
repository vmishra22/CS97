package cscie97.asn2.housemate.model;

public class MessagePacket {

	private String subjectIdentifier = null; //Sensor  which originated the command
	private String targetIdentifier = null;
	private String commandIdentifier = null; //Command like 'light off'
	private DeviceType originatingDeviceType; //Sensor/Appliance type of originating command
	private DeviceType targetDeviceType; //Sensor/Appliance type of originating command
	private boolean genericAvaCommand = false;
	private boolean avaQuestion = false;
	private String applianceStatusName = null;
	private String voice_print = null;

	public DeviceType getOriginatingDeviceType() {
		return originatingDeviceType;
	}

	public void setOriginatingDeviceType(DeviceType originatingDeviceType) {
		this.originatingDeviceType = originatingDeviceType;
	}

	public DeviceType getTargetDeviceType() {
		return targetDeviceType;
	}

	public void setTargetDeviceType(DeviceType targetDeviceType) {
		this.targetDeviceType = targetDeviceType;
	}

	public MessagePacket() {
		originatingDeviceType = DeviceType.DEFAULT;
		targetDeviceType = DeviceType.DEFAULT;
	}
	
	public boolean isGenericAvaCommand() {
		return this.genericAvaCommand;
	}

	public void setGenericAvaCommand(String genericAvaCommand) {
		if(genericAvaCommand.equalsIgnoreCase("statusGenericCommand"))
			this.genericAvaCommand = true;
	}

	public boolean isAvaQuestion() {
		return avaQuestion;
	}

	public void setAvaQuestion(String avaQuestion) {
		if(avaQuestion.equalsIgnoreCase("statusQuestion"))
			this.avaQuestion = true;
	}

	public String getSubjectIdentifier() {
		return subjectIdentifier;
	}
	public void setSubjectIdentifier(String subjectIdentifier) {
		this.subjectIdentifier = subjectIdentifier;
	}
	
	public String getCommandIdentifier() {
		return commandIdentifier;
	}
	public void setCommandIdentifier(String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
	}

	public String getTargetIdentifier() {
		return targetIdentifier;
	}

	public void setTargetIdentifier(String targetIdentifier) {
		this.targetIdentifier = targetIdentifier;
	}

	public void setApplianceStatusName(String statusName) {
		this.applianceStatusName = statusName;
	}
	
	public String getApplianceStatusName() {
		return this.applianceStatusName;
	}

	public void setVoicePrint(String voice_print) {
		this.voice_print = voice_print;
	}
	public String getVoicePrint() {
		return this.voice_print;
	}
	

}
