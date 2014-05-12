package ShortCircuit.States.GUI;

import ShortCircuit.States.Game.GameState;
import ShortCircuit.TowerMainState;
import ShortCircuit.States.Game.FriendlyState;
import ShortCircuit.States.Game.GraphicsState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import tonegod.gui.controls.buttons.Button;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.extras.Indicator;
import tonegod.gui.controls.lists.Slider;
import tonegod.gui.controls.menuing.Menu;
import tonegod.gui.controls.windows.AlertBox;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 * Gameplay GUI for ShortCircuit
 * @author Connor
 */
public class GameGUI extends AbstractAppState {

    private TowerMainState tMS;
    private SimpleApplication app;
    private Camera cam;
    private InputManager inputManager;
    private GameState GameState;
    private Button Charge;
    private Screen screen;
    private Node guiNode;
    private ButtonAdapter Settings;
    public ButtonAdapter Health;
    public ButtonAdapter Budget;
    public ButtonAdapter Score;
    public ButtonAdapter Level;
    private ButtonAdapter TextColorButton;
    private ButtonAdapter BloomToggleButton;
    public ButtonAdapter Modify;
    private ButtonAdapter Camera;
    private ButtonAdapter Menu;
    private ButtonAdapter CheatsButton;
    private ButtonAdapter CheatToggleButton;
    private Vector2f buttonSize = new Vector2f(200, 100);
    private boolean isFrills = true;
    private float updateTimer;
    private float frillsTimer;
    private ColorRGBA color = new ColorRGBA();
    private int leftButtons;
    private float rightButtons;
    private int height;
    private int width;
    private int internalHealth;
    private int internalBudget;
    private int internalScore;
    private int internalLevel;
    private int camlocation = 0;
    private Menu internalMenu;
    private Window SetWindow;
    private Slider BloomSlider;
    private AlertBox ObjectivePopup;
    private Indicator ProgressIndicator;
    private AudioNode endTheme;
    private boolean end = false;
    private StartGUI StartGUI;
    private ButtonAdapter soundToggle;
    private Slider SoundSlider;
    private Window PurchaseWindow;
    private ButtonAdapter PurchaseButton;
    private ButtonAdapter PurchaseChargerButton;
    private AssetManager assetManager;
    private Material oldbuttmat;
    private FriendlyState FriendlyState;
    private ButtonAdapter DowngradeButton;
    private float tenthHeight;
    private float tenthWidth;
    private GraphicsState GraphicsState;
    
    public GameGUI(TowerMainState _tMS) {
        this.tMS = _tMS;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.cam = this.app.getCamera();
        this.inputManager = this.app.getInputManager();
        this.assetManager = this.app.getAssetManager();
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.StartGUI = this.app.getStateManager().getState(StartGUI.class);
        this.FriendlyState = this.app.getStateManager().getState(FriendlyState.class);
        this.GraphicsState = this.app.getStateManager().getState(GraphicsState.class);
        width = tMS.getWidth();
        height = tMS.getHeight();
        inputManager.addListener(actionListener, new String[]{"Touch"});
        this.app.getListener().setLocation(new Vector3f(0, 0, 5f));
        this.app.getListener().setRotation(cam.getRotation());
        initScreen();
        getScalingDimensions();
        setupGUI();
        setCameraLocation();
        setInitialPlrInfo();
    }
    
    private void initScreen() {
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, getAtlas());
        screen.setUseMultiTouch(false);
        guiNode.addControl(screen);
        //screen.setUseKeyboardIcons(true);
    }
    
    public String getAtlas() {
        return "Interface/" + GraphicsState.getMatDir() + "Atlas.png";
    }
    
    private void getScalingDimensions() {
        tenthHeight = height/10;
        tenthWidth = width/10;
        buttonSize = new Vector2f(tenthWidth*1.75f, tenthHeight);
        leftButtons = 10;
        rightButtons = width - tenthWidth*1.75f - 10;
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
     * @param tpf 
     */
    private void frillsLoop(float tpf) {
        if (frillsTimer > .25 && isFrills) {
            if (GameState.getPlrLevel() != internalLevel) {
                GraphicsState.incBloomIntensity(.2f);
                internalLevel = GameState.getPlrLevel();
            }
            if (GameState.getFours() > 0 && !end) {
                endTheme();
                tMS.stopTheme();
                end = true;
            }
            updateFrills();
            frillsTimer = 0;
        } else {
            frillsTimer += tpf;
        }
    }

    private void endTheme() {
        endTheme = new AudioNode(app.getAssetManager(), "Audio/endtheme.wav");
        endTheme.setVolume(1.0f);
        endTheme.setPositional(false);
        endTheme.setLooping(true);
        endTheme.play();
    }

    /**
     * This will be the button that, after cheats are activated, shows up and
     * lets the user use dirty cheats.
     */
    private void cheatsButton() {
        CheatsButton = new ButtonAdapter(screen, "Cheats", new Vector2f(leftButtons, tenthHeight*8), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (!StartGUI.MainWindow.getIsVisible() && !SetWindow.getIsVisible()) {
                    tMS.toggleCheatsWindow();
                }
            }
        };
        CheatsButton.setText("Cheats");
        CheatsButton.setUseButtonPressedSound(true);
        screen.addElement(CheatsButton);
        CheatsButton.hide();
    }

    /**
     * This button will appear in settings, as a toggle. Next to the textcolor
     * button probably.
     */
    private void cheatToggleButton() {
        CheatToggleButton = new ButtonAdapter(screen, "CheatToggle", new Vector2f(440, 100), buttonSize.divide(1.5f)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                toggleCheats();
            }
        };
        CheatToggleButton.setIsToggleButton(true);
        CheatToggleButton.setText("Cheats");
        SetWindow.addChild(CheatToggleButton);
    }

    /**
     * This is the method for toggling the cheat menu button.
     */
    private void toggleCheats() {
        if (CheatsButton.getIsVisible()) {
            CheatsButton.hide();
        } else {
            CheatsButton.show();
        }

    }

    public void setAudioListenerPosition(Vector3f trans) {
        app.getListener().setLocation(trans);
        app.getListener().setRotation(new Quaternion().fromAngleAxis(FastMath.PI / 2, new Vector3f(0, 0, 1)));
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

                    if (GameState.isEnabled()) {
                        selectPlrObject(click2d, click3d);
                    }
                }
            }
        }
    };

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
        settingsWindow();
        cheatToggleButton();
        internalMenu();
        progressIndicator();
        chargeButton();
        modifyButton();
        cameraButton();
        settingsButton();
        healthButton();
        budgetButton();
        scoreButton();
        levelButton();
        menuButton();
        textColorToggleButton();
        bloomToggleButton();
        bloomLevelSlider();
        cheatsButton();
        soundToggleButton();
        soundSlider();
        purchaseButton();
        purchaseWindow();
        purchaseChargerButton();
        getOldMat();
        downgradeButton();

    }

    private void objectivePopup() {
        ObjectivePopup = new AlertBox(screen, "objective", new Vector2f(width / 2 - 200, height / 2 - 175), new Vector2f(400, 300)) {
            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
                tMS.pause();
                screen.removeElement(ObjectivePopup);
            }
        };
        tMS.pause();
        ObjectivePopup.setText("Objective");
        ObjectivePopup.setMsg("Objective: Build 4 purple towers.");
        screen.addElement(ObjectivePopup);
    }

    private void settingsWindow() {
        SetWindow = new Window(screen, new Vector2f(tenthWidth*2, tenthHeight*5), new Vector2f(width / 2, height / 4));
        SetWindow.setIgnoreMouse(true);
        SetWindow.setIsMovable(false);
        screen.addElement(SetWindow);
        SetWindow.hide();
    }

    private void purchaseWindow() {
        PurchaseWindow = new Window(screen, "pWindow", new Vector2f(rightButtons, tenthHeight*6), new Vector2f(300, 500));
        PurchaseWindow.setIgnoreMouse(true);
        PurchaseWindow.setWindowIsMovable(false);
        PurchaseWindow.setWindowTitle("Purchasables");
        PurchaseWindow.setTextAlign(BitmapFont.Align.Center);
        screen.addElement(PurchaseWindow);

        PurchaseWindow.hide();
    }

    private void purchaseButton() {
        PurchaseButton = new ButtonAdapter(screen, "PurchaseButton", new Vector2f(rightButtons, tenthHeight * 6), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (!StartGUI.MainWindow.getIsVisible()) {
                    if (PurchaseWindow.getIsVisible()) {
                        PurchaseWindow.hideWindow();
                        PurchaseChargerButton.hide();
                        Menu.setIgnoreMouse(false);
                    } else {
                        PurchaseWindow.showWindow();
                        PurchaseChargerButton.show();
                        Menu.setIgnoreMouse(true);
                    }
                }
            }
        };
        PurchaseButton.setText("Purchase");
        PurchaseButton.setUseButtonPressedSound(true);
        screen.addElement(PurchaseButton);
    }

    private void purchaseChargerButton() {
        PurchaseChargerButton = new ButtonAdapter(screen, "PurchaseCharger", new Vector2f(50, 50), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                FriendlyState.createCharger();
            }
        };
        PurchaseChargerButton.setZOrder(1.0f);
        PurchaseChargerButton.setText("Charger (100)");
        PurchaseChargerButton.setUseButtonPressedSound(true);
        PurchaseWindow.addChild(PurchaseChargerButton);
    }

    private void downgradeButton() {
        DowngradeButton = new ButtonAdapter(screen, "Downgrade", new Vector2f(50, 250), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                // FriendlyState.downgradeTower(); TODO: Reimplement Downgrade
            }
        };
        DowngradeButton.setZOrder(1.0f);
        DowngradeButton.setText("Downgrade");
        DowngradeButton.setUseButtonPressedSound(true);
        PurchaseWindow.addChild(DowngradeButton);
    }

    private void settingsButton() {
        Settings = new ButtonAdapter(screen, "Settings", new Vector2f(leftButtons, tenthHeight*5), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (!StartGUI.MainWindow.getIsVisible()) {
                    if (SetWindow.getIsVisible()) {
                        SetWindow.hideWindow();
                        Menu.setIgnoreMouse(false);
                        tMS.pause();
                    } else {
                        SetWindow.showWindow();
                        Menu.setIgnoreMouse(true);
                        tMS.pause();
                    }
                }
            }
        };
        Settings.setText("Settings");
        Settings.setUseButtonPressedSound(true);
        screen.addElement(Settings);
    }

    private void progressIndicator() {
        ProgressIndicator = new Indicator(screen, "Progress", new Vector2f(rightButtons, 600), Indicator.Orientation.HORIZONTAL) {
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
        color.interpolate(ColorRGBA.Blue, new ColorRGBA(0.2f, 0.0f, 0.2f, 0.4f), blend);
        ProgressIndicator.setIndicatorColor(color);
        //ProgressIndicator.setCurrentValue(GameState.getCurrentProgress());
    }

    private void updateText() {
        updatePlrInfo();
        updateTowerInfo();
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
            public void onMenuItemClicked(int index, Object value, boolean isToggled) {
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
            if (GameState.getPlrHealth() > 50 && Health.getFontColor() != ColorRGBA.Green) {
                Health.setFontColor(ColorRGBA.Green);
            } else if (GameState.getPlrHealth() <= 50 && GameState.getPlrHealth() > 25 && Health.getFontColor() != ColorRGBA.Yellow) {
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
            if (FriendlyState.getTowerList().get(FriendlyState.getSelected()).getType().equals("TowerUnbuilt")) {
                Modify.setText("Build: " + GameState.getCost(FriendlyState.getTowerList().get(FriendlyState.getSelected()).getType()));
            } else {
                Modify.setText("Upgrade: " + GameState.getCost(FriendlyState.getTowerList().get(FriendlyState.getSelected()).getType()));
            }
        }
    }

    private void updateChargeFrill() {
        if (GameState.getPlrBudget() >= 10 && Charge.getFontColor() != ColorRGBA.Green) {
            Charge.setFontColor(ColorRGBA.Green);
        } else if (GameState.getPlrBudget() < 10 && Charge.getFontColor() != ColorRGBA.Red) {
            Charge.setFontColor(ColorRGBA.Red);
        }
    }

    private void updateTowerFrills() {
        if (FriendlyState.getSelected() != -1) {
            if (GameState.getPlrBudget() >= Integer.parseInt(GameState.getCost(FriendlyState.getTowerList().get(FriendlyState.getSelected()).getType()))) {
                Modify.setFontColor(ColorRGBA.Green);
            } else {
                Modify.setFontColor(ColorRGBA.Red);
            }
        }
    }

    private void textColorToggleButton() {
        TextColorButton = new ButtonAdapter(screen, "TextColorToggle", new Vector2f(240, 100), buttonSize.divide(1.5f)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                tMS.toggleFrills();
            }
        };
        TextColorButton.setIsToggleButton(true);
        TextColorButton.setText("Disable Text Colors");
        SetWindow.addChild(TextColorButton);
    }

    private void bloomToggleButton() {
        BloomToggleButton = new ButtonAdapter(screen, "BloomToggle", new Vector2f(40, 100), buttonSize.divide(1.5f)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                GraphicsState.toggleBloom();
            }
        };
        BloomToggleButton.setIsToggleButton(true);
        BloomToggleButton.setText("Disable bloom");
        SetWindow.addChild(BloomToggleButton);
    }

    private void bloomLevelSlider() {
        BloomSlider = new Slider(screen, "BloomSlider", new Vector2f(60, 70), Slider.Orientation.HORIZONTAL, true) {
            @Override
            public void onChange(int selectedIndex, Object value) {
                //GraphicsState.setBloomIntensity(selectedIndex);
            }
        };
        BloomSlider.setStepFloatRange(0.0f, 20.0f, 1.0f);
        BloomSlider.setSelectedByValue(2.0f);
        SetWindow.addChild(BloomSlider);
    }

    private void soundToggleButton() {
        soundToggle = new ButtonAdapter(screen, "SoundToggle", new Vector2f(640, 100), buttonSize.divide(1.5f)) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (app.getListener().getVolume() == 0) {
                    app.getListener().setVolume(1f);
                } else {
                    app.getListener().setVolume(0f);
                }
            }
        };
        soundToggle.setText("Toggle Sound");
        SetWindow.addChild(soundToggle);
    }

    private void soundSlider() {
        SoundSlider = new Slider(screen, "SoundSlider", new Vector2f(670, 70), Slider.Orientation.HORIZONTAL, true) {
            @Override
            public void onChange(int selectedIndex, Object value) {
                app.getListener().setVolume(selectedIndex);
            }
        };
        SoundSlider.setStepFloatRange(0.0f, 1.0f, .1f);
        app.getListener().setVolume(1.0f);
        SetWindow.addChild(SoundSlider);
    }

    private void chargeButton() {
        Charge = new ButtonAdapter(screen, "charge", new Vector2f(leftButtons, tenthHeight), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                FriendlyState.chargeTower();
            }
        };
        Charge.setText("Charge: 10");
        screen.addElement(Charge);

    }

    private void modifyButton() {
        Modify = new ButtonAdapter(screen, "modify", new Vector2f(leftButtons, tenthHeight*2), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                FriendlyState.upgradeTower();
            }
        };
        Modify.setButtonHoverInfo(null, null);
        Modify.setText("Modify");
        Modify.setUseButtonPressedSound(true);
        screen.addElement(Modify);

    }

    private void cameraButton() {
        Camera = new ButtonAdapter(screen, "Camera", new Vector2f(leftButtons, tenthHeight*3), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
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
        Menu = new ButtonAdapter(screen, "Menu", new Vector2f(leftButtons, tenthHeight*6), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
                if (!SetWindow.getIsVisible()) {
                    SetWindow.setIgnoreMouse(true);
                    tMS.goToMainMenu();
                } else {
                    tMS.goToMainMenu();
                    SetWindow.setIgnoreMouse(false);
                }
            }
        };
        Menu.setText("Menu");
        Menu.setUseButtonPressedSound(true);
        screen.addElement(Menu);
    }

    private void healthButton() {
        Health = new ButtonAdapter(screen, "health", new Vector2f(rightButtons, tenthHeight), buttonSize) {
        };
        Health.setText("Health");
        Health.setIgnoreMouse(true);
        Health.setFontColor(ColorRGBA.Green);
        internalHealth = GameState.getPlrHealth();
        screen.addElement(Health);
    }

    private void budgetButton() {
        Budget = new ButtonAdapter(screen, "Budget", new Vector2f(rightButtons, tenthHeight*2), buttonSize) {
        };
        Budget.setText("Budget: ");
        Budget.setIgnoreMouse(true);
        screen.addElement(Budget);
    }

    private void scoreButton() {
        Score = new ButtonAdapter(screen, "Score", new Vector2f(rightButtons, tenthHeight*3), buttonSize) {
        };
        Score.setText("Score: ");
        Score.setIgnoreMouse(true);
        screen.addElement(Score);
    }

    private void levelButton() {
        Level = new ButtonAdapter(screen, "Level", new Vector2f(rightButtons, tenthHeight*4), buttonSize) {
        };
        Level.setText("Level: ");
        Level.setIgnoreMouse(true);
        screen.addElement(Level);
    }

    public void setCameraLocation() {
        cam.setLocation(GraphicsState.getCamLocation());
    }

    public void getOldMat() {
        Element button = screen.getElementById("Budget");
        oldbuttmat = button.getMaterial();
    }
    

    public void highlightButton(String buttonname) {
        Element button = screen.getElementById(buttonname);
        button.setMaterial(assetManager.loadMaterial("Common/Materials/WhiteColor.j3m"));
    }

    public void unhighlightButton(String buttonname) {
        screen.removeElement(screen.getElementById(buttonname));
        if (buttonname.equals("Budget")) {
            budgetButton();
        }
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
        screen.removeElement(Settings);
        screen.removeElement(CheatsButton);
        screen.removeElement(PurchaseButton);
        screen.removeElement(PurchaseWindow);
        screen.removeElement(ProgressIndicator);
        if (endTheme != null) {
            endTheme.stop();
        }
        guiNode.removeControl(screen);
    }
}
