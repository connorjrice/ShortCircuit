package ShortCircuit.MapXML;

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
    
    public void setHealth(int h) {
        health = h;
    }
    
    public void setBudget(int b) {
        budget = b;
    }
    
    public void setLevel(int l) {
        level = l;
    }
    
    public void setScore(int s) {
        score = s;
    }
    
    public void incHealth(int h) {
        health += h;
    }
    
    public void incBudget(int b) {
        budget += b;
    }
    
    public void incLevel(int l) {
        level += l;
    }
    
    public void incScore(int s) {
        score += s;
    }
    
    public void decHealth(int h) {
        health -= h;
    }
    
    public void decBudget(int b) {
        budget -= b;
    }
    
    public void decLevel(int l) {
        level -= l;
    }
    
    public void decScore(int s) {
        score -= s;
    }

    
}
