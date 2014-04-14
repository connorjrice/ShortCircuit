package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Factories.BeamFactory;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * This state controls the firing of beams, and the sounds that they make.
 * @author Connor Rice
 */
public class BeamState extends AbstractAppState {
    
    public Node beamNode = new Node("Beams");
    private float updateTimer = 0.0f;
    private AudioNode beamAudio;
    private AssetManager assetManager;
    private boolean shot = false;
    private BeamFactory bf;

    public BeamState() {}

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        app.getStateManager().getState(GameState.class)
                .getWorldNode().attachChild(beamNode);
        this.assetManager = app.getAssetManager();
        bf = new BeamFactory(app.getStateManager().getState(GameState.class));
    }

    /**
     * Removes all beams every .15 seconds.
     * @param tpf 
     */
    @Override
    public void update(float tpf) {
        super.update(tpf);
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
    
    public void makeLaserBeam(Vector3f origin, Vector3f target, String towertype, String beamtype, float beamwidth) {
        beamAudio = new AudioNode(assetManager, "Audio/"+towertype+".wav");
        beamAudio.setVolume(.3f);
        beamAudio.setLocalTranslation(origin);
        beamAudio.playInstance();
        beamNode.attachChild(bf.makeLaserBeam(origin, target, beamtype, beamwidth));
        shot = true;
    }


    @Override
    public void cleanup() {
        super.cleanup();
        beamNode.detachAllChildren();
    }
}
