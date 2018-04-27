import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Host{
	private int port = 8888;
	private DatagramSocket socket;
	private byte[] buf = new byte[1024000];
	private ArrayList<Node> connectedNodes;
	
	public Host() throws Exception {
		connectedNodes = new ArrayList<Node>();
		socket = new DatagramSocket(port);
		createRing();
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
			String[] string = received.split("_");
			if(string[0].equals("INIT")) {
				new RandomNode(socket, packet.getAddress(), packet.getPort()).start();
			}else if(string[0].equals("JOIN")){
				new JoinProcess(socket, new BigInteger(string[1]), string[2], packet.getPort()).start();
				
			}
		}
	}
	
	private void createRing() throws Exception {
		Chord.createRing(new Node(InetAddress.getLocalHost().getHostAddress(), port));
	}
	
	public static void main(String[] args) throws Exception{
		new Host();
	}
}
