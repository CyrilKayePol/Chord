import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;
import java.math.BigInteger;

public class Node implements Serializable{
	private static final long serialVersionUID = 1L;
	private Node successor, predecessor, temp = null;
	private String ip;
	private boolean isConnected;
	private BigInteger id;
	private Node[] finger = new Node[3];
	private int port;
	/*kun dako an computed id for finger table kuntra han mga present ids,
	 * then ginset ko la an value han finger[i] as this. Meaning kun may computed value of 94 
	 * ngan id hini na node is 90, it iya finger[i] is 94
	 * another, nagigin pre niya an iya kalugaringon*/
	/*perme an host an suc*/
	public Node(String ip,int port) throws Exception {
		this.ip = ip;
		isConnected = false;
		this.port = port;
		
		for(int a = 0;a< finger.length; a++) {
			finger[a] = this;
		}
		//createNodeID();,
	}
	
	public Node() {
		
	}
	
	@SuppressWarnings("unused")
	private void createNodeID() throws NoSuchAlgorithmException {
		String text = getIPAddress()+""+port;
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
		StringBuffer hex = new StringBuffer();
		
		for(int i = 0; i< hash.length; i++) {
			String s = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) hex.append('0');
			hex.append(s);
		}
		
		this.id = new BigInteger(hex.toString(),16);
		
	}
	
	public Node findSucessor(Node n, BigInteger id) {
		BigInteger big = null;
		if(n == null && id != null) {
			big = id;
		}else if(n!=null && id == null) {
			big = n.getID();
		}
		
		System.out.println("big = "+big);
		System.out.println("suc = "+successor.getID());
		System.out.println("dis = "+this.getID());
		// big > n && big <= successor
		if(1 == big.compareTo(this.getID()) && (-1 == big.compareTo(successor.getID()) ||
				0 == big.compareTo(successor.getID()))) {
			return successor;
		}else {
			temp = successor.closestPrecedingNode(n, id);
			if(temp == this) {
				return this;
			}
			return temp.findSucessor(n,id);
		}
		
	}
	
	public Node closestPrecedingNode(Node n, BigInteger id) {
		System.out.println("successor.successor = "+successor.id);
		return this.successor;
	}
	
	public void create() {
		predecessor = this;
		successor = this;
		//updateFingerTable();
		new Stabilize(this).start();
	}
	
	public void join(Node n) {
		predecessor = null;
		successor = n.findSucessor(this, null);
		System.out.println("--------------------------------");
		finger[0] = successor;
		//updateFingerTable();
		
		//new Stabilize(this).start();
	}
	
	public void updateFingerTable() {
		for(int a = 1; a<finger.length;a++) {
			finger[a] = this.findSucessor(null, computeFingerTable(a));
			System.out.println(this.getPort()+" updated "+a+": "+finger[a].getPort());
		}
	}
	
	public BigInteger computeFingerTable(int i) {
			BigInteger big = new BigInteger("0");
			BigInteger two = new BigInteger("2");
			big = big.add(this.getID().add(two.pow(i)));
			big = big.mod(two.pow(160));
			
			
		return big;
	}
	
	public Node getSuccessor() {
		return successor;
	}
	
	public void setSuccessor(Node node) {
		this.successor = node;
	}
	
	public Node getPredecessor() {
		return predecessor;
	}
	
	public void setPredecessor(Node node) {
		this.predecessor = node;
	}
	
	public String getIPAddress() {
		return ip;
	}
	
	
	public boolean getIsConnected() {
		return isConnected;
	}
	
	public void setIsConnected(boolean bool) {
		this.isConnected = bool;
	}
	
	public BigInteger getID() {
		return id;
	}
	
	public void setID(BigInteger id) {
		this.id = id;
	}
	
	public int getPort() {
		return port;
	}
	
	public Node[] getFingerTable(){
		return finger;
	}
	
	public void setFingerTable(Node[] finger) {
		this.finger = finger;
	}
	
}
