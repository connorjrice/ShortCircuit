/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.unimplemented;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Development
 */
public class RangerState {
    private ArrayList<Spatial> rangerList;
    private RangerFactory rf;
    private Random random = new Random();
    private Node creepNode = new Node("creepNode");
    
    private void initFactories() {
        //rf = new RangerFactory();
    }
    
    /**
     * TODO: Algorithm for determining where rangers should spawn.
     * @param towerVictimIndex
     * @return
     */
    private Vector3f getRangerSpawnPoint(int towerVictimIndex) {
        return new Vector3f();
    }
    
    private void spawnRanger() {
        /*int towerVictimIndex = random.nextInt(FriendlyState.getTowerList().size());
        Spatial ranger = rf.getRanger(getRangerSpawnPoint(towerVictimIndex),
                towerVictimIndex);
        rangerList.add(ranger);
        creepNode.attachChild(ranger);*/
    }
    
    public Geometry getRangerGeom() {
        return new Geometry("Ranger", new Box(1, 1, 1));
    }
    
}
