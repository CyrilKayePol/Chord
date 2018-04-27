import java.math.BigInteger;
import java.util.ArrayList;

public class Chord{
	private static int count = 0;
	private static ArrayList<Node> connectedNodes = new ArrayList<Node>();

	public static void createRing(Node node) throws Exception {
		node.setIsConnected(true);
		node.setPredecessor(node);
		node.setSuccessor(node);
		count++;
		connectedNodes.add(node);
		System.out.println("Successfully created chord ring with host "+node.getIPAddress());
		node.computeFingerTable();
	}
	
	public static Node getSuccessor(BigInteger big) throws Exception {
		Node temp = new Node();
		//System.out.println("big = "+big);
		
		if(connectedNodes.size() == 1) {
			temp = connectedNodes.get(0);
			
			return temp;
		}
		
		for(int a = 0;a<connectedNodes.size();a++) {
			if(-1 == big.compareTo( connectedNodes.get(a).getID())) {
				
				if(a == 0) {
					temp = connectedNodes.get(a);
					//System.out.println("HERKKLE");
				}else {
					if(temp.getID() == null) {
						temp = connectedNodes.get(a);
						//System.out.println("HERE");
						continue;
					}
					
					if(1 == temp.getID().compareTo(connectedNodes.get(a).getID())) {
						temp = connectedNodes.get(a);
						//System.out.println("here");
					}
				}
				//System.out.println(temp.getID());
			}else {
				if(a == connectedNodes.size() -1 && temp.getID() == null) {
					
					temp = getMinNode();
				}
			}
		}
		
		return temp;
		
	}
	
	public static Node getPredecessor(BigInteger big) throws Exception {
		Node temp = new Node();
		if(connectedNodes.size() == 1) {
			temp = connectedNodes.get(0);
			return temp;
		}
		for(int a = 0;a<connectedNodes.size();a++) {
			if(1 == big.compareTo( connectedNodes.get(a).getID())) {
				if(a == 0) {
					temp = connectedNodes.get(a);
				}else {
					if(temp.getID() == null) {
						temp = connectedNodes.get(a);
						continue;
					}
					
					if(-1 == temp.getID().compareTo(connectedNodes.get(a).getID())) {
						temp = connectedNodes.get(a);
					}
				}
				
			}else {
				if(a == connectedNodes.size() -1 && temp.getID() == null) {
					temp = getMaxNode();
				}
			}
		}
		
		return temp;
		
	}
	
	public static Node getMinNode() {
		Node temp = connectedNodes.get(0);
		
		for(int a = 1;a<connectedNodes.size();a++) {
			if(1 == temp.getID().compareTo(connectedNodes.get(a).getID())) {
				temp = connectedNodes.get(a);
			}
		}
		
		return temp;
	}
	
	public static Node getMaxNode() {
		Node temp = connectedNodes.get(0);
		
		for(int a = 1;a<connectedNodes.size();a++) {
			if(-1 == temp.getID().compareTo(connectedNodes.get(a).getID())) {
				temp = connectedNodes.get(a);
			}
		}
		
		return temp;
	}
	
	public static void updateFingerTables() throws Exception {
		for(int a = 0;a<connectedNodes.size();a++) {
			System.out.println("\tupdating "+connectedNodes.get(a).getID());
			connectedNodes.get(a).computeFingerTable();
		}
	}
	
	public static void updatePredecessor() throws Exception {
		for(int a = 0;a<connectedNodes.size();a++) {
			System.out.println("\tupdating predecessor"+connectedNodes.get(a).getID());
			System.out.println("before = "+connectedNodes.get(a).getPredecessor().getID());
			connectedNodes.get(a).setPredecessor(getPredecessor(connectedNodes.get(a).getID()));
			System.out.println("after = "+connectedNodes.get(a).getPredecessor().getID());
		}
	}
	public static ArrayList<Node> getConnectedNodes(){
		return connectedNodes;
	}
	
	public static void setConnectedNodes(Node n) {
		connectedNodes.add(n);
	}
	public static int getConnectedNodesCount() {
		return count;
	}
	
	public static void setCount() {
		count++;
	}
	
}