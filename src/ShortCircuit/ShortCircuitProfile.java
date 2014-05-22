package ShortCircuit;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

/**
 *
 * @author Connor
 */
public class ShortCircuitProfile extends SimpleApplication {
    private TowerMainState tms;
    
    
    public static void main(String[] args) {
        ShortCircuitProfile app = new ShortCircuitProfile();
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
        flyCam.setDragToRotate(true);
        flyCam.setRotationSpeed(0.0f);
        flyCam.setZoomSpeed(0.0f);
        setDisplayFps(false);
        setDisplayStatView(false);
        startTower();
    }
        
    public void startTower() {
        tms = new TowerMainState(true, "profilelevel.lvl.xml");
        stateManager.attach(tms);
    }
    
}
