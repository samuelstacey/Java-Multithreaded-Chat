import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	
	private Socket server;
	private Thread t;

	public ChatClient(String address, int port) {
		try {
			t = new ClientRead(address ,port);
			server = new Socket(address ,port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void go() {
		try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter serverOut = new PrintWriter(server.getOutputStream(), true);
			
			t.start();
			
			while(true) { //Need this to print ASAP currently it only does when the client is active cause its waiting for user input, multithreaded???
				String userInput = userIn.readLine();
				serverOut.println(userInput);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		int port = 14001;
		String addr  = "localhost";
		for (int i=0; i<args.length-1; i++) {
			if (args[i].equals("-ccp")) {
				try {
		            port = Integer.parseInt(args[i + 1]);
		        } catch (NumberFormatException e) {
		            System.out.println("Invalid port");
		        }
			}
			else if (args[i].equals("-cca")) {
				addr = args[i+1];
			}
		    else {
		        System.out.println("Invalid argument");
		    }
		}
		new ChatClient(addr, port).go();
	}
}
