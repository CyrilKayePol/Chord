import java.io.*;
import java.net.*;

public class Sender extends Thread {
	private DatagramSocket socket;
	private byte[] data;
	private InetAddress address;
	private int port;
	
	
	public Sender(DatagramSocket socket, byte[] data, InetAddress address, int port) {
		this.socket = socket;
		this.data = data;
		this.address = address;
		this.port = port;
	}
	public void run() {
		
			 try {
				 DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
				 socket.send(sendPacket);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
