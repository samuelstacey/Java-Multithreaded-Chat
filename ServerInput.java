import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerInput extends Thread {
	BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
	ChatServer server;
	
	
	public ServerInput(ChatServer server) {
		this.server = server;
	}

	public void run() {
		try {
			String command = userIn.readLine();
			System.out.println(command);
			if (command.equals("QUIT")) {
				server.killThreads();
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
