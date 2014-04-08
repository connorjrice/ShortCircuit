package ShortCircuit.States.Game;

import ShortCircuit.Controls.BombControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 * Does the bomb dropping.
 * @author Connor Rice
 */
public class BombState extends AbstractAppState {
    private SimpleApplication app;
    private GameState gs;
    private Node worldNode;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.gs = this.app.getStateManager().getState(GameState.class);
        this.worldNode = this.gs.getWorldNode();
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    protected void drawBomb(Vector3f translation) {
        Geometry bomb_geom = new Geometry("Bomb", gs.getBombMesh());
        bomb_geom.setMaterial(gs.getAssetManager().loadMaterial("Materials/Bomb.j3m"));
        bomb_geom.setLocalScale(.1f);
        bomb_geom.setLocalTranslation(translation);
        bomb_geom.addControl(new BombControl(.1f, gs));
        worldNode.attachChild(bomb_geom);
    }
    
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
}
