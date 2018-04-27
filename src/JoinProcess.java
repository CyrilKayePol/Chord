import java.math.BigInteger;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class JoinProcess extends Thread {
	BigInteger id;
	ArrayList<Node> connectedNodes = new ArrayList<Node>();
	String add;
	int port;
	DatagramSocket socket;
	public JoinProcess(DatagramSocket socket, BigInteger id, String add, int port) {
		this.id = id;
		connectedNodes = Chord.getConnectedNodes();
		this.add = add;
		this.port = port;
		this.socket = socket;
		
	}
	
	private void procedure() throws Exception {
		Node successor = Chord.getSuccessor(id);
		Node predecessor = Chord.getPredecessor(id);
		
		Node n = new Node(add,port);
		n.setIsConnected(true);
		n.setSuccessor(successor);
		n.setPredecessor(predecessor);
		n.computeFingerTable();
		Chord.setConnectedNodes(n);
		Chord.setCount();
		Chord.updateFingerTables();
		Chord.updatePredecessor();
		
		System.out.println("created a node with "+add+"_"+port);
		
	}
	
	public void run() {
		try {
			procedure();
			String message = "Done";
			byte[] buf = message.getBytes();
			
			Sender send = new Sender(socket, buf, InetAddress.getByName(add), port);
			send.start();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		stop();
		
	}
	
	
	
	
}
