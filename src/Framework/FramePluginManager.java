package Framework;

import java.util.ArrayList;
import java.util.List;

import PublicClasses.PluginObject;

public class FramePluginManager {

    private List<PluginObject> _pluginList = new ArrayList<PluginObject>();

    public FramePluginManager() {

    }

    public void addToList(PluginObject po) {
        _pluginList.add(po);
    }

    public PluginObject getPlugin(PluginObject po) {
        PluginObject resultPlugin = null;
        for (PluginObject p : _pluginList) {
            if (p.equals(po)) {
                resultPlugin = p;
            }
        }
        //null prüfung auf der anderen Seite
        return resultPlugin;
    }

    public List<PluginObject> getRegisteredPlugins() {
        return _pluginList;
    }

}
