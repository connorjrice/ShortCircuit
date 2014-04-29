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
public class GameplayParams {

    private PlayerParams pp;
    private MaterialParams mp;
    private LevelParams lp;

    public GameplayParams(PlayerParams _pp, MaterialParams _mp, LevelParams _lp) {
        pp = _pp;
        mp = _mp;
        lp = _lp;
    }



    public int getPlrHealth() {
        return pp.getHealth();
    }

    public int getPlrBudget() {
        return pp.getBudget();
    }

    public int getPlrLevel() {
        return pp.getLevel();
    }

    public int getPlrScore() {
        return pp.getScore();
    }
    
    public ColorRGBA getBackgroundColor() {
        return mp.getBackgroundColor();
    }
    
    public String getMatDir() {
        return mp.getMatDir();
    }
    
    public Vector3f getCamLocation() {
        return lp.getCamLocation();
    }

    public int getNumCreeps() {
        return lp.getNumCreeps();
    }

    public int getCreepMod() {
        return lp.getCreepMod();
    }

    public int getLevelCap() {
        return lp.getLevelCap();
    }

    public int getLevelMod() {
        return lp.getLevelMod();
    }
    
    public boolean getProfile() {
        return lp.getProfile();
    }
    
    public boolean getTutorial() {
        return lp.getTutorial();
    }
    
    public String getAllowedEnemies() {
        return lp.getAllowedEnemies();
    }
}
