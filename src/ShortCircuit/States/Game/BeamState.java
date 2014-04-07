package ShortCircuit.States.Game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import java.util.ArrayList;

/**
 * TODO: Documentation
 * TODO: Speed up
 * @author Connor Rice
 */
public class BeamState extends AbstractAppState {

    private Line beaml;
    private Geometry beamg;
    public Node beamNode = new Node("Beams");
    public ArrayList<Spatial> beamList = new ArrayList<Spatial>();
    private Material beam_matGreen;
    private Material beam_matPurple;
    private Material beam_matRed;
    private Material beam_matPink;
    private ColorRGBA colorPurple = new ColorRGBA(2.0f, 0.0f, 2.0f, 0);
    private float updateTimer = 0.0f;
    private SimpleApplication app;
    private AssetManager assetManager;
    private float beamwidth = 6.0f;
    private Node worldNode;
    private boolean shot = false;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.worldNode = this.app.getStateManager().getState(GameState.class).getWorldNode();
        this.assetManager = this.app.getAssetManager();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (isEnabled()) {
            if (shot) {
                if (updateTimer >= .15) {
                    beamNode.detachAllChildren();
                    updateTimer = 0;
                    shot = false;
                } else {
                    updateTimer += tpf;
                }
            }
        }
    }
    
    /**
     * Builds all of the different beams.
     */

    public void attachBeamNode() {
        worldNode.attachChild(beamNode);
    }

    public void reset() {
        beamList.clear();
    }

    /**
     * Creates a laser beam from the origin to the target.
     *
     * @param origin
     * @param target
     */
    public void makeLaserBeam(Vector3f origin, Vector3f target, String type) {
        if (type.equals("redLaser")) {
            beaml = new Line(origin, target);
            beaml.setLineWidth(beamwidth);
            beamg = new Geometry("Beam", beaml);
            beamg.setMaterial(assetManager.loadMaterial("Materials/redBeam.j3m"));
            beamNode.attachChild(beamg);
        } else if (type.equals("pinkLaser")) {
            beaml = new Line(origin, target);
            beaml.setLineWidth(beamwidth);
            beamg = new Geometry("Beam", beaml);
            beamg.setMaterial(assetManager.loadMaterial("Materials/orangeBeam.j3m"));
            beamNode.attachChild(beamg);
        } else if (type.equals("greenLaser")) {
            beaml = new Line(origin, target);
            beaml.setLineWidth(beamwidth);
            beamg = new Geometry("Beam", beaml);
            beamg.setMaterial(assetManager.loadMaterial("Materials/greenBeam.j3m"));
            beamNode.attachChild(beamg);

        } else if (type.equals("purpleLaser")) {
            beaml = new Line(origin, target);
            beaml.setLineWidth(beamwidth);
            beamg = new Geometry("Beam", beaml);
            beamg.setMaterial(assetManager.loadMaterial("Materials/purpleBeam.j3m"));
            beamNode.attachChild(beamg);
        }
        shot = true;
    }



    @Override
    public void cleanup() {
        super.cleanup();
        beamNode.detachAllChildren();
    }
}
