package ShortCircuit.MapXML.Objects;

import com.jme3.math.Vector3f;

/**
 * This class will contain the creep paramaters per level, including:
 * Speed, size, shape, health, value
 * TODO: Implement CreepParams
 * @author Connor
 */
public class CreepParams {
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
    
}
