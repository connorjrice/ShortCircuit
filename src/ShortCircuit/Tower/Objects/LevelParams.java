package ShortCircuit.Tower.Objects;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * This object contains all of the level parameters as specified by the XML file
 * for the level. It is created by MapGenerator, and used by GameState, which
 * gets the file from LevelState.
 *
 * @author Connor Rice
 */
public class LevelParams {

    private Vector3f camLocation;
    private int numCreeps;
    private int creepMod;
    private int levelCap;
    private int levelMod;
    private int plrHealth;
    private int plrBudget;
    private int plrLevel;
    private int plrScore;
    private boolean debug;
    private String matdir;
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
    private final ColorRGBA backgroundcolor;
    
    
    // TODO: array of ints, accessor methods at the right index

    public LevelParams(Vector3f _camLocation, int _numCreeps, int _creepMod, int _levelCap, int _levelMod, int _plrHealth,
            int _plrBudget, int _plrLevel, int _plrScore, boolean _debug, String _matdir, boolean _tutorial, 
            String _allowedenemies, ColorRGBA _backgroundcolor) {
        camLocation = _camLocation;
        numCreeps = _numCreeps;
        creepMod = _creepMod;
        levelCap = _levelCap;
        levelMod = _levelMod;
        plrHealth = _plrHealth;
        plrBudget = _plrBudget;
        plrLevel = _plrLevel;
        plrScore = _plrScore;
        debug = _debug;
        matdir = _matdir;
        tutorial = _tutorial;
        allowedenemies = _allowedenemies;
        backgroundcolor = _backgroundcolor;

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

    public int getPlrHealth() {
        return plrHealth;
    }

    public int getPlrBudget() {
        return plrBudget;
    }

    public int getPlrLevel() {
        return plrLevel;
    }

    public int getPlrScore() {
        return plrScore;
    }

    public boolean getDebug() {
        return debug;
    }

    public String getMatDir() {
        return matdir;
    }

    public boolean getTutorial() {
        return tutorial;
    }
    
    public String getAllowedEnemiesInt() {
        return allowedenemies;
    }
    
    public ColorRGBA getBackgroundColor() {
        return backgroundcolor;
    }
    
}
