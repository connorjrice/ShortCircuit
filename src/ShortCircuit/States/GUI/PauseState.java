package ShortCircuit.States.GUI;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector2f;
import com.jme3.ui.Picture;

/**
 * TODO: Document/reimplement
 * @author Connor Rice
 */
public class PauseState extends AbstractAppState {

    private AssetManager assetManager;
    private SimpleApplication app;
    private Picture paused;
    private InputManager inputManager;
    private GUIAppState guis;
    
    public PauseState(GUIAppState _guis) {
        guis = _guis;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        pausedPic();
    }
    
    @Override
    public void update(float tpf) {
        
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (isEnabled()) {
                if (keyPressed) {
                    if (name.equals("Touch")) {
                        Vector2f click2d = inputManager.getCursorPosition();
                        opHandle(click2d.getX(), click2d.getY());
                        
                    }
                }
            }
        }
    };
        
    private void opHandle(float x, float y) {
        if (y > 200 && y < 300) {
            if (x > 800 && x < 1000) {
                unpause();
            }
            else if (x > 1000 && x < 1300) {
                mainmenu();
            }
        }
    }

    public void pausedPic() {
        paused = new Picture("Paused");
        paused.setImage(assetManager, "Interface/Paused.jpg", false);
        paused.setLocalTranslation(-10000, -10000, -1000);
        paused.setWidth(500);
        paused.setHeight(800);
        app.getRootNode().attachChild(paused);
    }
    
    public void unpause() {
        guis.getGame().unpause();
    }
    
    public void mainmenu() {
        guis.getGame().goToMainMenu(false);
    }
    
    public void enable() {
        inputManager.addListener(actionListener, new String[]{"Touch"});
        paused.setLocalTranslation(710, 150, 0);
    }

    public void disable() {
        inputManager.removeListener(actionListener);
        paused.setLocalTranslation(-10000, -10000, -1000);
    }

    public void removePaused() {
        app.getRootNode().detachChild(paused);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }
}
