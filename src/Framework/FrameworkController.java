package Framework;

import Adapter.GUIFrameworkAdapter;
import PublicClasses.CommandObject;
import PublicClasses.PluginObject;
import java.io.IOException;
import java.util.List;

public class FrameworkController {

    private FrameMessSender _frameSender;
    private FrameMessReceiver _frameReceiver;
    private FramePluginManager _pluginManager;
    private GUIFrameworkAdapter _adapter;

    public FrameworkController(GUIFrameworkAdapter adapter) throws IOException {
        this._frameSender = new FrameMessSender();
        this._frameReceiver = new FrameMessReceiver(this);
        this._pluginManager = new FramePluginManager();
        this._adapter = adapter;
        run();
    }

    public void run() {
        //new Thread(_frameSender).start();
        new Thread(_frameReceiver).start();
    }

    public void sendMessage(CommandObject message) {
        _frameSender.sendMessage(message);
    }

    public void addPluginToManager(PluginObject po) {
        _pluginManager.addToList(po);
    }

    public List<PluginObject> getRegisteredPlugins() {
        return _pluginManager.getRegisteredPlugins();
    }
    
    public void showMessageInGUI(String message, String plugin){
       _adapter.showMessageInGUI(message, plugin);
    }

}
