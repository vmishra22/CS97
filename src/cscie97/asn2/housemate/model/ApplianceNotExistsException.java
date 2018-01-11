package cscie97.asn2.housemate.model;

public class ApplianceNotExistsException extends Exception {
	private String filename;
    private int lineNumber;
    private String applianceNameContext;
    private String description;
    private String commandContext;
    
	public ApplianceNotExistsException()
    {
    }

	public String getFileName() {
		return filename;
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getapplianceNameContext() {
		return applianceNameContext;
	}

	public void setapplianceNameContext(String applianceName) {
		this.applianceNameContext = applianceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCommandContext() {
		return commandContext;
	}

	public void setCommandContext(String commandContext) {
		this.commandContext = commandContext;
	}

	@Override
	public String toString() {
		return "ApplianceNotExistsException [filename=" + filename + ", lineNumber=" + lineNumber
				+ ", applianceNameContext=" + applianceNameContext + ", description=" + description
				+ ", commandContext=" + commandContext + "]";
	}

	

}
