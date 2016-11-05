import java.io.*;
import java.net.*;
import java.util.*;

class Server
{
	private static ChildProcess process;
	private static ArrayList<ChildProcess> processList;

	private static void commandPrompt(String clientSentence, DataOutputStream outToClient) throws Exception
	{
		String capitalizedSentence;
		//System.out.println("Running processes: "+processList.size());
		/*shutting down the server from the input of the client*/
		if (clientSentence.toLowerCase().equals("shutdown"))
		{
			/*sends msg to client*/
			outToClient.writeBytes("Server is Shutting down!");
			/*goes through arraylist to check if process is alive and stops it before shutdown*/
			for (ChildProcess p:processList)
			{
				if (p.isAlive())
					p.stopProcess();
			}
			System.exit(0);/*exits program*/
		}
		/*checks the status of the processes*/
		if (clientSentence.toLowerCase().equals("status"))
		{
			for(ChildProcess p:processList)
			{
				/*send msg to client about status of process*/
				outToClient.writeBytes(p.getProcessName());
				if (p.isAlive())
				{
					outToClient.writeBytes(" is running." + '\n');
				}
				else
				{
					outToClient.writeBytes(" has stopped." + '\n');
				}
			}
		}
		else
		{
			/*for commands that are not status or shutdown*/
			if (!clientSentence.toLowerCase().equals(""))
			{
				/*splits the commands using delimiter as space*/
				String clientSentenceSplit[] = clientSentence.split(" ");
				/*stores the tokens in an array*/
				List<String> command = Arrays.asList(clientSentenceSplit);
				/*checks if the first token is stop and it has another command*/
				if (command.get(0).toLowerCase().equals("stop") && command.get(1).toLowerCase() != null)
				{
					/*checks for the process that is used with stop if is available in the processlist array*/
					for (ChildProcess p:processList)
					{
						/*if found it stops it*/
						if (p.getProcessName().equals(command.get(1)))
							p.stopProcess();
					}
				}
				else
				{
					/*if command passed is not stop then is a used to create a new process*/
					process = new ChildProcess(command);
					process.start();/*starts the process*/
					processList.add(process);/*adds the process to processlist*/
				}
			}
			/*gives msg alerts to server*/
			System.out.println("Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			/*sends msg alerts to client*/
			outToClient.writeBytes(capitalizedSentence);
		}
	}

	public static void main(String args[]) throws Exception
	{
		String clientSentence;
		//String capitalizedSentence;
		/*instance to open a communication bridge for the specified port*/
		ServerSocket welcomeSocket = new ServerSocket(1900);
		/*accept a request that tries to make a connection*/
		Socket connectionSocket = welcomeSocket.accept();
		/*creates an array to store processes*/
		processList = new ArrayList<ChildProcess>();

		while(true)
		{
			/*object to read bytes from the input stream sent through the communication connection*/
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			/*object to sent bytes to the output stream using the connection instance*/
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			/*read bytes from the input stream*/
			clientSentence = inFromClient.readLine();
			/*calls function to manipulate commands*/
			commandPrompt(clientSentence, outToClient);
		}
	}
}
