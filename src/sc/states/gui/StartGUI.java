package sc.states.gui;

import scsdk.mapxml.FileLoader;
import sc.TowerMainState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import tonegod.gui.controls.buttons.Button;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.extras.Indicator;
import tonegod.gui.controls.lists.SelectList;
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
    private Button startButton;
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
    private float scaler;
    private SelectList levelList;
    private ButtonAdapter resumeLevel;
    private static final float FONT_SIZE = 48.0f;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.app.getFlyByCamera().setDragToRotate(true);
        assetManager.registerLoader(FileLoader.class, "");
        height = this.app.getContext().getSettings().getHeight();
        width = this.app.getContext().getSettings().getWidth();
        buttonSize = new Vector2f(width / 8, height / 8);
        scaler = width / 15;
        initScreen();
    }

    private void initScreen() {
        //screen = new Screen(app, "StyleDefs/ShortCircuit/style_map.gui.xml");
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        //screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);
        mainWindow();
        startLevelButton();
        exitButton();
        loadingpic();
        initLevelList();
    }
    
    private void initScreen(String atlas) {
        screen = new Screen(app, "StyleDefs/ShortCircuit/style_map.gui.xml");
        screen.setUseTextureAtlas(true, atlas);
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);
        mainWindow();
        startLevelButton();
        exitButton();
        loadingpic();
        initLevelList();
        resumeLevelButton();
    }

    private void initLevelList() {
        levelList = new SelectList(screen, "sel", 
                new Vector2f(scaler, scaler*.75f), 
                new Vector2f(scaler*3f, scaler*3f)) {
                    @Override
                    public void onChange() {
                        
                    }
                };
        levelList.setFontSize(FONT_SIZE);
        buildLevels();
        MainWindow.addChild(levelList);
    }

    public void onStart(String level) {

        if (firstLoad) {
            showLoading();
            tMS = new TowerMainState(level);
            stateManager.attach(tMS);
            firstLoad = false;
        } else {
            showLoading();
            stateManager.detach(tMS);
            tMS = new TowerMainState(level);
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

    private void showLoading() {
        guiNode.attachChild(loading);
    }
    
    public void hideloading() {
        guiNode.detachChild(loading);
    }

    private void mainWindow() {
        MainWindow = new Window(screen, 
                new Vector2f(width / 6, height / 3 - height / 6),
                new Vector2f(width / 1.5f, height / 1.5f));
        MainWindow.setIgnoreMouse(true);
        MainWindow.setWindowIsMovable(false);
        //MainWindow.setEffectZOrder(false);
        MainWindow.setIsResizable(false);
        MainWindow.setWindowTitle("ShortCircuit");
        screen.addElement(MainWindow);
    }
    
    private void buildLevels() {
        levelList.addListItem("Tutorial", "Tutorial.lvl.xml");
        levelList.addListItem("Level1", "Level1.lvl.xml");
        levelList.addListItem("Level2", "NewLevel2.lvl.xml");
        levelList.addListItem("Level3", "Level3.lvl.xml");
        levelList.addListItem("PathTest", "PathTest.lvl.xml");
        levelList.addListItem("TestGameOver", "TestGameOver.lvl.xml");
    }

    private void startLevelButton() {
        startButton = new ButtonAdapter(screen, "levelSel",
                new Vector2f(scaler, scaler*4), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                String level = (String) levelList.getListItem(
                        levelList.getSelectedIndex()).getValue();
                if (level != null) {
                    onStart(level);
                }
            }
        };
        startButton.setText("Start");
        startButton.setFontSize(FONT_SIZE);
        MainWindow.addChild(startButton);
    }
    
    public void resumeLevelButton() {
        resumeLevel = new ButtonAdapter(screen, "resume",
                new Vector2f(scaler*3, scaler*4), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                tMS.goToMainMenu();
            }
        };
        resumeLevel.setText("Resume");
        MainWindow.addChild(resumeLevel);
    }
    
    public boolean isResumeShown() {
        return (guiNode.hasChild(resumeLevel));
    }


    public void exitButton() {
        ExitButton = new ButtonAdapter(screen, "exit",
                new Vector2f(scaler*7, scaler*4), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                reallyExitDialog();
            }
        };
        ExitButton.setText("Exit");
        ExitButton.setFontSize(FONT_SIZE);
        MainWindow.addChild(ExitButton);
    }

    public void reallyExitDialog() {
        ReallyExitPopup = new DialogBox(screen, "really exit",
                new Vector2f(width/2-scaler, height/2-scaler),
                new Vector2f(scaler*2, scaler*2)) {
            @Override
            public void onButtonCancelPressed(MouseButtonEvent evt,
            boolean toggled) {
                screen.removeElement(ReallyExitPopup);
            }

            @Override
            public void onButtonOkPressed(MouseButtonEvent evt,
            boolean toggled) {
                super.cleanup();
                guiNode.detachAllChildren();
                app.stop();
            }
        };
        ReallyExitPopup.setWindowTitle("Exit");
        ReallyExitPopup.setMsg("Are you sure?");
        MainWindow.addChild(ReallyExitPopup);
    }

    public boolean toggle() {
        if (!MainWindow.getIsVisible()) {
            MainWindow.show();
            return true;
        } else {
            MainWindow.hide();
            return false;
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
        levelList.removeAllListItems();
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
