package ScSDK.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Connor Rice
 */
public class LevelParams implements Savable {

    private int[] intParams;
    private boolean[] boolParams;
    /**
     * Allowed Enemies 0123456 0 = small creep 1 = medium creep 2 = large creep
     * 3 = giant creep 4 = glob 5 = ranger 6 = digger Binary system, so sm-large
     * is 111000
     */
    private String allowedenemies;
    private String[] blockedNodes;

    public LevelParams() {
    }

    public LevelParams(int _numCreeps, int _creepMod,
            int _levelCap, int _levelMod, boolean _profile, boolean _tutorial,
            String _allowedenemies, String[] blockedNodes) {
        intParams = new int[]{_numCreeps, _creepMod, _levelCap, _levelMod};
        boolParams = new boolean[]{_profile, _tutorial};
        allowedenemies = _allowedenemies;
        this.blockedNodes = (String[]) blockedNodes;

    }

    public int getNumCreeps() {
        return intParams[0];
    }

    public int getCreepMod() {
        return intParams[1];
    }

    public int getLevelCap() {
        return intParams[2];
    }

    public int getLevelMod() {
        return intParams[3];
    }

    public boolean getProfile() {
        return boolParams[0];
    }

    public boolean getTutorial() {
        return boolParams[1];
    }

    public String getAllowedEnemies() {
        return allowedenemies;
    }

    public void incLevelCap() {
        intParams[2] *= 2;
    }

    public void updateNumCreeps() {
        intParams[0] += getCreepMod();
    }

    public String[] getBlockedNodes() {
        return blockedNodes;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        intParams = in.readIntArray("intParams", new int[4]);
        boolParams = in.readBooleanArray("boolParams", new boolean[2]);
        allowedenemies = in.readString("allowedenemies", "");
        blockedNodes = in.readStringArray("blockedNodes", new String[10]);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(intParams, "intParams", new int[intParams.length]);
        out.write(boolParams, "boolParams", new boolean[boolParams.length]);
        out.write(allowedenemies, "allowedenemies", allowedenemies);
        out.write(blockedNodes, "blockedNodes", 
                new String[blockedNodes.length]);
    }
}
