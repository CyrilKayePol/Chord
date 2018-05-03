import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {
	Node node, rand, newNode;
	DatagramSocket socket;
	DatagramPacket packet;
	Sender send;
	int port = 9898;
	int hostPort = 8888;
	String nodeAdd;
	int nodePort;
	private byte[] buf = new byte[1024000];
	private Object obj;
	
	public Client() throws Exception {
		socket = new DatagramSocket(port);
		node = new Node(InetAddress.getLocalHost().getHostAddress(), port);
		
		 rand = join("INIT", hostPort);
		 System.out.println("Node object received = "+rand.getPort());
		
		informNode();
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
				
			}else if(obj instanceof Node) {
				newNode = (Node) obj;
				
				new JoinProcess(socket,node ,newNode).start();
			}
			
		}
	}
	
	private Node join(String message, int hostPort) throws UnknownHostException {
		System.out.println("Requesting to join. . .");
		Node n = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] data = outputStream.toByteArray();
		
		send = new Sender(socket, data, InetAddress.getLocalHost(), hostPort);
		send.start();
		
		buf = new byte[10240000];
		packet = new DatagramPacket(buf, buf.length);
		
		System.out.println("waiting for response. . .");
		try {
			socket.receive(packet);
			data = packet.getData();
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			
			n = (Node) is.readObject();
			
		}catch(Exception e) {e.printStackTrace();}
		
		
		return n;
	}
	
	private void informNode() throws UnknownHostException {
		System.out.println("Informing random node. . .");
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(node);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		byte[] data = outputStream.toByteArray();
		
		send = new Sender(socket, data, InetAddress.getByName(rand.getIPAddress()), rand.getPort());
		send.start();
		
		buf = new byte[10240000];
		packet = new DatagramPacket(buf, buf.length);
		
		System.out.println("waiting for response. . .");
		try {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
				 data = packet.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				
				obj = is.readObject();
				
				this.node = (Node) obj;
				System.out.println("updated node. ");
				System.out.println("pre = "+node.getPredecessor().getID());
				System.out.println("suc = "+node.getSuccessor().getID());
				
			} catch (Exception e) {	
				e.printStackTrace();
			}
		}catch(Exception e) {e.printStackTrace();}
		
		
		
	}
	public static void main(String[] args) throws Exception {
		new Client();
	}
}
