package ShortCircuit.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Development
 */
public class GraphicsParams implements Savable {

    private MaterialParams mp;
    private FilterParams fp;
    private GeometryParams gp;
    private String[] towerTypes;
    private ArrayList<CreepParams> creepList;
    private HashMap<String, CreepParams> creepMap;
    
    public GraphicsParams() {
        this.creepMap = new HashMap<String, CreepParams>();
    }

    public GraphicsParams(MaterialParams mp, FilterParams fp, GeometryParams gp,
            String[] towerTypes, ArrayList<CreepParams> creepList) {
        this.mp = mp;
        this.fp = fp;
        this.gp = gp;
        this.towerTypes = towerTypes;
        this.creepList = creepList;
        this.creepMap = new HashMap<String, CreepParams>();
        parseCreeps();
    }

    public FilterParams getFilterParams() {
        return fp;
    }

    public MaterialParams getMaterialParams() {
        return mp;
    }

    public GeometryParams getGeometryParams() {
        return gp;
    }

    public String[] getTowerTypes() {
        return towerTypes;
    }

    public void parseCreeps() {
        for (CreepParams curCreep : creepList) {
            creepMap.put(curCreep.getType(), curCreep);
        }
    }
    
    public ArrayList<CreepParams> getCreepList() {
        return creepList;
    }

    public HashMap getCreepMap() {
        if (creepMap.isEmpty()) {
            parseCreeps();
        }
        return creepMap;
    }

    public CreepParams getCreepParams(String type) {
        if (creepMap.isEmpty()) {
            parseCreeps();
        }
        return creepMap.get(type);
    }

    public Set<String> getCreepTypes() {
        if (creepMap.isEmpty()) {
            parseCreeps();
        }
        return creepMap.keySet();
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        mp = (MaterialParams) in.readSavable("mp", new MaterialParams());
        fp = (FilterParams) in.readSavable("fp", new FilterParams());
        gp = (GeometryParams) in.readSavable("gp", new GeometryParams());
        towerTypes = in.readStringArray("towerTypes", new String[0]);
        creepList = in.readSavableArrayList("creepList", new ArrayList<CreepParams>());
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(mp, "mp", new MaterialParams());
        out.write(fp, "fp", new FilterParams());
        out.write(gp, "gp", new GameplayParams());
        out.write(towerTypes, "towerTypes", new String[20]);
        out.writeSavableArrayList(creepList, "creepList",new ArrayList<CreepParams>());
    }
}
