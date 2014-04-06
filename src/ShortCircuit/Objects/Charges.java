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
            damage = 20;
            remBullets = 150;
            type = _type;
        } else if (_type.equals("pinkLaser")) {
            damage = 40;
            remBullets = 150;
            type = _type;
        } else if (_type.equals("greenLaser")) {
            damage = 80;
            remBullets = 150;
            type = _type;
        } else if (_type.equals("purpleLaser")) {
            damage = 160;
            remBullets = 150;
            type = _type;
        } else if (_type.equals("baseLaser")) {
            damage = 75;
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
