package ShortCircuit.States.GUI.Game;

import ShortCircuit.States.GUI.StartGUI;
import ShortCircuit.States.Game.AudioState;
import ShortCircuit.States.Game.FriendlyState;
import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.GraphicsState;
import ShortCircuit.TowerMainState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;

/**
 *
 * @author Connor
 */
public class PurchaseWindowState extends AbstractAppState {
    
    private TowerMainState tMS;
    private SimpleApplication app;
    private GameGUI gameGUI;
    private FriendlyState FriendlyState;
    private StartGUI StartGUI;
    private Screen screen;
    
    private ButtonAdapter PurchaseButton;
    private ButtonAdapter PurchaseChargerButton;
    private ButtonAdapter DowngradeButton;
    private Window PurchaseWindow;
    
    public PurchaseWindowState(TowerMainState _tMS) {
        this.tMS = _tMS;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.gameGUI = (GameGUI) stateManager.getState(GameGUI.class);
        this.StartGUI = (StartGUI) stateManager.getState(StartGUI.class);
        this.FriendlyState = (FriendlyState) stateManager.getState(FriendlyState.class);
        this.screen = this.gameGUI.getScreen();
        layoutElements();
    }
    
    private void layoutElements() {
        purchaseButton();
        purchaseWindow();
        purchaseChargerButton();
        downgradeButton();
    }
    
    
    private void purchaseWindow() {
        PurchaseWindow = new Window(screen, "pWindow", 
                new Vector2f(gameGUI.getRightButtons(), gameGUI.getHeightScale(6)),
                new Vector2f(300, 500));
        PurchaseWindow.setIgnoreMouse(true);
        PurchaseWindow.setWindowIsMovable(false);
        PurchaseWindow.setWindowTitle("Purchasables");
        PurchaseWindow.setTextAlign(BitmapFont.Align.Center);
        screen.addElement(PurchaseWindow);

        PurchaseWindow.hide();
    }

    private void purchaseButton() {
        PurchaseButton = new ButtonAdapter(screen, "PurchaseButton",
                new Vector2f(gameGUI.getRightButtons(), gameGUI.getTenthHeight() * 6), gameGUI.getButtonSize()) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                if (!StartGUI.MainWindow.getIsVisible()) {
                    if (PurchaseWindow.getIsVisible()) {
                        PurchaseWindow.hideWindow();
                        PurchaseChargerButton.hide();
                        gameGUI.getMenu().setIgnoreMouse(false);
                    } else {
                        PurchaseWindow.showWindow();
                        PurchaseChargerButton.show();
                        gameGUI.getMenu().setIgnoreMouse(true);
                    }
                }
            }
        };
        PurchaseButton.setText("Purchase");
        PurchaseButton.setUseButtonPressedSound(true);
        screen.addElement(PurchaseButton);
    }

    private void purchaseChargerButton() {
        PurchaseChargerButton = new ButtonAdapter(screen, "PurchaseCharger",
                new Vector2f(50, 50), gameGUI.getButtonSize()) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                FriendlyState.createCharger();
            }
        };
        PurchaseChargerButton.setZOrder(1.0f);
        PurchaseChargerButton.setText("Charger (100)");
        PurchaseChargerButton.setUseButtonPressedSound(true);
        PurchaseWindow.addChild(PurchaseChargerButton);
    }

    private void downgradeButton() {
        DowngradeButton = new ButtonAdapter(screen, "Downgrade",
                new Vector2f(50, 250), gameGUI.getButtonSize()) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
 //               FriendlyState.downgradeTower(); TODO: Reimplement Downgrade
            }
        };
        DowngradeButton.setZOrder(1.0f);
        DowngradeButton.setText("Downgrade");
        DowngradeButton.setUseButtonPressedSound(true);
        PurchaseWindow.addChild(DowngradeButton);
    }

    
    public void hide() {
        PurchaseButton.hide();
        PurchaseWindow.hide();
    }
    
    public void show() {
        PurchaseButton.show();
    }
    
    @Override
    public void stateDetached(AppStateManager asm) {
        screen.removeElement(PurchaseButton);
        screen.removeElement(PurchaseWindow);
    }
    
}
