/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.States.GUI;

import ShortCircuit.TowerDefenseMain;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import tonegod.gui.controls.buttons.Button;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.menuing.Menu;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;

/**
 *
 * @author clarence
 */
public class TGStart extends AbstractAppState {
    private SimpleApplication app;
    private Node guiNode;
    private TowerDefenseMain game;
    private Screen screen;
    private Button Level1;
    private Button Level0;
    private boolean cont = true;
    private Menu MainMenu;
    private Window MainWindow;
    private ButtonAdapter Continue;
    
    public TGStart(TowerDefenseMain _game) {
        game = _game;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        initScreen();
    }
    
    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true,"tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);
        initButtons();
        //        initMenu();
        initWindow();
    }
    
    private void initWindow() {
        MainWindow = new Window(screen, "mainwindow", new Vector2f(app.getContext().getSettings().getWidth()/4, app.getContext().getSettings().getHeight()/4 ), new Vector2f(app.getContext().getSettings().getWidth()/2, app.getContext().getSettings().getHeight()/2));
        MainWindow.setWindowTitle("ShortCircuit");
        MainWindow.setWindowIsMovable(false);
        MainWindow.setIsResizable(false);
        MainWindow.addChild(Level1);
        MainWindow.addChild(Level0);
        screen.addElement(MainWindow);
    }
    
    private void initMenu() {
        MainMenu = new Menu(screen, new Vector2f(0,0), false) {
            @Override
            public void onMenuItemClicked(int index, Object value, boolean isToggled) {
                
            }
            
        };
        MainMenu.setLocalScale(5f,5f,1f);
        //MainMenu.addMenuItem("Level1", Level1, null);
        MainMenu.showMenu(null, 350,340);
        screen.addElement(MainMenu);
    }
    
    private void initButtons() {
        Level1 = new ButtonAdapter(screen, "level1", new Vector2f(300,450)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onStart("Level1");
            }
        };
        Level1.setText("Level1");
        Level1.setLocalScale(2f,2f,1f);
        Level0 = new ButtonAdapter(screen, "level0", new Vector2f(10,450)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onStart("Level0");
            }
        };

        Level0.setText("Level0");
        Level0.setLocalScale(2f, 2f, 1f);
    }
    
    private void continueButton() {
       Continue = new ButtonAdapter(screen, "level1", new Vector2f(10,200)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                game.continueFromPaused();
            }
        };
       Continue.setLocalScale(2f,2f,1f);
    }

    
    
    public void onStart(String level) {
        game.detachGameStates();
        game.startGame(false, level);
    }
    
    public void toggle() {
        if (cont) {
            MainWindow.hide();
            cont = false;
        }
        else {
            MainWindow.show();
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
