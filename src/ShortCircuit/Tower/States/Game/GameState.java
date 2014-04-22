package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Factories.BaseFactory;
import ShortCircuit.Tower.Factories.FloorFactory;
import ShortCircuit.Tower.Controls.BombControl;
import ShortCircuit.Tower.Controls.GlobControl;
import ShortCircuit.Tower.Objects.LevelParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * This is the main game state for the Tower Defense game. 
 * TODO: Documentation
 * @author Connor Rice
 */
public class GameState extends AbstractAppState {

    private CreepState CreepState;
    private TowerState TowerState;
    private SimpleApplication app;
    private AssetManager assetManager;
    private Node rootNode;
    private Sphere bombMesh = new Sphere(16, 16, 1.0f);
    private Node worldNode = new Node("World");
    private Box univ_box = new Box(1, 1, 1);
    private int levelCap;
    private int plrLevel;
    private int plrScore;
    private int plrHealth;
    private int plrBudget;
    private int plrBombs;
    private float updateTimer = 0;
    private boolean debugMode = false;
    private Vector3f camLocation;
    protected int numCreeps;
    protected int creepMod;
    public ScheduledThreadPoolExecutor ex;
    public String matDir;
    private Vector3f basevec;
    public AudioNode buildSound;
    public AudioNode levelUpSound;
    public AudioNode globPop;
    public int fours;
    private FloorFactory ff;
    private BaseFactory bf;
    private Material bomb_mat;

    /**
     * Constructor takes no input parameters.
     */
    public GameState() {
    }

    /**
     * Initialize GameState.
     * @param stateManager
     * @param app 
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        ex = new ScheduledThreadPoolExecutor(8);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.CreepState = this.app.getStateManager().getState(CreepState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
        // TODO: Dynamic backgorund color, XML
        this.app.getViewPort().setBackgroundColor(ColorRGBA.Blue);
    }

    /**
     * Update loop. Handles level incrementation.
     * @param tpf 
     */
    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (isEnabled()) {
            if (updateTimer > .1) {
                if (getPlrScore() > levelCap) {
                    nextLevel();
                }
                updateTimer = 0;
            } else {
                updateTimer += tpf;
            }
        }
    }

    /**
     * Initializes sounds and bomb material.
     * Called by setLevelParams().
     */
    private void initAssets() {
        buildSound = new AudioNode(assetManager, "Audio/buildgam.wav");
        buildSound.setPositional(false);
        buildSound.setVolume(.3f);

        levelUpSound = new AudioNode(assetManager, "Audio/levelup.wav");
        levelUpSound.setPositional(false);
        levelUpSound.setVolume(.6f);

        globPop = new AudioNode(assetManager, "Audio/globpop.wav");
        globPop.setVolume(.4f);

        bomb_mat = assetManager.loadMaterial("Materials/" + getMatDir() + "/Bomb.j3m");
    }

    /**
     * Initializes floor and base factories.
     * Called by setLevelParams().
     */
    private void initFactories() {
        ff = new FloorFactory(this);
        bf = new BaseFactory(this);
    }

    /**
     * Attaches the worldNode to the rootNode.
     */
    public void attachWorldNode() {
        rootNode.attachChild(worldNode);
    }

    /**
     * Sets the parameters for the level.
     * Called by LevelState.
     * @param lp 
     */
    public void setLevelParams(LevelParams lp) {
        setCamLocation(lp.getCamLocation());
        setNumCreeps(lp.getNumCreeps());
        setCreepMod(lp.getCreepMod());
        setLevelCap(lp.getLevelCap());
        setLevelMod(lp.getLevelMod());
        setPlrHealth(lp.getPlrHealth());
        setPlrBudget(lp.getPlrBudget());
        setPlrLvl(lp.getPlrLevel());
        setPlrScore(lp.getPlrScore());
        setDebug(lp.getDebug());
        setMatDir(lp.getMatDir());
        initAssets();
        initFactories();
    }

    /**
     * Increments level.
     * Called by update loop when conditions are met.
     */
    public void nextLevel() {
        incPlrLvl();
        playLevelUpSound();
        levelCap *= 2;
        updateNumCreeps();
        incPlrHealth(getPlrLvl());
    }


    private void updateNumCreeps() {
        numCreeps += creepMod;
    }

    /**
     * Provides the cost of various operations. TODO: Update in the same manner
     * as creeps
     *
     * @param type
     * @return (the cost of the operation)
     */
    public String getCost(Object type) {
        if (type.equals("Charge")) {
            return "10";
        } else if (type.equals("unbuilt")) {
            return "100";
        } else if (type.equals("Tower1")) {
            return "50";
        } else if (type.equals("Tower2")) {
            return "100";
        } else if (type.equals("Tower3")) {
            return "500";
        } else {
            return "0";
        }
    }

    public void touchHandle(Vector3f trans, Spatial target) {
        if (target.getName().equals("Tower")) {
            TowerState.shortenTower();
            int towerIndex = target.getUserData("Index");
            if (globCheck(trans, target, towerIndex)) { // If the tower isn't 
            } else {                                    //  globbed, we
                TowerState.towerSelected(towerIndex);  //   select the tower.
            }
        } else if (target.getName().equals("Glob")) {
            popGlob(trans, target.getControl(GlobControl.class));
        } else {
            dropBomb(trans, .1f);
        }
    }
    
    private boolean globCheck(Vector3f trans, Spatial target, int towerIndex) {
        if (TowerState.getGlobbedTowerList().contains(towerIndex)) {
            GlobControl glob = CreepState.getGlobList().get(TowerState.getGlobbedTowerList().indexOf(towerIndex)).getControl(GlobControl.class);
            int popHealth = popGlob(trans, glob);
            if (popHealth <= 0) {
                glob.remove();
                if (TowerState.getGlobbedTowerList().size() >= 1) {
                    TowerState.getGlobbedTowerList().remove(TowerState.getGlobbedTowerList().indexOf(towerIndex));
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public int popGlob(Vector3f trans, GlobControl glob) {
        int health = glob.decGlobHealth();
        globPop.setPitch(health
                * 0.1f + 1f);
        globPop.setLocalTranslation(trans);

        globPop.playInstance();
        if (health <= 0) {
            glob.remove();
        }
        return health;
    }

    public float getCurrentProgress() {
        return TowerState.getTowerList().size();
    }

    public void dropBomb(Vector3f translation, float initialSize) {
        Geometry bomb_geom = new Geometry("Bomb", getBombMesh());
        bomb_geom.setMaterial(bomb_mat);
        bomb_geom.setLocalScale(initialSize);
        bomb_geom.setLocalTranslation(translation);
        bomb_geom.addControl(new BombControl(initialSize, this));
        worldNode.attachChild(bomb_geom);
    }

    /**
     * Creates an AmbientLight and attaches it to worldNode. Called by
     * LevelState.
     */
    public void createLight() {
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(128f));
        worldNode.addLight(ambient);
    }

    /**
     * Creates the floor for the game, calls FloorFactory to get the floor.
     * Called by LevelState
     */
    public void createFloor(Vector3f floorscale, String floortexloc) {
        worldNode.attachChild(ff.getFloor(floorscale, floortexloc));

    }

    /**
     * Creates the player's base. Called by LevelState. Pending: Multiple Bases
     * (GUI additions as well)
     */
    public void createBase(String texloc, Vector3f _basevec, Vector3f basescale) {
        basevec = _basevec;
        worldNode.attachChild(bf.getBase(texloc, basevec, basescale));
    }

    /**
     * Returns the app. Used by BombControl to enqueue.
     *
     * @return the app.
     */
    public SimpleApplication getApp() {
        return this.app;
    }

    /**
     * Returns the TowerState. Used by TowerControl. TODO: See if there is a
     * less hacky way for TowerControl to have access to TowerState.
     *
     * @return TowerState, the Tower State
     */
    public TowerState getTowerState() {
        return TowerState;
    }

    public Vector3f getBuiltTowerSize() {
        return TowerState.getBuiltTowerSize();
    }

    public Node getWorldNode() {
        return worldNode;
    }

    public Node getCreepNode() {
        return CreepState.getCreepNode();
    }

    public ArrayList<Spatial> getCreepList() {
        return CreepState.getCreepList();
    }

    public ArrayList<Spatial> getCreepSpawnerList() {
        return CreepState.getCreepSpawnerList();
    }

    public ArrayList<Spatial> getTowerList() {
        return TowerState.getTowerList();
    }

    public ArrayList<Integer> getGlobbedTowerList() {
        return TowerState.getGlobbedTowerList();
    }

    public Sphere getBombMesh() {
        return bombMesh;
    }

    public Box getUnivBox() {
        return univ_box;
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

    public String getMatDir() {
        return matDir;
    }

    public void setMatDir(String _matDir) {
        matDir = _matDir;
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

    public int getBuildCost() {
        return 100;
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

    public void incPlrHealth(int inc) {
        plrHealth += inc;
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

    public void setCamLocation(Vector3f _cam) {
        camLocation = _cam;
    }

    public Vector3f getCamLocation() {
        return camLocation;
    }
    
    public void incFours() {
        fours += 1;
    }

    public int getFours() {
        return fours;
    }


    public void playBuildSound(float pitch) {
        buildSound.setPitch(pitch);
        buildSound.playInstance();
        buildSound.setPitch(1f);
    }

    public void playLevelUpSound() {
        levelUpSound.playInstance();
    }

    public ScheduledThreadPoolExecutor getEx() {
        return ex;
    }

    @Override
    public void cleanup() {
        super.cleanup();
        worldNode.detachAllChildren();
        rootNode.detachAllChildren();
        ex.shutdown();
    }
}
