package ShortCircuit.States.Game;

import ShortCircuit.Factories.BeamFactory;
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
 * This state controls the firing of beams, and the sounds that they make.
 * @author Connor Rice
 */
public class BeamState extends AbstractAppState {


    public Node beamNode = new Node("Beams");
    public ArrayList<Spatial> beamList = new ArrayList<Spatial>();
    private float updateTimer = 0.0f;
    private SimpleApplication app;
    private AudioNode beamAudio;
    private AssetManager assetManager;
    private Node worldNode;
    private boolean shot = false;
    private BeamFactory bf;

    
    public BeamState() {}

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.worldNode = this.app.getStateManager().getState(GameState.class).getWorldNode();
        this.assetManager = this.app.getAssetManager();
        bf = new BeamFactory(assetManager);
    }
    


    /**
     * Removes all beams every .15 seconds.
     * TODO: Make it so each beam has a life of .15 seconds
     * @param tpf 
     */
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
    
    public void makeLaserBeam(Vector3f origin, Vector3f target, String towertype, String beamtype, float beamwidth) {
        beamAudio = new AudioNode(assetManager, "Audio/"+towertype+".wav");
        beamAudio.setLocalTranslation(origin);
        beamAudio.playInstance();
        beamNode.attachChild(bf.makeLaserBeam(origin, target, beamtype, beamwidth));
        shot = true;
    }
    
    /**
     * Attaches the beam node to the world node.
     */
    public void attachBeamNode() {
        worldNode.attachChild(beamNode);
    }

    
    /**
     * Clears the beamlist, and removes any stray beams.
     */
    public void reset() {
        beamList.clear();
        beamNode.detachAllChildren();
    }


    @Override
    public void cleanup() {
        super.cleanup();
        beamNode.detachAllChildren();
    }
}
