package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.MainState.TowerMainState;
import ShortCircuit.Tower.States.GUI.GameGUI;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import tonegod.gui.controls.windows.AlertBox;
import tonegod.gui.core.Screen;

/**
 * This AppState will contain all tutorial functionality. Popups to explain
 * towers, banner to state what needs to be done/level up, how levels work, how
 * upgrades work, how budget works, how bombs work.
 *
 * TODO: Make main menu unopenable while dialog boxes are up.
 * 
 * @author Connor
 */
public class TutorialState extends AbstractAppState {

    private SimpleApplication app;
    private AssetManager assetManager;
    private Screen screen;
    private Node guiNode;
    private GameState GameState;
    private Material focusMaterial;
    private TowerMainState tMS;
    private TowerState TowerState;
    private CreepState CreepState;
    private GameGUI GameGUI;
    private boolean hasGlobbed = false;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.tMS = this.app.getStateManager().getState(TowerMainState.class);
        this.guiNode = this.app.getGuiNode();
        this.GameGUI = this.app.getStateManager().getState(GameGUI.class);
        this.GameState = stateManager.getState(GameState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
        this.CreepState = this.app.getStateManager().getState(CreepState.class);
        this.assetManager = this.app.getAssetManager();
        initScreen();
        initMaterials();
        tMS.pause();
        basePopup();
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
        if (CreepState.getGlobList().size() > 0 && !hasGlobbed) {
            globPopup();
            hasGlobbed = true;
        }

    }

    private void globPopup() {
        Spatial glob = GameState.getWorldNode().getChild("Glob");
        glob.setMaterial(focusMaterial);
        tMS.pause();
        AlertBox globA = new AlertBox(screen, "Glob", new Vector2f(glob.getLocalTranslation().x+200,600)) {
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                screen.removeElement(this);
                tMS.pause();
            }
        };
        globA.setMsg("This is a glob.");
        screen.addElement(globA);
    }

    /**
     * A banner, that will display messages such as "Tower needs to be charged"
     * and related messages.
     */
    private void initBanner() {
    }

    public void basePopup() {
        GameState.getWorldNode().getChild("Base").setMaterial(focusMaterial);
        AlertBox baseA = new AlertBox(screen, "Your Base", new Vector2f(800, 800)) {
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                GameState.getWorldNode().getChild("Base").setMaterial(assetManager.loadMaterial(GameState.getBaseTexLoc()));
                screen.removeElement(this);
                towerPopup();
            }
        };
        baseA.setMsg("This is your base. Defend it from creeps.");
        screen.addElement(baseA);
    }

    private void towerPopup() {
        focusBuiltTowers();
        AlertBox towerA = new AlertBox(screen, "Your Towers", new Vector2f(800, 600)) {
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                unfocusBuiltTowers();
                screen.removeElement(this);
                unbuiltTowerPopup();
            }
        };
        towerA.setMsg("These are your towers. They protect your base from creeps, and need to be charged from time to time.");
        screen.addElement(towerA);
    }

    private void focusBuiltTowers() {
        for (int i = 0; i < TowerState.getTowerList().size(); i++) {
            if (!TowerState.getTowerList().get(i).getUserData("Type").equals("TowerUnbuilt")) {
                TowerState.getTowerList().get(i).setMaterial(focusMaterial);
            }
        }
    }

    private void unfocusBuiltTowers() {
        for (int i = 0; i < TowerState.getTowerList().size(); i++) {
            if (!TowerState.getTowerList().get(i).getUserData("Type").equals("TowerUnbuilt")) {
                TowerState.getTowerList().get(i).setMaterial(assetManager.loadMaterial(getTowerOrigMat(i)));
            }
        }
    }

    private void unbuiltTowerPopup() {
        focusUnbuiltTowers();
        AlertBox unbuiltA = new AlertBox(screen, "Unbuilt Towers", new Vector2f(800, 500)) {
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                unfocusUnbuiltTowers();
                screen.removeElement(this);
                budgetPopup();
            }
        };
        unbuiltA.setMsg("These are towers that have not been built yet. You may build them for 100 credits.");
        screen.addElement(unbuiltA);
    }

    private void focusUnbuiltTowers() {
        for (int i = 0; i < TowerState.getTowerList().size(); i++) {
            if (TowerState.getTowerList().get(i).getUserData("Type").equals("TowerUnbuilt")) {
                TowerState.getTowerList().get(i).setMaterial(focusMaterial);
            }
        }
    }

    private void unfocusUnbuiltTowers() {
        for (int i = 0; i < TowerState.getTowerList().size(); i++) {
            if (TowerState.getTowerList().get(i).getUserData("Type").equals("TowerUnbuilt")) {
                TowerState.getTowerList().get(i).setMaterial(assetManager.loadMaterial(getTowerOrigMat(i)));
            }
        }

    }

    private void budgetPopup() {
        focusBudgetButton();
        AlertBox unbuiltA = new AlertBox(screen, "Budget", new Vector2f(1215, 125)) {
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                unfocusBudgetButton();
                screen.removeElement(this);
                creepPopup();
            }
        };
        unbuiltA.setMsg("This is where your budget is shown. You get more money when you kill creeps, and when you level up.");
        screen.addElement(unbuiltA);
    }

    private void focusBudgetButton() {
        GameGUI.highlightButton("Budget");
    }

    private void unfocusBudgetButton() {
        GameGUI.unhighlightButton("Budget");
    }

    private void upgradePopup() {
    }

    private void creepSpawnerPopup() {
        //CreepState.getCreepNode().getChild("spawner").setMaterial(focusMaterial);
    }

    private void creepPopup() {
        focusCreeps();
        AlertBox creepA = new AlertBox(screen, "Creep", new Vector2f(800, 400)) {
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                unfocusCreeps();
                screen.removeElement(this);
                start();
            }
        };
        creepA.setMsg("These are your enemies. They will spawn in proportion to your current level.");
        screen.addElement(creepA);
    }

    private void focusCreeps() {
        for (int i = 0; i < CreepState.creepList.size(); i++) {
            CreepState.creepList.get(i).setMaterial(focusMaterial);
        }
    }

    private void unfocusCreeps() {
        for (int i = 0; i < CreepState.creepList.size(); i++) {
            CreepState.creepList.get(i).setMaterial(assetManager.loadMaterial(getCreepOrigMat(i)));
        }
    }

    private void start() {
        tMS.pause();
    }

    private String getTowerOrigMat(int i) {
        return "Materials/" + GameState.getMatDir() + "/" + TowerState.getTowerList().get(i).getUserData("Type") + ".j3m";
    }

    private String getCreepOrigMat(int i) {
        String type = CreepState.getCreepList().get(i).getUserData("Name");
        return "Materials/" + GameState.getMatDir() + "/" + type + "Creep.j3m";
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        guiNode.removeControl(screen);
    }
}
