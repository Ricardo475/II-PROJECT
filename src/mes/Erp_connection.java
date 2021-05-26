package mes;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;





public class Erp_connection extends Thread{
		public DatagramSocket socket;
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
	            Main.packet=packet;
	            System.out.println("ADDRESS: "+address+" PORT: "+packet.getPort());
	            int port = packet.getPort();
	            packet = new DatagramPacket(buf, packet.getLength(), address, port);
	            String received 
	              = new String(packet.getData(), 0, packet.getLength());
	            //System.out.println(received);
	            /*String[] aux=received.split("]>");
	          
	            System.out.println("new:"+aux[1]);*/
	            //System.out.print(received);
	            write(received);
	            int duration= (((int)System.currentTimeMillis()-Main.start)/1000);
				//System.out.println(duration);
				try {
					Main.parse.parse(received,Main.OL,duration);
				} catch (ParserConfigurationException | IOException | SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
	           
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
