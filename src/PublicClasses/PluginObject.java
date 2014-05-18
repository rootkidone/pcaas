package PublicClasses;

import java.io.Serializable;
import java.util.HashMap;

public class PluginObject implements Serializable{
	
	private String _pluginName;
	private HashMap<String, String> _pluginInfo;
	
	public PluginObject(String name, HashMap<String, String> pluginInfo){
		this._pluginInfo = pluginInfo;
		this._pluginName = name;
	}
	
	public String getPluginName(){return _pluginName;}
	public HashMap<String, String> getPluginInfo(){return _pluginInfo;}

}
