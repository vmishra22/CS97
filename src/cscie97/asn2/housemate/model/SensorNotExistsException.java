package cscie97.asn2.housemate.model;

public class SensorNotExistsException extends Exception {
	private String filename;
    private int lineNumber;
    private String sensorNameContext;
    private String description;
    private String commandContext;
	public SensorNotExistsException() {
		super();
	}
	 /**
     * Filename of the file being input when the Sensor exception occurred.
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
	public String getSensorNameContext() {
		return sensorNameContext;
	}
	public void setSensorNameContext(String sensorNameContext) {
		this.sensorNameContext = sensorNameContext;
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
		return "SensorNotExistsException [filename=" + filename + ", lineNumber=" + lineNumber + ", sensorNameContext="
				+ sensorNameContext + ", description=" + description + ", commandContext=" + commandContext + "]";
	}
	
}
