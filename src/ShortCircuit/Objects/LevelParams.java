package ShortCircuit.Objects;

/**
 * This object contains all of the level parameters as specified by
 * the XML file for the level. It is created by MapGenerator, and used by
 * GameState, which gets the file from LevelState.
 * @author Connor Rice
 */
public class LevelParams {
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
    
    public LevelParams(int _numCreeps, int _creepMod, int _levelCap, int _levelMod, int _plrHealth,
            int _plrBudget, int _plrLevel, int _plrScore, boolean _debug, String _matdir) {
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
    
}
