package ShortCircuit.Tower.Objects;

/**
 *
 * @author Connor and Matthew
 */
public class PlayerParams {
    private int health;
    private int budget;
    private int level;
    private int score;
    
    public PlayerParams(int _health, int _budget, int _level, int _score) {
        health = _health;
        budget = _budget;
        level = _level;
        score = _score;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getBudget() {
        return budget;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getScore() {
        return score;
    }
    
}
