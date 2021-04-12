package mes;

public class paths {
	
	String element1, element2;
	//boolean connected;
	
	public paths(String String1, String String2) {
		
		element1 = String1;
		element2 = String2;
		//connected = connect;
	}
	
	boolean isConnected(String String1, String String2) {
		
		if((String1.compareTo(element1)==0) &&  (String2.compareTo(element2)==0))
			return true;
		
		else return false;
		
	}
	
	void print_path() {
		
		System.out.println("PATH " + this.element1 + "<->" + this.element2);
	}

}
