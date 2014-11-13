package ScSDK.MapXML;

import com.jme3.math.Vector3f;
import com.jme3.post.filters.BloomFilter.GlowMode;
import java.util.ArrayList;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;

/**
 *
 * @author Development
 */
public interface Parser {
    
    public Vector3f parseVector3f(String vec);
    
    public Vector3f parseVector3f(String x, String y, String z);
    
    public int parseInt(String s);
    
    public float parseFloat(String s);
    
    public GlowMode parseGlowMode(String s);
    
    public String[] parseCreepTypes(String s);
    
    public ArrayList<CreepSpawnerParams> parseCreepSpawnerList();
    
    public ArrayList<CreepParams> parseCreepList();
    
    public String getElement(String value, String parentNode);
    
    public String getExpression(String value, String parentNode);
    
    public String getValue(String expression);
    
}
