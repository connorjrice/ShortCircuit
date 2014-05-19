package ShortCircuit.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import java.io.IOException;

/**
 * This class will contain the creep paramaters per level, including:
 * Speed, size, shape, health, value
 * TODO: Implement CreepParams
 * @author Connor
 */
public class CreepParams implements Savable {
    private final int health;
    private final float speed;
    private final Vector3f size;
    private final String type;
    private final int value;

    public CreepParams(int health, float speed, Vector3f size, String type, int value) {
        this.health = health;
        this.speed = speed;
        this.size = size;
        this.type = type;
        this.value = value;
    }
    
    public int getHealth() {
        return health;
    }
    
    public float getSpeed() {
        return speed * .00001f;
    }
    
    public Vector3f getSize() {
        return size;
    }
    
    public String getType() {
        return type;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
    }
    
    
}
