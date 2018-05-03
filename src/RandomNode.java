import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class RandomNode extends Thread {
	DatagramSocket socket;
	InetAddress address;
	int port;
	public RandomNode(DatagramSocket socket, InetAddress address, int port) {
		this.socket = socket;
		this.address = address;
		this.port = port;
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		Random rand = new Random();
		int index = rand.nextInt(Host.connectedNodes.size());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(Host.connectedNodes.get(index));
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] data = outputStream.toByteArray();
		
		Sender send = new Sender(socket, data, address, port);
		send.start();
		
		stop();
	}
}
