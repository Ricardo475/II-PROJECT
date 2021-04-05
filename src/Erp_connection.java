import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Erp_connection extends Thread{
		private DatagramSocket socket;
	    private boolean running;

	    public Erp_connection() throws SocketException {
	        socket = new DatagramSocket(54321);
	    }

	    public void run() {
	        running = true;
	      
	        while (running) {
	        	  byte[] buf = new byte[1500];
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
	            int port = packet.getPort();
	            packet = new DatagramPacket(buf, buf.length, address, port);
	            String received 
	              = new String(packet.getData(), 0, packet.getLength());
	            //System.out.println(received);
	            String[] aux=received.split("]>");
	          
	            System.out.println("new:"+aux[1]);
	            
	            if (received.equals("end")) {
	                running = false;
	                continue;
	            }
	           
	        }
	        socket.close();
	    }
}
