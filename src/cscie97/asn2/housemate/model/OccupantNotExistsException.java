package cscie97.asn2.housemate.model;

public class OccupantNotExistsException extends Exception {

	private String filename;
    private int lineNumber;
    private String occupantNameContext;
    private String description;
    private String commandContext;
	public OccupantNotExistsException() {
		super();
	}
	
	/**
     * Filename of the file being input when the Occupant exception occurred.
     */
	public String getFileName() {
		return filename;
	}
	public void setFileName(String filename) {
		this.filename = filename;
	}
	/**
     * Line number where exception occurred.
     */
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	/**
     * Name that caused the exception
     */
	public String getOccupantNameContext() {
		return occupantNameContext;
	}
	public void setOccupantNameContext(String occupantNameContext) {
		this.occupantNameContext = occupantNameContext;
	}
	
	/**
     * Description of the exception
     */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
     * Content that caused the exception.
     */
	public String getCommandContext() {
		return commandContext;
	}
	public void setCommandContext(String commandContext) {
		this.commandContext = commandContext;
	}
	@Override
	public String toString() {
		return "OccupantAlreadyExistsException [filename=" + filename + ", lineNumber=" + lineNumber
				+ ", occupantNameContext=" + occupantNameContext + ", description=" + description + ", commandContext="
				+ commandContext + "]";
	}

	

}
