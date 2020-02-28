Multithreaded client/server chat using the console which is built using Java Socket API. 
Server is executed with port specified and listens for connections. Any connections are 
given their own thread and a connection is established. Any messages sent from client to 
server are sent back to the client and to all other clients that are connected. Client 
operates on a multithreaded system so that it can allow reading userinput and server input
simultaneously.



To run the code:
1. Run the ChatServer class, this can use the parameter of "-ccp" to choose port. The default port is 14001
2. Run the ChatClient class, this can use parameter "-ccp" to choose port and/or "-cca" to choose address. 
	The default values are 14001, localhost
3. When finished quit the ChatServer class with command "QUIT"

