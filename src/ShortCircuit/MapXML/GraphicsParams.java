package ShortCircuit.MapXML;

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
    private String[] towerTypes;
    
    public GraphicsParams(MaterialParams mp, FilterParams fp, GeometryParams gp, 
            ArrayList<TowerParams> towerList, ArrayList<CreepSpawnerParams> creepSpawnerList,
            String[] towerTypes) {
        this.mp = mp;
        this.fp = fp;
        this.gp = gp;
        this.towerList = towerList;
        this.creepSpawnerList = creepSpawnerList;
        this.towerTypes = towerTypes;
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
    
    public String[] getTowerTypes() {
        return towerTypes;
    }
    
    public ArrayList<CreepSpawnerParams> getCreepSpawnerList() {
        return creepSpawnerList;
    }
    
    
    
}
