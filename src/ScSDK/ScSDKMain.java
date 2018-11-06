package ScSDK;

import ScSDK.GUI.SDKGUI;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 * Program for generating XML files for ShortCircuit
 *
 * @author Connor Rice
 */
public class ScSDKMain extends SimpleApplication {

    public Node previewNode;

    public static void main(String[] args) {
        ScSDKMain app = new ScSDKMain();
        AppSettings sets = new AppSettings(true);
        sets.setResolution(1280, 720);
        sets.setFrequency(60);
        sets.setFullscreen(false);
        sets.setVSync(true);
        sets.setFrameRate(60);
        sets.setTitle("ShortCircuit MapXML");
        app.setSettings(sets);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.setShowSettings(false);
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        stateManager.attach(new SDKGUI());
    }
}
