package cscie97.asn2.housemate.model;


public class HouseAlreadyExistsException extends Exception {
	private String filename;
    private int lineNumber;
    private String houseNameContext;
    private String description;
    private String commandContext;
	public HouseAlreadyExistsException() {
		super();
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
	public String getHouseNameContext() {
		return houseNameContext;
	}
	public void setHouseNameContext(String houseNameContext) {
		this.houseNameContext = houseNameContext;
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
		return "HouseAlreadyExistsException [filename=" + filename + ", lineNumber=" + lineNumber
				+ ", houseNameContext=" + houseNameContext + ", description=" + description + ", commandContext="
				+ commandContext + "]";
	}
	
}
