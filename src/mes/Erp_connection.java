package mes;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;





public class Erp_connection extends Thread{
		private DatagramSocket socket;
	    private boolean running;
	    String xml;
	    private float start;
	    
	    public Erp_connection(OrdersState O) throws SocketException {
	        socket = new DatagramSocket(54321);
	        this.xml="aaa";
	       
	    }

	    public void run() {
	        running = true;
	      
	        while (running) {
	        	
	        	byte[] buf =   new byte[1500];
	            DatagramPacket packet 
	              = new DatagramPacket(buf, buf.length);
	            try {
					socket.receive(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            running = true;
		  
	            InetAddress address = packet.getAddress();
	            Main.address=address;
	            System.out.println("ADDRESS: "+address);
	            int port = packet.getPort();
	            packet = new DatagramPacket(buf, packet.getLength(), address, port);
	            String received 
	              = new String(packet.getData(), 0, packet.getLength());
	            //System.out.println(received);
	            /*String[] aux=received.split("]>");
	          
	            System.out.println("new:"+aux[1]);*/
	            //System.out.print(received);
	            write(received);
	           
	           
	           
	        }
	        socket.close();
	    }
	   synchronized public String getXML()
	    {
			return this.xml;
	    	
	    }
	   synchronized private void write(String s)
	   {
		   this.xml=s;
	   }
}
