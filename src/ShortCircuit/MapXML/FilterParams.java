package ShortCircuit.MapXML;

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
    private float downsampling;
    private float blurscale;
    private float exposurepower;
    private float bloomintensity;
    private GlowMode glowmode;
    
    // TODO: array float FilterParams

    public FilterParams(boolean _enabled, float _downsampling, float _blurscale,
            float _exposurepower, float _bloomintensity, GlowMode _glowmode) {
        enabled = _enabled;
        downsampling = _downsampling;
        blurscale = _blurscale;
        exposurepower = _exposurepower;
        bloomintensity = _bloomintensity;
        glowmode = _glowmode;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public float getDownSampling() {
        return downsampling;
    }

    public float getBlurScale() {
        return blurscale;
    }

    public float getExposurePower() {
        return exposurepower;
    }

    public float getBloomIntensity() {
        return bloomintensity;
    }

    public GlowMode getGlowMode() {
        return glowmode;
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
