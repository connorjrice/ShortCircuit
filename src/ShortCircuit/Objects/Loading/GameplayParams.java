package ShortCircuit.Objects.Loading;

import ShortCircuit.MapXML.Objects.LevelParams;
import ShortCircuit.MapXML.Objects.PlayerParams;

/**
 * This object contains all of the level parameters as specified by the XML file
 * for the level. It is created by MapGenerator, and used by GameState, which
 * gets the file from LevelState.
 *
 * @author Connor Rice
 */
public class GameplayParams {

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
    


}
