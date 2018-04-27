import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {
	Node node;
	DatagramSocket socket;
	DatagramPacket packet;
	Sender send;
	int port = 4598;
	int hostPort = 8888;
	String nodeAdd;
	int nodePort;
	private byte[] buf = new byte[1024000];
	
	public Client() throws Exception {
		socket = new DatagramSocket(port);
		node = new Node(InetAddress.getLocalHost().getHostAddress(), port);
		// ask host to join
		extractAddress(join("INIT", hostPort));
		
		// ask random node to join
		join("JOIN_"+node.getID()+"_"+InetAddress.getLocalHost().getHostAddress(), nodePort);
		
		run();
	}
	
	public void run() throws UnknownHostException {
		while(true) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	 
			String received = new String(packet.getData(), 0, packet.getLength());
			System.out.println("\n\t\t["+packet.getAddress()+": "+received+"]");
			String[] string = received.split("_");
			 if(string[0].equals("JOIN")){
				new JoinProcess(socket, new BigInteger(string[1]), string[2], packet.getPort()).start();
				
			}
		}
	}
	
	private String join(String message, int hostPort) throws UnknownHostException {
		System.out.println("Requesting to join. . .");

		byte[] buf = message.getBytes();
		
		send = new Sender(socket, buf, InetAddress.getLocalHost(), hostPort);
		send.start();
		
		buf = new byte[10240000];
		packet = new DatagramPacket(buf, buf.length);
		
		System.out.println("waiting for response. . .");
		try {
			socket.receive(packet);
		}catch(Exception e) {e.printStackTrace();}
		
		String received = new String(packet.getData(), 0, packet.getLength());
		System.out.println("\n\t\t["+packet.getAddress()+": "+received+"]");
		
		return received;
	}
	
	private void extractAddress(String s) {
		String[] string = s.split("_");
		nodeAdd = string[1];
		System.out.println("extracted: "+nodeAdd);
		nodePort = Integer.parseInt(string[2]);
		System.out.println("extracted: "+nodePort);
	}

	
	public static void main(String[] args) throws Exception {
		new Client();
	}
}
