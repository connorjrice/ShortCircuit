package ShortCircuit.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;

/**
 *
 * @author Connor and Matthew
 */
public class PlayerParams implements Savable {

    private int health;
    private int budget;
    private int level;
    private int score;

    public PlayerParams() {
    }

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

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        health = in.readInt("h", 100);
        budget = in.readInt("b", 100);
        level = in.readInt("l", 1);
        score = in.readInt("s", 0);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(health, "h", health);
        out.write(budget, "b", budget);
        out.write(level, "l", level);
        out.write(score, "s", score);
    }
}
