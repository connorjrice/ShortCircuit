package ShortCircuit.States.Game;

import ShortCircuit.Threading.DropBomb;
import ShortCircuit.Factories.BaseFactory;
import ShortCircuit.Factories.FloorFactory;
import ShortCircuit.Controls.BaseControl;
import ShortCircuit.Objects.LevelParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
/**
 * TODO: Finish integration with LevelState
 * TODO: Documentation
 * @author Connor Rice
 */
public class GameState extends AbstractAppState {
    
    private CreepState CreepState;
    private TowerState TowerState;
    private BeamState BeamState;

    
    private SimpleApplication app;
    private AssetManager assetManager;
    private Node rootNode;
    private Sphere bombMesh = new Sphere(16, 16, 1.0f);
    private Node worldNode = new Node("World");
    
    private int levelCap;
    private int levelMod;
    private int plrLevel;
    private int plrScore;
    private int plrHealth;
    private int plrBudget;
    private int plrBombs;
    private float updateTimer = 0;
    private boolean debugMode = false;
    private boolean plrWonLast;
    
    protected int numCreeps;
    protected int creepMod;

    public ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(4);
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.BeamState = this.app.getStateManager().getState(BeamState.class);
        this.CreepState = this.app.getStateManager().getState(CreepState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (isEnabled()) {
            if (updateTimer > .1) {
                if (getPlrScore() > levelCap) {
                    incPlrLvl();
                    levelCap *= 2;
                    incPlrHealth();
                }
                if (debugMode) {
                    debugLoopAdditions();
                }
                updateTimer = 0;
            } else {
                updateTimer += tpf;
            }
        }
    }


    
    public void attachWorldNode() {
        rootNode.attachChild(worldNode);        
    }
    
    public void setLevelParams(LevelParams lp) {
        setNumCreeps(lp.getNumCreeps());
        setCreepMod(lp.getCreepMod());
        setLevelCap(lp.getLevelCap());
        setLevelMod(lp.getLevelMod());
        setPlrHealth(lp.getPlrHealth());
        setPlrBudget(lp.getPlrBudget());
        setPlrLvl(lp.getPlrLevel());
        setPlrScore(lp.getPlrScore());
        setDebug(lp.getDebug());
    }

    
    private void debugLoopAdditions() {
        dropBomb(new Vector3f(0.0f,-7.5f,0.1f));
    }


    public void baseShoot() {
        BaseControl base = worldNode.getChild("Base").getControl(BaseControl.class);
        base.shootCreep();
    }

    /**
     * Provides the cost of various operations.
     *
     * @param type
     * @return (the cost of the operation)
     */
    public String getCost(Object type) {
        if (type.equals("Charge")) {
            return "10";
        } else if (type.equals("unbuilt")) {
            return "100";
        } else if (type.equals("redLaser")) {
            return "50";

        } else if (type.equals("pinkLaser")) {
            return "100";

        } else if (type.equals("greenLaser")) {
            return "500";
        } else {
            return "0";
        }
    }

    public void touchHandle(Vector3f trans, Spatial target) {
        if (target.getName().equals("Tower")) {
            TowerState.shortenTowers();
            int towerIndex = target.getUserData("Index");
            TowerState.towerSelected(towerIndex);
        } else {
            dropBomb(trans);
        }
    }

    public void dropBomb(Vector3f trans) {
        DropBomb db = new DropBomb(trans, this);
        db.run();
    }

    /**
     * Creates the light for the game world.
     */
    public void createLight() {
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(5f));
        worldNode.addLight(ambient);
    }

    /**
     * Creates a textured floor.
     */
    public void createFloor(Vector3f floorscale, String floortexloc) {
        FloorFactory ff = new FloorFactory(floorscale, floortexloc, 
                assetManager, this);
        worldNode.attachChild(ff.getFloor());

    }

    /**
     * Creates a base at the base vector given by LevelState.
     * TODO:Should be updated to allow for multiple bases
     */
    public void createBase(String texloc, Vector3f basevec, Vector3f basescale) {
        BaseFactory bf = new BaseFactory(texloc, basevec, basescale, assetManager, this);
        worldNode.attachChild(bf.getBase());
    }
    
    public BeamState getBeamState() {
        return BeamState;
    }
    
    public TowerState getTowerState() {
        return TowerState;
    }
    
    public CreepState getCreepState() {
        return CreepState;
    }

    public Node getWorldNode() {
        return worldNode;
    }
    
    public Node getCreepNode() {
        return CreepState.getCreepNode();
    }

    public ArrayList<Spatial> getCreepList() {
        return CreepState.creepList;
    }
    
    public ArrayList<Spatial> getCreepSpawnerList() {
        return CreepState.getCreepSpawnerList();
    }

    public ArrayList<Spatial> getTowerList() {
        return TowerState.towerList;
    }
    
    public Sphere getBombMesh() {
        return bombMesh;
    }
    
    public void setNumCreeps(int nc) {
        numCreeps = nc;
    }
    
    public void setCreepMod(int cm) {
        creepMod = cm;
    }
    
    protected int getNumCreeps() {
        return numCreeps;
    }
    
    protected int getCreepMod() {
        return creepMod;
    }
    

    // User Data Manipulation \\
    private void setLevelCap(int lc) {
        levelCap = lc;
    }
    
    private void setLevelMod(int lc) {
        levelCap = lc;
    }
    
    public void setDebug(boolean isDebug) {
        debugMode = isDebug;
    }
    
    public int getSelected() {
        return TowerState.getSelectedNum();
    }

    public void setPlrBombs(int bombs) {
    }

    public int getPlrBombs() {
        return plrBombs;
    }

    public void incPlrBombs(int inc) {
        plrBombs += inc;
    }

    public void decPlrBombs() {
        plrBombs -= 1;
    }

    public void setPlrLvl(int level) {
        plrLevel = level;
    }

    public int getPlrLvl() {
        return plrLevel;
    }

    public void incPlrLvl() {
        plrLevel += 1;
    }

    public void setPlrScore(int score) {
        plrScore = score;
    }
    
    public void incPlrScore(int inc) {
        plrScore += inc;
    }

    public int getPlrScore() {
        return plrScore;
    }

    public void setPlrHealth(int health) {
        plrHealth = health;
    }

    public int getPlrHealth() {
        return plrHealth;
    }

    public void incPlrHealth() {
        plrHealth += 5;

    }
    
    public void decPlrHealth(int dam) {
        plrHealth -= dam;
    }

    public void setPlrBudget(int budget) {
        plrBudget = budget;
    }

    public int getPlrBudget() {
        return plrBudget;
    }
    
    public void incPlrBudget(int value) {
        plrBudget += value;
    }

    public void decPlrBudget(int cost) {
        plrBudget -= cost;
    }
    
    public AssetManager getAssetManager() {
        return assetManager;
    }
    
    public boolean getDebugMode() {
        return debugMode;
    }

    @Override
    public void cleanup() {
        rootNode.detachAllChildren();
    }
   

    public ScheduledThreadPoolExecutor getEx() {
        return ex;
    }
}
