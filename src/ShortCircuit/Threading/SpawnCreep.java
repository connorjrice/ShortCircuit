package ShortCircuit.Threading;

import ShortCircuit.Objects.CreepTraits;
import ShortCircuit.Factories.CreepFactory;
import ShortCircuit.States.Game.CreepState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 * This class creates a new creep on a new thread.
 * @author clarence
 */
public class SpawnCreep implements Runnable {
    
    private ArrayList<Spatial> creepList;
    private Node creepNode;
    private CreepTraits ct;
    private AssetManager assetManager;
    private CreepState cs;
    
    
    public SpawnCreep(ArrayList<Spatial> _creepList, Node _creepNode, CreepTraits _ct,
            AssetManager _assetManager, CreepState _cs) {
        creepList = _creepList;
        creepNode = _creepNode;
        ct = _ct;
        assetManager = _assetManager;
        cs = _cs;
    }

    public void run() {
        CreepFactory cf = new CreepFactory(ct, assetManager, cs);
        creepList.add(cf.getCreep());
        creepNode.attachChild(cf.getCreep());
    }
    
    
}
