package ShortCircuit.Tower.Objects.Loading;

import ShortCircuit.Tower.MapXML.Objects.CreepSpawnerParams;
import ShortCircuit.Tower.MapXML.Objects.FilterParams;
import ShortCircuit.Tower.MapXML.Objects.GeometryParams;
import ShortCircuit.Tower.MapXML.Objects.MaterialParams;
import ShortCircuit.Tower.MapXML.Objects.TowerParams;
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
