package ScSDK.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Development
 */
public class GeometryParams implements Savable {

    private ArrayList<Vector3f> vecParams;
    private float beamwidth;

    public GeometryParams() {
    }

    public GeometryParams(Vector3f camLoc, Vector3f floorscale, 
            Vector3f baseVec, Vector3f baseScale, Vector3f towerBuiltSize, 
            Vector3f towerUnbuiltSize, Vector3f towerBuiltSelected,
            Vector3f towerUnbuiltSelected, Vector3f creepSpawnerHorizontalScale, 
            Vector3f creepSpawnerVerticalScale, float beamwidth) {
        
        this.vecParams = new ArrayList<Vector3f>(Arrays.asList(camLoc, 
                floorscale, baseVec, baseScale,
            towerBuiltSize, towerUnbuiltSize, towerBuiltSelected, 
            towerUnbuiltSelected, creepSpawnerHorizontalScale,
            creepSpawnerVerticalScale));
        
        this.beamwidth = beamwidth;
    }

    public Vector3f getCamLoc() {
        return vecParams.get(0);
    }

    public Vector3f getFloorScale() {
        return vecParams.get(1);
    }

    public Vector3f getBaseVec() {
        return vecParams.get(2);
    }

    public Vector3f getBaseScale() {
        return vecParams.get(3);
    }

    public Vector3f getTowerBuiltSize() {
        return vecParams.get(4);
    }

    public Vector3f getTowerUnbuiltSize() {
        return vecParams.get(5);
    }

    public Vector3f getTowerBuiltSelected() {
        return vecParams.get(6);
    }

    public Vector3f getTowerUnbuiltSelected() {
        return vecParams.get(7);
    }

    public Vector3f getCreepSpawnerHorizontalScale() {
        return vecParams.get(8);
    }

    public Vector3f getCreepSpawnerVerticalScale() {
        return vecParams.get(9);
    }

    public float getBeamWidth() {
        return beamwidth;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        vecParams = in.readSavableArrayList("vecParams", vecParams);
        beamwidth = in.readFloat("beamwidth", beamwidth);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.writeSavableArrayList(vecParams, "vecParams", 
                new ArrayList<Vector3f>());
    }
}
