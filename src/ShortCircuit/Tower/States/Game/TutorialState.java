package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.MainState.TowerMainState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import tonegod.gui.controls.windows.AlertBox;
import tonegod.gui.core.Screen;

/**
 * This AppState will contain all tutorial functionality. Popups to explain
 * towers, banner to state what needs to be done/level up, how levels work, how
 * upgrades work, how budget works, how bombs work.
 *
 * @author Connor
 */
public class TutorialState extends AbstractAppState {

    private SimpleApplication app;
    private Node rootNode;
    private AssetManager assetManager;
    private Screen screen;
    private Node guiNode;
    private GameState GameState;
    private Node worldNode;
    private Material focusMaterial;
    private int width;
    private int height;
    private TowerMainState tMS;
    private TowerState TowerState;
    private CreepState CreepState;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.tMS = stateManager.getState(TowerMainState.class);
        this.guiNode = this.app.getGuiNode();
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
        this.CreepState = this.app.getStateManager().getState(CreepState.class);
        this.worldNode = this.GameState.getWorldNode();
        this.assetManager = this.app.getAssetManager();
        width = tMS.getWidth();
        height = tMS.getHeight();
        initScreen();
        initMaterials();
        basePopup();
        tMS.pause();
    }

    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(false);
        guiNode.addControl(screen);
    }

    private void initMaterials() {
        focusMaterial = assetManager.loadMaterial("Materials/Circuit2/beam1.j3m");
    }

    @Override
    public void update(float tpf) {
    }

    /**
     * A banner, that will display messages such as "Tower needs to be charged"
     * and related messages.
     */
    private void initBanner() {
    }

    private void towerPopup() {
        focusBuiltTowers();
        AlertBox towerA = new AlertBox(screen, "Your Towers", new Vector2f(height/2, width/2)) {

            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
               unfocusBuiltTowers();
               screen.removeElement(this);
               //creepSpawnerPopup();
               tMS.pause();
               
            }  
            
        };
        towerA.setMsg("These are your towers. They protect your base from creeps, and need to be charged from time to time.");
        screen.addElement(towerA);
    }
    
    private void focusBuiltTowers() {
        for (int i = 0; i < TowerState.getTowerList().size(); i++) {
            if (!TowerState.getTowerList().get(i).getUserData("Type").equals("unbuilt")) {
                TowerState.getTowerList().get(i).setMaterial(focusMaterial);
            }
        }
    }
    
    private void unfocusBuiltTowers() {
        for (int i = 0; i < TowerState.getTowerList().size(); i++) {
            if (!TowerState.getTowerList().get(i).getUserData("Type").equals("unbuilt")) {
                TowerState.getTowerList().get(i).setMaterial(assetManager.loadMaterial(getTowerOrigMat(i)));
            }
        }
    }
    
    private String getTowerOrigMat(int i) {
        return "Materials/" + GameState.getMatDir() + "/" + TowerState.getTowerList().get(i).getUserData("Type") + ".j3m";
    }

    private void upgradePopup() {
    }

    private void creepSpawnerPopup() {
        //CreepState.getCreepNode().getChild("spawner").setMaterial(focusMaterial);
    }

    private void creepPopup() {
    }

    private void basePopup() {
        worldNode.getChild("Base").setMaterial(focusMaterial);
        AlertBox baseA = new AlertBox(screen, "Your Base", new Vector2f(width/2, height/2)) {
            
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                worldNode.getChild("Base").setMaterial(assetManager.loadMaterial(GameState.getBaseTexLoc()));
                screen.removeElement(this);
                towerPopup();
            }
        };
        baseA.setMsg("This is your base. Defend it from creeps.");
        screen.addElement(baseA);
        
    }

    @Override
    public void cleanup() {
        super.cleanup();
        guiNode.removeControl(screen);
    }
}
