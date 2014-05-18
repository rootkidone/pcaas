package JtRProto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.CharBuffer;
import java.util.Arrays;


//PorcessWriterAsync fängt den Status einer laufenden Session ab 
//und gibt diesen aus. Dazu wird alle t= 5 sec ein neuer Prozess gestartet,
//welcher eine Nachricht an den eigentlichen Arbeitsprozess sendet, der Arbeitprozess
//antwortet dann dem ErrorStream des Async Prozesses
public class ProcessWriterAsync implements Runnable {

	private ProcessMonitor _pm;
	private Process _workProcess;
	private int _workProcessPID;
	private PluginMessSender _sender;
	private int _interval;
	private String _directory;

	private ProcessWriterAsync(ProcessMonitor pm, Process workProcess,
			PluginMessSender sender,String directory, int interval) throws Exception {
		this._pm = pm;
		this._workProcess = workProcess;
		this._workProcessPID = getUnixPID(workProcess);
		this._sender = sender;
		this._interval = interval;
		this._directory = directory;
	}

	public static ProcessWriterAsync create(ProcessMonitor pm,
			Process workProcess, PluginMessSender sender,String directory, int interval) throws Exception {
		ProcessWriterAsync resProc = new ProcessWriterAsync(pm, workProcess,
				sender, directory, interval);

		Thread t = new Thread(resProc);
		t.start();
		return resProc;
	}

	//zwecks hochreichen der Exceptions geschachtelt
	public void run() {
		try {
			superRun();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("superRun failed - IO");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("superRun failed - Interrupted");
		}
	}

	public void superRun() throws IOException, InterruptedException {
		String[] commands = { "john", "--status" };
		ProcessBuilder statusProcessBuilder = new ProcessBuilder(commands);
		statusProcessBuilder.directory(new File(
				_directory));

		long statusTime = System.nanoTime();
		long interval = _interval * 1000L * 1000 * 1000;

		//solange der Cracking-Prozess von John läuft und alle 5 sec
		//wird ein Prozess gestartet der die Statuszeile abfragt
		//gefragt wird der Session-Prozess, geantwortet wird in den ErrorStream des
		//Status-Prozesses
		while (!(_pm.isComplete())) {
			if (statusTime + interval <= System.nanoTime()) {
				Process statusProcess = statusProcessBuilder.start();

				BufferedReader brStatErr = new BufferedReader(
						new InputStreamReader(statusProcess.getErrorStream()));
				BufferedWriter bwStat = new BufferedWriter(
						new OutputStreamWriter(statusProcess.getOutputStream()));

				new ProcessBuilder("kill", "-1", "" + _workProcessPID).start()
						.waitFor();

				String line;
				while ((line = brStatErr.readLine()) != null) {
					//sendet zurück an das Framework
					_sender.sendMessage(line);
				}

				statusTime = System.nanoTime();
				statusProcess.waitFor();
			}
			Thread.sleep(1000);
		}
	}

	//process = Prozess der Cracking-Session
	//return = PID des Prozesses der Crakcing Session
	public static int getUnixPID(Process process) throws Exception {
		System.out.println(process.getClass().getName());
		if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
			Class cl = process.getClass();
			Field field = cl.getDeclaredField("pid");
			field.setAccessible(true);
			Object pidObject = field.get(process);
			return (Integer) pidObject;
		} else {
			throw new IllegalArgumentException("Needs to be a UNIXProcess");
		}
	}

}
