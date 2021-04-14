package mes;

public class TransformationTable {
	
	String startingPart, producedPart;
	String toolNeeded;
	int processTimeSeconds;
	
	public TransformationTable(String startingPart, String producedPart,String toolNeeded, int processTimeSeconds){
	
		this.startingPart = startingPart;
		this.producedPart = producedPart;
		this.toolNeeded = toolNeeded;
		this.processTimeSeconds = processTimeSeconds;
	}
	
	
	public boolean existsTranformationInTable(String startingPart, String producedPart) {
		
		if(this.startingPart.compareTo(startingPart)==0 && this.producedPart.compareTo(producedPart)==0)
			return true;
		
		else return false;
		
	}
	
	
	public String producedPartByStartingPart(String startingPart) {
		
		if(this.startingPart.compareTo(startingPart)==0)
			return this.producedPart;
		else return "0";
		
	}
	
	public String get_toolNeeded(String startingPart, String producedPart) {
		
		if(existsTranformationInTable(startingPart, producedPart))
			return this.toolNeeded;
		else return "-1";
		
	}

}
