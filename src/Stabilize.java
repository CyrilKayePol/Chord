import java.math.BigInteger;

public class Stabilize extends Thread {
	private Node node;
	public Stabilize(Node n) {
		this.node = n;
	}
	public void run() {
		while(true) {
			stabilize();
		}
		
	}
	public void stabilize() {
		Node x = node.getSuccessor().getPredecessor();
		if(x!=null) {
			BigInteger big = x.getID();
			// x> n && x < successor
			if(1 == big.compareTo(node.getID()) && -1 == big.compareTo(node.getSuccessor().getID())) {
				node.setSuccessor(x);
				
			}
		}
		
		notifyNode(node);
	}
	
	public void notifyNode(Node n) {
		BigInteger big = n.getID();
		if(node.getPredecessor()==null ||
				(1 == big.compareTo(node.getPredecessor().getID()) && -1 == big.compareTo(node.getID()))){
			node.setPredecessor(n);
			
		}
	}
}
