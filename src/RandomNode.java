import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

public class RandomNode extends Thread {
	DatagramSocket socket;
	InetAddress address;
	int port;
	public RandomNode(DatagramSocket socket, InetAddress address, int port) {
		this.socket = socket;
		this.address = address;
		this.port = port;
	}
	
	public void run() {
		Random rand = new Random();
		int index = rand.nextInt(Chord.getConnectedNodesCount());
		
		ArrayList<Node> nodes = Chord.getConnectedNodes();
		String message = "RAND_"+nodes.get(index).getIPAddress()+"_"+nodes.get(index).getPort();
		byte[] buf = message.getBytes();
		
		Sender send = new Sender(socket, buf, address, port);
		send.start();
		
		System.out.println("HOST sent "+message+" successfully.");
		stop();
	}
}
