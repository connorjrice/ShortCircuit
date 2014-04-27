package ShortCircuit.Tower.MapXML;

import ShortCircuit.Tower.Objects.FilterParams;
import ShortCircuit.Tower.Objects.LevelParams;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
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
            unbuiltVecs.add(new Vector3f(Float.parseFloat(eElement.getElementsByTagName("x")
                    .item(0).getTextContent()),
                    Float.parseFloat(eElement.getElementsByTagName("y").item(0).getTextContent()),
                    Float.parseFloat(eElement.getElementsByTagName("z").item(0).getTextContent())));
        }
        return unbuiltVecs;

    }

    public ArrayList<Vector3f> getCreepSpawnVecs() {
        ArrayList<Vector3f> creepSpawnVecs = new ArrayList<Vector3f>();
        for (int i = 0; i < cList.getLength(); i++) {
            Element eElement = (Element) cList.item(i);
            creepSpawnVecs.add(new Vector3f(Float.parseFloat(eElement.getElementsByTagName("x")
                    .item(0).getTextContent()),
                    Float.parseFloat(eElement.getElementsByTagName("y").item(0).getTextContent()),
                    Float.parseFloat(eElement.getElementsByTagName("z").item(0).getTextContent())));
        }
        return creepSpawnVecs;

    }

    public ArrayList<String> getCreepSpawnDirs() {
        ArrayList<String> creepSpawnDirs = new ArrayList<String>();
        for (int i = 0; i < cList.getLength(); i++) {
            Element eElement = (Element) cList.item(i);
            creepSpawnDirs.add(eElement.getElementsByTagName("orientation").item(0).getTextContent());
        }
        return creepSpawnDirs;

    }

    public Vector3f getBaseVec() {
        Element eElement = (Element) bList.item(0);
        return new Vector3f(Float.parseFloat(eElement.getElementsByTagName("x")
                .item(0).getTextContent()),
                Float.parseFloat(eElement.getElementsByTagName("y").item(0).getTextContent()),
                Float.parseFloat(eElement.getElementsByTagName("z").item(0).getTextContent()));
    }

    public Vector3f getBaseScale() {
        Element eElement = (Element) bList.item(0);
        return new Vector3f(Float.parseFloat(eElement.getElementsByTagName("x")
                .item(1).getTextContent()),
                Float.parseFloat(eElement.getElementsByTagName("y").item(1).getTextContent()),
                Float.parseFloat(eElement.getElementsByTagName("z").item(1).getTextContent()));
    }

    public Vector3f getFloorScale() {
        Element eElement = (Element) fList.item(0);
        return new Vector3f(Float.parseFloat(eElement.getElementsByTagName("x")
                .item(0).getTextContent()),
                Float.parseFloat(eElement.getElementsByTagName("y").item(0).getTextContent()),
                Float.parseFloat(eElement.getElementsByTagName("z").item(0).getTextContent()));
    }

    public ArrayList<Integer> getStarterTowers() {
        ArrayList<Integer> starterTowers = new ArrayList<Integer>();
        for (int i = 0; i < tList.getLength(); i++) {
            Element eElement = (Element) tList.item(i);
            if (eElement.getElementsByTagName("isStarter").item(0).getTextContent().equals("true")) {
                starterTowers.add(Integer.parseInt(eElement.getAttribute("id")));
            }
        }
        return starterTowers;
    }

    public LevelParams getLevelParams() {  //throws LevelParseException {
        Element eElement = (Element) pList.item(0);
        String camlocS = eElement.getElementsByTagName("camLocation").item(0).getTextContent();
        String[] camlocF = camlocS.split(",");
        Vector3f camLocation = new Vector3f(Float.parseFloat(camlocF[0]), Float.parseFloat(camlocF[1]), Float.parseFloat(camlocF[2]));
        int numCreeps = Integer.parseInt(eElement.getElementsByTagName("numCreeps").item(0).getTextContent());
        int creepMod = Integer.parseInt(eElement.getElementsByTagName("creepMod").item(0).getTextContent());
        int levelCap = Integer.parseInt(eElement.getElementsByTagName("levelCap").item(0).getTextContent());
        int levelMod = Integer.parseInt(eElement.getElementsByTagName("levelMod").item(0).getTextContent());
        int plrHealth = Integer.parseInt(eElement.getElementsByTagName("plrHealth").item(0).getTextContent());
        int plrBudget = Integer.parseInt(eElement.getElementsByTagName("plrBudget").item(0).getTextContent());
        int plrLevel = Integer.parseInt(eElement.getElementsByTagName("plrLevel").item(0).getTextContent());
        int plrScore = Integer.parseInt(eElement.getElementsByTagName("plrScore").item(0).getTextContent());
        String debugs = eElement.getElementsByTagName("debug").item(0).getTextContent();
        String matdir = eElement.getElementsByTagName("matdir").item(0).getTextContent();
        String tutorials = eElement.getElementsByTagName("tutorial").item(0).getTextContent();
        boolean debug;
        if (debugs.equals("true")) {
            debug = true;
        } else {
            debug = false;
        }
        boolean tutorial;
        if (tutorials.equals("true")) {
            tutorial = true;
        } else {
            tutorial = false;
        }
        int allowedenemies = Integer.parseInt(eElement.getElementsByTagName("allowedenemies").item(0).getTextContent());

        return new LevelParams(camLocation, numCreeps, creepMod, levelCap, 
                levelMod, plrHealth, plrBudget, plrLevel, plrScore, debug,
                matdir, tutorial, allowedenemies);
    }

    public FilterParams getFilterParams() {
        Element eElement = (Element) pList.item(0);
        String blooms = eElement.getElementsByTagName("bloom").item(0).getTextContent();
        boolean bloom;
        if (blooms.equals("false")) {
            bloom = false;
        } else {
            bloom = true;
        }
        float downsampling = Float.parseFloat(eElement.getElementsByTagName("downsampling").item(0).getTextContent());
        float blurscale = Float.parseFloat(eElement.getElementsByTagName("blurscale").item(0).getTextContent());
        float exposurepower = Float.parseFloat(eElement.getElementsByTagName("exposurepower").item(0).getTextContent());
        float bloomintensity = Float.parseFloat(eElement.getElementsByTagName("bloomintensity").item(0).getTextContent());
        String glowmodes = eElement.getElementsByTagName("glowmode").item(0).getTextContent();
        GlowMode glowmode;
        if (glowmodes.equals("GlowMode.SceneAndObjects")) {
            glowmode = GlowMode.SceneAndObjects;
        } else if (glowmodes.equals("GlowMode.Scene")) {
            glowmode = GlowMode.Scene;
        } else if (glowmodes.equals("GlowMode.Objects")) {
            glowmode = GlowMode.Objects;
        } else {
            glowmode = null;
        }
        return new FilterParams(bloom, downsampling, blurscale, exposurepower, bloomintensity, glowmode);
    }
}