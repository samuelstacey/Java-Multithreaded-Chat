import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{

	private Socket socket;
	
	/**
	 * Constructor for server thread
	 * @param clientSocket
	 * 		clientsocket used to input/output to client
	 */
	public ServerThread(Socket clientSocket) {
		socket = clientSocket;
		
	}
	
	public void run() {
		try {
			//Input stream reader reads the data coming from the client
			InputStreamReader inpReader = new InputStreamReader(socket.getInputStream());
			//Used to put data input from client into a readable format
			BufferedReader clientIn = new BufferedReader(inpReader);
			//Used to write output data to the client
			PrintWriter clientOut = new PrintWriter(socket.getOutputStream(), true);
			
			//While loop used to take any input from the client, need to ad terminating condition
			while(true) {
				String userInput = clientIn.readLine();
				if (userInput == "q") { //to quit
						break;
				}
				clientOut.println(userInput);
								
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}
