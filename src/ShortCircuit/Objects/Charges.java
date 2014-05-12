package ShortCircuit.Objects;

/**
 * Charge object. Type input determines the amount of beams are left and how
 * much damage they do.
 * @author Connor Rice
 */
public class Charges {

    public int damage;
    public int remBeams;
    public String type;

    public Charges(String _type) {
        if (_type.equals("Tower1")) {
            damage = 100;
            remBeams = 10;
            type = _type;
        } else if (_type.equals("Tower2")) {
            damage = 150;
            remBeams = 20;
            type = _type;
        } else if (_type.equals("Tower3")) {
            damage = 250;
            remBeams = 30;
            type = _type;
        } else if (_type.equals("Tower4")) {
            damage = 400;
            remBeams = 40;
            type = _type;
        } else if (_type.equals("Towerc")) {
            damage = 1600;
            remBeams = 9999999;
            type = _type;
        }

    }

    public int shoot() {
        remBeams -= 1;
        return damage;
    }

    public String getType() {
        return type;
    }

    public int getRemBeams() {
        return remBeams;
    }
}
