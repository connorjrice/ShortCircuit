package ShortCircuit.Threading;


/**
 * Currently, this class moves creeps based upon a String called direction.
 * Eventually, this will be used to do pathfinding!
 * TODO: This isn't actually threaded. Make it so.
 * @author clarence
 */
public class IsCreepDead  {
    public String isDead;
    
    public IsCreepDead(String _isDead) {
        isDead = _isDead;
    }
    public String getString() {
        return isDead;
    }
}
