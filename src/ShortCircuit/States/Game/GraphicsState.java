package ShortCircuit.States.Game;

import ShortCircuit.GUI.StartGUI;
import ShortCircuit.Controls.BombControl;
import ShortCircuit.Controls.TowerControl;
import ShortCircuit.Factories.BaseFactory;
import ShortCircuit.Factories.BeamFactory;
import ShortCircuit.Factories.CreepSpawnerFactory;
import ShortCircuit.Factories.TowerFactory;
import ShortCircuit.MapXML.Objects.CreepSpawnerParams;
import ShortCircuit.MapXML.Objects.FilterParams;
import ShortCircuit.MapXML.Objects.GeometryParams;
import ShortCircuit.MapXML.Objects.MaterialParams;
import ShortCircuit.MapXML.Objects.TowerParams;
import ShortCircuit.Objects.Loading.GraphicsParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author Development
 */
public class GraphicsState extends AbstractAppState {
    private SimpleApplication app;
    private GeometryParams gp;
    private Sphere bombMesh = new Sphere(16, 16, 1.0f);
    private Node worldNode = new Node("World");
    private Box univ_box = new Box(1, 1, 1);
    private ViewPort viewPort;
    private FilterPostProcessor fpp;
    private BloomFilter bloom;
    private AssetManager assetManager;
    public float bloomIntensity;
    private Camera cam;
    private FlyByCamera flyCam;
    private boolean shot = false;
    private BeamFactory BeamFactory;
    private AudioState AudioState;
    private Node beamNode = new Node("Beams");
    private float updateTimer = 0.0f;
    private MaterialParams mp;
    private Material bomb_mat;
    private FilterParams fp;
    private AppStateManager stateManager;
    private GameState GameState;
    private BaseFactory BaseFactory;
    private EnemyState EnemyState;
    private FriendlyState FriendlyState;
    private StartGUI startGUI;
    private String floortexloc;
    private Node rootNode;
    private ArrayList<TowerParams> towerList;
    private ArrayList<CreepSpawnerParams> creepSpawnerList;
    private TowerFactory tf;
    private CreepSpawnerFactory csf;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.stateManager = this.app.getStateManager();
        this.viewPort = this.app.getViewPort();
        this.assetManager = this.app.getAssetManager();
        this.cam = this.app.getCamera();
        this.flyCam = this.app.getFlyByCamera();
        this.AudioState = this.stateManager.getState(AudioState.class);
        this.GameState = this.stateManager.getState(GameState.class);
        this.FriendlyState = this.stateManager.getState(FriendlyState.class);
        this.EnemyState = this.stateManager.getState(EnemyState.class);
        this.startGUI = this.stateManager.getState(StartGUI.class);
        BeamFactory = new BeamFactory(this);
        BaseFactory = new BaseFactory(this);
    }
    
    public void setGraphicsParams(GraphicsParams gp) {
        this.gp = gp.getGeometryParams();
        this.mp = gp.getMaterialParams();
        this.fp = gp.getFilterParams();
        this.towerList = gp.getTowerList();
        this.creepSpawnerList = gp.getCreepSpawnerList();
        initFilters();
        initAssets();
        initFactories();
        setCameraSets();
        createWorld();
        setBackgroundColor(mp.getBackgroundColor());
    }
    
    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (shot) {
            if (updateTimer >= .15) {
                beamNode.detachAllChildren();
                updateTimer = 0;
                shot = false;
            } else {
                updateTimer += tpf;
            }
        }
    }
    
    private void setCameraSets() {
        flyCam.setDragToRotate(true);
        flyCam.setRotationSpeed(0.0f);
        flyCam.setZoomSpeed(0.0f);
        cam.setLocation(gp.getCamLoc());
    }
    
    private void createWorld() {
        createLight();
        createFloor();
        createBase();
        buildTowers();
        buildCreepSpawners();
        attachBeamNode();
        attachWorldNode();
    }
    
    private void initFactories() {
        tf = new TowerFactory(this);
        csf = new CreepSpawnerFactory(this);
    }
    
    private void attachWorldNode() {
        rootNode.attachChild(worldNode);
    }
    
    private void attachBeamNode() {
        rootNode.attachChild(beamNode);
    }
    
    private void initAssets() {
        bomb_mat = assetManager.loadMaterial("Materials/" + getMatDir() + "/Bomb.j3m");
    }
    

        /**
     * Sets up the FilterPostProcessor and Bloom filter used by the game.
     * Called upon initialization of the game.
     */
    public void initFilters() {
        if (fp.getEnabled()) {
            fpp = new FilterPostProcessor(assetManager);
            if (fp.getGlowMode() != null) {
                bloom = new BloomFilter(fp.getGlowMode());
            }
            else {
                bloom = new BloomFilter();
            }
            bloom.setDownSamplingFactor(fp.getDownSampling());
            bloom.setBlurScale(fp.getBlurScale());
            bloom.setExposurePower(fp.getExposurePower());
            bloom.setBloomIntensity(fp.getBloomIntensity());
            bloomIntensity = fp.getBloomIntensity();
            fpp.addFilter(bloom);
            viewPort.addProcessor(fpp);
        }
    }
    
    
    
    private void removeFilters() {
        viewPort.removeProcessor(fpp);
    }
    
        /**
     * Toggle the bloom filter.
     */
    public void toggleBloom() {
        if (viewPort.getProcessors().contains(fpp)) {
            viewPort.removeProcessor(fpp);
        } else {
            viewPort.addProcessor(fpp);
        }

    }

    /**
     * Method for changing the bloom filter's exposure power.
     * @param newVal - what it will be changed to
     */
    public void setBloomExposurePower(float newVal) {
        bloom.setExposurePower(newVal);
    }

    /**
     * Method for changing the bloom filter's blur scape.
     * @param newVal - what it will be changed to
     */
    public void setBloomBlurScape(float newVal) {
        bloom.setBlurScale(newVal);
    }

    /**
     * Method fo setting the bloom filter's intensity
     * @param newVal - what it should be changed to.
     */
    public void setBloomIntensity(float newVal) {
        bloom.setBloomIntensity(newVal);
    }

    /**
     * Increments the bloom filter's intensity
     * @param add - value to be added to the filter.
     */
    public void incBloomIntensity(float add) {
        bloom.setBloomIntensity(bloom.getBloomIntensity() + add);
    }
    
    public BloomFilter getBloom() {
        return bloom;
    }
    
    public void makeLaserBeam(Vector3f origin, Vector3f target, String towertype, float beamwidth) {
        AudioState.beamSound(towertype, origin);
        beamNode.attachChild(BeamFactory.makeLaserBeam(origin, target, towertype, beamwidth));
        shot = true;
    }
    
    
    /*** World Methods ***/

    public void createLight() {
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(128f));
        worldNode.addLight(ambient);
    }

    public void createFloor() {
        Geometry floor_geom = new Geometry("Floor", univ_box);
        floor_geom.setMaterial(assetManager.loadMaterial(getFloorTexLoc()));
        floor_geom.setLocalScale(gp.getFloorScale());
        worldNode.attachChild(floor_geom);
    }
    
    private String getFloorTexLoc() {
        return "Materials/" + getMatDir() + "/Floor.j3m";
    }
    
    /*** Base Methods ***/

    public void createBase() {
        Spatial base = BaseFactory.getBase(getBaseTexLoc(), gp.getBaseVec(), gp.getBaseScale());
        worldNode.attachChild(base);
        GameState.setBaseBounds(base.getWorldBound());
        GameState.setFormattedBaseCoords(base);
    }
    
    public String getBaseTexLoc() {
        return "Materials/" +getMatDir()+"/Base.j3m";
    }
    
    public Vector3f getBaseVec() {
        return gp.getBaseVec();
    }
    
    public Vector3f getBaseScale() {
        return gp.getBaseScale();
    }
    
    /*** Tower Methods ***/
    
    public void buildTowers() {
        for (int i = 0; i < towerList.size(); i++) {
            createTower(towerList.get(i));
        }
        FriendlyState.setTowerList(towerList);
    }
    
    public void createTower(TowerParams tp) {
        towerList.set(tp.getIndex(), tf.getTower(tp));
        worldNode.attachChild(towerList.get(tp.getIndex()).getSpatial());
    }
    
    public void setTowerScale(int tindex, String scaletype) {
        if (scaletype.equals("BuiltSize")) {
            towerList.get(tindex).getSpatial().setLocalScale(getTowerBuiltSize());
        } else if (scaletype.equals("UnbuiltSize")) {
            towerList.get(tindex).getSpatial().setLocalScale(getTowerUnbuiltSize());
        } else if (scaletype.equals("BuiltSelected")) {
            towerList.get(tindex).getSpatial().setLocalScale(getTowerBuiltSelected());
        } else if (scaletype.equals("UnbuiltSelected")) {
            towerList.get(tindex).getSpatial().setLocalScale(getTowerUnbuiltSelected());
        }
    }
    
    public void towerTextureCharged(TowerParams tp) {
        tp.getSpatial().setMaterial(assetManager.loadMaterial(getTowerMatLoc(tp.getType())));
    }
    
    public void towerTextureCharged(TowerControl tc) {
        tc.getSpatial().setMaterial(assetManager.loadMaterial(getTowerMatLoc(tc.getTowerType())));
    }
    
    public void towerTextureEmpty(TowerControl tc) {
        tc.getSpatial().setMaterial(assetManager.loadMaterial(getTowerMatLoc("TowerEmpty")));
    }
    
    public void towerUpgradeStarter(TowerParams tp) {
        FriendlyState.upgradeTower(tp);
    }
    
    
    public String getTowerMatLoc(String type) {
        return "Materials/" + getMatDir() + "/" + type + ".j3m";
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
    
    public ArrayList<TowerParams> getTowerList() {
        return towerList;
    }
    
    /*** CreepSpawner Methods ***/
    public void createCreepSpawner(CreepSpawnerParams curSpawner) {
        creepSpawnerList.set(curSpawner.getIndex(), csf.getSpawner(curSpawner));
        worldNode.attachChild(creepSpawnerList.get(curSpawner.getIndex()).getSpatial());
    }

    public void buildCreepSpawners() {
        for (int i = 0; i < creepSpawnerList.size(); i++) {
            createCreepSpawner(creepSpawnerList.get(i));
        }
        EnemyState.setCreepSpawnerList(creepSpawnerList);
    }
    
    public String getCreepSpawnerMatLoc() {
        return "Materials/" + getMatDir() + "/CreepSpawner.j3m";
    }
    
    public Vector3f getCreepSpawnerHorizontalScale() {
        return gp.getCreepSpawnerHorizontalScale();
    }
    
    public Vector3f getCreepSpawnerVerticalScale() {
        return gp.getCreepSpawnerVerticalScale();
    }
    
    /*** Bomb Methods ***/
    
    public void dropBomb(Vector3f translation, float initialSize) {
        Geometry bomb_geom = new Geometry("Bomb", getBombMesh());
        bomb_geom.setMaterial(bomb_mat);
        bomb_geom.setLocalScale(initialSize);
        bomb_geom.setLocalTranslation(translation);

        bomb_geom.addControl(new BombControl(initialSize, this, AudioState));
        worldNode.attachChild(bomb_geom);
        AudioState.playBombSound(translation);

    }
    
    public Sphere getBombMesh() {
        return bombMesh;
    }


    
    /*** Global Methods ***/
    
    public Box getUnivBox() {
        return univ_box;
    }
    public String getMatDir() {
        return mp.getMatDir();
    }
    
    private void setBackgroundColor(ColorRGBA c) {
        app.getViewPort().setBackgroundColor(c);
    }
    public AssetManager getAssetManager() {
        return assetManager;
    }
    
    public ScheduledThreadPoolExecutor getEx() {
        return GameState.getEx();
    }
        
    public SimpleApplication getApp() {
        return app;
    }
    
    public FriendlyState getFriendlyState() {
        return FriendlyState;
    }
    
    public EnemyState getEnemyState() {
        return EnemyState;
    }
    
    public ArrayList<Spatial> getCreepList() {
        return EnemyState.getCreepList();
    }
    
    
    public GeometryParams getGeometryParams() {
        return gp;
    }
    
    public Vector3f getCamLocation() {
        return cam.getLocation();
    }
    
    public Node getWorldNode() {
        return worldNode;
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        removeFilters();
        beamNode.detachAllChildren();

    }

 
}
