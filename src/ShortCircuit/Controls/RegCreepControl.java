package ShortCircuit.Controls;

import ShortCircuit.DataStructures.Graph;
import ShortCircuit.PathFinding.Path;
import ShortCircuit.PathFinding.AStarPathFinder;
import ShortCircuit.PathFinding.jMEHeuristic;
import ShortCircuit.Threading.MoveCreep;
import ShortCircuit.States.Game.EnemyState;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * This is the control class for standard creeps.
 * It has not been decided if specialty creeps will inherit this control.
 * Currently, this control is used by the STD family of creeps.
 *
 * @author Connor Rice
 */
public class RegCreepControl extends AbstractControl implements Savable {

    protected EnemyState EnemyState;
    protected int creepNum;
    public AStarPathFinder pathFinder;
    private float updateTimer = 0;
    public Path path;
    public String baseCoords;
    public Vector3f baseVec;
    private MoveCreep mc;
    private DecimalFormat numFormatter = new DecimalFormat("0.0");

    
    public RegCreepControl(EnemyState _state, AStarPathFinder pathFinder) {
        EnemyState = _state;
        this.pathFinder = pathFinder;
        this.baseCoords = "0,0";
        this.baseVec = new Vector3f(0,0,0);
        this.mc = new MoveCreep(this, baseCoords);
    }
    
    public RegCreepControl() {
        this.mc = new MoveCreep(this, baseCoords);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (updateTimer > .05) {
            mc.run();                  
            updateTimer = 0;
        }  else {
            updateTimer += tpf;
        }
    }
    
    public Graph getWorldGraph() {
        return EnemyState.getWorldGraph();
    }
    
    public String getFormattedCoords() {
        return formatRoundNumber(getX())+","+formatRoundNumber(getY());
    }
    
    public String formatRoundNumber(float f) {
        return numFormatter.format(Math.round(f));
    }
    
    
    private String formatRoundNumber(Float value) {
        return numFormatter.format(Math.round(value));
    }

    
    public float getX() {
        return spatial.getWorldTranslation().x;
    }
    
    public float getY() {
        return spatial.getWorldTranslation().y;
    }
    
    public float getCreepSpeed() {
        return spatial.getUserData("Speed");
    }
    
    public int getCreepHealth() {
        return spatial.getUserData("Health");
    }


    /**
     * Decrements creep health, removes creep from world if below 0.
     * @param damage
     * @return 
     */
    public int decCreepHealth(int damage) {
        int health = getCreepHealth();
        spatial.setUserData("Health", health - damage);
        if (health - damage <= 0) {
            removeCreep(true);
        }
        return health - damage;
    }

    public int getValue() {
        return spatial.getUserData("Value");
    }

    public Vector3f getCreepLocation() {
        return spatial.getLocalTranslation();
    }

    public void removeCreep(boolean wasKilled) {
        EnemyState.creepList.remove(spatial);
        if (wasKilled) {
            EnemyState.incPlrBudget(getValue());
            EnemyState.incPlrScore(1);
        } else {
            EnemyState.decPlrHealth(getValue());
        }
        spatial.removeFromParent();
        spatial.removeControl(this);
        mc = null;
        pathFinder = null;
    }
    

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public RegCreepControl cloneForSpatial(Spatial spatial) {
        RegCreepControl control = new RegCreepControl(EnemyState, pathFinder);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        baseCoords = in.readString("baseCoords", "");
        
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(creepNum, "creepNum", 0);
        out.write(baseCoords, "baseCoords", baseCoords);
        
    }


}
