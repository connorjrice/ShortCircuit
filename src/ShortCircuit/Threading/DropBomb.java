package ShortCircuit.Threading;

import ShortCircuit.Controls.BombControl;
import ShortCircuit.States.Game.GameState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author Connor Rice
 */
public class DropBomb implements Runnable {
    private Vector3f translation;
    private GameState gs;
    
    public DropBomb(Vector3f trans, GameState _gs) {
        translation = trans;
        gs = _gs;
    }
    
    public void run() {
        Geometry bomb_geom = new Geometry("Bomb", gs.getBombMesh());
        bomb_geom.setMaterial(gs.getAssetManager().loadMaterial("Materials/Bomb.j3m"));
        bomb_geom.setLocalScale(.1f);
        bomb_geom.setLocalTranslation(translation);
        bomb_geom.addControl(new BombControl(.1f, gs));
        gs.getWorldNode().attachChild(bomb_geom);
    }
    
}
