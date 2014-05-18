import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class BinBashTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Scanner s = new Scanner(System.in);
		List<String> l = new ArrayList<String>();
		//String text = " ";
		while (true){
            String text = s.next();
            l.add(text);
            if(text.equals("end")) break;
            System.out.println(s);
        } 
	
		String[] commands = { "/bin/bash" };
		
		ProcessBuilder proB = new ProcessBuilder(commands);
		proB.directory(new File("/home/rootkid/john-1.7.9-jumbo-7/run"));
		
		Process process = proB.start();
		
		//PrintWriter processOut = new PrintWriter(process.getOutputStream());
		OutputStream os = process.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		System.out.printf("Output of running %s is:\n",
				Arrays.toString(commands));

		bw.write("john --wordlist=password.lst mypasswd");
		bw.newLine();
		bw.flush();
		
		while ((line = br.readLine()) != null) {
			//try with BufferedWriter
			//bw.write("q");
			bw.newLine();
			bw.flush();
			System.out.println(line+"##");
		}
		
		// Wait to get exit value
		try {
			int exitValue = process.waitFor();
			System.out.println("\n\nExit Value is " + exitValue);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
