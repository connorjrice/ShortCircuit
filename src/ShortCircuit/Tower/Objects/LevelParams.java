package ShortCircuit.Tower.Objects;

import com.jme3.math.Vector3f;

/**
 *
 * @author Connor Rice
 */
public class LevelParams {
    private Vector3f camLocation;
    private int numCreeps;
    private int creepMod;
    private int levelCap;
    private int levelMod;
    private boolean profile;
    private boolean tutorial;
    
    /**
     * Allowed Enemies
     * 0123456
     * 0 = small creep
     * 1 = medium creep
     * 2 = large creep
     * 3 = giant creep
     * 4 = glob
     * 5 = ranger
     * 6 = digger
     * Binary system, so sm-large is
     * 111000
     */
    private String allowedenemies;
    
    public LevelParams(Vector3f _camLocation, int _numCreeps, int _creepMod, 
            int _levelCap, int _levelMod, boolean _profile, boolean _tutorial,
            String _allowedenemies) {
        camLocation = _camLocation;
        numCreeps = _numCreeps;
        creepMod = _creepMod;
        levelCap = _levelCap;
        levelMod = _levelMod;
        profile = _profile;
        tutorial = _tutorial;
        allowedenemies = _allowedenemies;
    }
    
    public Vector3f getCamLocation() {
        return camLocation;
    }

    public int getNumCreeps() {
        return numCreeps;
    }

    public int getCreepMod() {
        return creepMod;
    }

    public int getLevelCap() {
        return levelCap;
    }

    public int getLevelMod() {
        return levelMod;
    }
    
    public boolean getProfile() {
        return profile;
    }
    
    public boolean getTutorial() {
        return tutorial;
    }
    
    public String getAllowedEnemies() {
        return allowedenemies;
    }
    
    
    
}
