import java.math.BigInteger;
import java.util.ArrayList;

public class Chord{
	
	public Chord() {
		System.out.println("Reinitialized chord!");
	}
	public static void createRing(Node node) throws Exception {
		node.setIsConnected(true);
		node.setPredecessor(node);
		node.setSuccessor(node);
		
		Host.connectedNodes.add(node);
		System.out.println("Successfully created chord ring with host "+node.getIPAddress());
		
	}
	
	public static Node getSuccessor(BigInteger big) throws Exception {
		Node temp = new Node();
		System.out.println("size: "+Host.connectedNodes.size());
		if(Host.connectedNodes.size() == 1) {
			temp = Host.connectedNodes.get(0);
			
			return temp;
		}
		
		for(int a = 0;a<Host.connectedNodes.size();a++) {
			if(-1 == big.compareTo( Host.connectedNodes.get(a).getID())) {
				
				if(a == 0) {
					temp = Host.connectedNodes.get(a);
					
				}else {
					if(temp.getID() == null) {
						temp = Host.connectedNodes.get(a);
						
						continue;
					}
					
					if(1 == temp.getID().compareTo(Host.connectedNodes.get(a).getID())) {
						temp = Host.connectedNodes.get(a);
						
					}
				}
				
			}else {
				if(a == Host.connectedNodes.size() -1 && temp.getID() == null) {
					
					temp = getMinNode();
				}
			}
		}
		
		return temp;
		
	}
	
	public static Node getPredecessor(BigInteger big) throws Exception {
		Node temp = new Node();
		if(Host.connectedNodes.size() == 1) {
			temp = Host.connectedNodes.get(0);
			return temp;
		}
		for(int a = 0;a<Host.connectedNodes.size();a++) {
			if(1 == big.compareTo( Host.connectedNodes.get(a).getID())) {
				if(a == 0) {
					temp = Host.connectedNodes.get(a);
				}else {
					if(temp.getID() == null) {
						temp = Host.connectedNodes.get(a);
						continue;
					}
					
					if(-1 == temp.getID().compareTo(Host.connectedNodes.get(a).getID())) {
						temp = Host.connectedNodes.get(a);
					}
				}
				
			}else {
				if(a == Host.connectedNodes.size() -1 && temp.getID() == null) {
					temp = getMaxNode();
				}
			}
		}
		
		return temp;
		
	}
	
	public static Node getMinNode() {
		Node temp = Host.connectedNodes.get(0);
		
		for(int a = 1;a<Host.connectedNodes.size();a++) {
			if(1 == temp.getID().compareTo(Host.connectedNodes.get(a).getID())) {
				temp = Host.connectedNodes.get(a);
			}
		}
		
		return temp;
	}
	
	public static Node getMaxNode() {
		Node temp = Host.connectedNodes.get(0);
		
		for(int a = 1;a<Host.connectedNodes.size();a++) {
			if(-1 == temp.getID().compareTo(Host.connectedNodes.get(a).getID())) {
				temp = Host.connectedNodes.get(a);
			}
		}
		
		return temp;
	}
	
	public static void updateFingerTables() throws Exception {
		for(int a = 0;a<Host.connectedNodes.size();a++) {
			System.out.println("\tupdating "+Host.connectedNodes.get(a).getID());
			//Host.connectedNodes.get(a).computeFingerTable();
		}
	}
	
	public static void updatePredecessor() throws Exception {
		for(int a = 0;a<Host.connectedNodes.size();a++) {
			System.out.println("\tupdating predecessor"+Host.connectedNodes.get(a).getID());
			System.out.println("before = "+Host.connectedNodes.get(a).getPredecessor().getID());
			Host.connectedNodes.get(a).setPredecessor(getPredecessor(Host.connectedNodes.get(a).getID()));
			System.out.println("after = "+Host.connectedNodes.get(a).getPredecessor().getID());
		}
	}
	public static ArrayList<Node> getConnectedNodes(){
		return Host.connectedNodes;
	}
	
	public static void setConnectedNodes(Node n) {
		Host.connectedNodes.add(n);
	}
	
}