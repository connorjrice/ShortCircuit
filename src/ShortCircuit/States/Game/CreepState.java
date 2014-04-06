package ShortCircuit.States.Game;

import ShortCircuit.Threading.SpawnCreep;
import ShortCircuit.Objects.CreepTraits;
import ShortCircuit.Factories.CreepSpawnerFactory;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * URGENT: Fix offering process to only offer towers the creeps that are in
 * the relevant range!
 *
 * @author Connor Rice
 */
public class CreepState extends AbstractAppState {

    private Box univ_box = new Box(1,1,1);
    public Node creepNode = new Node("Creep");
    public ArrayList<Spatial> creepList;
    private static final Vector3f SM_CREEP_SIZE =
            new Vector3f(0.125f, 0.125f, 0.20f);
    private static final Vector3f MD_CREEP_SIZE =
            new Vector3f(0.25f, 0.25f, 0.40f);
    private static final Vector3f LG_CREEP_SIZE =
            new Vector3f(0.4f, 0.4f, 0.60f);
    private static final int SM_CREEP_HEALTH = 100;
    private static final int MD_CREEP_HEALTH = 200;
    private static final int LG_CREEP_HEALTH = 400;
    private static final float SM_CREEP_SPEED = 0.03f;
    private static final float MD_CREEP_SPEED = 0.02f;
    private static final float LG_CREEP_SPEED = 0.01f;
    private static final String SM_CREEP_TEXLOC = "Textures/smallcreep.jpg";
    private static final String MD_CREEP_TEXLOC = "Textures/mediumcreep.jpg";
    private static final String LG_CREEP_TEXLOC = "Textures/largecreep.jpg";
    public Random creepXGen = new Random();
    
    private SimpleApplication app;
    private AssetManager assetManager;
    private GameState GameState;
    private ArrayList<Vector3f> creepSpawnerVecs;
    private ArrayList<String> creepSpawnerDirs;
    private ArrayList<Spatial> creepSpawners = new ArrayList<Spatial>();
    private int nextspawner;
    private Node worldNode;
    private SpawnCreep sc;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.worldNode = this.GameState.getWorldNode();
        creepList = new ArrayList<Spatial>();
    }

    public void attachCreepNode() {
        worldNode.attachChild(creepNode);
    }

    public void createCreepSpawner(int index, Vector3f spawnervec) {
        CreepSpawnerFactory csf = new CreepSpawnerFactory(index,
                "CreepSpawner", spawnervec, getCreepSpawnerDir(index), assetManager, this);
        creepSpawners.add(csf.getSpawner());
        creepNode.attachChild(csf.getSpawner());
    }

    public void buildCreepSpawners(ArrayList<Vector3f> _creepSpawnerVecs, ArrayList<String> _creepSpawnerDirs) {
        creepSpawnerVecs = _creepSpawnerVecs;
        creepSpawnerDirs = _creepSpawnerDirs;
        for (int i = 0; i < creepSpawnerVecs.size(); i++) {
            createCreepSpawner(i, creepSpawnerVecs.get(i));
        }
    }

    /**
     * Creates a creep based upon the input from creepBuilder.
     * @param ct, object containing traits of the creep
     */
    public void createCreep(CreepTraits ct) {
        sc = new SpawnCreep(creepList,creepNode, ct,assetManager,this);
        sc.run();
    }

    /**
     * Builds a creep from a random number generator, calls createCreep.
     */
    public void creepBuilder(Vector3f spawnerVec, int spawnIndex) {
        int size = creepXGen.nextInt(12);
        if (spawnerVec.getY() == 0) {
            if (size < 8) {
                createSmallCreep(spawnerVec, spawnIndex, true);
            } else if (8 <= size && size < 11) {
                createMediumCreep(spawnerVec, spawnIndex, true);
            } else if (size == 11) {
                createLargeCreep(spawnerVec, spawnIndex, true);
            }

        } else {
            if (size < 8) {
                createSmallCreep(spawnerVec, spawnIndex, false);
            } else if (8 <= size && size < 11) {
                createMediumCreep(spawnerVec, spawnIndex, false);
            } else if (size == 11) {
                createLargeCreep(spawnerVec, spawnIndex, false);
            }
        }
    }
    
    public String getCreepSpawnerDir(int index) {
        return creepSpawnerDirs.get(index);
    }
        
    

    private Vector3f getCreepVecVert(Vector3f spawnervec) {
        return new Vector3f(spawnervec.getX(), getCreepNextBound(), 0.2f);
    }

    private Vector3f getCreepVecHoriz(Vector3f spawnervec) {
        return new Vector3f(getCreepNextBound(), spawnervec.getY(), 0.2f);
    }
    
    private float getCreepNextBound() {
        int n = creepXGen.nextInt(2);
        if (n == 0) {
            return creepXGen.nextFloat() * 1.2f;
        } else {
            return -creepXGen.nextFloat() * 1.2f;
        }
    }

    private void createSmallCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createCreep(new CreepTraits("Creep", SM_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    SM_CREEP_SIZE, SM_CREEP_SPEED, "Small", SM_CREEP_TEXLOC, getCreepDirection(spawnIndex)));
        } else {
            createCreep(new CreepTraits("Creep", SM_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    SM_CREEP_SIZE, SM_CREEP_SPEED, "Small", SM_CREEP_TEXLOC, getCreepDirection(spawnIndex)));
        }
    }

    private void createMediumCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createCreep(new CreepTraits("Creep", MD_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    MD_CREEP_SIZE, MD_CREEP_SPEED, "Medium", MD_CREEP_TEXLOC, getCreepDirection(spawnIndex)));
        } else {
            createCreep(new CreepTraits("Creep", MD_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    MD_CREEP_SIZE, MD_CREEP_SPEED, "Medium", MD_CREEP_TEXLOC, getCreepDirection(spawnIndex)));
        }
    }

    private void createLargeCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createCreep(new CreepTraits("Creep", LG_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    LG_CREEP_SIZE, LG_CREEP_SPEED, "Large", LG_CREEP_TEXLOC, getCreepDirection(spawnIndex)));
        } else {
            createCreep(new CreepTraits("Creep", LG_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    LG_CREEP_SIZE, LG_CREEP_SPEED, "Large", LG_CREEP_TEXLOC, getCreepDirection(spawnIndex)));
        }
    }
    
    private String getCreepDirection(int spawnIndex) {
        return creepSpawnerDirs.get(spawnIndex);
    }

    /**
     * Determines how many creeps should be on the map based upon the player's
     * level.
     * TODO: Implment XML modification
     */
    public int getNumCreepsByLevel() {
        if (GameState.getPlrLvl() == 0) {
            return GameState.getNumCreeps();
        } else {
            return GameState.getNumCreeps() * GameState.getCreepMod();
        }
    }



    public void reset() {
        creepList.clear();
        creepSpawners.clear();
        creepNode.detachAllChildren();
    }

    public ArrayList<Spatial> getCreepSpawnerList() {
        return creepSpawners;
    }
    
    public BoundingVolume getBaseBounds() {
        return worldNode.getChild("Base").getWorldBound();
    }

    public Node getCreepNode() {
        return creepNode;
    }
    
    public ArrayList<Spatial> getCreepList() {
        return creepList;
    }

    public int getCreepListSize() {
        return creepList.size();
    }
    
    public AssetManager getAssetManager() {
        return assetManager;
    }
    
    public Box getUnivBox() {
        return univ_box;
    }

    public void decPlrHealth(int dam) {
        GameState.decPlrHealth(dam);
    }

    public void incPlrBudget(int value) {
        GameState.incPlrBudget(value);
    }

    public void incPlrScore(int inc) {
        GameState.incPlrScore(inc);
    }

    public void goToNextSpawner() {
        if (creepSpawners.size() > 1) {
            if (nextspawner < creepSpawners.size()-1) {
                nextspawner += 1;
            } else {
                nextspawner = 0;
            }
        }
    }

    public int getNextSpawner() {
        return nextspawner;
    }

    public ScheduledThreadPoolExecutor getEx() {
        return GameState.getEx();
    }
    
    @Override
    public void stateAttached(AppStateManager stateManager) {
        
    }
    
    @Override
    public void stateDetached(AppStateManager stateManager) {
        creepNode.detachAllChildren();
    }


   @Override
    public void cleanup() {
        super.cleanup();
        creepNode.detachAllChildren();
    }
}
