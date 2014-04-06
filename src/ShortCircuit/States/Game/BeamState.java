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
    private Node rootNode;
    private AssetManager assetManager;
    private float beamwidth = 2.0f;
    private Node worldNode;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.worldNode = this.app.getStateManager().getState(GameState.class).getWorldNode();
        this.assetManager = this.app.getAssetManager();
        initAssets();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (isEnabled()) {
            if (updateTimer >= .15) {
                beamNode.detachAllChildren();
                updateTimer = 0;
            } else {
                updateTimer += tpf;
            }
        }
    }
    
    /**
     * Builds all of the different beams.
     */
    public void initAssets() {
        beam_matRed = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        beam_matRed.setColor("Color", ColorRGBA.Red);
        beam_matPink = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        beam_matPink.setColor("Color", ColorRGBA.Pink);
        beam_matGreen = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        beam_matGreen.setColor("Color", ColorRGBA.Green);
        beam_matPurple = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        beam_matPurple.setColor("Color", colorPurple);
    }

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
            beamg.setMaterial(beam_matRed);
            beamNode.attachChild(beamg);
        } else if (type.equals("pinkLaser")) {
            beaml = new Line(origin, target);
            beaml.setLineWidth(beamwidth);
            beamg = new Geometry("Beam", beaml);
            beamg.setMaterial(beam_matPink);
            beamNode.attachChild(beamg);
        } else if (type.equals("greenLaser")) {
            beaml = new Line(origin, target);
            beaml.setLineWidth(beamwidth);
            beamg = new Geometry("Beam", beaml);
            beamg.setMaterial(beam_matGreen);
            beamNode.attachChild(beamg);

        } else if (type.equals("purpleLaser")) {
            beaml = new Line(origin, target);
            beaml.setLineWidth(beamwidth);
            beamg = new Geometry("Beam", beaml);
            beamg.setMaterial(beam_matPurple);
            beamNode.attachChild(beamg);
        } else if (type.equals("baseLaser")) {
            beaml = new Line(origin, target);
            beaml.setLineWidth(beamwidth);
            beamg = new Geometry("Beam", beaml);
            beamg.setMaterial(beam_matPurple);
            beamNode.attachChild(beamg);
        }
    }



    @Override
    public void cleanup() {
        super.cleanup();

    }
}
