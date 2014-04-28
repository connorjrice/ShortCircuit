package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Threading.TowerUpgrade;
import ShortCircuit.Tower.Factories.TowerFactory;
import ShortCircuit.Tower.Objects.Charges;
import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.Threading.TowerCharge;
import ShortCircuit.Tower.Threading.TowerDowngrade;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author Connor Rice
 */
public class TowerState extends AbstractAppState {

    private SimpleApplication app;
    private AssetManager assetManager;
    private ArrayList<Spatial> towerList = new ArrayList<Spatial>();
    private ArrayList<Vector3f> unbuiltTowerVecs = new ArrayList<Vector3f>();
    private ArrayList<Integer> globbedTowers = new ArrayList<Integer>();
    private Vector3f unbuiltTowerSize = new Vector3f(0.5f, 0.5f, .1f);
    private Vector3f builtTowerSize = new Vector3f(0.5f, 0.5f, 1.0f);
    private Vector3f unbuiltTowerSelected = new Vector3f(0.6f, 0.6f, 1.5f);
    private Vector3f builtTowerSelected = new Vector3f(0.7f, 0.7f, 2.5f);
    
    
    private TowerUpgrade tur;
    private TowerFactory tf;
    
    public String tow1MatLoc;
    public String tow2MatLoc;
    public String tow3MatLoc;
    public String tow4MatLoc;
    public String towUnMatLoc;
    public String towEmMatLoc;
                 
    public int selectedTower = -1;
    private GameState GameState;
    private Node towerNode = new Node("Tower");
    private int chargeCost = 10;
    private Node worldNode;
    public AudioNode charge;
    private HelperState HelperState;
    private TowerCharge tcr;
    private TowerDowngrade tdr;
    private AudioNode buildSound;

    public TowerState() {}
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.HelperState = this.app.getStateManager().getState(HelperState.class);
        this.worldNode = this.GameState.getWorldNode();
        initFactories();
        initRunnables();
        initAssets();
    }
    
    private void initFactories() {
        tf = new TowerFactory(GameState);

    }
    private void initRunnables() {
        tur = new TowerUpgrade(this);
        tcr = new TowerCharge(this);
        tdr = new TowerDowngrade(this);
    }
    
    private void initAssets() {
        charge = new AudioNode(assetManager, "Audio/chargegam.wav");
        charge.setPositional(false);
        charge.setVolume(.8f);
        
        buildSound = new AudioNode(assetManager, "Audio/buildgam.wav");
        buildSound.setPositional(false);
        buildSound.setVolume(.3f);
    }


    public void attachTowerNode() {
        worldNode.attachChild(towerNode);
    }

    /**
     * Modifies the size of a tower specified by the input parameter tindex.
     * @param tindex - index of the tower to be modified.
     * Then, sets selectedTower to be tindex for other methods to access.
     */
    public void towerSelected(int tindex) {
        Spatial selTower = towerList.get(tindex);
        if (selTower.getUserData("Type").equals("TowerUnbuilt")) {
            selTower.setLocalScale(unbuiltTowerSelected);
        } else {
            selTower.setLocalScale(builtTowerSelected);
        }
        selectedTower = tindex;
    }
    
    /**
     * Plays an instance of the charge sound.
     * Used locally by chargeTower().
     */
    public void playChargeSound() {
        charge.playInstance();
    }

    /**
     * If there has been a tower selected, (selectedTower is not -1), this
     * method shortens that tower to indicate that it is no longer selected.
     */
    public void shortenTower() {
        if (selectedTower != -1) {
            Spatial selTower = towerList.get(selectedTower);
            if (selTower.getUserData("Type").equals("TowerUnbuilt")) {
                selTower.setLocalScale(unbuiltTowerSize);
            } else {
                selTower.setLocalScale(builtTowerSize);
            }
        }
    }

    /**
     * Charges a tower at a specific index point.
     * @param selectedTower = the index on the towerList of the specified tower.
     */
    public void chargeTower() {
        if (GameState.getSelected() != -1) {
            TowerControl tower = getTowerList().get(GameState.getSelected()).getControl(TowerControl.class);
            tcr.setTower(tower);
            tcr.run();
            if (HelperState.getEmptyTowers().contains(tower.getSpatial())) {
                // TODO: debug this (charger/empty towers)
                HelperState.getEmptyTowers().remove(tower.getSpatial());
            }
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
    
    /**
     * Charges a tower at a specific index point.
     *
     * @param selectedTower = the index on the towerList of the specified tower.
     */
    public void chargerChargeTower(int index) {
        if (index != -1) {
            TowerControl tower = towerList.get(index).getControl(TowerControl.class);
            changeTowerTexture("Materials/"+ getMatDir()+"/"+tower.getTowerType()+".j3m", tower);
            tower.addCharges();
            playChargeSound();
        }
    }
    

    
    public String getSelectedTowerType() {
        return towerList.get(selectedTower).getUserData("Type");
    }


    /**
     * Creates a tower based upon the location from initialTowers() or
     * createNewTower().
     *
     * @param v, vector for tower
     */
    public void createTower(int index, Vector3f towervec, String type) {
        towerList.add(tf.getTower(index, towervec,  unbuiltTowerSize, type));
        towerNode.attachChild(towerList.get(towerList.size()-1));
    }

    public void buildUnbuiltTowers(ArrayList<Vector3f> unbuiltTowerIn) {
        unbuiltTowerVecs = unbuiltTowerIn;
        for (int i = 0; i < unbuiltTowerVecs.size(); i++) {
            createTower(i, unbuiltTowerVecs.get(i), "TowerUnbuilt");
        }
    }

    public void buildStarterTowers(ArrayList<Integer> starterTowerIn) {
        tow1MatLoc = "Materials/" + getMatDir() + "/Tower1.j3m";
        tow2MatLoc = "Materials/" + getMatDir()+ "/Tower2.j3m";
        tow3MatLoc = "Materials/" + getMatDir()+ "/Tower3.j3m";
        tow4MatLoc = "Materials/" + getMatDir()+ "/Tower4.j3m";
        towUnMatLoc = "Materials/" + getMatDir()+ "/TowerUnbuilt.j3m";
        towEmMatLoc = "Materials/" + getMatDir()+ "/EmptyTower.j3m";
        for (int i = 0; i < starterTowerIn.size(); i++) {
            TowerControl tower = towerList.get(starterTowerIn.get(i)).getControl(TowerControl.class);
            tower.charges.add(new Charges("Tower1"));
            tower.setTowerType("Tower1");
            tower.setBuilt();
            changeTowerTexture(tow1MatLoc, tower);
            tower.setSize(builtTowerSize);
        }
    }
    
    public void playBuildSound(float pitch) {
        buildSound.setPitch(pitch);
        buildSound.playInstance();
        buildSound.setPitch(1f);
    }

    /**
     * Changes a tower at the given index to the given texture.
     *
     * @param control
     * @param texture
     */
    public void changeTowerTexture(String matLoc, TowerControl control) {
        control.getSpatial().setMaterial(assetManager.loadMaterial(matLoc));
    }

    public void reset() {
        towerList.clear();
        unbuiltTowerVecs.clear();
        towerNode.detachAllChildren();
    }

    public Spatial getSelectedTower() {
        return towerList.get(selectedTower);
    }

    public int getSelectedNum() {
        return selectedTower;
    }
    
    public ArrayList<Spatial> getTowerList() {
        return towerList;
    }
    
    public ArrayList<Integer> getGlobbedTowerList() {
        return globbedTowers;
    }
    
    public String getMatDir() {
        return GameState.getMatDir();
    }

    public ArrayList<Spatial> getCreepList() {
        return GameState.getCreepList();
    }
    
    public ArrayList<Spatial> getCreepSpawnerList() {
        return GameState.getCreepSpawnerList();
    }

    public ScheduledThreadPoolExecutor getEx() {
        return GameState.getEx();
    }

    public SimpleApplication getApp() {
        return app;
    }
    
    public Vector3f getBuiltTowerSize() {
        return builtTowerSize;
    }
    
    public Vector3f getUnbuiltTowerSize() {
        return unbuiltTowerSize;
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
    
    public int getSelected() {
        return selectedTower;
    }
    
    public void incFours() {
        GameState.incFours();
    }
    

    @Override
    public void cleanup() {
        super.cleanup();
        towerNode.detachAllChildren();
        towerList.clear();
        globbedTowers.clear();
        
    }

}
