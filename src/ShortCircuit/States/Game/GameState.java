package ShortCircuit.States.Game;

import ShortCircuit.Factories.BaseFactory;
import ShortCircuit.Factories.FloorFactory;
import ShortCircuit.Controls.BaseControl;
import ShortCircuit.Controls.BombControl;
import ShortCircuit.Controls.GlobControl;
import ShortCircuit.Objects.LevelParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.light.AmbientLight;
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
 * TODO: Finish integration with LevelState TODO: Documentation
 *
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
    private Box univ_box = new Box(1,1,1);
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
    public ScheduledThreadPoolExecutor ex;
    public String matDir;
    private Vector3f basevec;
    public AudioNode buildSound;
    public AudioNode levelUpSound;
    public AudioNode globPop;
    public int fours;
    
    private FloorFactory ff = new FloorFactory();
    private BaseFactory bf = new BaseFactory();
    public GameState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.BeamState = this.app.getStateManager().getState(BeamState.class);
        this.CreepState = this.app.getStateManager().getState(CreepState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
        this.app.getViewPort().setBackgroundColor(ColorRGBA.DarkGray);


        buildSound = new AudioNode(assetManager, "Audio/buildgam.wav");
        buildSound.setPositional(false);
        buildSound.setVolume(.3f);
        levelUpSound = new AudioNode(assetManager, "Audio/levelup.wav");
        levelUpSound.setPositional(false);
        levelUpSound.setVolume(.6f);

        globPop = new AudioNode(assetManager, "Audio/globpop.wav");
        ex = new ScheduledThreadPoolExecutor(4);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (isEnabled()) {
            if (updateTimer > .1) {
                if (getPlrScore() > levelCap) {
                    nextLevel();
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
        setMatDir(lp.getMatDir());
    }

    public void nextLevel() {
        incPlrLvl();
        playLevelUpSound();
        levelCap *= 2;
        updateNumCreeps();
        incPlrHealth();

    }

    public void incFours() {
        fours += 1;
    }

    public int getFours() {
        return fours;
    }

    private void updateNumCreeps() {
        numCreeps += creepMod;
    }

    private void debugLoopAdditions() {
        dropBomb(new Vector3f(0.0f, -7.5f, 0.1f));
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
        } else if (type.equals("tower1")) {
            return "50";

        } else if (type.equals("tower2")) {
            return "100";

        } else if (type.equals("tower3")) {
            return "500";
        } else {
            return "0";
        }
    }

    public void touchHandle(Vector3f trans, Spatial target) {
        if (target.getName().equals("Tower")) {
            TowerState.shortenTowers();
            int towerIndex = target.getUserData("Index");
            if (TowerState.globbedTowers.contains(towerIndex)) {
                for (int i = 0; i < CreepState.globList.size(); i++) {
                    // TODO: Update this to take less resources
                    if (CreepState.globList.get(i).getUserData("TowerIndex").equals(towerIndex)) {
                        if (CreepState.globList.get(i) != null) {
                            Spatial glob = CreepState.globList.get(i);
                            int popHealth = popGlob(trans, glob);
                            if (popHealth <= 0) {
                                if (TowerState.globbedTowers.size() >= 1) {
                                    TowerState.globbedTowers.remove(TowerState.globbedTowers.indexOf(towerIndex));
                                }
                            }
                        }
                    }
                }
            } else {
                TowerState.towerSelected(towerIndex);
            }
        } else if (target.getName().equals("Glob")) {
            popGlob(trans, target);
        } else {
            dropBomb(trans);
        }
    }

    public int popGlob(Vector3f trans, Spatial target) {
        int health = target.getControl(GlobControl.class).decGlobHealth();
        globPop.setPitch(health
                * 0.1f + 1f);
        globPop.setLocalTranslation(trans);

        globPop.playInstance();
        return health;
    }

    public float getCurrentProgress() {
        return TowerState.towerList.size();
    }

    public void dropBomb(Vector3f translation) {
        Geometry bomb_geom = new Geometry("Bomb", getBombMesh());
        bomb_geom.setMaterial(assetManager.loadMaterial("Materials/Bomb.j3m"));
        bomb_geom.setLocalScale(.1f);
        bomb_geom.setLocalTranslation(translation);
        bomb_geom.addControl(new BombControl(.1f, this));
        worldNode.attachChild(bomb_geom);
    }

    /**
     * Creates the light for the game world.
     */
    public void createLight() {
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(128f));
        worldNode.addLight(ambient);


    }

    /**
     * Creates a textured floor.
     */
    public void createFloor(Vector3f floorscale, String floortexloc) {;
        worldNode.attachChild(ff.getFloor(floorscale, floortexloc, assetManager, this));

    }

    /**
     * Creates a base at the base vector given by LevelState. TODO:Should be
     * updated to allow for multiple bases
     */
    public void createBase(String texloc, Vector3f _basevec, Vector3f basescale) {
        basevec = _basevec;
        worldNode.attachChild(bf.getBase(texloc, basevec, basescale, assetManager, this));
    }

    public SimpleApplication getApp() {
        return this.app;
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
    
    public ArrayList<Integer> getGlobbedTowerList() {
        return TowerState.globbedTowers;
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

    public void playBuildSound(float pitch) {
        buildSound.setPitch(pitch);
        buildSound.playInstance();
        buildSound.setPitch(1f);
    }

    public void playLevelUpSound() {
        levelUpSound.playInstance();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        worldNode.detachAllChildren();
        rootNode.detachAllChildren();
        ex.shutdown();
    }

    public ScheduledThreadPoolExecutor getEx() {
        return ex;
    }
}
