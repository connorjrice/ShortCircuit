package ShortCircuit.Tower.Objects;

import com.jme3.math.ColorRGBA;

/**
 *
 * @author Development
 */
public class MaterialParams {
    private ColorRGBA backgroundcolor;
    private String matdir;
    
    
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

    
}
