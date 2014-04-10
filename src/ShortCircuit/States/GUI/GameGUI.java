package ShortCircuit.States.GUI;

import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.TowerState;
import ShortCircuit.TowerDefenseMain;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import tonegod.gui.controls.buttons.Button;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.menuing.Menu;
import tonegod.gui.controls.windows.Panel;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;

/**
 *
 * @author Connor
 */
public class GameGUI extends AbstractAppState {

    private TowerDefenseMain game;
    private SimpleApplication app;
    private Camera cam;
    private InputManager inputManager;
    private GameState gs;
    private TowerState TowerState;
    private Button Level0;
    private Button Charge;
    private Screen screen;
    private Node guiNode;
    public Button Modify;
    private ButtonAdapter Camera;
    private Panel leftPanel;
    private Panel rightPanel;
    private ButtonAdapter Pause;
    private int camlocation = 0;
    public ButtonAdapter Health;
    public ButtonAdapter Budget;
    public ButtonAdapter Score;
    public ButtonAdapter Level;
    private ButtonAdapter frills;
    private ButtonAdapter Loading;
    private ButtonAdapter Bloom;
    private Vector2f buttonSize = new Vector2f(200, 100);
    private boolean isFrills = true;
    private float updateTimer;
    private float frillsTimer;
    private ButtonAdapter Menu;
    private ScheduledThreadPoolExecutor ex;
    private int leftButtons = 10;
    private int rightButtons = 1605;
    private int height;
    private int width;
    private int internalSelected = -1;
    private int internalHealth;
    private int internalBudget;
    private int internalScore;
    private int internalLevel;
    private Menu MainMenu;
    private Window SetWindow;
    
    public GameGUI() {}

    public GameGUI(TowerDefenseMain _game) {
        this.game = _game;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.cam = this.app.getCamera();
        this.inputManager = this.app.getInputManager();
        this.gs = this.app.getStateManager().getState(GameState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
        this.ex = this.gs.getEx();
        width = game.getWidth();
        height = game.getHeight();
        inputManager.addListener(actionListener, new String[]{"Touch"});
        cam.setLocation(new Vector3f(0, 0, 20f));
        this.app.getListener().setLocation(new Vector3f (0,0,5f));
        this.app.getListener().setRotation(cam.getRotation());
        initScreen();
        setupGUI();
    }

    @Override
    public void update(float tpf) {

        if (updateTimer > .15) {
            if (gs.getPlrHealth() <= 0) {
                game.gameover();
            }
            updateText();
            updateTimer = 0;
        } else {
            updateTimer += tpf;
        }
        if (frillsTimer > .25 && isFrills) {
            updateFrills();
            frillsTimer = 0;
        }
        else {
            frillsTimer += tpf;
        }
        
    }
    public void setAudioListenerPosition(Vector3f trans) {
        app.getListener().setLocation(trans);
        app.getListener().setRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,   new Vector3f(0,0,1)));
    }
        /**
     * Handles touch events for GameState.
     */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (isEnabled()) {
                if (keyPressed) {
                    

                    Vector2f click2d = inputManager.getCursorPosition();
                    Vector3f click3d = cam.getWorldCoordinates(new Vector2f(
                            click2d.getX(), click2d.getY()), 0f);

                    //debugTCoords(click2d.getX(), click2d.getY());

                    if (gs.isEnabled()) {
                        selectPlrObject(click2d, click3d);
                    }
                }
            }
        }
    };
    
    private void setupGUI() {
        leftPanel();
        rightPanel();
        settingsMenu();
        chargeButton();
        modifyButton();
        cameraButton();
        settingsButton();
        healthButton();
        budgetButton();
        scoreButton();
        levelButton();
        menuButton();
        frillsToggle();
        bloomToggle();
        settingsWindow();
    }
    
    private void settingsWindow() {
        SetWindow = new Window(screen, new Vector2f(400,800), new Vector2f(width/2, height/4) );
        SetWindow.setIgnoreMouse(true);
        SetWindow.setIsMovable(false);
        screen.addElement(SetWindow);
        SetWindow.hide();
    }
    
    private void settingsButton() {
        Pause = new ButtonAdapter(screen, "Settings", new Vector2f(leftButtons, 800)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (!game.isStartWindowShown())
                    if (SetWindow.getIsVisible()) {
                        SetWindow.hideWindow();
                        Menu.setIgnoreMouse(false);
                        game.pause();
                    }
                    else {
                        SetWindow.showWindow();
                        Menu.setIgnoreMouse(true);
                        game.pause();
                    }
            }
        };
        Pause.setLocalScale(3f, 2f, 1f);
        Pause.setText("Settings");
        Pause.setUseButtonPressedSound(true);
        screen.addElement(Pause);
    }
    
    private void settingsMenu() {
        MainMenu = new Menu(screen, new Vector2f(0, 0), false) {
            @Override
            public void onMenuItemClicked(int index, Object value, boolean isToggled) {
            }
        };
        MainMenu.addMenuItem("Caption", null, null);
        MainMenu.addMenuItem("Caption1", null, null);
        MainMenu.addMenuItem("Caption2", null, null);
        screen.addElement(MainMenu);
    }

    private void updateText() {
        updatePlrInfo();
        updateTowerInfo();
    }
    
    private void updateFrills() {
        updateChargeFrill();
        updateHealthColor();
        updateTowerFrills();
    }

    private void updatePlrInfo() {
        if (gs.getPlrHealth() != internalHealth) {
            Health.setText("Health: " + gs.getPlrHealth());
            internalHealth = gs.getPlrHealth();
        }
        if (gs.getPlrBudget() != internalBudget) {
            Budget.setText("Budget: " + gs.getPlrBudget());
            internalBudget = gs.getPlrBudget();
        }
        if (gs.getPlrScore() != internalScore) {
            Score.setText("Score: " + gs.getPlrScore());            
            internalScore = gs.getPlrScore();
        }
        if (gs.getPlrLvl() != internalLevel) {
            Level.setText("Level: " + gs.getPlrLvl());
            internalLevel = gs.getPlrLvl();
        }
    }

    private void updateHealthColor() {
        if (gs.getPlrHealth() != internalHealth) {
            if (gs.getPlrHealth() > 50 && Health.getFontColor() != ColorRGBA.Green) {
                Health.setFontColor(ColorRGBA.Green);
            } else if (gs.getPlrHealth() <= 50 && gs.getPlrHealth() > 25 && Health.getFontColor() != ColorRGBA.Yellow) {
                Health.setFontColor(ColorRGBA.Yellow);
            } else if (gs.getPlrHealth() <= 25 && Health.getFontColor() != ColorRGBA.Red) {
                Health.setFontColor(ColorRGBA.Red);
            }
            internalHealth = gs.getPlrHealth();
        }
    }

    /**
     * Updates the build/upgrade price of the tower that is currently selected.
     * The selected tower number comes from GameState.
     */
    private void updateTowerInfo() {
        if (gs.getSelected() != -1) {
            if (gs.getTowerList().get(gs.getSelected()).getUserData("Type").equals("unbuilt")) {
                Modify.setText("Build: " + gs.getCost(gs.getTowerList().get(gs.getSelected()).getUserData("Type")));
            } else {
                Modify.setText("Upgrade: " +gs.getCost(gs.getTowerList().get(gs.getSelected()).getUserData("Type")));
            }
        }
    }
    
    private void updateChargeFrill() {
        if (gs.getPlrBudget() >= 10 && Charge.getFontColor() != ColorRGBA.Green) {
            Charge.setFontColor(ColorRGBA.Green);
        }
        else if (gs.getPlrBudget() < 10 && Charge.getFontColor() != ColorRGBA.Red) {
            Charge.setFontColor(ColorRGBA.Red);
        }
    }
    
    private void updateTowerFrills() {
        if (gs.getSelected() != -1) {
            if (gs.getPlrBudget() >= Integer.parseInt(gs.getCost(gs.getTowerList().get(gs.getSelected()).getUserData("Type")))) {
                Modify.setFontColor(ColorRGBA.Green);
            } else {
                Modify.setFontColor(ColorRGBA.Red);
            }
        }
    }

    /**
     * Handles collisions. TODO: Update this process of selecting objects
     *
     * @param click2d
     * @param click3d
     */
    private void selectPlrObject(Vector2f click2d, Vector3f click3d) {
        CollisionResults results = new CollisionResults();
        Vector3f dir = cam.getWorldCoordinates(
                new Vector2f(click2d.getX(), click2d.getY()), 1f);
        Ray ray = new Ray(click3d, dir);
        game.getRootNode().collideWith(ray, results);
        if (results.size() > 0) {
            Vector3f trans = results.getCollision(0).getContactPoint();
            Spatial target = results.getCollision(0).getGeometry();
            gs.touchHandle(trans, target); // Target
        }
    }


    
    private void frillsToggle() {
        frills = new ButtonAdapter(screen, "isFrills", new Vector2f(width / 4 + 100, height/2 - 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                game.toggleFrills();
            }
        };
        frills.setIsToggleButton(true);
        frills.setText("Disable Frills");
    }
    
    
    private void bloomToggle() {
        Bloom = new ButtonAdapter(screen, "bloom", new Vector2f(width / 4 + 300, height/2 - 100), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                game.toggleBloom();
            }
        };
        Bloom.setIsToggleButton(true);
        Bloom.setText("Disable bloom");
    }
        


    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "tonegod/gui/style/atlasdef/atlas.png");
        screen.setUseMultiTouch(false);
        guiNode.addControl(screen);
        //screen.setUseKeyboardIcons(true);

    }

    private void leftPanel() {
        leftPanel = new Panel(screen, "leftPanel", new Vector2f(0, 0), new Vector2f(325, 1200));
        leftPanel.setIgnoreMouse(true);
        leftPanel.setAsContainerOnly();
        screen.addElement(leftPanel);

    }

    private void rightPanel() {
        rightPanel = new Panel(screen, "rightPanel", new Vector2f(1595, 0), new Vector2f(325, 1200));
        rightPanel.setIgnoreMouse(true);
        rightPanel.setAsContainerOnly();
        screen.addElement(rightPanel);

    }

    private void chargeButton() {
        Charge = new ButtonAdapter(screen, "charge", new Vector2f(leftButtons, 100)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                TowerState.chargeTower();
            }
        };
        Charge.setLocalScale(3f, 2f, 1f);
        Charge.setText("Charge: 10");
        leftPanel.addChild(Charge);

    }

    private void modifyButton() {
        Modify = new ButtonAdapter(screen, "modify", new Vector2f(leftButtons, 200)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                TowerState.upgradeTower();
            }
        };
        Modify.setButtonHoverInfo(null, null);
        Modify.setLocalScale(3f, 2f, 1f);
        Modify.setText("Modify");
        Modify.setUseButtonPressedSound(true);
        leftPanel.addChild(Modify);

    }

    private void cameraButton() {
        Camera = new ButtonAdapter(screen, "Camera", new Vector2f(leftButtons, 300)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                doCamera();
            }
        };
        Camera.setLocalScale(3f, 2f, 1f);
        Camera.setText("Camera");
        Camera.setUseButtonPressedSound(true);
        screen.addElement(Camera);

    }

    private void doCamera() {
        if (camlocation == 0) {
            camlocation = 1;
            cam.setLocation(new Vector3f(0.06431137f, 3.8602734f, 3.5616555f));
            cam.setRotation(new Quaternion(1f, 0f, 0f, 0.5f));
            game.getFlyByCamera().setRotationSpeed(1.0f);
        } else if (camlocation == 1) {
            camlocation = 0;
            cam.setLocation(new Vector3f(0, 0, 20));
            cam.setRotation(new Quaternion(0, 1, 0, 0));
            game.getFlyByCamera().setRotationSpeed(0.0f);
        }
    }



    private void menuButton() {
        Menu = new ButtonAdapter(screen, "Menu", new Vector2f(leftButtons, 900)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (!SetWindow.getIsVisible()) {
                    SetWindow.setIgnoreMouse(true);
                    game.goToMainMenu();
                }
                else {
                    game.goToMainMenu();
                    SetWindow.setIgnoreMouse(false);
                }
            }
        };
        Menu.setLocalScale(3f, 2f, 1f);
        Menu.setText("Menu");
        Menu.setUseButtonPressedSound(true);
        screen.addElement(Menu);
    }

    private void healthButton() {
        Health = new ButtonAdapter(screen, "health", new Vector2f(rightButtons, 100)) {
        };
        Health.setLocalScale(3f, 2f, 1f);
        Health.setText("Health");
        Health.setIgnoreMouse(true);
        Health.setFontColor(ColorRGBA.Green);
        internalHealth = gs.getPlrHealth();
        screen.addElement(Health);
    }

    private void budgetButton() {
        Budget = new ButtonAdapter(screen, "Budget", new Vector2f(rightButtons, 200)) {
        };
        Budget.setLocalScale(3f, 2f, 1f);
        Budget.setText("Budget: ");
        Budget.setIgnoreMouse(true);
        screen.addElement(Budget);
    }

    private void scoreButton() {
        Score = new ButtonAdapter(screen, "Score", new Vector2f(rightButtons, 300)) {
        };
        Score.setLocalScale(3f, 2f, 1f);
        Score.setText("Score: ");
        Score.setIgnoreMouse(true);
        screen.addElement(Score);
    }

    private void levelButton() {
        Level = new ButtonAdapter(screen, "Level", new Vector2f(rightButtons, 400)) {
        };
        Level.setLocalScale(3f, 2f, 1f);
        Level.setText("Level: ");
        Level.setIgnoreMouse(true);
        screen.addElement(Level);
    }

    
    public void toggleFrills() {
        if (isFrills) {
            Health.setFontColor(ColorRGBA.White);
            Charge.setFontColor(ColorRGBA.White);
            Modify.setFontColor(ColorRGBA.White);
        }
        isFrills = !isFrills;
    }

    @Override
    public void cleanup() {
        super.cleanup();
        inputManager.removeListener(actionListener);
        screen.removeElement(Budget);
        screen.removeElement(Camera);
        screen.removeElement(Charge);
        screen.removeElement(Health);
        screen.removeElement(Level);
        screen.removeElement(Menu);
        screen.removeElement(Modify);
        screen.removeElement(Score);
        screen.removeElement(Pause);
        screen.removeElement(leftPanel);
        screen.removeElement(rightPanel);
        guiNode.removeControl(screen);

    }
}
