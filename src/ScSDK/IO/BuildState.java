package ScSDK.IO;

import ScSDK.Factories.BaseFactory;
import ScSDK.Factories.CreepSpawnerFactory;
import ScSDK.Factories.FloorFactory;
import ScSDK.Factories.TowerFactory;
import ScSDK.MapXML.BuildParams;
import ShortCircuit.MapXML.CreepSpawnerParams;
import ShortCircuit.MapXML.GeometryParams;
import ShortCircuit.MapXML.GraphicsParams;
import ShortCircuit.MapXML.MapGenerator;
import ShortCircuit.MapXML.MaterialParams;
import ShortCircuit.MapXML.TowerParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will take care of actually building the levels, without adding any
 * controls.
 *
 * @author Connor Rice
 */
public class BuildState extends AbstractAppState {

    private SimpleApplication app;
    private GeometryParams gp;
    private Box univ_box = new Box(1, 1, 1);
    private MaterialParams mp;
    private Node rootNode;
    private ArrayList<TowerParams> towerParamList;
    private ArrayList<CreepSpawnerParams> creepSpawnerList;
    private ArrayList<Spatial> towerList;
    private HashMap matHash;
    private TowerFactory tf;
    private CreepSpawnerFactory csf;
    private AssetManager assetManager;
    private FloorFactory ff;
    private BaseFactory bf;
    private String levelName;
    private MapGenerator mg;

    public BuildState(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        matHash = new HashMap(10);
        initFactories();
        initLists();
        createWorld();
    }

    private void createWorld() {
        this.mg = new MapGenerator(levelName, app);
        BuildParams bp = mg.getBuildParams();
        this.gp = bp.getGeometryParams();
        this.mp = bp.getMaterialParams();
        buildMatHash(bp.getTowerTypes());
        this.towerParamList = bp.getTowerList();
        this.creepSpawnerList = bp.getCreepSpawnerList();
        createLight();
        createFloor();
        createBase();
        buildTowers();
        buildCreepSpawners();
        embedLevelData();
    }

    private void buildMatHash(String[] towerTypes) {
        BuildMatHashSDK bms = new BuildMatHashSDK(this, towerTypes);
        bms.run();
        this.matHash = bms.getMatHash();
    }

    private void initLists() {
        towerParamList = new ArrayList<TowerParams>(32);
        towerList = new ArrayList<Spatial>(32);
        creepSpawnerList = new ArrayList<CreepSpawnerParams>();
    }

    private void initFactories() {
        bf = new BaseFactory(this);
        ff = new FloorFactory(this);
        tf = new TowerFactory(this);
        csf = new CreepSpawnerFactory(this);
    }

    private void embedLevelData() {
        rootNode.setUserData("GraphicsParams", mg.getGraphicsParams());
        rootNode.setUserData("GameplayParams", mg.getGameplayParams());
    }

    /**
     * * World Methods **
     */
    public void createLight() {
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(128f));
        rootNode.addLight(ambient);
    }

    public void createFloor() {
        Spatial floor = ff.getFloor();
        rootNode.attachChild(floor);
    }

    public Vector3f getFloorScale() {
        return gp.getFloorScale();
    }

    /**
     * * Base Methods **
     */
    public void createBase() {
        Spatial base = bf.getBase(gp.getBaseVec(), gp.getBaseScale());
        rootNode.attachChild(base);
    }

    public Vector3f getBaseVec() {
        return gp.getBaseVec();
    }

    public Vector3f getBaseScale() {
        return gp.getBaseScale();
    }

    /**
     * * Tower Methods **
     */
    public void buildTowers() {
        for (int i = 0; i < towerParamList.size(); i++) {
            createTower(towerParamList.get(i));
        }
    }

    public void createTower(TowerParams tp) {
        Spatial tower = tf.getTower(tp);
        towerList.add(tp.getIndex(), tower);
        rootNode.attachChild(tower);
    }

    public Vector3f getTowerBuiltSize() {
        return gp.getTowerBuiltSize();
    }

    public Vector3f getTowerUnbuiltSize() {
        return gp.getTowerUnbuiltSize();
    }

    public Vector3f getTowerBuiltSelected() {
        return gp.getTowerBuiltSelected();
    }

    public Vector3f getTowerUnbuiltSelected() {
        return gp.getTowerUnbuiltSelected();
    }

    /**
     * * CreepSpawner Methods **
     */
    public void createCreepSpawner(CreepSpawnerParams curSpawner) {
        creepSpawnerList.set(curSpawner.getIndex(), csf.getSpawner(curSpawner));
        rootNode.attachChild(creepSpawnerList.get(curSpawner.getIndex()).getSpatial());
    }

    public void buildCreepSpawners() {
        for (int i = 0; i < creepSpawnerList.size(); i++) {
            createCreepSpawner(creepSpawnerList.get(i));
        }
        //EnemyState.setCreepSpawnerList(creepSpawnerList);
    }

    public Vector3f getCreepSpawnerHorizontalScale() {
        return gp.getCreepSpawnerHorizontalScale();
    }

    public Vector3f getCreepSpawnerVerticalScale() {
        return gp.getCreepSpawnerVerticalScale();
    }

    /**
     * * Global Methods **
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Box getUnivBox() {
        return univ_box;
    }

    public String getMatDir() {
        return mp.getMatDir();
    }

    public String getMatLoc(String type) {
        return "Materials/" + getMatDir() + "/" + type + ".j3m";
    }

    public Material getMaterial(String key) {
        return (Material) matHash.get(key);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        rootNode.detachAllChildren();
        towerParamList.clear();
        creepSpawnerList.clear();
        towerList.clear();
        matHash.clear();
        mg = null;
        bf = null;
        ff = null;
        mp = null; 
        gp = null;
        tf = null;
    }
}
