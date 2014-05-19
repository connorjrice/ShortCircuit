/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author Development
 */
public class TowerLoad extends SimpleApplication {
    
    public static void main(String[] args) {
        TowerLoad app = new TowerLoad();
        AppSettings sets = new AppSettings(true);
        sets.setResolution(1920,1080);
        sets.setFrequency(60);
        sets.setFullscreen(false);
        sets.setVSync(true);
        sets.setFrameRate(60);
        app.setSettings(sets);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        String userHome = System.getProperty("user.home");
        assetManager.registerLocator(userHome, FileLocator.class);
        Node loadedNode = (Node)assetManager.loadModel("Models/MyModel.j3o");
        loadedNode.setName("loaded node");
        rootNode.attachChild(loadedNode);
    }
    
}
