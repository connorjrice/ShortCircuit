package ShortCircuit.MapXML;

import ShortCircuit.MapXML.LevelParams;
import ShortCircuit.MapXML.PlayerParams;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;

/**
 * This object contains all of the level parameters as specified by the XML file
 * for the level. It is created by MapGenerator, and used by GameState, which
 * gets the file from LevelState.
 *
 * @author Connor Rice
 */
public class GameplayParams implements Savable {

    private LevelParams lp;
    private PlayerParams pp;

    public GameplayParams(LevelParams lp, PlayerParams pp) {
        this.lp = lp;
        this.pp = pp;
        
    }
    
    public LevelParams getLevelParams() {
        return lp;
    }
    
    public PlayerParams getPlayerParams() {
        return pp;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(lp, "lp", lp);
        out.write(pp, "pp", pp);
    }
    


}
