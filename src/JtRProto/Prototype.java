package JtRProto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Prototype {

//	/**
//	 * @param args
//	 * @throws IOException
//	 * @throws InterruptedException 
//	 */
//	public static void main(String[] args) throws IOException, InterruptedException {
//
//		//einlesen des commandos
//		Scanner s = new Scanner(System.in);
//		List<String> l = new ArrayList<String>();
//		// String text = " ";
//
//		//operator schleife => Mainschleife
//		while (true) {
//			System.out.print("?-");
//			
//			l.clear();
//
//			//CommandListe bauen
//			String text = s.nextLine();
//			String[] formattedText = text.split(" ");
//			int i = 0;
//			while(i < formattedText.length){
//				l.add(formattedText[i]);
//				i = i + 1;
//			}
//
//			String[] commands = { "john", "--wordlist=password.lst", "mypasswd"};// komma?
//
//			//commandos übergeben, Dir setzen, Proc starten
//			ProcessBuilder proB = new ProcessBuilder(commands);
//			proB.directory(new File("/home/rootkid/john-1.7.9-jumbo-7/run"));
//			proB.redirectErrorStream(true);
//			
//			Process process = proB.start();
//			//Terminierungsüberwachung des Proc
//			ProcessMonitor proM = ProcessMonitor.create(process);
//			ProcessWriterAsync.create(proM);
//
//			//schreibend
//			OutputStream os = process.getOutputStream();
//			OutputStreamWriter osw = new OutputStreamWriter(os);
//			BufferedWriter bw = new BufferedWriter(osw);
//
//			//lesend
//			InputStream is = process.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader br = new BufferedReader(isr);
//			
//			String line;
//			System.out.printf("Output of running %s is:\n",
//					Arrays.toString(formattedText));
//			
//			//Proc output
//			while ((line = br.readLine()) != null) {
//				System.out.println(line + "##");
//			}
//			//bw.close();
//		}
//		
//
//	}
}
