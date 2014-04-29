package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Factories.BeamFactory;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * This state creates a beam node, is responsible for creating, attaching, and
 * detaching the beam geometry in the world, and calls on AudioState to
 * play the beam sounds.
 * @author Connor Rice
 */
public class BeamState extends AbstractAppState {
    
    public Node beamNode = new Node("Beams");

    private float updateTimer = 0.0f;
    private boolean shot = false;
    private BeamFactory bf;
    private AudioState AudioState;

    public BeamState() {}

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        stateManager.getState(GameState.class)
                .getWorldNode().attachChild(beamNode);
        this.AudioState = stateManager.getState(AudioState.class);
        bf = new BeamFactory(stateManager.getState(GameState.class));

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
        AudioState.beamSound(towertype, origin);
        beamNode.attachChild(bf.makeLaserBeam(origin, target, beamtype, beamwidth));
        shot = true;
    }


    @Override
    public void cleanup() {
        super.cleanup();
        beamNode.detachAllChildren();
    }
}
