package payload;

public class ProgramPayload {

	private Integer programId;
	private String programName;
	private String programDescription;
	private String programStatus;
	
	public Integer getProgramId() {
		return programId;
		
	}
	
	public void setProgramId(Integer programId) {
		
		this.programId=programId;
	}
	
	public String getProgramName() {
		return programName;
		
	}
	
	public void setProgramName(String programName) {
		
		this.programName=programName;
	}
	
	public String getProgramDescription() {
		return programDescription;
		
	}
	
	public void setProgramDescription(String programDescription) {
		
		this.programDescription=programDescription;
	}
	
	public String getProgramStatus() {
		return programStatus;
		
	}
	
	public void setProgramStatus(String programStatus) {
		
		this.programStatus=programStatus;
	}
	
}
