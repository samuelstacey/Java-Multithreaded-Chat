import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRead extends Thread {
	
	private Socket server;
	
	public ClientRead(String address, int port) { 
		try {
			server = new Socket(address,port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			BufferedReader serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
			while(true) {
				String serverRes = serverIn.readLine();
				System.out.println(serverRes);
			}
		} catch  (IOException e) {
			e.printStackTrace();
		}
	}
}
