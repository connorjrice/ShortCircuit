package ShortCircuit.Tower.States.Game;

import ShortCircuit.GUI.StartGUI;
import ShortCircuit.Tower.Controls.BombControl;
import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.Factories.BaseFactory;
import ShortCircuit.Tower.Factories.BeamFactory;
import ShortCircuit.Tower.Factories.TowerFactory;
import ShortCircuit.Tower.MapXML.Objects.FilterParams;
import ShortCircuit.Tower.MapXML.Objects.GeometryParams;
import ShortCircuit.Tower.MapXML.Objects.MaterialParams;
import ShortCircuit.Tower.MapXML.Objects.TowerParams;
import ShortCircuit.Tower.Objects.Loading.GraphicsParams;
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
    public Node beamNode = new Node("Beams");
    private float updateTimer = 0.0f;
    private MaterialParams mp;
    private String basetexloc;
    public String tow1MatLoc;
    public String tow2MatLoc;
    public String tow3MatLoc;
    public String tow4MatLoc;
    public String towUnMatLoc;
    public String towEmMatLoc;
    private String smCreepMatloc;
    private String mdCreepMatloc;
    private String lgCreepMatloc;
    private String xlCreepMatloc;
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
    private TowerFactory tf;
    
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
        initFilters();
        initAssets();
        initFactories();
        setCameraSets();
        createWorld();
        setBackgroundColor(mp.getBackgroundColor());
        startGUI.hideloading();
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
        cam.setLocation(new Vector3f(0,0,20f)); //TODO: Camera location into geometry
    }
    
    private void createWorld() {
        createLight();
        createFloor();
        createBase();
        buildTowers();
        attachWorldNode();
    }
    
    private void initFactories() {
        tf = new TowerFactory(this);
    }
    
    private void attachWorldNode() {
        rootNode.attachChild(worldNode);
    }
    
    private void initAssets() {
        bomb_mat = assetManager.loadMaterial("Materials/" + getMatDir() + "/Bomb.j3m");
        tow1MatLoc = "Materials/" + getMatDir() + "/Tower1.j3m";
        tow2MatLoc = "Materials/" + getMatDir() + "/Tower2.j3m";
        tow3MatLoc = "Materials/" + getMatDir() + "/Tower3.j3m";
        tow4MatLoc = "Materials/" + getMatDir() + "/Tower4.j3m";
        towUnMatLoc = "Materials/" + getMatDir() + "/TowerUnbuilt.j3m";
        towEmMatLoc = "Materials/" + getMatDir() + "/EmptyTower.j3m";
        smCreepMatloc = "Materials/" + getMatDir() + "/SmallCreep.j3m";
        mdCreepMatloc = "Materials/" + getMatDir() + "/MediumCreep.j3m";
        lgCreepMatloc = "Materials/" + getMatDir() + "/LargeCreep.j3m";
        xlCreepMatloc = "Materials/" + getMatDir() + "/GiantCreep.j3m";
        floortexloc = "Materials/" + getMatDir() + "/Floor.j3m";
        basetexloc = "Materials/" + getMatDir() + "/Base.j3m";
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
    
    public void makeLaserBeam(Vector3f origin, Vector3f target, String towertype, String beamtype, float beamwidth) {
        AudioState.beamSound(towertype, origin);
        beamNode.attachChild(BeamFactory.makeLaserBeam(origin, target, beamtype, beamwidth));
        shot = true;
    }
    
    
    /*** World Methods ***/

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
    public void createFloor() {
        Geometry floor_geom = new Geometry("Floor", univ_box);
        floor_geom.setMaterial(assetManager.loadMaterial(floortexloc));
        floor_geom.setLocalScale(gp.getFloorScale());
        worldNode.attachChild(floor_geom);
    }
    
    /*** Base Methods ***/

    public void createBase() {
        worldNode.attachChild(BaseFactory.getBase(basetexloc, gp.getBaseVec(), 
        gp.getBaseScale()));
    }
    
    public String getBaseTexLoc() {
        return basetexloc;
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
    
    public void changeTowerTexture(TowerParams tp) {
        tp.getSpatial().setMaterial(assetManager.loadMaterial(getTowerMatLoc(tp.getType())));
    }
    
    public void changeTowerTexture(TowerControl tc) {
        tc.getSpatial().setMaterial(assetManager.loadMaterial(getTowerMatLoc(tc.getTowerType())));
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
    
    /*** Bomb Methods ***/
    
    public void dropBomb(Vector3f translation, float initialSize) {
        Geometry bomb_geom = new Geometry("Bomb", getBombMesh());
        bomb_geom.setMaterial(bomb_mat);
        bomb_geom.setLocalScale(initialSize);
        bomb_geom.setLocalTranslation(translation);
        bomb_geom.addControl(new BombControl(initialSize, this));
        worldNode.attachChild(bomb_geom);
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
    
    public ArrayList<Spatial> getCreepList() {
        return EnemyState.getCreepList();
    }
    
    
    public Vector3f getCamLocation() {
        return cam.getLocation();
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        removeFilters();
        beamNode.detachAllChildren();

    }

 
}
