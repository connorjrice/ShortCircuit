package ShortCircuit.States.Game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
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
    private float updateTimer = 0.0f;
    private SimpleApplication app;
    private AssetManager assetManager;
    private float beamwidth = 6.0f;
    private Node worldNode;
    private boolean shot = false;
    private AudioNode tower1audio;
    private AudioNode tower2audio;
    private AudioNode tower3audio;
    private AudioNode tower4audio;
    
    public BeamState() {}

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.worldNode = this.app.getStateManager().getState(GameState.class).getWorldNode();
        this.assetManager = this.app.getAssetManager();
        initAudio();
    }
    
    private void initAudio() {
        tower1audio = new AudioNode(assetManager, "Audio/tower1.wav");
        tower1audio.setVolume(.4f);
        tower2audio = new AudioNode(assetManager, "Audio/tower2.wav");
        tower2audio.setVolume(.4f);
        tower3audio = new AudioNode(assetManager, "Audio/tower3.wav");
        tower3audio.setVolume(.4f);
        tower4audio = new AudioNode(assetManager, "Audio/tower4.wav");
        tower4audio.setVolume(.4f);

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
        beaml = new Line(origin, target);
        beaml.setLineWidth(beamwidth);
        beamg = new Geometry("Beam", beaml);
        if (type.equals("tower1")) {
            beamg.setMaterial(assetManager.loadMaterial("Materials/beam1.j3m"));
            tower1audio.setLocalTranslation(origin);
            tower1audio.playInstance();
        } else if (type.equals("tower2")) {
            beamg.setMaterial(assetManager.loadMaterial("Materials/beam2.j3m"));
            tower2audio.setLocalTranslation(origin);
            tower2audio.playInstance();
        } else if (type.equals("tower3")) {
            beamg.setMaterial(assetManager.loadMaterial("Materials/beam3.j3m"));
            tower3audio.setLocalTranslation(origin);
            tower3audio.playInstance();
        } else if (type.equals("tower4")) {
            beamg.setMaterial(assetManager.loadMaterial("Materials/beam4.j3m"));
            tower4audio.setLocalTranslation(origin);
            tower4audio.playInstance();
        }
        beamNode.attachChild(beamg);
        shot = true;
    }
    
    



    @Override
    public void cleanup() {
        super.cleanup();
        beamNode.detachAllChildren();
    }
}
