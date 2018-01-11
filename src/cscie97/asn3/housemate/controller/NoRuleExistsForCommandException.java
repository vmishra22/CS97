package cscie97.asn3.housemate.controller;

public class NoRuleExistsForCommandException extends Exception {
	private String filename;
    private int lineNumber;
    private String description;
    private String commandContext;
	public NoRuleExistsForCommandException() {
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
		return "NoRuleExistsForCommandException [filename=" + filename + ", lineNumber=" + lineNumber
			    + ", description=" + description + ", commandContext="
				+ commandContext + "]";
	}
}
