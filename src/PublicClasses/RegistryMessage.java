package PublicClasses;

import java.io.Serializable;

public class RegistryMessage implements Serializable{
	
	private PluginObject _pluginToRegister;
	private final String _regMessage = "REGISTER";
	
	public RegistryMessage(PluginObject pluginObject){
		this._pluginToRegister = pluginObject;
	}
	
	public PluginObject getPlugin(){return _pluginToRegister;}
}
