package ShortCircuit.Tower.MapXML;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.InputStream;
import org.xml.sax.InputSource;

/**
 * @author Connor Rice
 */
public class XMLLoader implements AssetLoader {   

    public InputSource load(AssetInfo assetInfo) {
        return createSourceFromStream(assetInfo.openStream());
    }
 
    public static InputSource createSourceFromStream(InputStream inputStream) {
        try {
            return new InputSource(inputStream);
        } catch (Exception ex) {
            return null;
        }
    }
}
