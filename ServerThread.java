import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{

	private Socket socket;
	private ChatServer server;
	PrintWriter clientOut;
	/**
	 * Constructor for server thread
	 * @param clientSocket
	 * 		clientSocket used to input/output to client
	 */
	public ServerThread(Socket clientSocket, ChatServer chatServer) {
		socket = clientSocket;
		this.server = chatServer;
	}
	
	public void run() {
		try {
			//Input stream reader reads the data coming from the client
			InputStreamReader inpReader = new InputStreamReader(socket.getInputStream());
			//Used to put data input from client into a readable format
			BufferedReader clientIn = new BufferedReader(inpReader);
			//Used to write output data to the client
			clientOut = new PrintWriter(socket.getOutputStream(), true);
			
			//While loop used to take any input from the client, need to ad terminating condition
			while(true) {
				String userInput = clientIn.readLine();
				if (userInput == "q") { //to quit
						break;
				}	
				server.sendToAll(userInput);
				Thread.sleep(50);
			}
		} catch (IOException e1) {
			System.out.println("Client Disconnected");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("interruptd");
			currentThread().interrupt();
		}
    }
	
	
	/**
     * Prints a message to the client.
     * @param toPrint
     * 		the string to be printed to the client
     */
	public void printString(String toPrint) {
			clientOut.println(toPrint);
			//used to force to print
			clientOut.flush();
	}
	
}
