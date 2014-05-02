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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

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
    private Node gameplayParamsChildren;
    private Node graphicsParamsChildren;
    private Node enemyParamsChildren;
    private XPath xpath;
    private final String level;
    private InputSource inputSource;

    public MapGenerator(String level, Application app) {
        this.app = app;
        this.level = level;
        this.assetManager = this.app.getAssetManager();
        xpath = XPathFactory.newInstance().newXPath();  
        inputSource = new InputSource("assets/XML/" + level+ ".lvl.xml");
    }

    
    public GameplayParams getGameplayParams() {
        return new GameplayParams(parseLevelParams(), parsePlayerParams(), parseTowerList());
    }
    
    public EnemyParams getEnemyParams() {
        return new EnemyParams(parseCreepList(), parseCreepSpawnerList());
    }
    
    public GraphicsParams getGraphicsParams() {
        return new GraphicsParams(parseMaterialParams(), parseFilterParams(), parseGeometryParams(), parseBaseParams());
    }
    
    private LevelParams parseLevelParams() {
        String levelElement = "gameplayparams/param[@id = 'levelParams']/";
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
        String playerElement = "gameplayparams/param[@id = 'playerParams']/";
        int plrHealth = parseInt(getElement("plrHealth", playerElement));
        int plrBudget = parseInt(getElement("plrBudget", playerElement));
        int plrLevel = parseInt(getElement("plrLevel", playerElement));
        int plrScore = parseInt(getElement("plrScore", playerElement));
        return new PlayerParams(plrHealth, plrBudget, plrLevel, plrScore);
    }
    
    private GeometryParams parseGeometryParams() {
        String geometryElement = "gameplayparams/param[@id = 'geometryParams']/";     
        Vector3f floorScale = parseVector3f(getElement("/floor/scale", geometryElement));
        return new GeometryParams(floorScale);
    }
    
    private BaseParams parseBaseParams() {
        String baseElement = "gameplayparams/param[@id = 'baseParams']/";
        Vector3f baseVec = parseVector3f(getElement("baseVec", baseElement));
        Vector3f baseScale = parseVector3f(getElement("baseScale", baseElement));
        return new BaseParams(baseVec, baseScale);
    }
    
    private ArrayList<TowerParams> parseTowerList() {
        ArrayList<TowerParams> towerList = new ArrayList<TowerParams>();
        String towerExpression = "gameplayparams/param[@id = 'towerParams']/";
        int numTowers = parseInt(getElement("numTowers", towerExpression));
        System.out.println(numTowers);
        for (int i = 0; i < numTowers; i++) {
            String curTowerExpression = towerExpression + "tower[@id = '"+i+"']/";
            Vector3f towerVec = parseVector3f(getElement("vec", curTowerExpression));
            boolean starter = parseBoolean(getElement("isStarter", curTowerExpression));
            towerList.add(new TowerParams(towerVec, starter));
        }
        return towerList;
    }
  
    private MaterialParams parseMaterialParams() {
        String materialElement = "graphicsparams/param[@id = 'materialParams']/";
        String matdir = getElement("matdir", materialElement);
        String colors = getElement("backgroundcolor", materialElement);
        ColorRGBA backgroundcolor = parseColorRGBA(colors);
        return new MaterialParams(backgroundcolor, matdir);
    }
    
    private FilterParams parseFilterParams() {
        String filterElement = "graphicsparams/param[@id = 'filterParams']/";
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
        String creepExpression = "enemyparams/param[@id = 'creepParams']/";
        String[] creepTypes = parseCreepTypes(getElement("creepTypes", creepExpression));
        ArrayList<CreepParams> creepList = new ArrayList<CreepParams>();
        for (int i = 0; i < creepTypes.length; i++) {
            String curCreepExpression = creepExpression+creepTypes[i]+"Creep/";
            System.out.println(curCreepExpression);
            int health = parseInt(getElement("health", curCreepExpression));
            float speed = parseFloat(getElement("speed", curCreepExpression));
            Vector3f size = parseVector3f(getElement("size", curCreepExpression));
            creepList.add(new CreepParams(health, speed, size, creepTypes[i]));
        }
        return creepList;
    }
    
    public ArrayList<CreepSpawnerParams> parseCreepSpawnerList() {
        String creepExpression = "enemyparams/param[@id = 'creepSpawnerParams']/";
        int numSpawners = parseInt(getElement("numSpawners", creepExpression));
        ArrayList<CreepSpawnerParams> creepSpawnerList = new ArrayList<CreepSpawnerParams>();
        for (int i = 0; i < numSpawners; i++) {
            String curCreepSpawnerExpression = creepExpression+ "creepSpawner[@id = '"+i+"']/";
            Vector3f vec = parseVector3f(getElement("vec", curCreepSpawnerExpression));
            String orientation = getElement("orientation", curCreepSpawnerExpression);
            creepSpawnerList.add(new CreepSpawnerParams(vec, orientation));
        }
        return creepSpawnerList;
    }
    
    public String[] parseCreepTypes(String s) {
        return s.split(",");
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
    
    public String getElement(String value, String parentNode) {
        return getValue(getExpression(value, parentNode));
    }
    
    public String getExpression(String value, String parentNode) {
        return "/level/"+parentNode+value+"/text()";
    }
    
    public String getValue(String expression) {
        String returnvalue = "";
        try {
            NodeList nodes = (NodeList) xpath.evaluate(expression, inputSource, XPathConstants.NODESET);
            returnvalue = nodes.item(0).getTextContent();
        } catch (XPathExpressionException ex) {
            Logger.getLogger(MapGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return returnvalue;
    }
 
    public String getElement(String s, Element e) {
        return getElement(s, e, 0);
    }

    public String getElement(String s, Element e, int i) {
        return null;
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