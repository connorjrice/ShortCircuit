package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Controls.ChargerControl;
import ShortCircuit.Tower.Factories.TowerFactory;
import ShortCircuit.Tower.MapXML.Objects.TowerParams;
import ShortCircuit.Tower.Threading.TowerCharge;
import ShortCircuit.Tower.Threading.TowerDowngrade;
import ShortCircuit.Tower.Threading.TowerUpgrade;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * This state controls the spawning and operation of friendly NPCS.
 * Currently, the goal is the implementation of the Charger NPC.
 * @author Connor
 */
public class FriendlyState extends AbstractAppState {

    private SimpleApplication app;
    private GameState GameState;
    private EnemyState EnemyState;
    private Node worldNode;
    private AssetManager assetManager;
    
    private ArrayList<TowerParams> emptyTowers;
    private ArrayList<Spatial> activeChargers;
    
    private Node towerNode = new Node("Tower");
    
    private ArrayList<Integer> globbedTowers = new ArrayList<Integer>();
    
    private TowerFactory tf;
    private TowerCharge tcr;
    private TowerUpgrade tur;
    private TowerDowngrade tdr;
    
    public int selectedTower = -1;
    

    private Sphere chargerSphere = new Sphere(32,32,1f);
    
    private float updateTimer = 0f;
    private AppStateManager stateManager;
    private FriendlyState FriendlyState;
    private AudioState AudioState;
    private GraphicsState GraphicsState;
    private ArrayList<TowerParams> towerList;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.GameState = this.stateManager.getState(GameState.class);
        this.EnemyState = this.stateManager.getState(EnemyState.class);
        this.AudioState = this.stateManager.getState(AudioState.class);
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.worldNode = this.GameState.getWorldNode();
        this.assetManager = this.app.getAssetManager();
        initLists();
        initRunnables();
    }
    
    private void initLists() {
        emptyTowers = new ArrayList<TowerParams>();
        activeChargers = new ArrayList<Spatial>();
    }
    
    @Override
    public void update(float tpf) {

    }
    
    public void setTowerList(ArrayList<TowerParams> listIn) {
        towerList = listIn;
    }

    private void initRunnables() {
        tur = new TowerUpgrade(this);
        tcr = new TowerCharge(this);
        tdr = new TowerDowngrade(this);
    }
    
    public void attachTowerNode() {
        worldNode.attachChild(towerNode);
    }

    /**
     * Modifies the size of a tower at tindex.
     * @param tindex - index of the tower to be modified. Then, sets
     * selectedTower to be tindex for other methods to access.
     */
    public void towerSelected(int tindex) {
        TowerParams tp = towerList.get(tindex);
        if (tp.getType().equals("TowerUnbuilt")) {
            GraphicsState.setTowerScale(tindex, "UnbuiltSelected");
        } else {
            GraphicsState.setTowerScale(tindex, "BuiltSelected");
        }
        selectedTower = tindex;
    }

    /**
     * Returns towers to their normal size. Called by GameState when selecting a
     * new tower.
     */
    public void shortenTower() {
        if (selectedTower != -1) {
            TowerParams tp = towerList.get(selectedTower);
            if (tp.getType().equals("TowerUnbuilt")) {
                tp.setScale(GraphicsState.getTowerUnbuiltSelected());
            } else {
                tp.setScale(GraphicsState.getTowerBuiltSelected());
            }
        }
    }

    /**
     * Charging method used by GUI button.
     */
    public void chargeTower() {
        if (getSelected() != -1) {
            tcr.setTower(towerList.get(getSelected()));
            tcr.run();
            if (getEmptyTowers().contains(towerList.get(getSelected()))) {
                getEmptyTowers().remove(towerList.get(getSelected()));
            }
        }
    }

    /**
     * Charging method used by player-friendly charger NPC.
     * @param index - index of tower being charged by charger.
     */
    public void chargeTower(int index) {
        if (index != -1) {
            GraphicsState.changeTowerTexture(towerList.get(index));
            towerList.get(index).getControl().addCharges();
            playChargeSound();
        }
    }

    public void upgradeTower() {
        if (selectedTower != -1) {
            tur.run();
        }
    }

    public void downgradeTower() {
        if (selectedTower != -1) {
            tdr.setVictim(selectedTower);
            tdr.run();
        }
    }

    public String getSelectedTowerType() {
        return towerList.get(selectedTower).getType();
    }


    public void changeTowerTexture(TowerParams tower) {
        GraphicsState.changeTowerTexture(tower);
    }

    public void playChargeSound() {
        AudioState.chargeSound();
    }

    public void playBuildSound(float pitch) {
        AudioState.buildSound(pitch);
    }

    /**
     * Returns the directory of the materials that are being used by the current
     * level. Used internally and by runnables.
     */
    public String getMatDir() {
        return GraphicsState.getMatDir();
    }
    
    public ArrayList<TowerParams> getTowerList() {
        return towerList;
    }
    
    public Vector3f getTowerBuiltSize() {
        return GraphicsState.getTowerBuiltSize();
    }

    public Vector3f getTowerUnbuiltSize() {
        return GraphicsState.getTowerUnbuiltSize();
    }



    public ArrayList<Integer> getGlobbedTowerList() {
        return globbedTowers;
    }

    /**
     * These are methods that are needed by factories to access GameState,
     * Not Used internally.
     */
    
    public int getSelected() {
        return selectedTower;
    }

    public void incFours() {
        GameState.incFours();
    }

    public int getPlrBudget() {
        return GameState.getPlrBudget();
    }

    public void decPlrBudget(int cost) {
        GameState.decPlrBudget(cost);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

        /**
     * These are methods that are used by TowerParams, 
     * Not used internally.
     */
    
    public ScheduledThreadPoolExecutor getEx() {
        return GameState.getEx();
    }

    public SimpleApplication getApp() {
        return app;
    }

    public ArrayList<Spatial> getCreepList() {
        return EnemyState.getCreepList();
    }

    public ArrayList<Spatial> getCreepSpawnerList() {
        return EnemyState.getCreepSpawnerList();
    }

    /**
     * These are methods that are used by GameState, 
     * Not used internally.
     */
    
    public int getSelectedTowerIndex() {
        return selectedTower;
    }
    
    /**
     * Creates the charger spatial and adds the user data to it. Assigns
     * material and translation, then calls addNewCharger() to finish adding
     * it to the game.
     */
    public void createCharger() {
        Spatial charger = new Geometry("Charger", chargerSphere);
        charger.setUserData("RemainingCharges", 10);
        charger.setUserData("Health", 100);
        charger.setMaterial(assetManager.loadMaterial("Materials/Plain/Base.j3m"));
        charger.setLocalTranslation(GraphicsState.getBaseVec().add(0,0,1f));
        addNewCharger(charger);
    }

    /*
     * Adds an empty tower to the list of towers that need to be charged.
     */
    public void addEmptyTower(TowerParams empty) {
        if (!emptyTowers.contains(empty)) {
            emptyTowers.add(empty);
        }

    }
    
    /**
     * Clears the list of empty towers.
     */
    public void clearEmptyTowers() {
        emptyTowers.clear();
    }
    
    /**
     * Returns the list of all active chargers as an arraylist of spatials.
     * @return activeChargers, the list of active charger NPC's
     */
    public ArrayList<Spatial> getActiveChargers() {
        return activeChargers;
    }
    
    public ArrayList<TowerParams> getEmptyTowers() {
        return emptyTowers;
    }
    
    /**
     * Clears the list of all active chargers.
     */
    public void clearActiveChargers() {
        activeChargers.clear();
    }
    
    public Vector3f getHomeVec() {
        return GraphicsState.getBaseVec().add(0f,0f,1f);
    }
    
    /**
     * Adds a new charger to the worldNode after adding control and creating
     * a reference to the charger in the active charger list.
     * @param charger 
     */
    private void addNewCharger(Spatial charger) {
        charger.addControl(new ChargerControl(this));
        activeChargers.add(charger);
        worldNode.attachChild(charger);
    }
    
    
    @Override
    public void cleanup() {
        super.cleanup();
        clearActiveChargers();
        clearEmptyTowers();
        towerNode.detachAllChildren();

    }
}
