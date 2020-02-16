import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class ServerThread extends Thread{

	private Socket socket;
	
	public ServerThread(Socket clientSocket) {
		socket = clientSocket;
	}
	
	public void run() {
		
		try {
			InputStreamReader inpReader = new InputStreamReader(socket.getInputStream());
			BufferedReader clientIn = new BufferedReader(inpReader);
			PrintWriter clientOut = new PrintWriter(socket.getOutputStream(), true);
			
			while(true) {
				String userInput = clientIn.readLine();
				clientOut.println(userInput);
				
				Thread.sleep(100);
				
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); //if asked to interrupt then do it
		}
    }
}
