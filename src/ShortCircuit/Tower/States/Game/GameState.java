package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Factories.BaseFactory;
import ShortCircuit.Tower.Controls.GlobControl;
import ShortCircuit.Tower.MapXML.Objects.BaseParams;
import ShortCircuit.Tower.MapXML.Objects.LevelParams;
import ShortCircuit.Tower.MapXML.Objects.PlayerParams;
import ShortCircuit.Tower.MapXML.Objects.TowerParams;
import ShortCircuit.Tower.Objects.Loading.GameplayParams;
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
 * PENDING: Clean up GameState
 * PENDING: Implment allowedenemies
 * TODO: Implement Digger and Ranger 
 * TODO: Retool main menu
 * @author Connor Rice
 */
public class GameState extends AbstractAppState {

    private EnemyState EnemyState;
    private SimpleApplication app;
    private AssetManager assetManager;
    private Node rootNode;


    private float updateTimer = 0;
    public ScheduledThreadPoolExecutor ex;
    public int fours;
    private BaseFactory bf;


    private AppStateManager stateManager;
    private AudioState AudioState;
    private GameplayParams GameplayParams;
    private LevelParams lp;
    private GameplayParams gp;
    private PlayerParams pp;
    
    private Node worldNode = new Node("World");
    private FriendlyState FriendlyState;
    private GraphicsState GraphicsState;
    private BaseParams bp;

    public GameState() {}


    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        ex = new ScheduledThreadPoolExecutor(8);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.EnemyState = this.stateManager.getState(EnemyState.class);
        this.FriendlyState = this.stateManager.getState(FriendlyState.class);
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.AudioState = this.stateManager.getState(AudioState.class);
    }
    
    /**
     * This method assigns values to gameplay variables, and initializes
     * the assets and factories for the game.
     * @param gp 
     */
    public void setGameplayParams(GameplayParams gp) {
        this.gp = gp;
        this.lp = gp.getLevelParams();
        this.pp = gp.getPlayerParams();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (isEnabled()) {
            if (updateTimer > .1) {
                if (pp.getScore() > lp.getLevelCap()) {
                    nextLevel();
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



    /**
     * Increments level. Called by update loop when conditions are met.
     */
    public void nextLevel() {
        incPlrLevel(1);
        incLevelCap();
        playLevelUpSound();
        updateNumCreeps();
        incPlrHealth(pp.getLevel());
    }

    /**
     * Updates the number of creeps that are spawned in a level. The number of
     * creeps is incremented by creepMod, which is fed in from LevelParameters.
     */
    private void updateNumCreeps() {
        lp.updateNumCreeps();
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
        } else if (type.equals("TowerUnbuilt")) {
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
            FriendlyState.shortenTower();
            int towerIndex = target.getUserData("Index");
            if (globCheck(trans, towerIndex)) { // If the tower isn't 
            } else {                                    //  globbed, we
                FriendlyState.towerSelected(towerIndex);  //   select the tower.
            }
        } else if (target.getName().equals("Glob")) {
            popGlob(trans, target.getControl(GlobControl.class));
        } else {
            GraphicsState.dropBomb(trans, .1f);
        }
    }

    /**
     * Check to see if the tower is globbed. When collision is done, we don't
     * want to select the tower to do things like charge/upgrade because it is
     * globbed. This checks to see if a tower is globbed, and applies damage to
     * the glob rather than selecting the tower.
     *
     * @param trans
     * @param target
     * @param towerIndex
     * @return
     */
    private boolean globCheck(Vector3f trans, int towerIndex) {
        if (FriendlyState.getGlobbedTowerList().contains(towerIndex)) {
            popGlob(trans, EnemyState.getGlobList().get(FriendlyState.getGlobbedTowerList().indexOf(towerIndex))
                    .getControl(GlobControl.class));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Damages glob enemy. Called from touchHandle and globCheck
     *
     * @param trans
     * @param glob
     * @return
     */
    public int popGlob(Vector3f trans, GlobControl glob) {
        int health = glob.decGlobHealth();
        AudioState.globSound(health, trans);
        if (health <= 0) {
            glob.remove();
        }
        return health;
    }
    
    
    
    
    public int getPlrHealth() {
        return pp.getHealth();
    }
    
    public int getPlrBudget() {
        return pp.getBudget();
    }
    
    public int getPlrLevel() {
        return pp.getLevel();
    }
    
    public int getPlrScore() {
        return pp.getScore();
    }

    
    
    public void setPlrHealth(int h) {
        pp.setHealth(h);
    }
    
    public void setPlrBudget(int b) {
        pp.setBudget(b);
    }
    
    public void setPlrLevel(int l) {
        pp.setLevel(l);
    }
    
    public void setPlrScore(int s) {
        pp.setScore(s);
    }
    
    
    public void incPlrHealth(int h) {
        pp.incHealth(h);
    }
    
    public void incPlrBudget(int b) {
        pp.incBudget(b);
    }
    
    public void incPlrLevel(int l) {
        pp.incLevel(l);
    }
    
    public void incPlrScore(int s) {
        pp.incScore(s);
    }
    
    
    public void decPlrHealth(int h) {
        pp.decScore(h);
    }
    
    public void decPlrBudget(int b) {
        pp.decBudget(b);
    }
    
    public void decPlrLevel(int l) {
        pp.decLevel(l);
    }
    
    public void decPlrScore(int s) {
        pp.decScore(s);
    }

    
    
    
    
    public void incLevelCap() {
        lp.incLevelCap();
    }
    
    
    public int getNumCreeps() {
        return lp.getNumCreeps();
    }
    
 
    
    /**
     * TODO: Progress indication
     *
     * @return
     */
    public float getCurrentProgress() {
        return FriendlyState.getTowerList().size();
    }


    
    public GameplayParams getGameplayParams() {
        return GameplayParams;
    }




    public void incFours() {
        fours += 1;
    }

    public int getFours() {
        return fours;
    }

    public void playLevelUpSound() {
        AudioState.levelUpSound();
    }

    public ScheduledThreadPoolExecutor getEx() {
        return ex;
    }

    
    
    public Vector3f getBaseVec() {
        return bp.getBaseVec();
    }
    
    public Vector3f getBaseScale() {
        return bp.getBaseScale();
    }

    public ArrayList<TowerParams> getTowerParams() {
        return gp.getTowerList();
    }
    
    public String getAtlas() {
        return "Interface/" + GraphicsState.getMatDir() + "Atlas.png";
    }
    
    public Node getWorldNode() {
        return worldNode;
    }

    @Override
    public void cleanup() {
        super.cleanup();
        worldNode.detachAllChildren();
        rootNode.detachAllChildren();
        ex.shutdown();
    }
}
