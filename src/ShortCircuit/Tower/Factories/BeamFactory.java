package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.States.Game.GameState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;

/**
 *
 * @author Connor
 */
public class BeamFactory {
    private Line beaml;
    private Geometry beamg;
    private GameState gs;
    
    public BeamFactory(GameState _gs) {
        gs = _gs;
    }
    
    public Geometry makeLaserBeam(Vector3f origin, Vector3f target, String beamtype, float beamWidth) {
        beaml = new Line(origin, target);
        beaml.setLineWidth(beamWidth);
        beamg = new Geometry("Beam", beaml);
        beamg.setMaterial(gs.getAssetManager().loadMaterial("Materials/"+gs.getMatDir()+"/"+beamtype+".j3m"));
        return beamg;
    }
}
