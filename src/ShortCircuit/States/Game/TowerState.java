package ShortCircuit.States.Game;

import ShortCircuit.Threading.UpgradeTower;
import ShortCircuit.Factories.TowerFactory;
import ShortCircuit.Objects.Charges;
import ShortCircuit.Controls.TowerControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
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
    public ArrayList<Spatial> towerList = new ArrayList<Spatial>();
    public ArrayList<Vector3f> unbuiltTowerVecs = new ArrayList<Vector3f>();
    private Vector3f unbuiltTowerSize = new Vector3f(0.5f, 0.5f, .1f);
    private Vector3f builtTowerSize = new Vector3f(0.5f, 0.5f, 1.0f);
    private Vector3f unbuiltTowerSelected = new Vector3f(0.6f, 0.6f, 1.5f);
    private Vector3f builtTowerSelected = new Vector3f(0.7f, 0.7f, 2.5f);
    
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
    private int buildCost = 100;
    private Node worldNode;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.worldNode = this.GameState.getWorldNode();


    }


    public void attachTowerNode() {

        worldNode.attachChild(towerNode);
    }

    /**
     * Makes the tower slightly larger to show which is currently selected.
     *
     * @param selectedTower
     */
    public void towerSelected(int tindex) {
        Spatial selTower = towerList.get(tindex);
        if (selTower.getUserData("Type").equals("unbuilt")) {
            selTower.setLocalScale(unbuiltTowerSelected);
        } else {
            selTower.setLocalScale(builtTowerSelected);
        }
        selectedTower = tindex;
    }

    /**
     * Returns towers to normal size.
     */
    public void shortenTowers() {
        if (selectedTower != -1) {
            Spatial selTower = towerList.get(selectedTower);
            if (selTower.getUserData("Type").equals("unbuilt")) {
                selTower.setLocalScale(unbuiltTowerSize);
            } else {
                selTower.setLocalScale(builtTowerSize);
            }
        }
    }

    /**
     * Charges a tower at a specific index point.
     *
     * @param selectedTower = the index on the towerList of the specified tower.
     */
    public void chargeTower() {
        if (selectedTower != -1) {
            TowerControl tower = towerList.get(selectedTower).getControl(TowerControl.class);
            if (GameState.getPlrBudget() >= chargeCost) {
                if (tower.getType().equals("redLaser")) {
                    changeTowerTexture(tow1MatLoc, tower);
                    tower.charges.add(new Charges("redLaser"));
                    GameState.decPlrBudget(chargeCost);
                } else if (tower.getType().equals("pinkLaser")) {
                    changeTowerTexture(tow2MatLoc, tower);
                    tower.charges.add(new Charges("pinkLaser"));
                    GameState.decPlrBudget(chargeCost);
                } else if (tower.getType().equals("greenLaser")) {
                    changeTowerTexture(tow3MatLoc, tower);
                    tower.charges.add(new Charges("greenLaser"));
                    GameState.decPlrBudget(chargeCost);
                } else if (tower.getType().equals("purpleLaser")) {
                    changeTowerTexture(tow4MatLoc, tower);
                    tower.charges.add(new Charges("purpleLaser"));
                    GameState.decPlrBudget(chargeCost);
                }
            }
        }
    }
    
    public void upgradeTower() {
        if (selectedTower != -1) {
            UpgradeTower ut = new UpgradeTower(GameState, towerList.get(selectedTower).getUserData("Type"));
            ut.run();
        }
    }


    /**
     * Creates a tower based upon the location from initialTowers() or
     * createNewTower().
     *
     * @param v, vector for tower
     */
    public void createTower(int index, Vector3f towervec, String type) {
        TowerFactory tf = new TowerFactory(index, towervec,  unbuiltTowerSize, type, assetManager, GameState);
        towerList.add(tf.getTower());
        towerNode.attachChild(tf.getTower());
    }

    public void buildUnbuiltTowers(ArrayList<Vector3f> unbuiltTowerIn) {
        unbuiltTowerVecs = unbuiltTowerIn;
        for (int i = 0; i < unbuiltTowerVecs.size(); i++) {
            createTower(i, unbuiltTowerVecs.get(i), "unbuilt");
        }
    }

    public void buildStarterTowers(ArrayList<Integer> starterTowerIn) {
        tow1MatLoc = "Materials/" + GameState.getMatDir() + "/Tower1.j3m";
        tow2MatLoc = "Materials/" + GameState.getMatDir()+ "/Tower2.j3m";
        tow3MatLoc = "Materials/" + GameState.getMatDir()+ "/Tower3.j3m";
        tow4MatLoc = "Materials/" + GameState.getMatDir()+ "/Tower4.j3m";
        towUnMatLoc = "Materials/" + GameState.getMatDir()+ "/UnbuiltTower.j3m";
        towEmMatLoc = "Materials/" + GameState.getMatDir()+ "/EmptyTower.j3m";
        for (int i = 0; i < starterTowerIn.size(); i++) {
            TowerControl tower = towerList.get(i).getControl(TowerControl.class);
            tower.charges.add(new Charges("redLaser"));
            tower.setType("redLaser");
            tower.setBuilt();
            changeTowerTexture(tow1MatLoc, tower);
            tower.setSize(builtTowerSize);
        }
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
    
    @Override
    public void stateAttached(AppStateManager stateManager) {
        
    }
    
    @Override
    public void stateDetached(AppStateManager stateManager) {
        towerNode.detachAllChildren();
    }


    @Override
    public void cleanup() {
        super.cleanup();
        towerNode.detachAllChildren();
    }
}
