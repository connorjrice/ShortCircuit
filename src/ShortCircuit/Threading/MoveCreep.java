package ShortCircuit.Threading;

import ShortCircuit.Controls.RegCreepControl;
import ShortCircuit.DataStructures.Interfaces.PathFinder;
import ShortCircuit.DataStructures.Nodes.GraphNode;
import ShortCircuit.DataStructures.Objects.Path;
import ShortCircuit.States.Game.EnemyState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;

/**
 * Runnable for moving creeps.
 * @author clarence
 */
public class MoveCreep implements Runnable {

    private EnemyState es;
    private RegCreepControl cc;


    public MoveCreep(EnemyState es, RegCreepControl cc) {
        this.es = es;
        this.cc = cc;

        System.out.println(es.getFormattedBaseCoords());
       // moveamount = .004f;
        ///TODO: Make moveamount something more modifiable.
    }

    public void run() {

    }
}
