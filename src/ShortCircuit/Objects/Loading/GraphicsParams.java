package ShortCircuit.Objects.Loading;

import ShortCircuit.MapXML.Objects.CreepSpawnerParams;
import ShortCircuit.MapXML.Objects.FilterParams;
import ShortCircuit.MapXML.Objects.GeometryParams;
import ShortCircuit.MapXML.Objects.MaterialParams;
import ShortCircuit.MapXML.Objects.TowerParams;
import java.util.ArrayList;

/**
 *
 * @author Development
 */
public class GraphicsParams {
    private MaterialParams mp;
    private FilterParams fp;
    private GeometryParams gp;
    private ArrayList<TowerParams> towerList;
    private ArrayList<CreepSpawnerParams> creepSpawnerList;
    
    public GraphicsParams(MaterialParams mp, FilterParams fp, GeometryParams gp, 
            ArrayList<TowerParams> towerList, ArrayList<CreepSpawnerParams> creepSpawnerList) {
        this.mp = mp;
        this.fp = fp;
        this.gp = gp;
        this.towerList = towerList;
        this.creepSpawnerList = creepSpawnerList;
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
    
    public ArrayList<TowerParams> getTowerList() {
        return towerList;
    }
    
    public ArrayList<CreepSpawnerParams> getCreepSpawnerList() {
        return creepSpawnerList;
    }
    
    
    
}
