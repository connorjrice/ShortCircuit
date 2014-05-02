package ShortCircuit.Tower.Objects.Loading;

import ShortCircuit.Tower.MapXML.Objects.BaseParams;
import ShortCircuit.Tower.MapXML.Objects.LevelParams;
import ShortCircuit.Tower.MapXML.Objects.PlayerParams;
import ShortCircuit.Tower.MapXML.Objects.GeometryParams;
import ShortCircuit.Tower.MapXML.Objects.TowerParams;
import java.util.ArrayList;

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
    private GeometryParams gp;
    private BaseParams bp;
    private TowerParams tp;
    private ArrayList<TowerParams> towerList;

    public GameplayParams(LevelParams lp, PlayerParams pp, ArrayList<TowerParams> towerList) {
        this.lp = lp;
        this.pp = pp;
        this.towerList = towerList;
        
    }
    
    public LevelParams getLevelParams() {
        return lp;
    }
    
    public PlayerParams getPlayerParams() {
        return pp;
    }
    

    
    public ArrayList<TowerParams> getTowerList() {
        return towerList;
    }

}
