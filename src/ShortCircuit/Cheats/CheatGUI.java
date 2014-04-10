package ShortCircuit.Cheats;

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
 * GUI for interacting with cheat methods :)
 *
 * @author Connor
 */
public class CheatGUI extends AbstractAppState {

    private SimpleApplication app;
    private Node guiNode;
    private ButtonAdapter MillionDollars;
    private Screen screen;
    private Window CheatWindow;
    private Vector2f buttonSize = new Vector2f(200, 100);
    public int width;
    public int height;
    private ButtonAdapter BillionDollars;
    private CheatState CheatState;
    private ButtonAdapter AmmoH4KZ;
    private ButtonAdapter TowerHack;

    public CheatGUI() {}

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.CheatState = this.app.getStateManager().getState(CheatState.class);
        width = this.app.getContext().getSettings().getWidth();
        height = this.app.getContext().getSettings().getHeight();
        initScreen();
        setupGUI();
    }

    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(false);
        guiNode.addControl(screen);
    }

    private void setupGUI() {
        cheatWindow();
        millionButton();
        billionButton();
        ammoH4KZ();
        towerHack();
    }
    
    

    private void cheatWindow() {
        CheatWindow = new Window(screen, new Vector2f(400, 800), new Vector2f(width / 2, height / 4));
        CheatWindow.setIgnoreMouse(true);
        CheatWindow.setIsMovable(false);
        screen.addElement(CheatWindow);
        CheatWindow.hide();
    }

    private void millionButton() {
        MillionDollars = new ButtonAdapter(screen, "Millions", new Vector2f(40, 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                CheatState.giveMeAMillionDollars();
            }
        };
        MillionDollars.setText("Give Million");
        CheatWindow.addChild(MillionDollars);

    }

    private void billionButton() {
        BillionDollars = new ButtonAdapter(screen, "Billions", new Vector2f(240, 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                CheatState.giveMeABillionDollars();
            }
        };
        BillionDollars.setText("Give Billion");
        CheatWindow.addChild(BillionDollars);

    }

    private void ammoH4KZ() {
        AmmoH4KZ = new ButtonAdapter(screen, "AmmoH4KZ", new Vector2f(440, 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                CheatState.badassAmmoH4KX();
            }
        };
        AmmoH4KZ.setText("@MM0 H4KZ");
        CheatWindow.addChild(AmmoH4KZ);
    }
    
    private void towerHack() {
        TowerHack = new ButtonAdapter(screen, "TowerHack", new Vector2f(640, 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                CheatState.makeTowersBadAss();
            }
        };
        TowerHack.setText("Tower Hack");
        CheatWindow.addChild(TowerHack);
    }
    
    public void toggleCheatWindow() {
        if (CheatWindow.getIsVisible()) {
            CheatWindow.hide();
        }
        else {
            CheatWindow.show();
        }
    }
    
    @Override
    public void cleanup() {
        screen.removeElement(CheatWindow);
        guiNode.removeControl(screen);
    }
}