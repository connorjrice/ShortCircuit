package ShortCircuit.MapXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
    private ArrayList<CreepParams> creepList;
    private HashMap<String, CreepParams> creepMap;
    
    public GraphicsParams(MaterialParams mp, FilterParams fp, GeometryParams gp, 
            ArrayList<TowerParams> towerList, ArrayList<CreepSpawnerParams> creepSpawnerList,
            String[] towerTypes, ArrayList<CreepParams> creepList) {
        this.mp = mp;
        this.fp = fp;
        this.gp = gp;
        this.towerList = towerList;
        this.creepSpawnerList = creepSpawnerList;
        this.towerTypes = towerTypes;
        this.creepList = creepList;
        this.creepMap = new HashMap<String, CreepParams>();
        parseCreeps();
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
    

    
    
    private void parseCreeps() {
        for (CreepParams curCreep : creepList) {
            creepMap.put(curCreep.getType(), curCreep);
        }
    }
    
    public HashMap getCreepMap() {
        return creepMap;
    }
    
    public CreepParams getCreepParams(String type) {
        return creepMap.get(type);
    }
    
    public Set<String> getCreepTypes() {
        return creepMap.keySet();
    }
    
    
    
}
