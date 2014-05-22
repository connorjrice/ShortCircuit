package ScSDK.MapXML;

import ShortCircuit.MapXML.CreepParams;
import ShortCircuit.MapXML.GeometryParams;
import ShortCircuit.MapXML.TowerParams;
import ShortCircuit.MapXML.CreepSpawnerParams;
import ShortCircuit.MapXML.MaterialParams;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Development
 */
public class BuildParams implements Savable {

    private MaterialParams mp;
    private GeometryParams gp;
    private ArrayList<TowerParams> towerList;
    private ArrayList<CreepSpawnerParams> creepSpawnerList;
    private String[] towerTypes;

    public BuildParams(MaterialParams mp, GeometryParams gp,
            ArrayList<TowerParams> towerList, ArrayList<CreepSpawnerParams> creepSpawnerList, String[] towerTypes) {
        this.mp = mp;
        this.gp = gp;
        this.towerList = towerList;
        this.creepSpawnerList = creepSpawnerList;
        this.towerTypes = towerTypes;
    }


    public MaterialParams getMaterialParams() {
        return mp;
    }

    public GeometryParams getGeometryParams() {
        return gp;
    }

    public ArrayList<TowerParams> getTowerList() {
        return towerList;
    }

    public String[] getTowerTypes() {
        return towerTypes;
    }

    public ArrayList<CreepSpawnerParams> getCreepSpawnerList() {
        return creepSpawnerList;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
    }
}
