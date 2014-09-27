package ScSDK.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import java.io.IOException;

/**
 * Wrapper for information about creeps.
 * @author Connor
 */
public class CreepParams implements Savable {

    private float speed;
    private Vector3f size;
    private String type;
    private int[] intParams;

    public CreepParams() {
    }

    public CreepParams(int health, float speed, Vector3f size, 
            String type, int value) {
        this.intParams = new int[] {health, value};
        this.speed = speed;
        this.size = size;
        this.type = type;
    }

    public int getHealth() {
        return intParams[0];
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
        return intParams[1];
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        intParams = in.readIntArray("intParams", new int[2]);
        speed = in.readFloat("speed", 0.0f);
        size = (Vector3f) in.readSavable("size", new Vector3f());
        type = in.readString("type", "creep");
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(intParams, "intParams", new int[2]);
        out.write(speed, "speed", 1.0f);
        out.write(size, "size", new Vector3f());
        out.write(type, "type", "");

    }
}
