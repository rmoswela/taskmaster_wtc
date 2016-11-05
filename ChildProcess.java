import java.util.List;

public class ChildProcess extends Thread
{
	private Process process;
	private List<String> command;
	private int processStatus;
	private String name;

	public ChildProcess(List<String> cmd)
	{
		command = cmd;
	}

	public int getStatus()
	{
		return processStatus;
	}

	public String getProcessName()
	{
		return name;
	}

	public void stopProcess()
	{
		process.destroy();
	}

	public void run()
	{
		try
		{
			ProcessBuilder pb = new ProcessBuilder(command);
			name = command.get(0);
			process = pb.start();
		
			processStatus = process.waitFor();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
