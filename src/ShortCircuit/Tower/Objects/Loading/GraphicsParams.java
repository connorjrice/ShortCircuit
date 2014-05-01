package ShortCircuit.Tower.Objects.Loading;

import ShortCircuit.Tower.MapXML.Objects.FilterParams;
import ShortCircuit.Tower.MapXML.Objects.MaterialParams;

/**
 *
 * @author Development
 */
public class GraphicsParams {
    private MaterialParams mp;
    private FilterParams fp;
    
    public GraphicsParams(MaterialParams mp, FilterParams fp) {
        this.mp = mp;
        this.fp = fp;
    }
    
    public FilterParams getFilterParams() {
        return fp;
    }
    
    public MaterialParams getMaterialParams() {
        return mp;
    }
    
    
    
}
