package ShortCircuit.MapXML;

import ShortCircuit.Objects.LevelParams;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.util.ArrayList;

/**
 * TODO: Document TODO: Creep spawner direction specification from within XML
 *
 * @author Connor Rice
 */
public class MapGenerator {

    private String levelName;
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
        levelName = level;
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
            creepSpawnDirs.add(eElement.getElementsByTagName("direction").item(0).getTextContent());
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
        System.out.println(matdir);
        boolean debug;
        if (debugs.equals("true")) {
            debug = true;
        } else {
            debug = false;
        }
        return new LevelParams(numCreeps, creepMod, levelCap, levelMod, plrHealth, plrBudget, plrLevel, plrScore, debug, matdir);
    }
}