import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRead extends Thread {

	private Socket server;

	// Constructor to connect to server using IP and port
	public ClientRead(String address, int port) {
		try {
			server = new Socket(address, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Could not connect to server, please check server details.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server socket could not be created, the port may be reserved");
		}
	}

	public void run() {
		try {
			// Buffered reader to read input from the server
			BufferedReader serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
			while (true) {
				// reads input from server and outputs it line by line
				String serverRes = serverIn.readLine();
				System.out.println(serverRes);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error while receiving message from server");
		}
	}
}
