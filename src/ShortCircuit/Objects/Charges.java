package ShortCircuit.Objects;

/**
 * TODO: Document
 * @author Connor Rice
 */
public class Charges {

    public int damage;
    public int remBullets;
    public String type;

    public Charges(String _type) {
        if (_type.equals("redLaser")) {
            damage = 100;
            remBullets = 150;
            type = _type;
        } else if (_type.equals("pinkLaser")) {
            damage = 200;
            remBullets = 150;
            type = _type;
        } else if (_type.equals("greenLaser")) {
            damage = 400;
            remBullets = 150;
            type = _type;
        } else if (_type.equals("purpleLaser")) {
            damage = 800;
            remBullets = 150;
            type = _type;
        } else if (_type.equals("baseLaser")) {
            damage = 1600;
            remBullets = 9999999;
            type = _type;
        }

    }

    public int shoot() {
        remBullets -= 1;
        return damage;
    }

    public String getType() {
        return type;
    }

    public int getRemBullets() {
        return remBullets;
    }
}
