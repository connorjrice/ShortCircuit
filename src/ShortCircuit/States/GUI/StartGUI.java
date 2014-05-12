package ShortCircuit.States.GUI;

import ShortCircuit.TowerMainState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import tonegod.gui.controls.buttons.Button;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.extras.Indicator;
import tonegod.gui.controls.menuing.Menu;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;
import tonegod.gui.controls.windows.DialogBox;

/**
 * Start menu
 * @author Connor Rice
 */
public class StartGUI extends AbstractAppState {

    private SimpleApplication app;
    private Node guiNode;
    private Screen screen;
    private Button newGame;
    public Menu MainMenu;
    public Window MainWindow;
    private int height;
    private int width;
    private Vector2f buttonSize;
    private AssetManager assetManager;
    private Indicator ind;
    private ButtonAdapter ExitButton;
    private DialogBox ReallyExitPopup;
    private AppStateManager stateManager;
    private Picture loading;
    private boolean firstLoad = true;
    private TowerMainState tMS;
    private Menu levelMenu;
    private float scaler;
    private FlyByCamera flyCam;

    public StartGUI() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.app.getFlyByCamera().setDragToRotate(true);
        height = this.app.getContext().getSettings().getHeight();
        width = this.app.getContext().getSettings().getWidth();
        buttonSize = new Vector2f(width / 8, height / 8);
        scaler = width / 13;

        initScreen();
    }

    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "Interface/StartAtlas.png");
        screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);
        mainWindow();
        newGame();
        initLoadBar();
        exitButton();
        loadingpic();
        initLevelMenu();
    }
    
    private void initScreen(String atlas) {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, atlas);
        screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);
        mainWindow();
        newGame();
        initLoadBar();
        exitButton();
        loadingpic();
        initLevelMenu();
    }
    

    private void initLevelMenu() {
        levelMenu = new Menu(screen, "levelmenu", new Vector2f(), true) {
            @Override
            public void onMenuItemClicked(int index, Object value, boolean isToggled) {
                if (value.equals("Start1")) {
                    onStart("Level1", false);
                } else if (value.equals("Start2")) {
                    onStart("Level2", false);
                } else if (value.equals("Start3")) {
                    onStart("Level3", false);
                } else if (value.equals("GameOverTest")) {
                    onStart("TestGameOver", false);
                } else if (value.equals("StartPath")) {
                    onStart("PathTest", false);
                }
            }
        };
        levelMenu.addMenuItem("Level1", "Start1", null);
        levelMenu.addMenuItem("Level2", "Start2", null);
        levelMenu.addMenuItem("Level3", "Start3", null);
        levelMenu.addMenuItem("TestGameOver", "GameOverTest", null);
        levelMenu.addMenuItem("PathTest", "StartPath", null);
        levelMenu.setPreferredSize(new Vector2f(300,300));
        levelMenu.hide();
        MainWindow.addChild(levelMenu);
    }

    public void onStart(String level, boolean debug) {
        if (firstLoad) {
            showloading();
            tMS = new TowerMainState(debug, level);
            stateManager.attach(tMS);
            firstLoad = false;
        } else {
            showloading();
            stateManager.detach(tMS);
            tMS = new TowerMainState(debug, level);
            stateManager.attach(tMS);
        }
        forceHide();
    }

    private void loadingpic() {
        loading = new Picture("loading");
        loading.setImage(assetManager, "Interface/internalloadingsc.jpg", false);
        loading.setWidth(width);
        loading.setHeight(height);
    }

    private void showloading() {
        guiNode.attachChild(loading);
    }
    
    public void hideloading() {
        guiNode.detachChild(loading);
    }

    private void initLoadBar() {
        ind = new Indicator(screen, "loadbar", new Vector2f(width / 2, height / 2 - 100), Indicator.Orientation.HORIZONTAL) {
            @Override
            public void onChange(float arg0, float arg1) {
            }
        };
        ind.setBaseImage(screen.getStyle("Window").getString("defaultImg"));
        ind.setIndicatorColor(ColorRGBA.randomColor());
        ind.setAlphaMap(screen.getStyle("Indicator").getString("alphaImg"));
        ind.setIndicatorPadding(new Vector4f(7, 7, 7, 7));
        ind.setMaxValue(100);
        ind.setDisplayPercentage();
    }

    private void mainWindow() {
        MainWindow = new Window(screen, new Vector2f(width / 4, height / 2 - height / 4), new Vector2f(width / 2, height / 2));
        MainWindow.setIgnoreMouse(true);
        MainWindow.setWindowIsMovable(false);
        MainWindow.setEffectZOrder(false);
        MainWindow.setIsResizable(false);
        MainWindow.setWindowTitle("ShortCircuit");
        screen.addElement(MainWindow);
    }

    private void newGame() {
        newGame = new ButtonAdapter(screen, "newGame", new Vector2f(MainWindow.getWidth() - scaler * 6, MainWindow.getHeight() - scaler * 1.5f), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (levelMenu.getIsVisible()) {
                    levelMenu.hideMenu();
                } else {
                    levelMenu.showMenu(null, 100,300);
                }
            }
        };
        newGame.setText("New Game");
        newGame.setFont("Interface/Fonts/DejaVuSans.fnt");
        MainWindow.addChild(newGame);
    }


    public void exitButton() {
        ExitButton = new ButtonAdapter(screen, "exit", new Vector2f(MainWindow.getWidth() - scaler * 2, MainWindow.getHeight() - scaler * 1.5f), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                reallyExitDialog();
            }
        };
        ExitButton.setText("Exit");
        ExitButton.setFont("Interface/Fonts/DejaVuSans.fnt");
        MainWindow.addChild(ExitButton);
    }

    public void reallyExitDialog() {
        ReallyExitPopup = new DialogBox(screen, "really exit", new Vector2f(200, 200)) {
            @Override
            public void onButtonCancelPressed(MouseButtonEvent evt, boolean toggled) {
                screen.removeElement(ReallyExitPopup);
            }

            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                super.cleanup();
                guiNode.detachAllChildren();
                app.stop(false);
            }
        };
        ReallyExitPopup.setText("Exit");
        ReallyExitPopup.setMsg("Are you sure?");
        MainWindow.addChild(ReallyExitPopup);
    }

    public void toggle() {
        if (!MainWindow.getIsVisible()) {
            MainWindow.show();
            levelMenu.hide();
        } else {
            MainWindow.hide();
            levelMenu.hide();
        }
    }
    
    public void forceHide() {
        MainWindow.hide();
        MainWindow.hide();
    }

    public boolean mainWindowShown() {
        return MainWindow.getIsVisible();
    }

    public Indicator getInd() {
        return ind;
    }
    
    public void updateAtlas(String newAtlas) {
        guiNode.removeControl(screen);
        initScreen(newAtlas);
        forceHide();
    }


    @Override
    public void cleanup() {
        super.cleanup();
        screen.removeElement(MainWindow);
        guiNode.removeControl(screen);
    }
}
