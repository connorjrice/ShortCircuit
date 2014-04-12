package ShortCircuit.States.Game;

import ShortCircuit.Controls.TowerControl;
import ShortCircuit.Factories.CreepFactory;
import ShortCircuit.Objects.CreepTraits;
import ShortCircuit.Factories.CreepSpawnerFactory;
import ShortCircuit.Factories.GlobFactory;
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
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * URGENT: Fix offering process to only offer towers the creeps that are in
 * the relevant range!
 * 
 * TODO: New Enemies: 
 * 1. Descendant (working on)
 * 2. Digger
 * 3. Lone Ranger
 * 4. Portable spawner
 *
 * @author Connor Rice
 */
public class CreepState extends AbstractAppState {

    private Box univ_box = new Box(1,1,1);
    private Sphere glob_sphere = new Sphere(32,32,1f);
    public Node creepNode = new Node("Creep");
    public ArrayList<Spatial> creepList;
    private static final Vector3f SM_CREEP_SIZE =
            new Vector3f(0.125f, 0.125f, 0.20f);
    private static final Vector3f MD_CREEP_SIZE =
            new Vector3f(0.25f, 0.25f, 0.40f);
    private static final Vector3f LG_CREEP_SIZE =
            new Vector3f(0.4f, 0.4f, 0.60f);
    private static final Vector3f XL_CREEP_SIZE =
                new Vector3f(0.6f, 0.6f, 0.8f);
    private static final int SM_CREEP_HEALTH = 100;
    private static final int MD_CREEP_HEALTH = 200;
    private static final int LG_CREEP_HEALTH = 400;
    private static final int XL_CREEP_HEALTH = 800;
    private static final float SM_CREEP_SPEED = 0.045f;
    private static final float MD_CREEP_SPEED = 0.035f;
    private static final float LG_CREEP_SPEED = 0.025f;
    private static final float XL_CREEP_SPEED = 0.030f;
    private String smCreepMatloc;
    private String mdCreepMatloc; 
    private String lgCreepMatloc;
    private String xlCreepMatloc;
    public Random random = new Random();
    
    private SimpleApplication app;
    private AssetManager assetManager;
    private GameState GameState;
    private ArrayList<Vector3f> creepSpawnerVecs;
    private ArrayList<String> creepSpawnerDirs;
    private ArrayList<Spatial> creepSpawners = new ArrayList<Spatial>();
    public ArrayList<Spatial> globList;
    private int nextspawner;
    private int nextrandom;
    private float randomCheck = 0;
    private Node worldNode;
    private CreepFactory cf = new CreepFactory();
    private GlobFactory gf = new GlobFactory();
    private CreepSpawnerFactory csf = new CreepSpawnerFactory();

    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.worldNode = this.GameState.getWorldNode();
        creepList = new ArrayList<Spatial>();
        globList = new ArrayList<Spatial>();
    }
    
    @Override
    public void update(float tpf) {
        if (isEnabled()) {
            if (randomCheck > nextrandom) {
                spawnRandomEnemy();
                getNextRandomSpecialEnemyInt();
                randomCheck = 0;
            }
            else {
                randomCheck += tpf;
            }
        }

    }
    
    private void getNextRandomSpecialEnemyInt() {
        nextrandom = random.nextInt(15);
    }
    
    private void spawnRandomEnemy() {
        spawnGlob();
    }
    
    private void spawnGlob() {
        int towerVictimIndex = random.nextInt(GameState.getTowerList().size());
        if (!GameState.getTowerList().get(towerVictimIndex).getControl(TowerControl.class).getIsGlobbed()) {
            Vector3f towerVictimLocation = GameState.getTowerList().get(towerVictimIndex).getLocalTranslation();
            Spatial glob = gf.getGlob(towerVictimLocation, towerVictimIndex, assetManager, this);
            GameState.getTowerList().get(towerVictimIndex).getControl(TowerControl.class).globTower();
            creepNode.attachChild(glob);
            globList.add(glob);
        }
        else {
            spawnGlob();
        }
    }
    
    public Sphere getGlobSphere() {
        return glob_sphere;
    }

    public void attachCreepNode() {
        worldNode.attachChild(creepNode);
    }

    public void createCreepSpawner(int index, Vector3f spawnervec) {
        creepSpawners.add(csf.getSpawner(index,
                "CreepSpawner", spawnervec, getCreepSpawnerDir(index), assetManager, this));
        creepNode.attachChild(creepSpawners.get(creepSpawners.size()-1));
    }

    public void buildCreepSpawners(ArrayList<Vector3f> _creepSpawnerVecs, ArrayList<String> _creepSpawnerDirs) {
        smCreepMatloc = "Materials/" + getMatDir() + "/SmallCreep.j3m";
        mdCreepMatloc = "Materials/" + getMatDir() + "/MediumCreep.j3m";
        lgCreepMatloc = "Materials/" + getMatDir() + "/LargeCreep.j3m";
        xlCreepMatloc = "Materials/" + getMatDir() + "/GiantCreep.j3m";
        creepSpawnerVecs = _creepSpawnerVecs;
        creepSpawnerDirs = _creepSpawnerDirs;
        for (int i = 0; i < creepSpawnerVecs.size(); i++) {
            createCreepSpawner(i, creepSpawnerVecs.get(i));
        }
    }


    /**
     * This is where the process of building a standard creep begins.
     * The flow of creep is:
     * 1. startSTDCreep, which is called by a CreepSpawner. The true/false
     * boolean determines whether or not the creep is moving vertically
     * (along the Y-axis) or horizontally (along the X-axis). The random
     * numbers are there to determine the type (sm, md, etc).
     * 
     * 2. Into prepare Sm/Md  STD     Creep()
     *         prepare(type)(standard)creep
     * 2a. At this state, the CreepTraits object is created.
     * 
     * 3. Into createSTDCreep, which takes care of spawning the creep.
     */
    public void startSTDCreep(Vector3f spawnerVec, int spawnIndex) {
        int size = random.nextInt(15);
        if (spawnerVec.getY() == 0) {
            if (size < 8) {
                prepareSmSTDCreep(spawnerVec, spawnIndex, true);
            } else if (8 <= size && size < 11) {
                prepareMdSTDCreep(spawnerVec, spawnIndex, true);
            } else if (size >= 11 && size <= 13) {
                prepareLgSTDCreep(spawnerVec, spawnIndex, true);
            } else if (size == 14) {
                prepareXlSTDCreep(spawnerVec, spawnIndex, true);
            }

        } else {
            if (size < 8) {
                prepareSmSTDCreep(spawnerVec, spawnIndex, false);
            } else if (8 <= size && size < 11) {
                prepareMdSTDCreep(spawnerVec, spawnIndex, false);
            } else if (size >= 11 && size <= 13) {
                prepareLgSTDCreep(spawnerVec, spawnIndex, false);
            } else if (size == 14) {
                prepareXlSTDCreep(spawnerVec, spawnIndex, false);
            }
            
        }
    }
    



    private void prepareSmSTDCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createSTDCreep(new CreepTraits("Creep", SM_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    SM_CREEP_SIZE, SM_CREEP_SPEED, "Small", smCreepMatloc, getCreepDirection(spawnIndex)));
        } else {
            createSTDCreep(new CreepTraits("Creep", SM_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    SM_CREEP_SIZE, SM_CREEP_SPEED, "Small", smCreepMatloc, getCreepDirection(spawnIndex)));
        }
    }

    private void prepareMdSTDCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createSTDCreep(new CreepTraits("Creep", MD_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    MD_CREEP_SIZE, MD_CREEP_SPEED, "Medium", mdCreepMatloc, getCreepDirection(spawnIndex)));
        } else {
            createSTDCreep(new CreepTraits("Creep", MD_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    MD_CREEP_SIZE, MD_CREEP_SPEED, "Medium", mdCreepMatloc, getCreepDirection(spawnIndex)));
        }
    }

    private void prepareLgSTDCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createSTDCreep(new CreepTraits("Creep", LG_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    LG_CREEP_SIZE, LG_CREEP_SPEED, "Large", lgCreepMatloc, getCreepDirection(spawnIndex)));
        } else {
            createSTDCreep(new CreepTraits("Creep", LG_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    LG_CREEP_SIZE, LG_CREEP_SPEED, "Large", lgCreepMatloc, getCreepDirection(spawnIndex)));
        }
    }
    
    private void prepareXlSTDCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createSTDCreep(new CreepTraits("Creep", XL_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    XL_CREEP_SIZE, XL_CREEP_SPEED, "Large", xlCreepMatloc, getCreepDirection(spawnIndex)));
        } else {
            createSTDCreep(new CreepTraits("Creep", LG_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    XL_CREEP_SIZE, XL_CREEP_SPEED, "Large", xlCreepMatloc, getCreepDirection(spawnIndex)));
        }
    }
    
    
    /**
     * Creates a creep based upon the input from creepBuilder.
     * @param ct, object containing traits of the creep
     */
    private void createSTDCreep(CreepTraits ct) {
        creepList.add(cf.getCreep(ct, assetManager, this));
        creepNode.attachChild(creepList.get(creepList.size()-1));
    }

    public String getCreepSpawnerDir(int index) {
        return creepSpawnerDirs.get(index);
    }
    
    public String getMatDir() {
        return GameState.getMatDir();
    }
    
    private String getCreepDirection(int spawnIndex) {
        return creepSpawnerDirs.get(spawnIndex);
    }
    
            
    private Vector3f getCreepVecVert(Vector3f spawnervec) {
        return new Vector3f(spawnervec.getX(), getCreepNextBound(), 0.2f);
    }

    private Vector3f getCreepVecHoriz(Vector3f spawnervec) {
        return new Vector3f(getCreepNextBound(), spawnervec.getY(), 0.2f);
    }
    
    private float getCreepNextBound() {
        int n = random.nextInt(2);
        if (n == 0) {
            return random.nextFloat() * 1.2f;
        } else {
            return -random.nextFloat() * 1.2f;
        }
    }
    
    
    
    

    /**
     * Determines how many creeps should be on the map based upon the player's
     * level.
     * The numbers are determined by nodes in the level XML file.
     * 
     */
    public int getNumCreepsByLevel() {
        return GameState.getNumCreeps();
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
    
    public ArrayList<Spatial> getTowerList() {
        return GameState.getTowerList();
    }
    
    public ArrayList<Integer> getGlobbedTowerList() {
        return GameState.getGlobbedTowerList();
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
    
    public SimpleApplication getApp() {
        return app;
    }

   @Override
    public void cleanup() {
        super.cleanup();
        creepNode.detachAllChildren();
        globList.clear();
        creepList.clear();
   }
}
