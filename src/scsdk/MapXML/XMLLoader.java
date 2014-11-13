package ScSDK.MapXML;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * @author Connor Rice
 */
public class XMLLoader implements AssetLoader {   

    public Document load(AssetInfo assetInfo) {
        return createDocFromStream(assetInfo.openStream());
    }
 
    public static Document createDocFromStream(InputStream inputStream) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            return builder.parse(inputStream);
        } catch (Exception ex) {
            return null;
        }
    }
}
