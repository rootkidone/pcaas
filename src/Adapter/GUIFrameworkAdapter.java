/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adapter;

import Framework.FrameworkController;
import GUI.FrameworkGUI;
import PublicClasses.CommandObject;
import PublicClasses.PluginObject;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author rootkid
 */
public class GUIFrameworkAdapter {

    private FrameworkGUI _gui;
    private FrameworkController _frameworkController;

    public GUIFrameworkAdapter() throws IOException {
        _frameworkController = new FrameworkController(this);
    }

    public void setGUI(FrameworkGUI gui) {
        _gui = gui;
    }

    public void setFrameworkController(FrameworkController controller) {
        _frameworkController = controller;
    }

    public void sendMessage(CommandObject message) {
        _frameworkController.sendMessage(message);
    }
    
    public List<PluginObject> getRegisteredPlugins(){
        return _frameworkController.getRegisteredPlugins();
    }
    
    public void showMessageInGUI(String message, String plugin){
        _gui.showMessage(message, plugin);
    }
}
