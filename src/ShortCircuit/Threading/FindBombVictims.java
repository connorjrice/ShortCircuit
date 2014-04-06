package ShortCircuit.Threading;

import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 * TODO: This isn't actually threaded. Make it so.
 * @author Connor Rice
 */
public class FindBombVictims  {
    
    public ArrayList<Spatial> creepVictims;
    
    public FindBombVictims(ArrayList<Spatial> _creepVictims) {
        creepVictims = _creepVictims;
    }
    
    public ArrayList<Spatial> getCreepVictims() {
        return creepVictims;
    }
    
}
