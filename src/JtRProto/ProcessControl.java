package JtRProto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

//ProcessControl managed des Arbeitsauftrag des Frameworks,
//der Output des Prozesses wird durch den PluginMessSender an das
//Framework zurück gesendet.

public class ProcessControl implements Runnable {
	
	private String _jtRArguments;
	private String _directory;
	private int _interval;
	private PluginMessSender _plugMessSender;
	//private PlugInMessReceiver _plugMessReceiver = new PlugInMessReceiver();
	
	//jtRArguments = Arbeitsauftrag
	public ProcessControl(String jtRArguments, String directory, int interval,
                PluginMessSender pluginSender){
		this._jtRArguments = jtRArguments;
		this._directory = directory;
		this._interval = interval;
                this._plugMessSender = pluginSender;
		//receiver not needed here
	}
	
	public static ProcessControl create(String jtRArguments, String directory, int interval,
                PluginMessSender pluginSender){
		ProcessControl p = new ProcessControl(jtRArguments, directory, interval,
                pluginSender);
		Thread t = new Thread(p);
		t.start();
		return p;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

			//CommandListe bauen
			String text = _jtRArguments;
			String[] formattedText = text.split(" ");

			//Automatisierung zu Testzwecken
			//String[] commands = { "john", "--wordlist=password.lst", "mypasswd"};// komma?

			//commandos übergeben, Dir setzen, Proc starten
			ProcessBuilder proB = new ProcessBuilder(formattedText);
			proB.directory(new File(_directory));
			proB.redirectErrorStream(true);
			
			//PluginMessSender plugMess = new PluginMessSender();
			
			Process process;
			try {
				process = proB.start();
				//Terminierungsüberwachung des Proc
				ProcessMonitor proM = ProcessMonitor.create(process);
				//StatusAbfrage
				ProcessWriterAsync.create(proM, process, _plugMessSender, _directory, _interval);

				//schreibend
				OutputStream os = process.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);

				//lesend
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				
				String line;
				System.out.printf("Output of running %s is:\n",
						Arrays.toString(formattedText));
				
				//Proc output
				while ((line = br.readLine()) != null) {
					//System.out.println(line + "##");
					//geht zurück an das Framework
					_plugMessSender.sendMessage(line);
				}
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
