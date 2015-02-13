package sc.states.game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 * This class controls all audio manipulation and playing.
 *
 * @author Connor Rice
 */
public class AudioState extends AbstractAppState {

    private SimpleApplication app;
    private AssetManager assetManager;
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
    private AudioNode begTheme;
    private AudioNode endTheme;
    /* TowerState Nodes */
    private AudioNode emptySound;
    private AudioNode chargeSound;
    private AudioNode buildSound;

    public AudioState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        initAudioNodes();
        setNodeVolumes();
        setNodePositional();
        setListenerParams();
        begTheme();
    }

    private void setListenerParams() {
        this.app.getListener().setLocation(new Vector3f(0, 0, 5f));
        this.app.getListener().setRotation(app.getCamera().getRotation());
    }

    public void setAudioListenerPosition(Vector3f trans) {
        app.getListener().setLocation(trans);
        app.getListener().setRotation(new Quaternion().fromAngleAxis(
                FastMath.PI / 2, new Vector3f(0, 0, 1)));
    }

    /**
     * Initializes all audio nodes.
     */
    private void initAudioNodes() {
        begTheme = new AudioNode(assetManager, "Audio/theme.wav");
        tower1audio = new AudioNode(assetManager, "Audio/Tower1.wav");
        tower2audio = new AudioNode(assetManager, "Audio/Tower2.wav");
        tower3audio = new AudioNode(assetManager, "Audio/Tower3.wav");
        tower4audio = new AudioNode(assetManager, "Audio/Tower4.wav");
        levelUpSound = new AudioNode(assetManager, "Audio/levelup.wav");
        globPop = new AudioNode(assetManager, "Audio/globpop.wav");
        chargeSound = new AudioNode(assetManager, "Audio/chargegam.wav");
        buildSound = new AudioNode(assetManager, "Audio/buildgam.wav");
        bombsound = new AudioNode(assetManager, "Audio/bomb.wav");
        emptySound = new AudioNode(assetManager, "Audio/emptytower.wav");
        endTheme = new AudioNode(app.getAssetManager(), "Audio/endtheme.wav");
    }

    /**
     * Sets node volumes. TODO: AudioParams?
     */
    private void setNodeVolumes() {
        begTheme.setVolume(1.0f);
        levelUpSound.setVolume(.6f);
        globPop.setVolume(.4f);
        chargeSound.setVolume(.8f);
        buildSound.setVolume(.3f);
        endTheme.setVolume(1.0f);
    }

    /**
     * Sets nodes to be non-positional.
     */
    private void setNodePositional() {
        begTheme.setPositional(false);
        levelUpSound.setPositional(false);
        chargeSound.setPositional(false);
        buildSound.setPositional(false);
        endTheme.setPositional(false);
    }

    /**
     * Plays an instance of the empty tower sound.
     */
    public void emptyTowerSound() {
        emptySound.playInstance();
    }

    /**
     * Plays the correct sound based upon tower's type. Called from TowerState.
     */
    public void beamSound(String towertype, Vector3f origin) {
        if (towertype.equals("Tower1")) {
            //TODO: hashmap/arraylist for tower audio nodes
            tower1audio.setVolume(.3f);
            tower1audio.setLocalTranslation(origin);
            tower1audio.playInstance();
        } else if (towertype.equals("Tower2")) {
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
            elseAudio = new AudioNode(assetManager,
                    "Audio/" + towertype + ".wav");
            elseAudio.setVolume(.3f);
            elseAudio.setLocalTranslation(origin);
            elseAudio.playInstance();
        }

    }

    /**
     * Plays the glob popping sound, scaled to the correct pitch. Called by
     * GameState.
     *
     * @param health - Health of glob
     * @param trans - Translation for sound
     */
    public void globSound(int health, Vector3f trans) {
        globPop.setPitch(health
                * 0.1f + 1f);
        globPop.setLocalTranslation(trans);
        globPop.playInstance();
    }

    /**
     * Plays the level up sound. Called by GameState.
     */
    public void levelUpSound() {
        levelUpSound.play();
    }

    /**
     * Plays an instance of charge sound. Called by GameState.
     */
    public void chargeSound() {
        chargeSound.playInstance();
    }

    /**
     * Plays an instance of the build sound. Called by GameState.
     *
     * @param pitch - offset, distinguishes between levels.
     */
    public void buildSound(float pitch) {
        buildSound.setPitch(pitch);
        buildSound.playInstance();
    }

    /**
     * Clones the previously declared AudioNode bombsound, sets the cloned node
     * to a given translation, plays the node, and then returns the node so that
     * BombControl can end it.
     */
    public AudioNode playBombSound(Vector3f trans) {
        AudioNode an = bombsound.clone();
        an.setLocalTranslation(trans);
        an.play();
        return an;
    }

    /**
     * Plays a loop of the beginning theme.
     */
    public void begTheme() {
        begTheme.setLooping(true);
        begTheme.play();
    }

    /**
     * Ends the beginning theme's loop.
     */
    public void stopBegTheme() {
        begTheme.stop();
    }

    /**
     * Plays a loop of the ending theme. Stops the beginning theme.
     */
    public void endLoop() {
        stopBegTheme();
        endTheme.setLooping(true);
        endTheme.play();
    }

    /**
     * Ends the ending theme's loop.
     */
    public void stopEnd() {
        endTheme.stop();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        stopBegTheme();
        stopEnd();
    }
}
