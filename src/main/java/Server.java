import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	private static List<String> ipList = new ArrayList<>();
	private static byte[] sourceData = new byte[8500];
	private static int serverPort = 3005;

	public static void main(String args[]) throws IOException {
		DatagramSocket socket = new DatagramSocket(serverPort);
		DatagramPacket dataPacket = new DatagramPacket(sourceData,
				sourceData.length);
		while (true) {
			socket.receive(dataPacket);
			String address = dataPacket.getAddress().getHostAddress();
			System.out.println(address);
			boolean existingUser = ipList.stream().anyMatch(ip -> ip.equals(address));
			if (!existingUser) {
				ipList.add(address);
			}
			for (String ip : ipList) {
				if (!ip.equals(address)) {
					System.out.println("received: " + ip + " " + address);
					dataPacket.setAddress(InetAddress.getByName(ip));
					socket.send(dataPacket);
				}
			}
		}
	}
}
