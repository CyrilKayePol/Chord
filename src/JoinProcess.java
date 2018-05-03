import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class JoinProcess extends Thread {

	DatagramSocket socket;
	Node node, n;

	public JoinProcess(DatagramSocket socket,Node n, Node node) {
		this.n= n;
		this.node = node;
		this.socket = socket;
		
	}
	
	private void procedure() throws Exception {
		System.out.println("Need to find successor of "+node.getPort());
		node.join(n);
		node.setIsConnected(true);
		System.out.println("Found successor of "+node.getPort() +": "+node.getSuccessor().getPort());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		byte[] data = outputStream.toByteArray();
		
		Sender send = new Sender(socket, data, InetAddress.getLocalHost(), 8888);
		send.start();
		
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		try {
			
			procedure();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(node);
			byte[] data = outputStream.toByteArray();
			Sender send = new Sender(socket, data, InetAddress.getByName(node.getIPAddress()), node.getPort());
			send.start();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		stop();
		
	}
	
	
	
	
}
