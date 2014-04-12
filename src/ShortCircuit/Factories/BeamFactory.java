/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Factories;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;

/**
 *
 * @author Connor
 */
public class BeamFactory {
    private AssetManager assetManager;
    private Line beaml;
    private Geometry beamg;
    
    public BeamFactory(AssetManager _assetManager) {
        assetManager = _assetManager;
    }
    
    public Geometry makeLaserBeam(Vector3f origin, Vector3f target, String beamtype, float beamWidth) {
        beaml = new Line(origin, target);
        beaml.setLineWidth(beamWidth);
        beamg = new Geometry("Beam", beaml);
        beamg.setMaterial(assetManager.loadMaterial("Materials/"+beamtype+".j3m"));
        return beamg;
    }
}
