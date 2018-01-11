package cscie97.asn2.housemate.model;

public class HouseNotExistsException extends Exception {
	
	private String filename;
    private int lineNumber;
    private String houseNameContext;
    private String description;
    private String commandContext;
	public HouseNotExistsException() {
		super();
	}
	/**
     * Filename of the file being input when the House exception occurred.
     * @return file name
     */
	public String getFileName() {
		return filename;
	}
	public void setFileName(String filename) {
		this.filename = filename;
	}
	/**
     * Line number where exception occurred.
     * @return line number
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
	public String getHouseNameContext() {
		return houseNameContext;
	}
	public void setHouseNameContext(String houseNameContext) {
		this.houseNameContext = houseNameContext;
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
		return "HouseNotExistsException [filename=" + filename + ", lineNumber=" + lineNumber + ", houseNameContext="
				+ houseNameContext + ", description=" + description + ", commandContext=" + commandContext + "]";
	}
    
}
