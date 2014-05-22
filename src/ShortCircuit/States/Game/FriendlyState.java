package ShortCircuit.States.Game;

import ShortCircuit.Controls.ChargerControl;
import ShortCircuit.Controls.TowerControl;
import DataStructures.Queue;
import ShortCircuit.Threading.TowerCharge;
import ShortCircuit.Threading.TowerDowngrade;
import ShortCircuit.Threading.TowerUpgrade;
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
 * This state controls the spawning and operation of friendly NPCS. Currently,
 * the goal is the implementation of the Charger NPC.
 *
 * @author Connor
 */
public class FriendlyState extends AbstractAppState {

    private SimpleApplication app;
    private EnemyState EnemyState;
    private AssetManager assetManager;
    private Queue<TowerControl> emptyTowers;
    private Queue<Spatial> activeChargers;
    private boolean[] globbedTowers; 
    private TowerCharge tcr;
    private TowerUpgrade tur;
    private TowerDowngrade tdr;
    public int selectedTower = -1;
    private Sphere chargerSphere = new Sphere(32, 32, 1f);
    private AppStateManager stateManager;
    private AudioState AudioState;
    private GraphicsState GraphicsState;
    private ArrayList<Spatial> towerList;
    private GameState GameState;
    private Node rootNode;
    
    public FriendlyState() {
        
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();
        this.EnemyState = this.stateManager.getState(EnemyState.class);
        this.AudioState = this.stateManager.getState(AudioState.class);
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.GameState = this.stateManager.getState(GameState.class);

        initLists();
        initRunnables();
    }

    private void initLists() {
        emptyTowers = new Queue<TowerControl>();
        activeChargers = new Queue<Spatial>();

    }

    public void setTowerList(ArrayList<Spatial> listIn) {
        towerList = listIn;
        globbedTowers = new boolean[towerList.size()];
    }

    private void initRunnables() {
        tur = new TowerUpgrade(this);
        tcr = new TowerCharge(this);
        tdr = new TowerDowngrade(this);
    }

    /**
     * Modifies the size of a tower at tindex.
     *
     * @param tindex - index of the tower to be modified. Then, sets
     * selectedTower to be tindex for other methods to access.
     */
    public void towerSelected(int tindex) {
        Spatial tp = towerList.get(tindex);
        System.out.println(tp.getUserDataKeys());
        if (tp.getUserData("Type").equals("TowerUnbuilt")) {
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
            Spatial tp = towerList.get(selectedTower);
            if (tp.getUserData("Type").equals("TowerUnbuilt")) {
                tp.setLocalScale(GraphicsState.getTowerUnbuiltSize());
            } else {
                tp.setLocalScale(GraphicsState.getTowerBuiltSize());
            }
        }
    }

    /**
     * Charging method used by GUI button.
     */
    public void chargeTower() {
        if (getSelected() != -1) {
            tcr.setTower(towerList.get(getSelected()), false);
            tcr.run();

        }
    }

    /**
     * Charging method used by player-friendly charger NPC.
     *
     * @param index - index of tower being charged by charger.
     */
    public void chargeTower(int index) {
        if (index != -1) {
            tcr.setTower(towerList.get(index), true);
            tcr.run();
        }
    }

    public void upgradeTower() {
        if (selectedTower != -1) {
            tur.run();
        }
    }

    public void upgradeTower(TowerControl tp) {
        tur.setManualTower(tp);
        tur.run();
    }

    public void downgradeTower() {
        if (selectedTower != -1) {
            tdr.setVictim(selectedTower);
            tdr.run();
        }
    }

    public String getSelectedTowerType() {
        return towerList.get(selectedTower).getUserData("Type");
    }
    
    

    public void towerTextureCharged(Spatial s) {
        GraphicsState.towerTextureCharged(s);
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

    public Spatial getTower(int tindex) {
        return towerList.get(tindex);
    }
    
    public ArrayList<Spatial> getTowerList() {
        return towerList;
    }

    public Vector3f getTowerBuiltSize() {
        return GraphicsState.getTowerBuiltSize();
    }

    public Vector3f getTowerUnbuiltSize() {
        return GraphicsState.getTowerUnbuiltSize();
    }

    public boolean[] getGlobbedTowerList() {
        return globbedTowers;
    }
    
    public void setTowerGlobbedStatus(int index, boolean bool) {
        globbedTowers[index] = bool;
    }
    
    public boolean getTowerGlobbedStatus(int index) {
        return globbedTowers[index];
    }
    

    /**
     * These are methods that are needed by factories to access GameState, Not
     * Used internally.
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

    /**
     * These are methods that are used by TowerParams, Not used internally.
     */
    public ScheduledThreadPoolExecutor getEx() {
        return GraphicsState.getEx();
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
     * Creates the charger spatial and adds the user data to it. Assigns
     * material and translation, then calls addNewCharger() to finish adding it
     * to the game.
     */
    public void createCharger() {
        Spatial charger = new Geometry("Charger", chargerSphere);
        charger.setUserData("RemainingCharges", 10);
        charger.setUserData("Health", 100);
        charger.setMaterial(assetManager.loadMaterial("Materials/Neon/Tower4.j3m"));
        charger.setLocalTranslation(GraphicsState.getBaseVec().add(0, 0, 2f));
        //charger.setLocalTranslation(new Vector3f(0,0,2f));
        addNewCharger(charger);
    }

    /*
     * Adds an empty tower to the list of towers that need to be charged.
     */
    public void addEmptyTower(TowerControl empty) {
        emptyTowers.enqueue(empty);
        GraphicsState.towerTextureEmpty(empty.getSpatial());
        AudioState.emptySound();
    }
    
    public void makeLaserBeam(Vector3f origin, Vector3f target, String towertype, float beamwidth) {
        GraphicsState.makeLaserBeam(origin, target, towertype, beamwidth);
    }

    /**
     * Clears the list of empty towers.
     */
    public void clearEmptyTowers() {
        emptyTowers.clear();
    }

    /**
     * Returns the list of all active chargers as an arraylist of spatials.
     *
     * @return activeChargers, the list of active charger NPC's
     */
    public Queue<Spatial> getActiveChargers() {
        return activeChargers;
    }

    public Queue<TowerControl> getEmptyTowers() {
        return emptyTowers;
    }

    /**
     * Clears the list of all active chargers.
     */
    public void clearActiveChargers() {
        activeChargers.clear();
    }

    public Vector3f getHomeVec() {
        return GraphicsState.getBaseVec().add(0f, 0f, 1f);
    }

    /**
     * Adds a new charger to the worldNode after adding control and creating a
     * reference to the charger in the active charger list.
     *
     * @param charger
     */
    private void addNewCharger(Spatial charger) {
        charger.addControl(new ChargerControl(this));
        activeChargers.enqueue(charger);
        rootNode.attachChild(charger);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        clearActiveChargers();
        clearEmptyTowers();
    }
}
