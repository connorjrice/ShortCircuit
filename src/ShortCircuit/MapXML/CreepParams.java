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
    private  int health;
    private float speed;
    private Vector3f size;
    private String type;
    private int value;
    
    public CreepParams() {}

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
        health = in.readInt("health", 100);
        speed = in.readFloat("speed", 1.0f);
        size = (Vector3f) in.readSavable("size", new Vector3f());
        type = in.readString("type", "creep");
        value = in.readInt("value", 100);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(health, "health", health);
        out.write(speed, "speed", speed);
        out.write(size, "size", new Vector3f());
        out.write(type, "type", "");
        out.write(value, "value", value);

    }
    
    
}
