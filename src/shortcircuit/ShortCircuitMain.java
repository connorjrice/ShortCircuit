package ShortCircuit;

import ShortCircuit.States.GUI.StartGUI;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

/**
 * ShortCircuit: A tower defense game
 * TODO: Update atlas def files :(
 * @author Connor Rice
 */
public class ShortCircuitMain extends SimpleApplication {
    
    private StartGUI sgui;

    public static void main(String[] args) {
        //Logger.getLogger("").setLevel(Level.OFF);
        ShortCircuitMain app = new ShortCircuitMain();
        AppSettings sets = new AppSettings(true);
        sets.setResolution(1920,1080);
        sets.setFrequency(60);
        sets.setFullscreen(false);
        sets.setVSync(true);
        sets.setFrameRate(60);
        sets.setTitle("ShortCircuit");
        app.setShowSettings(false);        
        sets.setSettingsDialogImage("Interface/loading.jpg");
        app.setSettings(sets);
        app.start();
    }
    
    /**
     * Initialize camera, add input mapping, bring up start menu. Also adjust
     * statview/fps
     */
    @Override
    public void simpleInitApp() {
        setDisplayFps(false);
        setDisplayStatView(false);
        startGUI();
    }
    
    public void startGUI() {
        sgui = new StartGUI();
        stateManager.attach(sgui);
    }
  
    @Override
    public void destroy() {
        super.destroy();
        rootNode.detachAllChildren();
        System.exit(1337);
    }
    
}
