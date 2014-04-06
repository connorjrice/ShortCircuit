package ShortCircuit.States.GUI;

import ShortCircuit.TowerDefenseMain;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.ui.Picture;

/**
 * TODO: Document/reimplement
 * @author Connor Rice
 */
public class GameOverAppState extends AbstractAppState {

    private AssetManager assetManager;
    private SimpleApplication app;
    private Picture gameover;
    private TowerDefenseMain game;
    private InputManager inputManager;
    
    public GameOverAppState(TowerDefenseMain game) {
        this.game = game;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        gameOverPic();
    }
    
    public void gameOverPic() {
        gameover = new Picture("GameOver");
        gameover.setImage(assetManager, "Interface/gameover.jpg", false);
        gameover.setLocalTranslation(-10000, -10000, -10000);
        gameover.setWidth(1200);
        gameover.setHeight(800);
        app.getRootNode().attachChild(gameover);
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (isEnabled()) {
                if (keyPressed) {
                    if (name.equals("Touch")) {
                        game.goToMainMenu(true);
                    }
                }
            }
        }
    };
    
    public void endGame() {
        game.gameover();
    }
    
    public void updateHighScore() {
        
    }

    public void disable() {
        inputManager.removeListener(actionListener);
        gameover.setLocalTranslation(-10000, -10000, -10000);
    }

    public void enable() {
        inputManager.addListener(actionListener, new String[]{"Touch"});
        gameover.setLocalTranslation(355, 150, 1);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }
}
