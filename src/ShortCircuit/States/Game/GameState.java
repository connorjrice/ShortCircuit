package ShortCircuit.States.Game;

import ShortCircuit.States.GUI.StartGUI;
import ShortCircuit.Controls.GlobControl;
import ScSDK.MapXML.LevelParams;
import ScSDK.MapXML.PlayerParams;
import ShortCircuit.Objects.GameplayParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.text.DecimalFormat;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * PENDING: Implment allowedenemies TODO: Implement Digger and Ranger TODO:
 * Retool main menu
 * FIXME: Bug where gameover on a level does not let the user play level again.
 * 
 * New gameplay idea: have a set number of walls you can build in the beginning,
 * and only a few prebuilt towers. Then, you build towers 
 * (not in prelisted places)
 *
 * @author Connor Rice
 */
public class GameState extends AbstractAppState {

    private SimpleApplication app;
    private Node rootNode;
    private float updateTimer = 0;
    public ScheduledThreadPoolExecutor ex;
    public int fours;
    private AppStateManager stateManager;
    private AudioState AudioState;
    private GameplayParams GameplayParams;
    private LevelParams lp;
    private PlayerParams pp;
    private FriendlyState FriendlyState;
    private GraphicsState GraphicsState;
    private BoundingVolume baseBounds;
    private StartGUI StartGUI;
    private String formattedBaseCoords;
    private PathfindingState PathState;
    private boolean bombActive;
    private DecimalFormat numFormatter = new DecimalFormat("0.0");

    public GameState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.ex = new ScheduledThreadPoolExecutor(8);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.stateManager = this.app.getStateManager();
        this.FriendlyState = this.stateManager.getState(FriendlyState.class);
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.PathState = this.stateManager.getState(PathfindingState.class);
        this.AudioState = this.stateManager.getState(AudioState.class);
        this.StartGUI = this.stateManager.getState(StartGUI.class);
    }

    /**
     * This method assigns values to gameplay variables, and initializes the
     * assets and factories for the game.
     *
     * @param gp
     */
    public void setGPBuild(GameplayParams gp) {
        this.lp = gp.getLevelParams();
        this.pp = gp.getPlayerParams();
        if (StartGUI != null) {
            StartGUI.hideloading();
        }
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

    public Node getRootNode() {
        return rootNode;
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
            if (globCheck(towerIndex)) { // If the tower isn't 
            } else {                                    //  globbed, we
                FriendlyState.towerSelected(towerIndex);  //   select the tower.
            }
        } else if (target.getName().equals("Glob")) {
            popGlob(trans, target.getControl(GlobControl.class));
        } else {
            if (bombActive) {
                GraphicsState.dropBomb(trans, .1f);
            } else {
                PathState.nextVec(trans);
            }

        }
    }

    public void toggleBomb() {
        bombActive = !bombActive;
    }

    public void setBombStatus(boolean b) {
        bombActive = b;
    }

    public boolean getBombActive() {
        return bombActive;
    }

    public String[] getBlockedNodes() {
        return lp.getBlockedNodes();
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
    private boolean globCheck(int towerIndex) {
        return FriendlyState.getTowerGlobbedStatus(towerIndex);
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
        pp.decHealth(h);
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

    public Vector3f getBaseVec() {
        return baseBounds.getCenter();
    }

    public BoundingVolume getBaseBounds() {
        return baseBounds;
    }

    public String getFormattedBaseCoords() {
        return formattedBaseCoords;
    }

    public void setFormattedBaseCoords(Spatial base) {
        formattedBaseCoords = formatRoundNumber(base.getLocalTranslation().x) +
                "," + formatRoundNumber(base.getLocalTranslation().y);
    }

    private String formatRoundNumber(Float value) {
        return numFormatter.format(Math.round(value));
    }

    public void setBaseBounds(BoundingVolume baseBounds) {
        this.baseBounds = baseBounds;
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
    
    public void decFours() {
        fours -= 1;
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

    public String getAtlas() {
        return "Interface/" + GraphicsState.getMatDir() + "Atlas.png";
    }

    @Override
    public void cleanup() {
        super.cleanup();
        rootNode.detachAllChildren();
        ex.shutdown();
    }

}
