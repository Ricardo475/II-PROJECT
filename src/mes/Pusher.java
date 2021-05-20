package mes;

public class Pusher {
	
	int totalPieces_unloaded, p1Pieces_unloaded, p2Pieces_unloaded, p3Pieces_unloaded, p4Pieces_unloaded, p5Pieces_unloaded, p6Pieces_unloaded, p7Pieces_unloaded, p8Pieces_unloaded, p9Pieces_unloaded;
	int pusherID;
	//int piecesInRoller;
	
	public void setPusher(int id) {
		
		this.pusherID = id;
		this.p1Pieces_unloaded = 0;
		this.p2Pieces_unloaded = 0;
		this.p3Pieces_unloaded = 0;
		this.p4Pieces_unloaded = 0;
		this.p5Pieces_unloaded = 0;
		this.p6Pieces_unloaded = 0;
		this.p7Pieces_unloaded = 0;
		this.p8Pieces_unloaded = 0;
		this.p9Pieces_unloaded = 0;
		this.totalPieces_unloaded = 0;
		//this.piecesInRoller = 0;
		Main.DB.store_pusher(this);
	}
	public Pusher()
	{
		
	}
	public Pusher(int pusherID,int p1,int p2,int p3,int p4,int p5,int p6,int p7,int p8,int p9)
	{
		this.pusherID = pusherID;
		this.p1Pieces_unloaded = p1;
		this.p2Pieces_unloaded = p2;
		this.p3Pieces_unloaded = p3;
		this.p4Pieces_unloaded = p4;
		this.p5Pieces_unloaded = p5;
		this.p6Pieces_unloaded = p6;
		this.p7Pieces_unloaded = p7;
		this.p8Pieces_unloaded = p8;
		this.p9Pieces_unloaded = p9;
		this.totalPieces_unloaded = p1+p2+p3+p4+p5+p6+p7+p8+p9;
	}
	public void print_Pusher() {
		
		System.out.println("PUSHER NO:" + this.pusherID /*+"|| Pieces In the Rollers: " + this.piecesInRoller */+ "|| Quantity Unloaded: " + this.totalPieces_unloaded + "\n--P1:" + this.p1Pieces_unloaded + "\n--P2:" + this.p2Pieces_unloaded + "\n--P3:" + this.p3Pieces_unloaded + "\n--P4:" + this.p4Pieces_unloaded + "\n--P5:" + this.p5Pieces_unloaded + "\n--P6:" + this.p6Pieces_unloaded + "\n--P7:" + this.p7Pieces_unloaded + "\n--P8:" + this.p8Pieces_unloaded + "\n--P9:" + this.p9Pieces_unloaded );
	}
	
	public void updatePusher(String pieceType) {
		
		if(pieceType == "P1") p1Pieces_unloaded++;
		else if(pieceType == "P2") p2Pieces_unloaded++;
		else if(pieceType == "P3") p3Pieces_unloaded++;
		else if(pieceType == "P4") p4Pieces_unloaded++;
		else if(pieceType == "P5") p5Pieces_unloaded++;
		else if(pieceType == "P6") p6Pieces_unloaded++;
		else if(pieceType == "P7") p7Pieces_unloaded++;
		else if(pieceType == "P8") p8Pieces_unloaded++;
		else if(pieceType == "P9") p9Pieces_unloaded++;
		
		totalPieces_unloaded++;
		Main.DB.store_pusher(this);
		//piecesInRoller++;
	}
	

}
