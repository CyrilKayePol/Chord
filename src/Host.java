import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/*problems include:
 * 1) how to compute finger table correctly
 * 2) update finger table
 * 3) traverse the chord ring to find successor
 * 4) how to update predecessor*/
public class Host{
	private int port = 8888;
	private DatagramSocket socket;
	private byte[] buf = new byte[1024000];
	public static ArrayList<Node> connectedNodes = new ArrayList<Node>();
	private Node node;
	private Object obj;
	
	
	public Host() throws Exception {
		socket = new DatagramSocket(port);
		createRing();
		//new Stabilize(node).start();
		run();
	}
	
	public void run() {
		while(true) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
				byte[] data = packet.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				
				obj = is.readObject();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			Node newNode = null;
			if( obj instanceof String) {
				
				new RandomNode(socket, packet.getAddress(), packet.getPort()).start();
				
			}else if(obj instanceof Node) {
				newNode = (Node) obj;
				if(newNode.getIsConnected()) {
					connectedNodes.add(newNode);
					System.out.println("Members: ");
					for(int a = 0;a<connectedNodes.size();a++) {
						System.out.println(connectedNodes.get(a).getPort());
					}
				}else {
					new JoinProcess(socket,node ,newNode).start();
				}
				
			}
			
		}
	}
	
	private void createRing() throws Exception {
		node = new Node(InetAddress.getLocalHost().getHostAddress(), port);
		node.setID(new BigInteger("56"));
		node.setFingerTable(computeFingerTable());
		node.setIsConnected(true);
		node.create();
		connectedNodes.add(node);
	}
	
	public Node[] computeFingerTable() throws Exception {
		Node[] finger = new Node[3];
		System.out.println("Finger table of ["+node.getID()+"]");
		for(int i = 0;i<3;i++) {
			finger[i] = node;
		}
		
		return finger;
	}
	
	public static void main(String[] args) throws Exception{
		new Host();
	}
}
