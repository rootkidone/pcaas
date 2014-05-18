package JtRProto;

public class ProcessMonitor implements Runnable {

	  private final Process _proc;
	  private volatile boolean _complete = false;
	  
	  public ProcessMonitor(Process p){
		  this._proc = p;
	  }

	  public boolean isComplete() { return _complete; }

	  public void run() {
	    try {
	    	//waitFor blockierend
			_proc.waitFor();
			//System.out.println("waitFor done");
		    _complete = true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	  public static ProcessMonitor create(Process proc) {
		ProcessMonitor procMon = new ProcessMonitor(proc);
	    Thread t = new Thread(procMon);
	    t.start();
	    return procMon;
	  }
	}
