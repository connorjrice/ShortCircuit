package ShortCircuit.Tower.Objects.Loading;

import ShortCircuit.Tower.MapXML.Objects.BaseParams;
import ShortCircuit.Tower.MapXML.Objects.FilterParams;
import ShortCircuit.Tower.MapXML.Objects.GeometryParams;
import ShortCircuit.Tower.MapXML.Objects.MaterialParams;

/**
 *
 * @author Development
 */
public class GraphicsParams {
    private MaterialParams mp;
    private FilterParams fp;
    private GeometryParams gp;
    private BaseParams bp;
    
    public GraphicsParams(MaterialParams mp, FilterParams fp, GeometryParams gp,
            BaseParams bp) {
        this.mp = mp;
        this.fp = fp;
        this.gp = gp;
        this.bp = bp;

    }
    
    public FilterParams getFilterParams() {
        return fp;
    }
    
    public MaterialParams getMaterialParams() {
        return mp;
    }
    
        public GeometryParams getGeometryParams() {
        return gp;
    }
    
    public BaseParams getBaseParams() {
        return bp;
    }
    
    
    
}
