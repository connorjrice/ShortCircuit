package ScSDK.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Connor Rice
 */
public class LevelParams implements Savable {
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
    private HashMap geomHash;
    
    public LevelParams(int _numCreeps, int _creepMod, 
            int _levelCap, int _levelMod, boolean _profile, boolean _tutorial,
            String _allowedenemies, HashMap blockedNodes) {
        numCreeps = _numCreeps;
        creepMod = _creepMod;
        levelCap = _levelCap;
        levelMod = _levelMod;
        profile = _profile;
        tutorial = _tutorial;
        allowedenemies = _allowedenemies;
        this.geomHash = blockedNodes;
                
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
    
    public void incLevelCap() {
        levelCap *= 2;
    }
    
    public void updateNumCreeps() {
        numCreeps += creepMod;
    }
    
    public HashMap getGeomHash() {
        return geomHash;
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
