package mes;

public class ListOrders extends Order{

	String from, to;
	int quantityTotal, quantityDone, quantityRunning, quantityWaiting,timeSent,timeReceived,maxDelay,penalty,startTime,endTime,penaltyIncurred;
	
	
	public ListOrders(int orderNumber) {
		super(orderNumber,0,0,0);
		
	}
	
	
	void setParameters(String from, String to, int quantityTotal,int quantityDone, int quantityRunning, int quantityWaiting,int timeSent, int timeReceived, int maxDelay, int penalty, int startTime, int endTime, int penaltyIncurred) {
		
		this.from = from;
		this.to = to;
		this.quantityTotal = quantityTotal;
		this.quantityDone = quantityDone;
		this.quantityRunning = quantityRunning;
		this.quantityWaiting = quantityWaiting;
		this.timeSent = timeSent;
		this.timeReceived = timeReceived;
		this.maxDelay = maxDelay;
		this.penalty = penalty;
		this.startTime = startTime;
		this.endTime = endTime;
		this.penaltyIncurred = penaltyIncurred;
	}
	

}
