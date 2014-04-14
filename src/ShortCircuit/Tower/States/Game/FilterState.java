package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Objects.FilterParams;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.ViewPort;

/**
 * This state takes care of the filters used in the Tower portion of the game.
 * @author Connor
 */
public class FilterState extends AbstractAppState {
    private ViewPort viewPort;
    private FilterPostProcessor fpp;
    private BloomFilter bloom;
    private AssetManager assetManager;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.viewPort = app.getViewPort();
        this.assetManager = app.getAssetManager();
    }
    
    /**
     * Sets up the FilterPostProcessor and Bloom filter used by the game.
     * Called upon initialization of the game.
     */
    public void initFilters(FilterParams fp) {
        if (fp.getEnabled()) {
            fpp = new FilterPostProcessor(assetManager);
            if (fp.getGlowMode() != null) {
                bloom = new BloomFilter(fp.getGlowMode());
            }
            else {
                bloom = new BloomFilter();
            }
            bloom.setDownSamplingFactor(fp.getDownSampling());
            bloom.setBlurScale(fp.getBlurScale());
            bloom.setExposurePower(fp.getExposurePower());
            bloom.setBloomIntensity(fp.getBloomIntensity());
            fpp.addFilter(bloom);
            viewPort.addProcessor(fpp);
        }
    }
    
    private void removeFilters() {
        viewPort.removeProcessor(fpp);
    }
    
        /**
     * Toggle the bloom filter.
     */
    public void toggleBloom() {
        if (viewPort.getProcessors().contains(fpp)) {
            viewPort.removeProcessor(fpp);
        } else {
            viewPort.addProcessor(fpp);
        }

    }

    /**
     * Method for changing the bloom filter's exposure power.
     * @param newVal - what it will be changed to
     */
    public void setBloomExposurePower(float newVal) {
        bloom.setExposurePower(newVal);
    }

    /**
     * Method for changing the bloom filter's blur scape.
     * @param newVal - what it will be changed to
     */
    public void setBloomBlurScape(float newVal) {
        bloom.setBlurScale(newVal);
    }

    /**
     * Method fo setting the bloom filter's intensity
     * @param newVal - what it should be changed to.
     */
    public void setBloomIntensity(float newVal) {
        bloom.setBloomIntensity(newVal);
    }

    /**
     * Increments the bloom filter's intensity
     * @param add - value to be added to the filter.
     */
    public void incBloomIntensity(float add) {
        bloom.setBloomIntensity(bloom.getBloomIntensity() + add);
    }

    
    @Override
    public void cleanup() {
        super.cleanup();
        removeFilters();
    }
}
