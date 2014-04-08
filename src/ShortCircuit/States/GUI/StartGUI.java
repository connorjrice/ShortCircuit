package ShortCircuit.States.GUI;

import ShortCircuit.TowerDefenseMain;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import tonegod.gui.controls.buttons.Button;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.menuing.Menu;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;

/**
 * Start menu
 * @author Connor Rice
 */
public class StartGUI extends AbstractAppState {

    private SimpleApplication app;
    private Node guiNode;
    private TowerDefenseMain game;
    private Screen screen;
    private Button Level1;
    private Button newGame;
    private boolean cont = true;
    private Menu MainMenu;
    private Window MainWindow;
    private ButtonAdapter debug;
    private int height;
    private int width;
    private Vector2f buttonSize = new Vector2f(200, 100);
    private ButtonAdapter frills;

    public StartGUI(TowerDefenseMain _game) {
        game = _game;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        width = game.getWidth();
        height = game.getHeight();
        initScreen();
    }

    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);
        initWindow();
        newGame();
        level1();
        debugButton();
        isFrillsCheck();
    }

    private void initWindow() {
        MainWindow = new Window(screen, "mainwindow",
                new Vector2f(width / 4, height / 4), new Vector2f(width / 2, height / 2));
        MainWindow.setWindowTitle("ShortCircuit");
        MainWindow.setWindowIsMovable(false);
        MainWindow.setIsResizable(false);
        MainWindow.setIgnoreMouse(true);
        screen.addElement(MainWindow);
    }

    private void initMenu() {
        MainMenu = new Menu(screen, new Vector2f(0, 0), false) {
            @Override
            public void onMenuItemClicked(int index, Object value, boolean isToggled) {
            }
        };
        MainMenu.setLocalScale(5f, 5f, 1f);
        //MainMenu.addMenuItem("Level1", Level1, null);
        MainMenu.showMenu(null, 350, 340);
        screen.addElement(MainMenu);
    }

    private void level1() {
        Level1 = new ButtonAdapter(screen, "level1", new Vector2f(width / 4 + 300, height / 2 + 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onStart("Level1");
            }
        };
        Level1.setText("Level1");
        newGame.setFont("Interface/Fonts/DejaVuSans.fnt");
        screen.addElement(Level1);
    }

    private void newGame() {
        newGame = new ButtonAdapter(screen, "newGame", new Vector2f(width / 4 + 100, height / 2 + 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onStart("Level0");
            }
        };

        newGame.setText("Level0");
        newGame.setFont("Interface/Fonts/DejaVuSans.fnt");
        screen.addElement(newGame);
    }

    private void debugButton() {
        debug = new ButtonAdapter(screen, "debug", new Vector2f(width / 4 + 500, height / 2 + 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onDebug();
            }
        };
        debug.setText("Debug");
        debug.setFont("Interface/Fonts/DejaVuSans.fnt");
        screen.addElement(debug);
    }
    
    private void isFrillsCheck() {
        frills = new ButtonAdapter(screen, "isFrills", new Vector2f(width / 4 + 100, height/2 - 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                game.toggleFrills();
            }
        };
        frills.setIsToggleButton(true);
        frills.setText("Disable Frills");
        screen.addElement(frills);
    }
        

    public void onStart(String level) {
        game.detachGameStates();
        game.startGame(false, level);
    }

    public void onDebug() {
        game.detachGameStates();
        game.startGame(true, null);
    }

    private void hideAll() {
        MainWindow.hide();
        newGame.hide();
        Level1.hide();
        debug.hide();
        frills.hide();
    }

    private void showAll() {
        MainWindow.show();
        newGame.show();
        Level1.show();
        debug.show();
        frills.show();
    }

    public void toggle() {
        if (cont) {
            hideAll();
            cont = false;
        } else {
            showAll();
            cont = true;
        }
    }

    public boolean mainWindowShown() {
        return cont;
    }

    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }

    @Override
    public void cleanup() {
        super.cleanup();
        screen.removeElement(MainWindow);
        guiNode.removeControl(screen);
    }
}
