package ShortCircuit.Tower.Objects.Loading;

import ShortCircuit.Tower.MapXML.Objects.CreepParams;
import ShortCircuit.Tower.MapXML.Objects.CreepSpawnerParams;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class will take in all of the different creepparam objects and return
 * them to the LoadingState.
 *
 * @author Connor Rice
 */
public class EnemyParams {

    private ArrayList<CreepParams> creepList;
    private ArrayList<CreepSpawnerParams> creepSpawnerList;
    private HashMap<String, CreepParams> creepMap;
    private HashMap<String, CreepSpawnerParams> creepSpawnerMap;


    public EnemyParams(ArrayList<CreepParams> creepList, ArrayList<CreepSpawnerParams> creepSpawnerList) {
        this.creepList = creepList;
        this.creepMap = new HashMap<String, CreepParams>();
        this.creepSpawnerList = creepSpawnerList;
        this.creepSpawnerMap = new HashMap<String, CreepSpawnerParams>();
        parseCreeps();
    }
    
    private void parseCreeps() {
        for(CreepParams curCreep : creepList) {
            creepMap.put(curCreep.getType(), curCreep);
        }
    }
    
    public CreepParams getCreepParams(String type) {
        return creepMap.get(type);
    }
    
    public Set<String> getCreepTypes() {
        return creepMap.keySet();
    }
    
    public ArrayList<Vector3f> getCreepSpawnerVecs() {
        return null;
    }
}
