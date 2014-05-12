package ShortCircuit.MapXML;

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
    private HashMap<String, CreepParams> creepMap;


    public EnemyParams(ArrayList<CreepParams> creepList) {
        this.creepList = creepList;
        this.creepMap = new HashMap<String, CreepParams>();
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
    
}
