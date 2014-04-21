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
    private AudioNode tower1audio;
    private AudioNode tower2audio;
    private AudioNode tower3audio;
    private AudioNode tower4audio;
    private AudioNode elseAudio;
    private float updateTimer = 0.0f;
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
        tower1audio = new AudioNode(assetManager, "Audio/Tower1.wav");
        tower2audio = new AudioNode(assetManager, "Audio/Tower2.wav");
        tower3audio = new AudioNode(assetManager, "Audio/Tower3.wav");
        tower4audio = new AudioNode(assetManager, "Audio/Tower4.wav");
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
        decideSound(towertype, origin);

        beamNode.attachChild(bf.makeLaserBeam(origin, target, beamtype, beamwidth));
        shot = true;
    }
    
    private void decideSound(String towertype, Vector3f origin) {
        if (towertype.equals("Tower1")) {
            tower1audio.setVolume(.3f);
            tower1audio.setLocalTranslation(origin);
            tower1audio.playInstance();
        }
        else if (towertype.equals("Tower2")) {
            tower2audio.setVolume(.3f);
            tower2audio.setLocalTranslation(origin);
            tower2audio.playInstance();
        } else if (towertype.equals("Tower3")) {
            tower3audio.setVolume(.3f);
            tower3audio.setLocalTranslation(origin);
            tower3audio.playInstance();
        } else if (towertype.equals("Tower4")) {
            tower4audio.setVolume(.3f);
            tower4audio.setLocalTranslation(origin);
            tower4audio.playInstance();
        } else {
            elseAudio = new AudioNode(assetManager, "Audio/"+towertype+".wav");
            elseAudio.setVolume(.3f);
            elseAudio.setLocalTranslation(origin);
            elseAudio.playInstance();
        }
        
    }


    @Override
    public void cleanup() {
        super.cleanup();
        beamNode.detachAllChildren();
    }
}
