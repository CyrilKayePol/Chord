import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class Node{
	private Node successor, predecessor;
	private String ip;
	private boolean isConnected;
	private BigInteger id;
	private Node[] finger = new Node[160];
	private int port;
	
	public Node(String ip,int port) throws Exception {
		this.ip = ip;
		isConnected = false;
		this.port = port;
		createNodeID();
	}
	
	public Node() {
		
	}
	
	
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
		
		setID(new BigInteger(hex.toString(),16));
		
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
	
	public void computeFingerTable() throws Exception {
		System.out.println("Finger table of ["+id+"]");
		for(int i = 0;i<3;i++) {
			
			BigInteger big = new BigInteger("0");
			BigInteger two = new BigInteger("2");
			big = big.add(id.add(two.pow(i)));
			big = big.mod(two.pow(160));
			
			Node suc = Chord.getSuccessor(big);
			
			finger[i] = suc;
			System.out.println(suc.getID());
		}
	}
	
}
