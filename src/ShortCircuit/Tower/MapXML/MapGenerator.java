package ShortCircuit.Tower.MapXML;

import ShortCircuit.Tower.Objects.FilterParams;
import ShortCircuit.Tower.Objects.GameplayParams;
import ShortCircuit.Tower.Objects.LevelParams;
import ShortCircuit.Tower.Objects.MaterialParams;
import ShortCircuit.Tower.Objects.PlayerParams;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.filters.BloomFilter.GlowMode;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.util.ArrayList;

/**
 * Generates maps for Tower game based upon XML files. Files must have .lvl.xml
 * extensions
 *
 * @author Connor Rice
 */
public class MapGenerator {

    private Document doc;
    private NodeList tList;
    private Application app;
    private AssetManager assetManager;
    private NodeList bList;
    private NodeList cList;
    private NodeList fList;
    private NodeList pList;

    public MapGenerator(String level, Application app) {
        this.app = app;
        this.assetManager = this.app.getAssetManager();
        assetManager.registerLoader(XMLLoader.class, "lvl.xml");
        this.doc = (Document) assetManager.loadAsset("XML/" + level + ".lvl.xml");
    }

    public void parseXML() {
        tList = doc.getElementsByTagName("tower");
        bList = doc.getElementsByTagName("base");
        cList = doc.getElementsByTagName("creepspawn");
        fList = doc.getElementsByTagName("floor");
        pList = doc.getElementsByTagName("levelparams");
    }

    public ArrayList<Vector3f> getUnbuiltTowerVecs() {
        ArrayList<Vector3f> unbuiltVecs = new ArrayList<Vector3f>();
        for (int i = 0; i < tList.getLength(); i++) {
            Element eElement = (Element) tList.item(i);
            unbuiltVecs.add(parseVector3f(getElement("x", eElement),
                    getElement("y", eElement), getElement("z", eElement)));
        }
        return unbuiltVecs;

    }

    public ArrayList<Vector3f> getCreepSpawnVecs() {
        ArrayList<Vector3f> creepSpawnVecs = new ArrayList<Vector3f>();
        for (int i = 0; i < cList.getLength(); i++) {
            Element eElement = (Element) cList.item(i);
            creepSpawnVecs.add(parseVector3f(getElement("x", eElement),
                    getElement("y", eElement), getElement("z", eElement)));
        }
        return creepSpawnVecs;

    }

    public ArrayList<String> getCreepSpawnDirs() {
        ArrayList<String> creepSpawnDirs = new ArrayList<String>();
        for (int i = 0; i < cList.getLength(); i++) {
            Element eElement = (Element) cList.item(i);
            creepSpawnDirs.add(getElement("orientation", eElement));
        }
        return creepSpawnDirs;
    }

    public Vector3f getBaseVec() {
        Element eElement = (Element) bList.item(0);
        return parseVector3f(getElement("x", eElement),
                getElement("y", eElement), getElement("z", eElement));
    }

    public Vector3f getBaseScale() {
        Element eElement = (Element) bList.item(0);
        return parseVector3f(getElement("x", eElement, 1),
                getElement("y", eElement, 1), getElement("z", eElement, 1));
    }

    public Vector3f getFloorScale() {
        Element eElement = (Element) fList.item(0);
        return parseVector3f(getElement("x", eElement),
                getElement("y", eElement), getElement("z", eElement));
    }

    public ArrayList<Integer> getStarterTowers() {
        ArrayList<Integer> starterTowers = new ArrayList<Integer>();
        for (int i = 0; i < tList.getLength(); i++) {
            Element eElement = (Element) tList.item(i);
            if (getElement("isStarter", eElement).equals("true")) {
                starterTowers.add(parseInt(eElement.getAttribute("id")));
            }
        }
        return starterTowers;
    }

    public GameplayParams getGameplayParams() {  //throws LevelParseException {
        Element eElement = (Element) pList.item(0);
        String camlocS = getElement("camLocation", eElement);
        String profiles = getElement("profile", eElement);
        String matdir = getElement("matdir", eElement);
        String tutorials = getElement("tutorial", eElement);
        String colors = getElement("backgroundcolor", eElement);
        int numCreeps = parseInt(getElement("numCreeps", eElement));
        int creepMod = parseInt(getElement("creepMod", eElement));
        int levelCap = parseInt(getElement("levelCap", eElement));
        int levelMod = parseInt(getElement("levelMod", eElement));
        int plrHealth = parseInt(getElement("plrHealth", eElement));
        int plrBudget = parseInt(getElement("plrBudget", eElement));
        int plrLevel = parseInt(getElement("plrLevel", eElement));
        int plrScore = parseInt(getElement("plrScore", eElement));
        String allowedenemies = getElement("allowedenemies", eElement);
        boolean profile = parseBoolean(profiles);
        boolean tutorial = parseBoolean(tutorials);
        Vector3f camLocation = parseVector3f(camlocS);
        ColorRGBA backgroundcolor = parseColorRGBA(colors);
        PlayerParams pp = createPlayerParams(plrHealth, plrBudget, plrLevel, plrScore);
        LevelParams lp = createLevelParams(camLocation, numCreeps, creepMod , levelCap, levelMod, profile, tutorial, allowedenemies);
        MaterialParams mp = createMaterialParams(backgroundcolor, matdir);
        // TODO: Implement PlayerParams in GameplayParams
        return new GameplayParams(pp, mp, lp);
    }

    public LevelParams createLevelParams(Vector3f _camLocation, int _numCreeps, int _creepMod, 
            int _levelCap, int _levelMod, boolean _profile, boolean _tutorial,
            String _allowedenemies) {
        return new LevelParams(_camLocation, _numCreeps, _creepMod, _levelCap,
                _levelMod, _profile, _tutorial, _allowedenemies);
    }
    
    public PlayerParams createPlayerParams(int _plrHealth, int _plrBudget, int _plrLevel, int _plrScore) {
        return new PlayerParams(_plrHealth,_plrBudget,_plrLevel,_plrScore);
    }
    
    public MaterialParams createMaterialParams(ColorRGBA _backgroundcolor, String _matdir) {
        return new MaterialParams(_backgroundcolor, _matdir);
    }


    public FilterParams getFilterParams() {
        Element eElement = (Element) pList.item(0);
        String blooms = getElement("bloom", eElement);
        boolean bloom;
        if (blooms.equals("false")) {
            bloom = false;
        } else {
            bloom = true;
        }
        float downsampling = parseFloat(getElement("downsampling", eElement));
        float blurscale = parseFloat(getElement("blurscale", eElement));
        float exposurepower = parseFloat(getElement("exposurepower", eElement));
        float bloomintensity = parseFloat(getElement("bloomintensity", eElement));
        String glowmodes = getElement("glowmode", eElement);
        GlowMode glowmode = parseGlowMode(glowmodes);
        return new FilterParams(bloom, downsampling, blurscale, 
                exposurepower, bloomintensity, glowmode);
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