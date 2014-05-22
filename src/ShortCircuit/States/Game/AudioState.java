package ShortCircuit.States.Game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;

/**
 * This class controls all audio manipulation and playing.
 * @author Connor Rice
 */

public class AudioState extends AbstractAppState {
    private SimpleApplication app;
    private AssetManager assetManager;
    
    
    private AudioNode theme;
    
    /* BeamState Nodes */
    private AudioNode tower1audio;
    private AudioNode tower2audio;
    private AudioNode tower3audio;
    private AudioNode tower4audio;
    private AudioNode elseAudio;
    
    /* GameState Nodes */
    private AudioNode levelUpSound;
    private AudioNode globPop;
    private AudioNode bombsound;

    /* TowerState Nodes */
    private AudioNode emptySound;
    private AudioNode chargeSound;
    private AudioNode buildSound;
    private AudioNode bombsound2;
    
    public AudioState() {}
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        initAudioNodes();
        setNodeVolumes();
        setNodePositional();
        themeLoop();
    }
    
    /**
     * Initializes all audio nodes used in game.
     * 
     */
    private void initAudioNodes() {
        theme = new AudioNode(assetManager, "Audio/theme.wav");
        tower1audio = new AudioNode(assetManager, "Audio/Tower1.wav");
        tower2audio = new AudioNode(assetManager, "Audio/Tower2.wav");
        tower3audio = new AudioNode(assetManager, "Audio/Tower3.wav");
        tower4audio = new AudioNode(assetManager, "Audio/Tower4.wav");
        levelUpSound = new AudioNode(assetManager, "Audio/levelup.wav");
        globPop = new AudioNode(assetManager, "Audio/globpop.wav");
        chargeSound = new AudioNode(assetManager, "Audio/chargegam.wav");
        buildSound = new AudioNode(assetManager, "Audio/buildgam.wav");
        bombsound = new AudioNode(assetManager, "Audio/bomb.wav");
        bombsound2 = new AudioNode(assetManager, "Audio/bomb.wav");
        emptySound = new AudioNode(assetManager, "Audio/emptytower.wav");

    }
    
    private void setNodeVolumes() {
        theme.setVolume(1.0f);
        levelUpSound.setVolume(.6f);
        globPop.setVolume(.4f);
        chargeSound.setVolume(.8f);
        buildSound.setVolume(.3f);
    }
    
    private void setNodePositional() {
        theme.setPositional(false);
        levelUpSound.setPositional(false);
        chargeSound.setPositional(false);
        buildSound.setPositional(false);
    }
    
    public void emptySound() {
        emptySound.playInstance();
    }
    
    /**
     * Plays the correct sound based upon tower's type.
     * Called from TowerState.
     */
    public void beamSound(String towertype, Vector3f origin) {
        if (towertype.equals("Tower1")) {
            //TODO: hashmap/arraylist for tower audio nodes
            tower1audio.setVolume(.3f);
            tower1audio.setLocalTranslation(origin);
            tower1audio.playInstance();
        }
        else if (towertype.equals("Tower2")) {
            tower2audio.setVolume(.3f);
            tower2audio.setLocalTranslation(origin);
            tower2audio.playInstance();
        } else if (towertype.equals("Tower3")) {
            tower3audio.setVolume(.3f);
            tower3audio.setLocalTranslation(origin);
            tower3audio.playInstance();
        } else if (towertype.equals("Tower4")) {
            tower4audio.setVolume(.3f);
            tower4audio.setLocalTranslation(origin);
            tower4audio.playInstance();
        } else {
            elseAudio = new AudioNode(assetManager, "Audio/"+towertype+".wav");
            elseAudio.setVolume(.3f);
            elseAudio.setLocalTranslation(origin);
            elseAudio.playInstance();
        }
        
    }
    
    /**
     * Plays the glob popping sound, scaled to the correct pitch.
     * Called by GameState.
     * @param health - Health of glob
     * @param trans  - Translation for sound
     */
    public void globSound(int health, Vector3f trans) {
        globPop.setPitch(health
                * 0.1f + 1f);
        globPop.setLocalTranslation(trans);
        globPop.playInstance();
    }
    
    /**
     * Plays the level up sound.
     * Called by GameState.
     */
    public void levelUpSound() {
        levelUpSound.play();
    }
    
    /**
     * Plays the charge sound.
     * Called by GameState.
     */
    public void chargeSound() {
        chargeSound.playInstance();
    }
    
    public void buildSound(float pitch) {
        buildSound.setPitch(pitch);
        buildSound.playInstance();
    }
    
    /**
     * Plays the bomb sound.
     */
    public AudioNode playBombSound(Vector3f trans) {
        AudioNode an = new AudioNode(assetManager, "Audio/bomb.wav");
        an.play();
        return an;
    }
    
    /**
     * Stop the bomb sound.
     */
    public void stopBombSound() {
        bombsound.detachAllChildren();
    }
    
    public void themeLoop() {
        theme.setLooping(true);
        theme.play();
    }

    public void stopTheme() {
        theme.stop();
    }

    
    @Override
    public void cleanup() {
        super.cleanup();
        stopTheme();
    }
}
