package ScSDK.MapXML;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.File;
import java.io.InputStream;

/**
 * @author Connor Rice
 */
public class FileLoader implements AssetLoader {   

    public File load(AssetInfo assetInfo) {
        return createFileFromStream(assetInfo.openStream()) ;
    }
    
    public static File createFileFromStream(InputStream stream) {
        return new File(stream.toString());
    }
 

}
