package ScSDK.MapXML;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.filters.BloomFilter;
import java.util.ArrayList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;

/**
 *
 * @author Development
 */
public class ShortCircuitParser implements Parser {
    private XPath xpath;
    private Document doc;
    
    public ShortCircuitParser(XPath xpath, Document doc) {
        this.xpath = xpath;
        this.doc = doc;
    }
    
    public ArrayList<CreepSpawnerParams> parseCreepSpawnerList() {
        String creepExpression =
                "enemyparams/param[@id = 'creepSpawnerParams']/";
        int numSpawners = parseInt(getElement("numSpawners", creepExpression));
        ArrayList<CreepSpawnerParams> creepSpawnerList = 
                new ArrayList<CreepSpawnerParams>();
        for (int i = 0; i < numSpawners; i++) {
            String curCreepSpawnerExpression = 
                    creepExpression+ "creepSpawner[@id = '"+i+"']/";
            Vector3f vec = parseVector3f(getElement("vec", 
                    curCreepSpawnerExpression));
            String orientation = getElement("orientation", 
                    curCreepSpawnerExpression);
            creepSpawnerList.add(new CreepSpawnerParams(vec, orientation, i));
        }
        return creepSpawnerList;
    }
    
    public ArrayList<CreepParams> parseCreepList() {
        String creepExpression = "enemyparams/param[@id = 'creepParams']/";
        String[] creepTypes = parseCreepTypes(getElement("creepTypes",
                creepExpression));
        ArrayList<CreepParams> creepList = new ArrayList<CreepParams>();
        for (int i = 0; i < creepTypes.length; i++) {
            String curCreepExpression = creepExpression+creepTypes[i]+"Creep/";
            int health = parseInt(getElement("health", curCreepExpression));
            float speed = parseFloat(getElement("speed", curCreepExpression));
            Vector3f size = parseVector3f(getElement("size",
                    curCreepExpression));
            int value = parseInt(getElement("value", curCreepExpression));
            creepList.add(new CreepParams(health, speed, size, creepTypes[i],
                    value));
        }
        return creepList;
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
    
    public BloomFilter.GlowMode parseGlowMode(String s) {
        if (s.equals("GlowMode.SceneAndObjects")) {
            return BloomFilter.GlowMode.SceneAndObjects;
        } else if (s.equals("GlowMode.Scene")) {
            return BloomFilter.GlowMode.Scene;
        } else if (s.equals("GlowMode.Objects")) {
            return BloomFilter.GlowMode.Objects;
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
            return xpath.evaluate(expression, doc);
        } catch (XPathExpressionException ex) {
            System.out.println(ex.getCause());
            return null;
        }
    }

}
