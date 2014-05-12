/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Factories;

import ShortCircuit.States.Game.GraphicsState;
import com.jme3.scene.Geometry;

/**
 *
 * @author Development
 */
public class FloorFactory {
    private GraphicsState gs;
    
    public FloorFactory(GraphicsState gs) {
        this.gs = gs;
    }
    
    public Geometry getFloor() {
        Geometry floor_geom = new Geometry("Floor", gs.getUnivBox());
        floor_geom.setMaterial(gs.getMaterial("Floor"));
        floor_geom.setLocalScale(gs.getFloorScale());
        return floor_geom;
    }
    
}
