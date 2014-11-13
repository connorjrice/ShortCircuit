package ScSDK.MapXML;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.post.filters.BloomFilter.GlowMode;
import java.io.IOException;

/**
 * This object contains all of the filter parameters for filters such as Bloom.
 *
 * @author Connor
 */
public class FilterParams implements Savable {

    private boolean enabled;
    private float[] filterFloats;
    private GlowMode glowmode;
    
    public FilterParams() {}

    public FilterParams(boolean _enabled, float _downsampling, float _blurscale,
            float _exposurepower, float _bloomintensity, GlowMode _glowmode) {
        filterFloats = new float[] {_downsampling, _blurscale, _exposurepower,
            _bloomintensity};
        enabled = _enabled;
        glowmode = _glowmode;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public float getDownSampling() {
        return filterFloats[0];
    }

    public float getBlurScale() {
        return filterFloats[1];
    }

    public float getExposurePower() {
        return filterFloats[2];
    }

    public float getBloomIntensity() {
        return filterFloats[3];
    }

    public GlowMode getGlowMode() {
        return glowmode;
    }
    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        enabled = in.readBoolean("enabled", true);
        filterFloats = in.readFloatArray("filterFloats", new float[4]);
        glowmode = GlowMode.SceneAndObjects;
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(enabled, "enabled", enabled);
        out.write(filterFloats, "filterFloats", new float[4]);
        out.write(glowmode, "glowmode", glowmode);
    }
    
}
