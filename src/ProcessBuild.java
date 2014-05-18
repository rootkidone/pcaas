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
import java.util.Scanner;

public class ProcessBuild {
	public static void main(String[] args) throws IOException, InterruptedException {
		//String[] commands = { "gnome-terminal" };
		Scanner s = new Scanner(System.in);
		ArrayList<String> l = new ArrayList<String>();
		//String text = " ";
//		while (true){
//            String text = s.next();
//            l.add(text);
//            if(text.equals("end")) break;
//            System.out.println(s);
//        }  
		//String[] commands = {"bash", "-c", "john " + l.get(0) + " " + l.get(1)};
				//" " + l.get(2)};
		String[] commands = { "/bin/bash" };//,"john --wordlist=password.lst mypasswd" };

		ProcessBuilder proB = new ProcessBuilder(commands);
		proB.directory(new File("/home/rootkid/john-1.7.9-jumbo-7/run"));
		
		File i = new File("/home/rootkid/test2.txt");
		i.createNewFile();
		
		//proB.redirectInput(ProcessBuilder.Redirect.to(i));
		Process process = proB.start();
		
		PrintWriter processOut = new PrintWriter(process.getOutputStream());
		OutputStream os = process.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		System.out.printf("Output of running %s is:\n",
				Arrays.toString(commands));
//		
		byte[] bArr = ("\n".getBytes());
		
		bw.write("john --wordlist=password.lst mypasswd");
		bw.newLine();
		bw.flush();
		while ((line = br.readLine()) != null) {
			//try with PrintWriter
			processOut.print("q");
			processOut.flush();
			//try with OutputStream
			os.write(bArr);
			os.flush();
			//try with BufferedWriter
			bw.write("q");
			bw.newLine();
			bw.flush();
			System.out.println(line+"##");
		}
		
//		processOut.close();
//		os.close();
		bw.close();

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