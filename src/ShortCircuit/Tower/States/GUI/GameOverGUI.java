package ShortCircuit.Tower.States.GUI;

import ShortCircuit.Tower.MainState.TowerMainState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;

/**
 * @author Connor
 */
public class GameOverGUI extends AbstractAppState {
    private TowerMainState tMS;
    private Window GameOverWindow;
    private Screen screen;
    private SimpleApplication app;
    private Node guiNode;
    private int height;
    private int width;
    private ButtonAdapter startButton;
    private Vector2f buttonSize = new Vector2f(200, 100);
    
    public GameOverGUI(TowerMainState _tMS) {
        this.tMS = _tMS;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        width = tMS.getWidth();
        height = tMS.getHeight();
        initScreen();
    }
    
    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);
        mainWindow();
        backToStart();
    }
    
    private void mainWindow() {
        GameOverWindow = new Window(screen, "gameover",
                new Vector2f(width / 4, height / 4), new Vector2f(width / 2, height / 2));
        GameOverWindow.setWindowTitle("Game Over");
        GameOverWindow.setWindowIsMovable(false);
        GameOverWindow.setIsResizable(false);
        GameOverWindow.setIgnoreMouse(true);
        GameOverWindow.setZOrder(-1f);
        screen.addElement(GameOverWindow);
    }
    
        private void backToStart() {
            startButton = new ButtonAdapter(screen, "start", new Vector2f(width / 4 + 300, height / 2 + 100), buttonSize) {
                @Override
                public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                    tMS.backToTGStart();
                }
            };
            startButton.setText("Too bad.");
            startButton.setFont("Interface/Fonts/DejaVuSans.fnt");
            screen.addElement(startButton);
    }
        @Override
    public void stateDetached(AppStateManager stateManager) {
        screen.removeElement(GameOverWindow);
        screen.removeElement(startButton);
        guiNode.removeControl(screen);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        screen.removeElement(GameOverWindow);
        screen.removeElement(startButton);
        guiNode.removeControl(screen);
    }
}
