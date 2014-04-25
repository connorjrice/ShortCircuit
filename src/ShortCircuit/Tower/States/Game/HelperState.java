package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Controls.ChargerControl;
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

/**
 * This state controls the spawning and operation of friendly NPCS.
 * Currently, the goal is the implementation of the Charger NPC.
 * TODO: Implement Charger NPC Type
 * @author Connor
 */
public class HelperState extends AbstractAppState {

    private SimpleApplication app;
    private GameState GameState;
    private TowerState TowerState;
    private CreepState CreepState;
    private Node worldNode;
    private AssetManager assetManager;
    
    private ArrayList<Spatial> emptyTowers;
    private ArrayList<Spatial> activeChargers;
    
    private Sphere chargerSphere = new Sphere(32,32,1f);
    
    private float updateTimer = 0f;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
        this.CreepState = this.app.getStateManager().getState(CreepState.class);
        this.worldNode = this.GameState.getWorldNode();
        this.assetManager = this.app.getAssetManager();
        initLists();
    }
    
    private void initLists() {
        emptyTowers = new ArrayList<Spatial>();
        activeChargers = new ArrayList<Spatial>();
    }
    
    @Override
    public void update(float tpf) {
        if (!activeChargers.isEmpty()) {
            if (updateTimer > 0.5f) {
                if (!emptyTowers.isEmpty()) {
                    System.out.println("Should go");
                    goCharge();
                }
                updateTimer = 0;
            }
            else {
                updateTimer += tpf;
            }
        }
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
        charger.setLocalTranslation(GameState.getBaseVec().add(0,0,1f));
        addNewCharger(charger);
    }
    
    /**
     * Tells the first active charger to go charge a tower.
     */
    private void goCharge() {

    }
    
    /*
     * Adds an empty tower to the list of towers that need to be charged.
     */
    public void addEmptyTower(Spatial empty) {
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
    
    public ArrayList<Spatial> getEmptyTowers() {
        return emptyTowers;
    }
    
    /**
     * Clears the list of all active chargers.
     */
    public void clearActiveChargers() {
        activeChargers.clear();
    }
    
    public Vector3f getHomeVec() {
        return GameState.getBaseVec().add(0f,0f,1f);
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
    
    public void chargeTower(int index) {
        TowerState.chargerChargeTower(index);
    }
    
    public AssetManager getAssetManager() {
        return assetManager;
    }
    
    public String getMatDir() {
        return GameState.getMatDir();
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        clearActiveChargers();
        clearEmptyTowers();
    }
}
