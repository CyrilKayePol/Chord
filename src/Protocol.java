import java.util.ArrayList;
import java.util.Random;

public class Protocol {
	private static final int INIT = 0;
	private int state = INIT;
	
	public Protocol() {
		
	}
	
	public String procedure(String input) {
		String output = null;
		if(state == INIT) {
			if(input.equals("INIT")) {
				ArrayList<Node> connectedNodes = Chord.getConnectedNodes();
				int index = randomize(connectedNodes.size());
				output = "RAND_"+connectedNodes.get(index).getIPAddress();
			}
		}
		return output;
	}
	
	private int randomize(int bound) {
		Random rand = new Random();
		
		return rand.nextInt(bound);
	}
}
