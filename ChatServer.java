import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

		// accept a connection
		// respond to the request 
		
	private ServerSocket in;
	private ArrayList<Thread> threadList = new ArrayList<Thread>();
	private Thread t;
		
	public ChatServer(int port) {
		try {
			in = new ServerSocket(port);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToAll(String toSend) {
		//REMOVE the clients if they disconnect
		for (Thread t : threadList) {
			((ServerThread) t).printString(toSend);
		}
	}
	
	public synchronized void killThreads() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Thread t : threadList) {
			t.interrupt();
		}
	}
		
	public void go() {
		try {
			System.out.println("Server listening");
			Socket s;
			Thread t1 = new ServerInput(this);
			t1.start();
			while (true) {
				s = in.accept();
				t = new ServerThread(s , this); 
				threadList.add(t);
				t.start();//Starts the thread
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
		
	public static void main(String[] args) {
		int port = 14001;
		for (int i=0; i<args.length-1; i++) {
			if (args[i].equals("-ccp")) {
				try {
		            port = Integer.parseInt(args[i + 1]);
		        } catch (NumberFormatException e) {
		            System.out.println("Invalid port");
		        }
			}
		    else {
		        System.out.println("Invalid argument");
		    }
		}
		ChatServer server = new ChatServer(port);
		server.go();
	}
}

