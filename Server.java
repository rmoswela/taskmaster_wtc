import java.io.*;
import java.net.*;

class Server
{
	public static void main(String args[]) throws Exception
	{
		String clientSentence;
		String capitalizedSentence;
		/*instance to open a communication bridge for the specified port*/
		ServerSocket welcomeSocket = new ServerSocket(1900);
		/*accept a request that tries to make a connection*/
		Socket connectionSocket = welcomeSocket.accept();

		while(true)
		{
			/*object to read bytes from the input stream sent through the communication connection*/
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			/*object to sent bytes to the output stream using the connection instance*/
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			/*read bytes from the input stream*/
			clientSentence = inFromClient.readLine();
			/*compare whats read to shutdown*/
			if (clientSentence.toLowerCase().equals("shutdown"))
			{
				/*write the message to output stream*/
				outToClient.writeBytes("Server is Shutting down!");
				System.exit(0);/*exit success*/
			}
			/*we can log events and queries here to the log file*/
			System.out.println("Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		}
	}
}
