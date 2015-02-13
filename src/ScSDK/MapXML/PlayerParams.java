package ScSDK.MapXML;

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

    private int[] intParams;

    public PlayerParams() {
    }

    public PlayerParams(int health, int budget, int level, int score) {
        this.intParams = new int[] {health, budget, level, score};
    }

    public int getHealth() {
        return intParams[0];
    }

    public int getBudget() {
        return intParams[1];
    }

    public int getLevel() {
        return intParams[2];
    }

    public int getScore() {
        return intParams[3];
    }

    public void setHealth(int h) {
        intParams[0] = h;
    }

    public void setBudget(int b) {
        intParams[1] = b;
    }

    public void setLevel(int l) {
        intParams[2] = l;
    }

    public void setScore(int s) {
        intParams[3] = s;
    }

    public void incHealth(int h) {
        intParams[0] += h;
    }

    public void incBudget(int b) {
        intParams[1] += b;
    }

    public void incLevel(int l) {
        intParams[2] += l;
    }

    public void incScore(int s) {
        intParams[3] += s;
    }

    public void decHealth(int h) {
        intParams[0] -= h;
    }

    public void decBudget(int b) {
        intParams[1] -= b;
    }

    public void decLevel(int l) {
        intParams[2] -= l;
    }

    public void decScore(int s) {
        intParams[3] -= s;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        intParams = in.readIntArray("intParams", new int[4]);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(intParams, "intParams", new int[4]);
    }
}
