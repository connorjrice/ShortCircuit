package ShortCircuit.Tower.MapXML;

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
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.filters.BloomFilter.GlowMode;
import java.util.ArrayList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;

/**
 * Generates maps for Tower game based upon XML files. Files must have .lvl.xml
 * extensions
 *
 * TODO: Fix inputSource bug (won't load outside IDE)
 * @author Connor Rice
 */
public class MapGenerator {

    private XPath xpath;
    private InputSource inputSource;
    private AssetManager assetManager;
    private String level;

    public MapGenerator(String level, SimpleApplication app) {
        xpath = XPathFactory.newInstance().newXPath();
        this.assetManager = app.getAssetManager();
        assetManager.registerLoader(XMLLoader.class, "lvl.xml");
        this.level = level;
        this.inputSource = new InputSource("assets/XML/" + level + ".lvl.xml");
        //this.inputSource = getInputSource();
    }
    
    private InputSource getInputSource() {
        return (InputSource) assetManager.loadAsset("XML/" + level + ".lvl.xml");
    }

    
    public GameplayParams getGameplayParams() {
        return new GameplayParams(parseLevelParams(), parsePlayerParams());
    }
    
    public EnemyParams getEnemyParams() {
        return new EnemyParams(parseCreepList());
    }
    
    public GraphicsParams getGraphicsParams() {
        return new GraphicsParams(parseMaterialParams(), parseFilterParams(), 
                parseGeometryParams(), parseTowerList(), parseCreepSpawnerList());
    }
    
    private LevelParams parseLevelParams() {
        String levelExpression = "gameplayparams/param[@id = 'levelParams']/";
        String profiles = getElement("profile", levelExpression);
        String tutorials = getElement("tutorial", levelExpression);
        int numCreeps = parseInt(getElement("numCreeps", levelExpression));
        int creepMod = parseInt(getElement("creepMod", levelExpression));
        int levelCap = parseInt(getElement("levelCap", levelExpression));
        int levelMod = parseInt(getElement("levelMod", levelExpression));
        String allowedenemies = getElement("allowedenemies", levelExpression);
        boolean profile = parseBoolean(profiles);
        boolean tutorial = parseBoolean(tutorials);
        return new LevelParams(numCreeps, creepMod, levelCap, levelMod, profile, tutorial, allowedenemies);
    }
    
    private PlayerParams parsePlayerParams() {
        String playerExpression = "gameplayparams/param[@id = 'playerParams']/";
        int plrHealth = parseInt(getElement("plrHealth", playerExpression));
        int plrBudget = parseInt(getElement("plrBudget", playerExpression));
        int plrLevel = parseInt(getElement("plrLevel", playerExpression));
        int plrScore = parseInt(getElement("plrScore", playerExpression));
        return new PlayerParams(plrHealth, plrBudget, plrLevel, plrScore);
    }
    
    private GeometryParams parseGeometryParams() {
        String geometryExpression = "gameplayparams/param[@id = 'geometryParams']/";
        Vector3f camLoc = parseVector3f(getElement("/camera/camLocation", geometryExpression));
        Vector3f floorScale = parseVector3f(getElement("/floor/scale", geometryExpression));
        Vector3f baseVec = parseVector3f(getElement("/base/baseVec", geometryExpression));
        Vector3f baseScale = parseVector3f(getElement("/base/baseScale", geometryExpression));
        Vector3f towerBuiltSize = parseVector3f(getElement("/tower/builtSize", geometryExpression));
        Vector3f towerUnbuiltSize = parseVector3f(getElement("/tower/unbuiltSize", geometryExpression));
        Vector3f towerBuiltSelected = parseVector3f(getElement("/tower/builtSelected", geometryExpression));
        Vector3f towerUnbuiltSelected = parseVector3f(getElement("/tower/unbuiltSelected", geometryExpression));
        Vector3f horizontalScale = parseVector3f(getElement("/creepspawner/horizontalscale", geometryExpression));
        Vector3f verticalScale = parseVector3f(getElement("/creepspawner/verticalscale", geometryExpression));
        float beamwidth = parseFloat(getElement("/tower/beamwidth", geometryExpression));
        return new GeometryParams(camLoc, floorScale, baseVec, baseScale, 
                towerBuiltSize, towerUnbuiltSize, towerBuiltSelected, 
                towerUnbuiltSelected, horizontalScale, verticalScale, beamwidth);
    }
    
    private ArrayList<TowerParams> parseTowerList() {
        ArrayList<TowerParams> towerList = new ArrayList<TowerParams>();
        String towerExpression = "gameplayparams/param[@id = 'towerParams']/";
        int numTowers = parseInt(getElement("numTowers", towerExpression));
        for (int i = 0; i < numTowers; i++) {
            String curTowerExpression = towerExpression + "tower[@id = '"+i+"']/";
            Vector3f towerVec = parseVector3f(getElement("vec", curTowerExpression));
            boolean starter = parseBoolean(getElement("isStarter", curTowerExpression));
            towerList.add(new TowerParams(towerVec, starter, i));
        }
        return towerList;
    }
  
    private MaterialParams parseMaterialParams() {
        String materialElement = "graphicsparams/param[@id = 'materialParams']/";
        String matdir = getElement("matdir", materialElement);
        String colors = getElement("bgcolor", materialElement);
        System.out.println(matdir);
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
            int health = parseInt(getElement("health", curCreepExpression));
            float speed = parseFloat(getElement("speed", curCreepExpression));
            Vector3f size = parseVector3f(getElement("size", curCreepExpression));
            int value = parseInt(getElement("value", curCreepExpression));
            creepList.add(new CreepParams(health, speed, size, creepTypes[i], value));
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
            creepSpawnerList.add(new CreepSpawnerParams(vec, orientation, i));
        }
        return creepSpawnerList;
    }
    
    public String[] parseCreepTypes(String s) {
        return s.split(",");
    }

    public ColorRGBA parseColorRGBA(String colors) {
        System.out.println(colors);
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
    
    public String getElement(String value, String parentNode) {
        return getValue(getExpression(value, parentNode));
    }
    
    public String getExpression(String value, String parentNode) {
        return "/level/"+parentNode + value+ "/text()";
    }
    
    public String getValue(String expression) {
        try {
            return xpath.evaluate(expression, inputSource);
        } catch (XPathExpressionException ex) {
            System.out.println(ex.getCause());
            return null;
        }
    }

}
