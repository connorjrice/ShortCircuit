package ShortCircuit.States.Game;

import ShortCircuit.MainState.TowerMainState;
import ShortCircuit.States.GUI.GameGUI;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.LineWrapMode;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Screen;

/**
 * This AppState will contain all tutorial functionality. Popups to explain
 * towers, banner to state what needs to be done/level up, how levels work, how
 * upgrades work, how budget works, how bombs work.
 *
 * TODO: Make main menu unopenable while dialog boxes are up.
 * TODO: Scaling for tutorial buttons
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
    private FriendlyState FriendlyState;
    private EnemyState EnemyState;
    private GameGUI GameGUI;
    private boolean hasGlobbed = false;
    private AppStateManager stateManager;
    private GraphicsState GraphicsState;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.tMS = this.stateManager.getState(TowerMainState.class);
        this.guiNode = this.app.getGuiNode();
        this.GameGUI = this.stateManager.getState(GameGUI.class);
        this.GameState = stateManager.getState(GameState.class);
        this.FriendlyState = this.stateManager.getState(FriendlyState.class);
        this.EnemyState = this.stateManager.getState(EnemyState.class);
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.assetManager = this.app.getAssetManager();
        initScreen();
        initMaterials();
        tMS.pause();
        basePopup();
    }

    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, GameGUI.getAtlas());
        screen.setUseMultiTouch(false);
        guiNode.addControl(screen);
    }

    private void initMaterials() {
        focusMaterial = assetManager.loadMaterial("Materials/Circuit2/beam1.j3m");
    }

    @Override
    public void update(float tpf) {
        if (EnemyState.getGlobList().size() > 0 && !hasGlobbed) {
            globPopup();
            hasGlobbed = true;
        }

    }

    private void globPopup() {
        Spatial glob = GameState.getWorldNode().getChild("Glob");
        glob.setMaterial(focusMaterial);
        tMS.pause();
        ButtonAdapter globA = new ButtonAdapter(screen, "Glob", new Vector2f(glob.getLocalTranslation().x+200,600)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                screen.removeElement(this);
                tMS.pause();
            }
        };
        globA.setText("This is a glob.");
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
        ButtonAdapter baseA = new ButtonAdapter(screen, "Your Base", new Vector2f(800, 800)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                GameState.getWorldNode().getChild("Base")
                        .setMaterial(assetManager.loadMaterial(GraphicsState.getBaseTexLoc()));
                screen.removeElement(this);
                towerPopup();
            }

        };
        baseA.setDimensions(300, 100);
        baseA.setText("This is your base. Defend it from creeps.");
        screen.addElement(baseA);
    }

    private void towerPopup() {
        focusBuiltTowers();
        ButtonAdapter towerA = new ButtonAdapter(screen, "Your Towers", new Vector2f(700, 600)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                unfocusBuiltTowers();
                screen.removeElement(this);
                unbuiltTowerPopup();
            }
        };
        towerA.setDimensions(500, 100);
        towerA.setText("These are your towers. They protect your base from creeps, and need to be charged from time to time.");
        towerA.setTextWrap(LineWrapMode.Word);
        towerA.setTextAlign(BitmapFont.Align.Center);
        screen.addElement(towerA);
    }

    private void focusBuiltTowers() {
        for (int i = 0; i < FriendlyState.getTowerList().size(); i++) {
            if (!FriendlyState.getTowerList().get(i).getType().equals("TowerUnbuilt")) {
                FriendlyState.getTowerList().get(i).getSpatial().setMaterial(focusMaterial);
            }
        }
    }

    private void unfocusBuiltTowers() {
        for (int i = 0; i < FriendlyState.getTowerList().size(); i++) {
            if (!FriendlyState.getTowerList().get(i).getType().equals("TowerUnbuilt")) {
                FriendlyState.getTowerList().get(i).getSpatial().setMaterial(assetManager.loadMaterial(getTowerOrigMat(i)));
            }
        }
    }

    private void unbuiltTowerPopup() {
        focusUnbuiltTowers();
        ButtonAdapter unbuiltA = new ButtonAdapter(screen, "Unbuilt Towers", new Vector2f(800, 500)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                unfocusUnbuiltTowers();
                screen.removeElement(this);
                budgetPopup();
            }
        };
        unbuiltA.setDimensions(500,100);
        unbuiltA.setText("These are towers that have not been built yet. You may build them for 100 credits.");
        screen.addElement(unbuiltA);
    }

    private void focusUnbuiltTowers() {
        for (int i = 0; i < FriendlyState.getTowerList().size(); i++) {
            if (FriendlyState.getTowerList().get(i).getType().equals("TowerUnbuilt")) {
                FriendlyState.getTowerList().get(i).getSpatial().setMaterial(focusMaterial);
            }
        }
    }

    private void unfocusUnbuiltTowers() {
        for (int i = 0; i < FriendlyState.getTowerList().size(); i++) {
            if (FriendlyState.getTowerList().get(i).getType().equals("TowerUnbuilt")) {
                FriendlyState.getTowerList().get(i).getSpatial().setMaterial(assetManager.loadMaterial(getTowerOrigMat(i)));
            }
        }

    }

    private void budgetPopup() {
        focusBudgetButton();
        ButtonAdapter budgetA = new ButtonAdapter(screen, "Budget", new Vector2f(1215, 125)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                unfocusBudgetButton();
                screen.removeElement(this);
                creepPopup();
            }
        };
        budgetA.setDimensions(500,100);
        budgetA.setText("This is where your budget is shown. You get more money when you kill creeps, and when you level up.");
        screen.addElement(budgetA);
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
        ButtonAdapter creepA = new ButtonAdapter(screen, "Creep", new Vector2f(800, 400)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                unfocusCreeps();
                screen.removeElement(this);
                start();
            }
        };
        creepA.setDimensions(500,100);
        creepA.setText("These are your enemies. They will spawn in proportion to your current level.");
        screen.addElement(creepA);
    }

    private void focusCreeps() {
        for (int i = 0; i < EnemyState.creepList.size(); i++) {
            EnemyState.creepList.get(i).setMaterial(focusMaterial);
        }
    }

    private void unfocusCreeps() {
        for (int i = 0; i < EnemyState.creepList.size(); i++) {
            EnemyState.creepList.get(i).setMaterial(assetManager.loadMaterial(getCreepOrigMat(i)));
        }
    }

    private void start() {
        tMS.pause();
    }

    private String getTowerOrigMat(int i) {
        return "Materials/" + GraphicsState.getMatDir() + "/" + FriendlyState.getTowerList().get(i).getType() + ".j3m";
    }

    private String getCreepOrigMat(int i) {
        String type = EnemyState.getCreepList().get(i).getUserData("Name");
        return "Materials/" + GraphicsState.getMatDir() + "/" + type + "Creep.j3m";
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        guiNode.removeControl(screen);
    }
}
