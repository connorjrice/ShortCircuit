package ShortCircuit.GUI;

import ShortCircuit.ShortCircuitMain;
import ShortCircuit.Tower.MainState.TowerMainState;
import ShortCircuit.Transit.Game.TransitGameState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapFont;
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
    private Button Level1;
    private Button newGame;
    private boolean cont = true;
    public Menu MainMenu;
    public Window MainWindow;
    private ButtonAdapter debug;
    private int height;
    private int width;
    private Vector2f buttonSize = new Vector2f(200, 100);

    private ColorRGBA color;
    private AssetManager assetManager;
    private Indicator ind;
    private AudioNode theme;
    private boolean isTheme = false;
    private ButtonAdapter ExitButton;
    private DialogBox ReallyExitPopup;
    private ButtonAdapter transit;
    private TransitGameState tGS;
    private AppStateManager stateManager;
    private Picture loading;
    private boolean firstLoad = true;
    private TowerMainState tMS;

    
    public StartGUI() {}

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        height = this.app.getContext().getSettings().getHeight();
        width = this.app.getContext().getSettings().getWidth();
                
        initScreen();
    }
    


   

    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);

        settingsWindow();
        newGame();
        level1();
        debugButton();
        initLoadBar();
        exitButton();
        transitButton();
        loadingpic();
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
    
    
    private void initLoadBar() {
        color = ColorRGBA.randomColor();
        ind = new Indicator(screen, "loadbar", new Vector2f(width/2, height/2-100), Indicator.Orientation.HORIZONTAL) {
            @Override
            public void onChange(float arg0, float arg1) {
            }
        };
        ind.setBaseImage(screen.getStyle("Window").getString("defaultImg"));
        ind.setIndicatorColor(ColorRGBA.randomColor());
        ind.setAlphaMap(screen.getStyle("Indicator").getString("alphaImg"));
        ind.setIndicatorPadding(new Vector4f(7,7,7,7));
        ind.setMaxValue(100);
        ind.setDisplayPercentage();
    }
    
    public Indicator getInd() {
        return ind;
    }

    /*private void initWindow() {
        MainWindow = new Window(screen, "mainwindow",
                new Vector2f(width / 4, height / 4), new Vector2f(width / 2, height / 2));
        MainWindow.setWindowTitle("ShortCircuit");
        MainWindow.setWindowIsMovable(false);
        MainWindow.setIsResizable(false);
        MainWindow.setIgnoreMouse(true);
        MainWindow.setAsContainerOnly();
        screen.addElement(MainWindow);
    }*/
    
    private void settingsWindow() {
        MainWindow = new Window(screen, new Vector2f(width/4, height/2-height/4), new Vector2f(width/2, height/2) );
        MainWindow.setIgnoreMouse(true);
        MainWindow.setWindowIsMovable(false);
        MainWindow.setEffectZOrder(false);
        MainWindow.setIsResizable(false);
        MainWindow.setText("ShortCircuit");
        MainWindow.setTextAlign(BitmapFont.Align.Center);
        screen.addElement(MainWindow);
    }




    private void level1() {
        Level1 = new ButtonAdapter(screen, "level1", new Vector2f(540,height/2+100), buttonSize) {
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
        newGame = new ButtonAdapter(screen, "newGame", new Vector2f(740,height/2+100), buttonSize) {
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
        debug = new ButtonAdapter(screen, "debug", new Vector2f(940,height/2+100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onDebug();
            }
        };
        debug.setText("Debug");
        debug.setFont("Interface/Fonts/DejaVuSans.fnt");
        screen.addElement(debug);
    }
    
    private void transitButton() {
        transit = new ButtonAdapter(screen, "transit", new Vector2f(540,height/2-100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onTransit();
            }
        };
        transit.setText("Transit");
        transit.setFont("Interface/Fonts/DejaVuSans.fnt");
        screen.addElement(transit);
    }
    
    public void onTransit() {
        tGS = new TransitGameState();
        stateManager.attach(tGS);
        toggle();
    }
    

    public void onStart(String level) {
        if (firstLoad) {
            tMS = new TowerMainState(false, level);
            stateManager.attach(tMS);
            firstLoad = false;
        }
        else {
            tMS.detachStates();
            stateManager.detach(tMS);
            tMS = new TowerMainState(false, level);
            stateManager.attach(tMS);
        }
    }

    public void onDebug() {
        //tMS.startGame(true, null);
    }
    
    public void exitButton() {
        ExitButton = new ButtonAdapter(screen, "exit", new Vector2f(1140,height/2+100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                reallyExitDialog();
            }
        };
        ExitButton.setText("Exit");
        ExitButton.setFont("Interface/Fonts/DejaVuSans.fnt");
        screen.addElement(ExitButton);
    }
    
    public void reallyExitDialog() {
        ReallyExitPopup = new DialogBox(screen, "really exit", new Vector2f(width/2, height/2)) {

            @Override
            public void onButtonCancelPressed(MouseButtonEvent evt, boolean toggled) {
                screen.removeElement(ReallyExitPopup);
            }

            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                app.stop();
            }
        };
        ReallyExitPopup.setText("Exit");
        ReallyExitPopup.setMsg("Are you sure?");
        screen.addElement(ReallyExitPopup);
    }

    
    public void toggle() {
        if (MainWindow.getIsVisible()) {
            MainWindow.hide();
            newGame.hide();
            Level1.hide();
            debug.hide();
            ExitButton.hide();
            transit.hide();
        }
        else {
            MainWindow.show();
            newGame.show();
            Level1.show();
            debug.show();
            ExitButton.show();
            transit.show();
        }
    }
    
    

    /*public void toggle() {
        if (cont) {
            hideAll();
            cont = false;
        } else {
            showAll();
            cont = true;
        }
    }*/

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
