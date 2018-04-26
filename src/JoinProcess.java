import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;

public class JoinProcess extends Thread {
	BigInteger id;
	ArrayList<Node> connectedNodes = new ArrayList<Node>();
	String add;
	int port;
	public JoinProcess(BigInteger id, String add, int port) {
		this.id = id;
		connectedNodes = Chord.getConnectedNodes();
		this.add = add;
		this.port = port;
		
	}
	
	private void procedure() throws Exception {
		Node successor = Chord.getSuccessor(id);
		Node predecessor = Chord.getPredecessor(id);
		
		Node n = new Node(add.toString(),port);
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
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		stop();
		
	}
	
	
	
	
}
