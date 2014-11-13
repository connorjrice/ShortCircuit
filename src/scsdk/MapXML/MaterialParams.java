package ScSDK.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.ColorRGBA;
import java.io.IOException;

/**
 *
 * @author Development
 */
public class MaterialParams implements Savable {

    private ColorRGBA backgroundcolor;
    private String matdir;
    
    public MaterialParams() {}

    public MaterialParams(ColorRGBA _backgroundcolor, String _matdir) {
        backgroundcolor = _backgroundcolor;
        matdir = _matdir;
    }

    public ColorRGBA getBackgroundColor() {
        return backgroundcolor;
    }

    public String getMatDir() {
        return matdir;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        backgroundcolor = (ColorRGBA) in.readSavable("bgcolor",
                new ColorRGBA());
        matdir = in.readString("matdir", "");
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(backgroundcolor, "bgcolor", new ColorRGBA());
        out.write(matdir, "matdir", "");
    }
}
