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
    private boolean cont;
    
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
    }
    
    private void initButtons() {
        Level1 = new ButtonAdapter(screen, "level1", new Vector2f(500,500)) {

            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onStartLevel1();
            }

        };
        Level0 = new ButtonAdapter(screen, "level0", new Vector2f(500,200)) {

            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                onStartLevel0();
            }
        };
        Level1.setText("Level1");
        Level1.setLocalScale(5f,5f,1f);
        Level0.setText("Level0");
        Level0.setLocalScale(5f, 5f, 1f);
        screen.addElement(Level0);
        screen.addElement(Level1);
        guiNode.addControl(screen);
    }
    
    private void continueButton() {
        
    }
    
    public void attachContinueButton() {
        cont = true;
        continueButton();
    }
    
    
    public void onStartLevel0() {
        cont = false;
        game.detachGameStates();
        game.startGame(false, "Level0");
    }
    
    public void onStartLevel1() {
        cont = false;
        game.detachGameStates();
        game.startGame(false, "Level1");
    }

    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        screen.removeElement(Level0);
        screen.removeElement(Level1);
        guiNode.removeControl(screen);
    }

}
