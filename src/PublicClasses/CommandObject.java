package PublicClasses;

import java.io.Serializable;

public class CommandObject implements Serializable{

	private String _programInstance;
	private String _commandString;
	private boolean _saveOutput;
	private boolean _saveStatus;
	private String _saveOutputTo;
	private String _saveStatusTo;
	private String _directory;
	private int _interval;
	
	public CommandObject(String program,String directory, String command, boolean saveOut, boolean saveStat,
			String saveOutTo, String saveStatTo, int interval){
		this._programInstance = program;
		this._commandString = command;
		this._saveOutput = saveOut;
		this._saveOutputTo = saveOutTo;
		this._saveStatus = saveStat;
		this._saveStatusTo = saveStatTo;
		this._directory = directory;
		this._interval = interval;
	}
	
	//Nullprüfung auf der anderen Seite
	public String getProgramInstance(){return _programInstance;}
	public String getCommand(){return _commandString;}
	public boolean getSaveOutput(){return _saveOutput;}
	public boolean getSaveStatus(){return _saveStatus;}
	public String getSaveOutputTo(){return _saveOutputTo;}
	public String getSaveStatusTo(){return _saveStatusTo;}
	public String getDirectory(){return _directory;}
	public int getInterval(){return _interval;}
	
	
	
}
