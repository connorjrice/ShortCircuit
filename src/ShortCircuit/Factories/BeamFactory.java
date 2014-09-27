package ShortCircuit.Factories;

import ShortCircuit.States.Game.GraphicsState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;

/**
 *
 * @author Connor Rice
 */
public class BeamFactory {

    private Line beaml;
    private Geometry beamg;
    private GraphicsState gs;
    private AssetManager assetManager;

    public BeamFactory(GraphicsState gs) {
        this.gs = gs;
        this.assetManager = gs.getAssetManager();
    }

    public Geometry makeLaserBeam(Vector3f origin, Vector3f target,
            String towertype, float beamWidth) {
        beaml = new Line(origin, target);
        beaml.setLineWidth(beamWidth);
        beamg = new Geometry("Beam", beaml);
        beamg.setMaterial(assetManager.loadMaterial(
                gs.getMatLoc(towertype+"Beam")));
        return beamg;
    }
}
