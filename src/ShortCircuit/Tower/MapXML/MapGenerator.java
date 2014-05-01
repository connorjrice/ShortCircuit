package ShortCircuit.Tower.MapXML;

import ShortCircuit.Tower.MapXML.Objects.BaseParams;
import ShortCircuit.Tower.MapXML.Objects.CreepParams;
import ShortCircuit.Tower.MapXML.Objects.CreepSpawnerParams;
import ShortCircuit.Tower.Objects.Loading.EnemyParams;
import ShortCircuit.Tower.MapXML.Objects.FilterParams;
import ShortCircuit.Tower.Objects.Loading.GameplayParams;
import ShortCircuit.Tower.MapXML.Objects.GeometryParams;
import ShortCircuit.Tower.MapXML.Objects.LevelParams;
import ShortCircuit.Tower.MapXML.Objects.MaterialParams;
import ShortCircuit.Tower.MapXML.Objects.PlayerParams;
import ShortCircuit.Tower.MapXML.Objects.TowerParams;
import ShortCircuit.Tower.Objects.Loading.GraphicsParams;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.filters.BloomFilter.GlowMode;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.util.ArrayList;
import org.w3c.dom.Node;

/**
 * Generates maps for Tower game based upon XML files. Files must have .lvl.xml
 * extensions
 * 
 * TODO: write psudeocode to do A* process
 * @author Connor Rice
 */
public class MapGenerator {

    private Document doc;
    private Application app;
    private AssetManager assetManager;
    private NodeList gameplayParamsChildren;
    private NodeList graphicsParamsChildren;
    private NodeList enemyParamsChildren;

    public MapGenerator(String level, Application app) {
        this.app = app;
        this.assetManager = this.app.getAssetManager();
        assetManager.registerLoader(XMLLoader.class, "lvl.xml");
        this.doc = (Document) assetManager.loadAsset("XML/" + level + ".lvl.xml");
    }

    public void parseXML() {
        gameplayParamsChildren = doc.getChildNodes().item(0).getChildNodes();
        graphicsParamsChildren = doc.getChildNodes().item(1).getChildNodes();
        enemyParamsChildren = doc.getChildNodes().item(2).getChildNodes();
    }
    
    public GameplayParams getGameplayParams() {
        return new GameplayParams(parseLevelParams(), parsePlayerParams(), parseGeometryParams(), parseBaseParams(), parseTowerList());
    }
    
    public EnemyParams getEnemyParams() {
        return new EnemyParams(parseCreepList(), parseCreepSpawnerList());
    }
    
    public GraphicsParams getGraphicsParams() {
        return new GraphicsParams(parseMaterialParams(), parseFilterParams());
    }
    
    private LevelParams parseLevelParams() {
        Element levelElement = (Element) gameplayParamsChildren.item(0);
        String camlocS = getElement("camLocation", levelElement);
        String profiles = getElement("profile", levelElement);
        String tutorials = getElement("tutorial", levelElement);
        Vector3f camLocation = parseVector3f(camlocS);
        int numCreeps = parseInt(getElement("numCreeps", levelElement));
        int creepMod = parseInt(getElement("creepMod", levelElement));
        int levelCap = parseInt(getElement("levelCap", levelElement));
        int levelMod = parseInt(getElement("levelMod", levelElement));
        String allowedenemies = getElement("allowedenemies", levelElement);
        boolean profile = parseBoolean(profiles);
        boolean tutorial = parseBoolean(tutorials);
        return new LevelParams(camLocation, numCreeps, creepMod, levelCap, 
                levelMod, profile, tutorial, allowedenemies);
    }
    
    private PlayerParams parsePlayerParams() {
        Element playerElement = (Element) gameplayParamsChildren.item(1);
        int plrHealth = parseInt(getElement("plrHealth", playerElement));
        int plrBudget = parseInt(getElement("plrBudget", playerElement));
        int plrLevel = parseInt(getElement("plrLevel", playerElement));
        int plrScore = parseInt(getElement("plrScore", playerElement));
        return new PlayerParams(plrHealth, plrBudget, plrLevel, plrScore);
    }
    
    private GeometryParams parseGeometryParams() {
        Element geometryElement = (Element) gameplayParamsChildren.item(2);
        Vector3f floorScale = parseVector3f(getElement("floorscale", geometryElement));
        return new GeometryParams(floorScale);
    }
    
    private BaseParams parseBaseParams() {
        Element baseElement = (Element) gameplayParamsChildren.item(3);
        Vector3f baseVec = parseVector3f(getElement("baseVec", baseElement));
        Vector3f baseScale = parseVector3f(getElement("baseScale", baseElement));
        return new BaseParams(baseVec, baseScale);
    }
    
    private ArrayList<TowerParams> parseTowerList() {
        ArrayList<TowerParams> towerList = new ArrayList<TowerParams>();
        NodeList towerNodes = gameplayParamsChildren.item(4).getChildNodes();
        for (int i = 0; i < towerNodes.getLength(); i++) {
            NodeList curTower = towerNodes.item(i).getChildNodes();
            float x = parseFloat(getContent(curTower.item(0)));
            float y = parseFloat(getContent(curTower.item(1)));
            float z = parseFloat(getContent(curTower.item(2)));
            boolean starter = parseBoolean(getContent(curTower.item(3)));
            towerList.add(new TowerParams(x, y, z, starter));
        }
        return towerList;
    }

    private MaterialParams parseMaterialParams() {
        Element materialElement = (Element) gameplayParamsChildren.item(2);
        String matdir = getElement("matdir", materialElement);
        String colors = getElement("backgroundcolor", materialElement);
        ColorRGBA backgroundcolor = parseColorRGBA(colors);
        return new MaterialParams(backgroundcolor, matdir);
    }
    
    private FilterParams parseFilterParams() {
        Element filterElement = (Element) gameplayParamsChildren.item(3);
        String blooms = getElement("bloom", filterElement);
        boolean bloom;
        if (blooms.equals("false")) {
            bloom = false;
        } else {
            bloom = true;
        }
        float downsampling = parseFloat(getElement("downsampling", filterElement));
        float blurscale = parseFloat(getElement("blurscale", filterElement));
        float exposurepower = parseFloat(getElement("exposurepower", filterElement));
        float bloomintensity = parseFloat(getElement("bloomintensity", filterElement));
        String glowmodes = getElement("glowmode", filterElement);
        GlowMode glowmode = parseGlowMode(glowmodes);
        return new FilterParams(bloom, downsampling, blurscale, 
                exposurepower, bloomintensity, glowmode);
    }
    
    public ArrayList<CreepParams> parseCreepList() {
        Element eElement = (Element) enemyParamsChildren.item(0);        
        NodeList creepNodes = eElement.getChildNodes();
        ArrayList<CreepParams> creepList = new ArrayList<CreepParams>();
        for (int i = 0; i < creepNodes.getLength(); i++) {
            String type = creepNodes.item(i).getNodeName();
            NodeList curCreep = creepNodes.item(i).getChildNodes();
            int health = parseInt(curCreep.item(0).getTextContent());
            float speed = parseFloat(curCreep.item(1).getTextContent());
            Vector3f size = parseVector3f(curCreep.item(2).getTextContent());
            creepList.add(new CreepParams(health, speed, size, type));
        }
        return creepList;
    }
    
    public ArrayList<CreepSpawnerParams> parseCreepSpawnerList() {
        Element eElement = (Element) enemyParamsChildren.item(0);        
        NodeList creepSpawnerNodes = eElement.getChildNodes();
        ArrayList<CreepSpawnerParams> creepList = new ArrayList<CreepSpawnerParams>();
        for (int i = 0; i < creepSpawnerNodes.getLength(); i++) {
            String type = creepSpawnerNodes.item(i).getNodeName();
            NodeList curCreepSpawner = creepSpawnerNodes.item(i).getChildNodes();
            float x = parseFloat(getContent(curCreepSpawner.item(0)));
            float y = parseFloat(getContent(curCreepSpawner.item(1)));
            float z = parseFloat(getContent(curCreepSpawner.item(2)));
            String orientation = getContent(curCreepSpawner.item(3));
            creepList.add(new CreepSpawnerParams(x, y, z, orientation));
        }
        return creepList;
    }

    public ColorRGBA parseColorRGBA(String colors) {
        if (colors.equals("Black")) {
            return ColorRGBA.Black;
        } else if (colors.equals("DarkGrey")) {
            return ColorRGBA.DarkGray;
        } else if (colors.equals("Blue")) {
            return ColorRGBA.Blue;
        } else if (colors.equals("Purple")) {
            return new ColorRGBA(.5f, .0f, .5f, .5f);
        } else if (colors.equals("Red")) {
            return new ColorRGBA(.4f, .12f, .13f, 0.3f);
        } else {
            return ColorRGBA.randomColor();
        }
    }

    public boolean parseBoolean(String bool) {
        if (bool.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public Vector3f parseVector3f(String vec) {
        String[] split = vec.split(",");
        return new Vector3f(parseFloat(split[0]),
                parseFloat(split[1]), parseFloat(split[2]));
    }

    public Vector3f parseVector3f(String x, String y, String z) {
        return new Vector3f(Float.parseFloat(x),
                Float.parseFloat(y), Float.parseFloat(z));
    }

    public int parseInt(String s) {
        return Integer.parseInt(s);
    }

    public float parseFloat(String s) {
        return Float.parseFloat(s);
    }
    
    public String getContent(Node n) {
        return n.getTextContent();
    }

    public String getElement(String s, Element e) {
        return getElement(s, e, 0);
    }

    public String getElement(String s, Element e, int i) {
        return e.getElementsByTagName(s).item(i).getTextContent();
    }
    
    public GlowMode parseGlowMode(String s) {
        if (s.equals("GlowMode.SceneAndObjects")) {
            return GlowMode.SceneAndObjects;
        } else if (s.equals("GlowMode.Scene")) {
            return GlowMode.Scene;
        } else if (s.equals("GlowMode.Objects")) {
            return GlowMode.Objects;
        } else {
            return null;
        }
    }

}