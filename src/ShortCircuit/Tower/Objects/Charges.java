package ShortCircuit.Tower.Objects;

/**
 * TODO: Document
 * TODO: Switch from charges system to integer container for overall number of
 * remaining charges. This should save memory.
 * @author Connor Rice
 */
public class Charges {

    public int damage;
    public int remBullets;
    public String type;

    public Charges(String _type) {
        if (_type.equals("tower1")) {
            damage = 100;
            remBullets = 10;
            type = _type;
        } else if (_type.equals("tower2")) {
            damage = 150;
            remBullets = 20;
            type = _type;
        } else if (_type.equals("tower3")) {
            damage = 250;
            remBullets = 30;
            type = _type;
        } else if (_type.equals("tower4")) {
            damage = 400;
            remBullets = 40;
            type = _type;
        } else if (_type.equals("towerc")) {
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
