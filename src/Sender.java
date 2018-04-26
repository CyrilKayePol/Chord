import java.io.*;
import java.net.*;

public class Sender extends Thread {
	private DatagramSocket socket;
	private byte[] buf;
	private InetAddress address;
	private int port;
	
	
	public Sender(DatagramSocket socket, byte[] buf, InetAddress address, int port) {
		this.socket = socket;
		this.buf = buf;
		this.address = address;
		this.port = port;
	}
	public void run() {
		
			 try {
				 DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
				
				 socket.send(packet);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
