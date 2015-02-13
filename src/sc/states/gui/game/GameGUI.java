package sc.states.gui.game;

import sc.states.gui.StartGUI;
import sc.states.game.AudioState;
import sc.states.game.GameState;
import sc.TowerMainState;
import sc.states.game.FriendlyState;
import sc.states.game.GraphicsState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.extras.Indicator;
import tonegod.gui.controls.menuing.Menu;
import tonegod.gui.controls.windows.AlertBox;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 * Gameplay GUI for ShortCircuit
 *
 * @author Connor
 */
public class GameGUI extends AbstractAppState {

    private TowerMainState tMS;
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AppStateManager stateManager;
    private Camera cam;
    private GameState GameState;
    private FriendlyState FriendlyState;
    private StartGUI StartGUI;
    private GraphicsState GraphicsState;
    private AudioState AudioState;
    private Screen screen;
    private Node guiNode;
    private ButtonAdapter Charge;
    private ButtonAdapter Health;
    private ButtonAdapter Budget;
    private ButtonAdapter Score;
    private ButtonAdapter Level;
    private ButtonAdapter Modify;
    private ButtonAdapter Camera;
    private ButtonAdapter Menu;
    private ButtonAdapter Bomb;
    private ButtonAdapter BuildButton;
    private Menu internalMenu;
    private Window BuildWindow;
    private AlertBox ObjectivePopup;
    private Indicator ProgressIndicator;
    private ColorRGBA color = new ColorRGBA();
    private Vector2f buttonSize = new Vector2f(200, 100);
    private boolean isFrills = true;
    private boolean end = false;
    private float updateTimer;
    private float frillsTimer;
    private float leftButtons;
    private float rightButtons;
    private float tenthHeight;
    private float tenthWidth;
    private int height;
    private int width;
    private int internalHealth;
    private int internalBudget;
    private int internalScore;
    private int internalLevel;
    private int camlocation = 0;
    private SettingsWindowState SettingsWindowState;
    private PurchaseWindowState PurchaseWindowState;

    public GameGUI(TowerMainState _tMS) {
        this.tMS = _tMS;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        getResources();
        getStates();
        getScalingDimensions();
        initInput();
        initScreen();
        setupGUI();
        setCameraLocation();
        setInitialPlrInfo();
    }

    private void getResources() {
        this.guiNode = this.app.getGuiNode();
        this.cam = this.app.getCamera();
        this.inputManager = this.app.getInputManager();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
    }

    private void getStates() {
        this.GameState = (GameState) getState(GameState.class);
        this.StartGUI = (StartGUI) getState(StartGUI.class);
        this.SettingsWindowState = (SettingsWindowState) getState(SettingsWindowState.class);
        this.PurchaseWindowState = (PurchaseWindowState) getState(PurchaseWindowState.class);
        this.FriendlyState = (FriendlyState) getState(FriendlyState.class);
        this.GraphicsState = (GraphicsState) getState(GraphicsState.class);
        this.AudioState = (AudioState) getState(AudioState.class);
    }

    private AppState getState(Class c) {
        return stateManager.getState(c);
    }

    private void getScalingDimensions() {
        width = tMS.getWidth();
        height = tMS.getHeight();
        tenthHeight = height / 10;
        tenthWidth = width / 10;
        buttonSize = new Vector2f(tenthWidth * 1.75f, tenthHeight);
        leftButtons = 10;
        rightButtons = width - tenthWidth * 1.75f - 10;
    }

    private void initInput() {
        inputManager.addListener(actionListener, new String[]{"Touch"});
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
                    if (GameState.isEnabled()) {
                        selectPlrObject(click2d, click3d);
                    }
                }
            }
        }
    };

    private void initScreen() {
        screen = new Screen(app, "StyleDefs/ShortCircuit/style_map.gui.xml");
        screen.setUseTextureAtlas(true, getAtlasString());
        screen.setUseMultiTouch(false);
        screen.setGlobalUIScale(tenthHeight, tenthWidth);
        guiNode.addControl(screen);
    }

    @Override
    public void update(float tpf) {
        textLoop(tpf);
        frillsLoop(tpf);
    }

    /**
     * Updates the GUI text, and checks to see if the game is over.
     */
    private void textLoop(float tpf) {
        if (updateTimer > .15) {
            checkGameOver();
            updateText();
            updateTimer = 0;
        } else {
            updateTimer += tpf;
        }
    }

    private void checkGameOver() {
        if (GameState.getPlrHealth() <= 0) {
            tMS.gameover();
        }
    }

    /**
     * Does extra frilly operations (text color, bloom level bump)
     *
     * @param tpf
     */
    private void frillsLoop(float tpf) {
        if (frillsTimer > .25 && isFrills) {
            if (GameState.getPlrLevel() != internalLevel) {
                GraphicsState.incBloomIntensity(.2f);
                internalLevel = GameState.getPlrLevel();
                Level.setText("Level: " + Integer.toString(internalLevel));
            }
            if (GameState.getFours() > 0 && !end) {
                AudioState.endLoop();
                end = true;
            }
            updateFrills();
            frillsTimer = 0;
        } else {
            frillsTimer += tpf;
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
        app.getRootNode().collideWith(ray, results);
        if (results.size() > 0) {
            Vector3f trans = results.getCollision(0).getContactPoint();
            Spatial target = results.getCollision(0).getGeometry();
            GameState.touchHandle(trans, target); // Target
        }
    }

    private void setupGUI() {
        internalMenu();
        progressIndicator();
        chargeButton();
        modifyButton();
        buildButton();
        cameraButton();
        healthButton();
        budgetButton();
        scoreButton();
        levelButton();
        menuButton();
        buildWindow();
        bombToggle();
    }

    private void objectivePopup() {
        ObjectivePopup = new AlertBox(screen, "objective",
                new Vector2f(width / 2 - 200, height / 2 - 175), new Vector2f(400, 300)) {
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt,
            boolean toggled) {
                tMS.pause();
                screen.removeElement(ObjectivePopup);
            }
        };
        tMS.pause();
        ObjectivePopup.setText("Objective");
        ObjectivePopup.setMsg("Objective: Build 4 purple towers.");
        screen.addElement(ObjectivePopup);
    }



    private void buildButton() {
        BuildButton = new ButtonAdapter(screen, "Build",
                new Vector2f(leftButtons, getHeightScale(2)), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                if (!StartGUI.MainWindow.getIsVisible()) {
                    if (BuildWindow.getIsVisible()) {
                        BuildWindow.hideWindow();
                    } else {
                        BuildWindow.showWindow();
                    }
                }
            }
        };
        BuildButton.setText("Build...");
        BuildButton.setUseButtonPressedSound(true);
        screen.addElement(BuildButton);

    }

    /**
     * Cover up everything but menu up. *
     */
    private void buildWindow() {
        BuildWindow = new Window(screen, getHorizontalWindowPosition(2),
                new Vector2f(width / 2, height / 4));
        BuildWindow.setIgnoreMouse(true);
        BuildWindow.setIsMovable(false);
        screen.addElement(BuildWindow);
        BuildWindow.hide();
    }

    private void progressIndicator() {
        ProgressIndicator = new Indicator(screen, "Progress",
                new Vector2f(rightButtons, 600), Indicator.Orientation.HORIZONTAL) {
            @Override
            public void onChange(float arg0, float arg1) {
            }
        };
        ProgressIndicator.setText("Progress");
        ProgressIndicator.setTextAlign(BitmapFont.Align.Center);
        ProgressIndicator.setWidth(300);
        ProgressIndicator.setMaxValue(4);
        screen.addElement(ProgressIndicator);

    }

    public void modifyProgress() {
        float blend = ProgressIndicator.getCurrentValue() * 0.01f;
        color.interpolate(ColorRGBA.Blue, new ColorRGBA(0.2f, 0.0f, 0.2f, 0.4f),
                blend);
        ProgressIndicator.setIndicatorColor(color);
        //ProgressIndicator.setCurrentValue(GameState.getCurrentProgress());
    }

    private void updateText() {
        updatePlrInfo();
        updateTowerInfo();
    }
    
    private void updateChargeFrill() {
        if (GameState.getPlrBudget() >= 10 && Charge.getFontColor()
                != ColorRGBA.Green) {
            Charge.setFontColor(ColorRGBA.Green);
        } else if (GameState.getPlrBudget() < 10 && Charge.getFontColor()
                != ColorRGBA.Red) {
            Charge.setFontColor(ColorRGBA.Red);
        }
    }

    private void updateTowerFrills() {
        if (FriendlyState.getSelected() != -1) {
            if (GameState.getPlrBudget() >= Integer.parseInt(
                    GameState.getCost(FriendlyState.getTowerList()
                    .get(FriendlyState.getSelected()).getUserData("Type")))) {
                Modify.setFontColor(ColorRGBA.Green);
            } else {
                Modify.setFontColor(ColorRGBA.Red);
            }
        }
    }

    private void updateFrills() {
        updateChargeFrill();
        updateHealthColor();
        updateTowerFrills();
        modifyProgress();
    }

    public void toggleFrills() {
        if (isFrills) {
            Health.setFontColor(ColorRGBA.White);
            Charge.setFontColor(ColorRGBA.White);
            Modify.setFontColor(ColorRGBA.White);
            internalHealth += 1; // So that it gets updated
        }
        isFrills = !isFrills;
    }

    private void internalMenu() {
        internalMenu = new Menu(screen, new Vector2f(0, 0), false) {
            @Override
            public void onMenuItemClicked(int index, Object value,
            boolean isToggled) {
            }
        };
        internalMenu.addMenuItem("Caption", null, null);
        internalMenu.addMenuItem("Caption1", null, null);
        internalMenu.addMenuItem("Caption2", null, null);
        screen.addElement(internalMenu);
    }

    private void updatePlrInfo() {
        // flags
        if (GameState.getPlrHealth() != internalHealth) {
            Health.setText("Health: " + GameState.getPlrHealth());
            internalHealth = GameState.getPlrHealth();
        }
        if (GameState.getPlrBudget() != internalBudget) {
            Budget.setText("Budget: " + GameState.getPlrBudget());
            internalBudget = GameState.getPlrBudget();
        }
        if (GameState.getPlrScore() != internalScore) {
            Score.setText("Score: " + GameState.getPlrScore());
            internalScore = GameState.getPlrScore();
        }
        if (GameState.getPlrLevel() != internalLevel) {
            Level.setText("Level: " + GameState.getPlrLevel());
            internalLevel = GameState.getPlrLevel();
        }
    }

    private void setInitialPlrInfo() {
        Health.setText("Health: " + GameState.getPlrHealth());
        Budget.setText("Budget: " + GameState.getPlrBudget());
        Score.setText("Score: " + GameState.getPlrScore());
        Level.setText("Level: " + GameState.getPlrLevel());
    }

    private void updateHealthColor() {
        if (GameState.getPlrHealth() != internalHealth) {
            if (GameState.getPlrHealth() > 50 && Health.getFontColor()
                    != ColorRGBA.Green) {
                Health.setFontColor(ColorRGBA.Green);
            } else if (GameState.getPlrHealth() <= 50 && 
                    GameState.getPlrHealth() > 25 && Health.getFontColor() != ColorRGBA.Yellow) {
                Health.setFontColor(ColorRGBA.Yellow);
            } else if (GameState.getPlrHealth() <= 25 && Health.getFontColor() != ColorRGBA.Red) {
                Health.setFontColor(ColorRGBA.Red);
            }
            internalHealth = GameState.getPlrHealth();
        }
    }

    /**
     * Updates the build/upgrade price of the tower that is currently selected.
     * The selected tower number comes from GameState.
     */
    private void updateTowerInfo() {
        if (FriendlyState.getSelected() != -1) {
            if (FriendlyState.getTowerList().get(FriendlyState.getSelected())
                    .getUserData("Type").equals("TowerUnbuilt")) {
                Modify.setText("Build: " + GameState.getCost(FriendlyState
                        .getTowerList().get(FriendlyState.getSelected()).getUserData("Type")));
            } else {
                Modify.setText("Upgrade: " + GameState.getCost(FriendlyState
                        .getTowerList().get(FriendlyState.getSelected()).getUserData("Type")));
            }
        }
    }

    private void chargeButton() {
        Charge = new ButtonAdapter(screen, "charge",
                new Vector2f(leftButtons, 10), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                FriendlyState.chargeTower();
            }
        };
        Charge.setText("Charge: 10");
        screen.addElement(Charge);
    }

    private void modifyButton() {
        Modify = new ButtonAdapter(screen, "modify",
                new Vector2f(leftButtons, getHeightScale(1)), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                FriendlyState.upgradeTower();
            }
        };
        Modify.setButtonHoverInfo(null, null);
        Modify.setText("Modify");
        Modify.setUseButtonPressedSound(true);
        screen.addElement(Modify);
    }

    private void cameraButton() {
        Camera = new ButtonAdapter(screen, "Camera", new Vector2f(leftButtons,
                getHeightScale(3)), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                doCamera();
            }
        };
        Camera.setText("Camera");
        Camera.setUseButtonPressedSound(true);
        screen.addElement(Camera);
    }

    private void doCamera() {
        if (camlocation == 0) {
            camlocation = 1;
            cam.setLocation(new Vector3f(0.06431137f, 3.8602734f, 3.5616555f));
            cam.setRotation(new Quaternion(1f, 0f, 0f, 0.5f));
            app.getFlyByCamera().setRotationSpeed(1.0f);
        } else if (camlocation == 1) {
            camlocation = 0;
            cam.setLocation(GraphicsState.getCamLocation());
            cam.setRotation(new Quaternion(0, 1, 0, 0));
            app.getFlyByCamera().setRotationSpeed(0.0f);
        }
    }

    private void menuButton() {
        Menu = new ButtonAdapter(screen, "Menu", new Vector2f(leftButtons,
                getHeightScale(8)), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                tMS.goToMainMenu();
            }
        };
        Menu.setText("Menu");
        Menu.setUseButtonPressedSound(true);
        screen.addElement(Menu);
    }

    private void healthButton() {
        Health = new ButtonAdapter(screen, "health",
                new Vector2f(rightButtons, getHeightScale(1)), buttonSize) {
        };
        Health.setText("Health");
        Health.setIgnoreMouse(true);
        Health.setFontColor(ColorRGBA.Green);
        internalHealth = GameState.getPlrHealth();
        screen.addElement(Health);
    }

    private void budgetButton() {
        Budget = new ButtonAdapter(screen, "Budget",
                new Vector2f(rightButtons, getHeightScale(2)), buttonSize) {
        };
        Budget.setText("Budget: ");
        Budget.setIgnoreMouse(true);
        screen.addElement(Budget);
    }

    private void scoreButton() {
        Score = new ButtonAdapter(screen, "Score",
                new Vector2f(rightButtons, getHeightScale(3)), buttonSize) {
        };
        Score.setText("Score: ");
        Score.setIgnoreMouse(true);
        screen.addElement(Score);
    }

    private void levelButton() {
        Level = new ButtonAdapter(screen, "Level",
                new Vector2f(rightButtons, getHeightScale(4)), buttonSize) {
        };
        Level.setText("Level: ");
        Level.setIgnoreMouse(true);
        screen.addElement(Level);
    }

    private void bombToggle() {
        Bomb = new ButtonAdapter(screen, "BombToggle",
                new Vector2f(rightButtons, getHeightScale(7)), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt,
            boolean toggled) {
                GameState.toggleBomb();
                if (GameState.getBombActive()) {
                    Bomb.setText("Drop Bombs");
                } else {
                    Bomb.setText("Build Walls");
                }
            }
        };
        Bomb.setText("Build Walls");
        screen.addElement(Bomb);
    }

    public void setCameraLocation() {
        cam.setLocation(GraphicsState.getCamLocation());
    }

    public void highlightButton(String buttonname) {
        Element button = screen.getElementById(buttonname);
        button.setMaterial(assetManager.loadMaterial(
                "Common/Materials/WhiteColor.j3m"));
    }

    public void unhighlightButton(String buttonname) {
        screen.removeElement(screen.getElementById(buttonname));
        if (buttonname.equals("Budget")) {
            budgetButton();
        }
    }

    public String getAtlasString() {
        return "Interface/" + GraphicsState.getMatDir() + "Atlas.png";
    }

    public float getHeightScale(int i) {
        return (tenthHeight * i) + 10;
    }

    public Vector2f getHorizontalWindowPosition(int i) {
        return new Vector2f(tenthWidth * 2, getHeightScale(i) -
                tenthHeight / 2);
    }

    public void toggle(boolean hide) {
        if (hide) {
            hide();
            SettingsWindowState.hide();
            PurchaseWindowState.hide();
        } else {
            show();
            SettingsWindowState.show();
            PurchaseWindowState.hide();
        }
    }
    
    public Screen getScreen() {
        return screen;
    }
    
    public float getLeftButtons() {
        return leftButtons;
    }
    
    public float getRightButtons() {
        return rightButtons;
    }
    
    public float getTenthHeight() {
        return tenthHeight;
    }
    
    public ButtonAdapter getMenu() {
        return Menu;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }

    public void hide() {
        Budget.hide();
        Camera.hide();
        Charge.hide();
        Health.hide();
        Level.hide();
        Menu.hide();
        Modify.hide();
        Score.hide();
        ProgressIndicator.hide();
        Bomb.hide();
        BuildButton.hide();
        BuildWindow.hide();
    }
    
    public Vector2f getButtonSize() {
        return buttonSize;
    }

    public void show() {
        Budget.show();
        Camera.show();
        Charge.show();
        Health.show();
        Level.show();
        Menu.show();
        Modify.show();
        Score.show();
        ProgressIndicator.show();
        Bomb.show();
        BuildButton.show();
    }

    @Override
    public void stateDetached(AppStateManager asm) {
        super.cleanup();
        inputManager.clearMappings();
        inputManager.removeListener(actionListener);
        screen.removeElement(Budget);
        screen.removeElement(Camera);
        screen.removeElement(Charge);
        screen.removeElement(Health);
        screen.removeElement(Level);
        screen.removeElement(Menu);
        screen.removeElement(Modify);
        screen.removeElement(Score);
        screen.removeElement(ProgressIndicator);
        screen.removeElement(Bomb);
        screen.removeElement(BuildButton);
        guiNode.removeControl(screen);
    }
}
