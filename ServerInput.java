import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerInput extends Thread {
	// Buffered reader to take user input from server console
	BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
	ChatServer server;

	// constructor to initialise the server in this class so I can call killthreads()
	public ServerInput(ChatServer server) {
		this.server = server;
	}

	public void run() {
		try {
			// Read user input line by line
			while (true) {
				String command = userIn.readLine();
				// If the user enters the quit command then the threads are killed and the
				// program exits
				if (command.equals("QUIT")) {
					// calls the method to kill threads then exits
					server.killThreads();
					System.exit(0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not read from server console");
		}
	}
}
