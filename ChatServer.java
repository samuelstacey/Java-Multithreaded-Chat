import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

		// accept a connection
		// respond to the request 
		
	private ServerSocket in;
		
	public ChatServer(int port) {
		try {
			in = new ServerSocket(port);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void go() {
		
		try {
			System.out.println("Server listening");
			Socket s;
			while (true) {
				s = in.accept();
				new ServerThread(s).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
		
	public static void main(String[] args) {
		new ChatServer(14002).go();
	}
}

