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
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import tonegod.gui.controls.buttons.Button;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Screen;

/**
 *
 * @author Connor
 */
public class LoadingGUI extends AbstractAppState {
    
    public TowerDefenseMain game;
    private SimpleApplication app;
    private Node guiNode;
    private Screen screen;
    private int width;
    private int height;
    private ButtonAdapter Loading;
    private Vector2f buttonSize = new Vector2f(200, 100);
    
    
    public LoadingGUI() {}
    
    public LoadingGUI(TowerDefenseMain _game) {
        this.game = _game;
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
    
    public void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(true);
        guiNode.addControl(screen);
        screen.setUseKeyboardIcons(true);
        loading();
    }
    
    public void loading() {
        Loading = new ButtonAdapter(screen, "loading", new Vector2f(width / 4 + 300, height / 2 + 100), buttonSize);
        Loading.setText("Loading...");
        Loading.setFont("Interface/Fonts/DejaVuSans.fnt");
        screen.addElement(Loading);
    }
    
    public void attachLoading() {
        guiNode.attachChild(Loading);
    }
    
}
