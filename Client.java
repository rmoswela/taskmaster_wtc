import java.io.*;
import java.net.*;
import java.util.*;

class Client
{
	private static void CommandPrompt(Scanner inFromUser, Scanner inFromServer, DataOutputStream outToServer) throws Exception
	{
		String sent;
		System.out.print("taskmaster$ ");
		/*read from the inputstream*/
		String command = inFromUser.nextLine();

		if (command.toLowerCase().equals("exit"))
		{
			System.exit(0);/*exit client program*/
		}
		/*write to outputstream*/
		outToServer.writeBytes(command + '\n');
		/*read from the outputstream*/
		sent = inFromServer.nextLine();


		if (command.toLowerCase().equals("status"))
		{
			System.out.println("client Process status: " + sent);
		}
		System.out.println("From Server: " + sent);
		CommandPrompt(inFromUser, inFromServer, outToServer);
	}
	public static void main(String args[]) throws Exception
	{
		/*create an object to read values byte by byte from the inputstream*/
		Scanner inFromUser = new Scanner(new InputStreamReader(System.in));
		/*create a socket instance to create a communication bridge and connect to specified servername using specified port*/
		Socket clientSocket = new Socket("localhost", 1900);
		/*object to write values to a stream using the socket instance*/
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		/*object to read values from the input stream using socket instance*/
		Scanner inFromServer = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
		/*call the function to handle data written and read*/
		CommandPrompt(inFromUser, inFromServer, outToServer);
		/*close communication or socket instance*/
		clientSocket.close();
	}
}
