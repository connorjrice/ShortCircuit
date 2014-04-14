package ShortCircuit.Tower.Objects;

import com.jme3.math.Vector3f;

/**
 * CreepTraits holds the information about a given creep, including:
 * Health, Size, Speed, Type, SpawnIndex
 * URGENT: Create CreepSpawner object with direction that is fed in from
 * MapGenerator
 * @author Connor Rice
 */
public class CreepTraits {
    
    public String name;
    public int health;
    public int spawnIndex;
    public Vector3f size;
    public float speed;
    public String type;
    public Vector3f spawnPoint;
    public String matloc;
    private int value;
    
    public CreepTraits(String _name, int _health, int _spawnIndex, Vector3f _spawnPoint,
            Vector3f _size, float _speed, int _value, String _texloc) {
        name = _name;
        health = _health;
        spawnIndex = _spawnIndex;
        size = _size;
        spawnPoint = _spawnPoint;
        speed = _speed;
        value = _value;
        matloc = _texloc;
    }

    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getSpawnIndex() {
        return spawnIndex;
    }
    
    public Vector3f getSpawnPoint() {
        return spawnPoint;
    }
    
    public Vector3f getSize() {
        return size;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getMaterialLocation() {
        return matloc;
    }

}
