package sc.states.gui.game;

import sc.states.gui.StartGUI;
import sc.states.game.AudioState;
import sc.states.game.GraphicsState;
import sc.TowerMainState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.lists.Slider;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;

/**
 *
 * @author Connor
 */
public class SettingsWindowState extends AbstractAppState {
    private SimpleApplication app;
    private Slider SoundSlider;
    private Slider BloomSlider;
    private ButtonAdapter TextColorButton;
    private ButtonAdapter BloomToggleButton;
    private ButtonAdapter soundToggle;
    private ButtonAdapter CheatToggleButton;
    private ButtonAdapter CheatsButton;
    private ButtonAdapter Settings;
    private Window SetWindow;
    private Screen screen;
    private GameGUI gameGUI;
    private StartGUI StartGUI;
    private GraphicsState GraphicsState;
    private AudioState AudioState;
    private TowerMainState tMS;
    
    public SettingsWindowState(TowerMainState _tMS) {
        this.tMS = _tMS;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.gameGUI = (GameGUI) stateManager.getState(GameGUI.class);
        this.StartGUI = (StartGUI) stateManager.getState(StartGUI.class);
        this.GraphicsState = (GraphicsState) stateManager.getState(GraphicsState.class);
        this.AudioState = (AudioState) stateManager.getState(AudioState.class);
        this.screen = this.gameGUI.getScreen();
        layoutElements();
    }
    
    private void layoutElements() {
        settingsWindow();
        cheatsButton();
        settingsButton();
        cheatToggleButton();
        textColorToggleButton();
        bloomToggleButton();
        bloomLevelSlider();
        soundToggleButton();
        soundSlider();
    }

    /**
     * Brings up Cheat Window (inside CheatGUI). Activated from Settings.
     */
    private void cheatsButton() {
        CheatsButton = new ButtonAdapter(screen, "Cheats",
                new Vector2f(gameGUI.getLeftButtons(), gameGUI.getHeightScale(7)), gameGUI.getButtonSize()) {
                    @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (!StartGUI.MainWindow.getIsVisible() && !SetWindow.getIsVisible()) {
                    tMS.toggleCheatsWindow();
                }
            }
        };
        CheatsButton.setText("Cheats");
        CheatsButton.setUseButtonPressedSound(true);
        screen.addElement(CheatsButton);
        CheatsButton.hide();
    }
    
    /**
     * Toggles visibility of the cheats button.
     */
    private void cheatToggleButton() {
        CheatToggleButton = new ButtonAdapter(screen, "CheatToggle",
                new Vector2f(440, 100), gameGUI.getButtonSize().divide(1.5f)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                toggleCheats();
            }
        };
        CheatToggleButton.setIsToggleButton(true);
        CheatToggleButton.setText("Cheats");
        SetWindow.addChild(CheatToggleButton);
    }
    
        /**
     * This is the method for toggling the cheat menu button.
     */
    private void toggleCheats() {
        if (CheatsButton.getIsVisible()) {
            CheatsButton.hide();
        } else {
            CheatsButton.show();
        }

    }



    private void textColorToggleButton() {
        TextColorButton = new ButtonAdapter(screen, "TextColorToggle",
                new Vector2f(240, 100), gameGUI.getButtonSize().divide(1.5f)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                tMS.toggleFrills();
            }
        };
        TextColorButton.setIsToggleButton(true);
        TextColorButton.setText("Disable Text Colors");
        SetWindow.addChild(TextColorButton);

    }

    private void bloomToggleButton() {
        BloomToggleButton = new ButtonAdapter(screen, "BloomToggle",
                new Vector2f(40, 100), gameGUI.getButtonSize().divide(1.5f)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                GraphicsState.toggleBloom();
            }
        };
        BloomToggleButton.setIsToggleButton(true);
        BloomToggleButton.setText("Disable bloom");
        SetWindow.addChild(BloomToggleButton);

    }

    private void bloomLevelSlider() {
        BloomSlider = new Slider(screen, "BloomSlider", new Vector2f(60, 70),
                Slider.Orientation.HORIZONTAL, true) {
            @Override
            public void onChange(int selectedIndex, Object value) {
                //GraphicsState.setBloomIntensity(selectedIndex);
            }
        };
        BloomSlider.setStepFloatRange(0.0f, 20.0f, 1.0f);
        BloomSlider.setSelectedByValue(2.0f);
        SetWindow.addChild(BloomSlider);

    }

    private void soundToggleButton() {
        soundToggle = new ButtonAdapter(screen, "SoundToggle",
                new Vector2f(640, 100), gameGUI.getButtonSize().divide(1.5f)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                if (app.getListener().getVolume() == 0) {
                    app.getListener().setVolume(1f);
                } else {
                    app.getListener().setVolume(0f);
                }
            }
        };
        soundToggle.setText("Toggle Sound");
        SetWindow.addChild(soundToggle);

    }

    private void soundSlider() {
        SoundSlider = new Slider(screen, "SoundSlider", new Vector2f(670, 70),
                Slider.Orientation.HORIZONTAL, true) {
            @Override
            public void onChange(int selectedIndex, Object value) {
                app.getListener().setVolume(selectedIndex);
            }
        };
        SoundSlider.setStepFloatRange(0.0f, 1.0f, .1f);
        app.getListener().setVolume(1.0f);
        SetWindow.addChild(SoundSlider);

    }
    
    private void settingsButton() {
        Settings = new ButtonAdapter(screen, "Settings",
                new Vector2f(gameGUI.getLeftButtons(), gameGUI.getHeightScale(6)), gameGUI.getButtonSize()) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                if (!StartGUI.MainWindow.getIsVisible()) {
                    if (SetWindow.getIsVisible()) {
                        SetWindow.hideWindow();
                        gameGUI.getMenu().setIgnoreMouse(false);
                        tMS.pause();
                    } else { 
                        SetWindow.showWindow();
                        gameGUI.getMenu().setIgnoreMouse(true);
                        tMS.pause();
                    }
                }
            }
        };
        Settings.setText("Settings");
        Settings.setUseButtonPressedSound(true);
        screen.addElement(Settings);
    }

    private void settingsWindow() {
        SetWindow = new Window(screen, gameGUI.getHorizontalWindowPosition(6),
                new Vector2f(gameGUI.getWidth() / 2, gameGUI.getHeight() / 4));
        SetWindow.setIgnoreMouse(true);
        SetWindow.setIsMovable(false);
        screen.addElement(SetWindow);
        SetWindow.hide();
    }
    
    public void hide() {
        CheatsButton.hide();
        Settings.hide();
    }
    
    public void show() {
        Settings.show();
    }
    
    @Override
    public void stateDetached(AppStateManager asm)  {
        screen.removeElement(CheatsButton);
        screen.removeElement(Settings);
    }
    
    
}
