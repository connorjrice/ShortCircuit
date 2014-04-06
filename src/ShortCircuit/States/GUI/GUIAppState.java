package ShortCircuit.States.GUI;

import ShortCircuit.States.Game.GameState;
import ShortCircuit.TowerDefenseMain;
import ShortCircuit.States.Game.TowerState;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

/**
 * GUI State KNOWNBUG: Change camera, then continue is black screen LONGTERM:
 * Ideally I want to be able to scale the GUI. That probably can be done from
 * the basic heart of this class, but I'm not afraid to completely rewrite it.
 *
 * @author Connor Rice
 */
public class GUIAppState extends AbstractAppState {

    private static final Trigger TRIGGER_ACTIVATE = new MouseButtonTrigger(
            MouseInput.BUTTON_LEFT);
    private final static String MAPPING_ACTIVATE = "Touch";
    private Node guiNode = new Node("guiNode");
    private AppSettings settings;
    private BitmapFont customFont;
    private BitmapText chargeText;
    private TowerDefenseMain game;
    private AssetManager assetManager;
    public BitmapText scoreText;
    public BitmapText healthText;
    public BitmapText budgetText;
    public BitmapText debugText;
    public BitmapText pauseText;
    public BitmapText upgradeText;
    public BitmapText buildUpgText;
    public BitmapText levelText;
    public String debugt = "";
    private float gameTimer = 0;
    private boolean budgetSmall = false;
    private Camera cam;
    private InputManager inputManager;
    private Application app;
    public int counter = 0;
    private int camlocation = 0;
    private BitmapText cameraText;
    private GameState GameState;
    private TowerState TowerState;
    

    public GUIAppState(TowerDefenseMain game) {
        this.game = game;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;
        this.assetManager = this.app.getAssetManager();
        this.cam = this.app.getCamera();
        this.inputManager = this.app.getInputManager();
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
        inputManager.addListener(actionListener, new String[]{MAPPING_ACTIVATE});
        app.getRenderer().applyRenderState(RenderState.DEFAULT);

        cam.setLocation(new Vector3f(0, 0, 20f));

        guiNode.setQueueBucket(Bucket.Gui);
        guiNode.setCullHint(CullHint.Never);

        customFont = game.getAssetManager().loadFont("Interface/Fonts/DejaVuSans.fnt");
        settings = game.getContext().getSettings();
        setupGUI();

    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        guiNode.updateLogicalState(tpf);
        guiNode.updateGeometricState();
        if (isEnabled()) {
            if (GameState.isEnabled()) {
                if (gameTimer > .1) {
                    updateText();
                    gameTimer = 0;
                } else {
                    gameTimer += tpf;
                }

            }

        }
    }

    public void updateText() {
        if (GameState.getPlrBudget() > 100 && budgetSmall != true) {
            budgetText.setSize(budgetText.getSize() - 10);
            budgetSmall = true;
        }
        updatePlrInfo();
        updateTowerInfo();
        changeHealthColorCheck();

        levelText.setText("Level: " + Integer.toString(GameState.getPlrLvl()));
        debugText.setText("Debug: " + debugt);
    }

    /**
     * Various things that might want to be shown in debug. Eventually, make a
     * button with a menu of all of this info. That'd be really quite swell.
     */
    private void debugTOptions() {
        //debugt = Integer.toString(GameState.getCreepList().size());
        //debugt = Integer.toString(TowerState.selectedTower);
    }

    /**
     * Here is another useful one. Shows the X and Y coords of the last touch.
     * Called from within the actionListener.
     */
    private void debugTCoords(float x, float y) {
        //debugt = "X: " + Float.toString(x) + "Y: " + Float.toString(y);
    }

    /**
     * Updates the build/upgrade price of the tower that is currently selected.
     * The selected tower number comes from GameState.
     */
    private void updateTowerInfo() {
        if (GameState.getSelected() != -1) {
            if (GameState.getTowerList().get(GameState.getSelected()).getUserData("Type").equals("unbuilt")) {
                buildUpgText.setText("Build: " + GameState.getCost(GameState.getTowerList().get(GameState.getSelected()).getUserData("Type")));
            } else {
                buildUpgText.setText("Upgrade: " + GameState.getCost(GameState.getTowerList().get(GameState.getSelected()).getUserData("Type")));
            }
        }
    }

    /**
     * Updates relevant game information. Information comes from GameState.
     */
    private void updatePlrInfo() {
        scoreText.setText("Kills: " + Integer.toString(GameState.getPlrScore()));
        healthText.setText("Health: " + Integer.toString(GameState.getPlrHealth()));
        budgetText.setText("Budget: " + Integer.toString(GameState.getPlrBudget()));
    }

    /**
     * Changes the color of the health text based upon how much health the user
     * currently has. System is Green, Yellow, Red Numbers: 100-50, 50-26, 25-0.
     */
    private void changeHealthColorCheck() {
        if (GameState.getPlrHealth() > 50 && healthText.getColor() != ColorRGBA.Green) {
            healthText.setColor(ColorRGBA.Green);
        } else if (GameState.getPlrHealth() <= 50 && GameState.getPlrHealth() > 25 && healthText.getColor() != ColorRGBA.Yellow) {
            healthText.setColor(ColorRGBA.Yellow);
        } else if (GameState.getPlrHealth() <= 25 && healthText.getColor() != ColorRGBA.Red) {
            healthText.setColor(ColorRGBA.Red);
        }
    }

    /**
     * Calls all of the things that need to be displayed. I don't like this sort
     * of method.
     */
    private void setupGUI() {
        healthText();
        budgetText();
        scoreText();
        debugText();
        levelText();
        chargeText();
        modifyText();
        cameraText();
        chargeButton();
        cameraButton();
        pauseButton();
        modifyButton();
        healthButton();
        levelButton();
        budgetButton();
        scoreButton();
        pauseText();
    }
    /**
     * Handles touch events. in a poor manner if you ask me. selectPlrObject is
     * one of the culprits. TODO: Update this logic/process
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
                    opHandle(click2d.getX(), click2d.getY());
                
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
        game.getRootNode().collideWith(ray, results);
        if (results.size() > 0) {
            Vector3f trans = results.getCollision(0).getContactPoint();
            Spatial target = results.getCollision(0).getGeometry();
            gameplayAction(trans, target); // Target
        }
    }

    /**
     * Either drops a bomb or selects a tower
     *
     * @param trans - where the collision was
     * @param target - if the target is the floor, drop a bomb
     */
    private void gameplayAction(Vector3f trans, Spatial target) {
        GameState.touchHandle(trans, target);
    }

    /**
     * Because I haven't figured out a better solution, the input handling for
     * buttons in the GUI is basically a conditional bounds check. If it's in
     * the bounds of the box, we do the thing we want.
     *
     * @param x
     * @param y
     */
    private void opHandle(float x, float y) {
        if (x > 0 && x < 370 && GameState.isEnabled()) {
            if (y > 750 && y < 900) {
                touchCharge(); // Charge selected tower
            } else if (y > 550 && y < 700) {
                touchModify(); // Either build or upgrade
            } else if (y > 340 && y < 510) {
                touchCam(); // Changes camera angles
            }

        } else if (x > 1400) {
            if (y > 910 && y < 1035) {
                touchPause();
            }
        }
    }

    /**
     * Calls the method in TowerState to charge the selected tower. Redirects
     * from opHandle.
     */
    private void touchCharge() {
        TowerState.chargeTower();
    }

    /**
     * Calls the method in TowerState to build or upgrade the selected tower.
     * Redirects from opHandle.
     */
    private void touchModify() {
        TowerState.upgradeTower();
    }

    /**
     * Calls the method in the main method to pause the game. This is why GUI
     * state needs access to Game.
     */
    private void touchPause() {
        game.pause();
    }
    
    

    /**
     * Changes camera angles. TODO: Camera angles are not very useful at the
     * moment. There also is a bug that has been noted. This is the culprit
     * section I believe.
     */
    private void touchCam() {
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

    /**
     * The base can shoot insanely powerful lasers. However, I've decided to
     * probably not include this functionality.
     */
    private void touchBaseShoot() {
        GameState.baseShoot();
    }

    /**
     * BUTTONS GO HERE.
     */
    private void chargeButton() {
        Picture charge = new Picture("Charge");
        charge.setImage(assetManager, "Interface/Charge.png", false);
        charge.setLocalTranslation(0, settings.getHeight() / 2 + 200, 0);
        charge.setWidth(375);
        charge.setHeight(150);
        guiNode.attachChild(charge);
    }

    private void modifyButton() {
        Picture build = new Picture("Modify");
        build.setImage(assetManager, "Interface/Build.png", false);
        build.setLocalTranslation(0, settings.getHeight() / 2 + 25, 0);
        build.setWidth(375);
        build.setHeight(150);
        guiNode.attachChild(build);
    }

    private void cameraButton() {
        Picture pause = new Picture("Upgrade1");
        pause.setImage(assetManager, "Interface/Pause.png", false);
        pause.setLocalTranslation(0, settings.getHeight() / 2 - 150, 0);
        pause.setWidth(375);
        pause.setHeight(150);
        guiNode.attachChild(pause);
    }

    private void pauseButton() {
        Picture pause = new Picture("Pause");
        pause.setImage(assetManager, "Interface/Pause.png", false);
        pause.setLocalTranslation(settings.getWidth() - 375, settings.getHeight() / 2 + 375, 0);
        pause.setWidth(375);
        pause.setHeight(150);
        guiNode.attachChild(pause);
    }

    private void healthButton() {
        Picture health = new Picture("Health");
        health.setImage(assetManager, "Interface/Health.png", false);
        health.setLocalTranslation(settings.getWidth() - 375, settings.getHeight() / 2 + 200, 0);
        health.setWidth(375);
        health.setHeight(150);
        guiNode.attachChild(health);
    }

    private void levelButton() {
        Picture level = new Picture("Level");
        level.setImage(assetManager, "Interface/Level.png", false);
        level.setLocalTranslation(settings.getWidth() - 375, settings.getHeight() / 2 + 25, 0);
        level.setWidth(375);
        level.setHeight(150);
        guiNode.attachChild(level);
    }

    private void budgetButton() {
        Picture budget = new Picture("Budget");
        budget.setImage(assetManager, "Interface/Budget.png", false);
        budget.setLocalTranslation(settings.getWidth() - 375, settings.getHeight() / 2 - 150, 0);
        budget.setWidth(375);
        budget.setHeight(150);
        guiNode.attachChild(budget);
    }

    private void scoreButton() {
        Picture score = new Picture("Score");
        score.setImage(assetManager, "Interface/Kills.png", false);
        score.setLocalTranslation(settings.getWidth() - 375, settings.getHeight() / 2 - 325, 0);
        score.setWidth(375);
        score.setHeight(150);
        guiNode.attachChild(score);
    }

    /**
     * Hierarchy of buttons/text Buttons will come first Then text Both should
     * follow the order:
     *
     * Charge Modify Camera Pause Health Level Budget Score.
     */
    /**
     * TEXT GOES HERE.
     */
    private void chargeText() {
        chargeText = new BitmapText(customFont, false);
        chargeText.setSize(customFont.getCharSet().getRenderedSize());
        chargeText.setColor(ColorRGBA.Orange);
        chargeText.setText("Charge (10)");
        chargeText.setLocalTranslation(10, settings.getHeight() / 2 + 310, 2);
        guiNode.attachChild(chargeText);
    }

    private void modifyText() {
        buildUpgText = new BitmapText(customFont, false);
        buildUpgText.setSize(customFont.getCharSet().getRenderedSize() - 2);
        buildUpgText.setColor(ColorRGBA.Red);
        buildUpgText.setText("Build (100)");
        buildUpgText.setLocalTranslation(10, settings.getHeight() / 2 + 135, 2);
        guiNode.attachChild(buildUpgText);
    }

    private void cameraText() {
        cameraText = new BitmapText(customFont, false);
        cameraText.setSize(customFont.getCharSet().getRenderedSize());
        cameraText.setColor(ColorRGBA.Blue);
        cameraText.setText("Camera");
        cameraText.setLocalTranslation(10, settings.getHeight() / 2 - 40, 2);
        guiNode.attachChild(cameraText);
    }

    private void pauseText() {
        pauseText = new BitmapText(customFont, false);
        pauseText.setSize(customFont.getCharSet().getRenderedSize());
        pauseText.setColor(ColorRGBA.Orange);
        pauseText.setText("Pause");
        pauseText.setLocalTranslation(settings.getWidth() - 350, settings.getHeight() / 2 + 485, 2);
        guiNode.attachChild(pauseText);
    }

    private void healthText() {
        healthText = new BitmapText(customFont, false);
        healthText.setSize(customFont.getCharSet().getRenderedSize());
        healthText.setColor(ColorRGBA.Red);
        healthText.setText("Health: " + Integer.toString(GameState.getPlrHealth()));
        healthText.setLocalTranslation(settings.getWidth() - 350, settings.getHeight() / 2 + 310, 2);
        guiNode.attachChild(healthText);

    }

    private void levelText() {
        levelText = new BitmapText(customFont, false);
        levelText.setSize(customFont.getCharSet().getRenderedSize());
        levelText.setColor(ColorRGBA.Blue);
        levelText.setText("Level: " + GameState.getPlrLvl());
        levelText.setLocalTranslation(settings.getWidth() - 350, settings.getHeight() / 2 + 135, 2);
        guiNode.attachChild(levelText);
    }

    private void budgetText() {
        budgetText = new BitmapText(customFont, false);
        budgetText.setSize(customFont.getCharSet().getRenderedSize());
        budgetText.setColor(ColorRGBA.Black);
        budgetText.setText("Budget: " + Integer.toString(GameState.getPlrBudget()));
        budgetText.setLocalTranslation(settings.getWidth() - 350, settings.getHeight() / 2 - 40, 2);
        guiNode.attachChild(budgetText);

    }

    private void scoreText() {
        scoreText = new BitmapText(customFont, false);
        scoreText.setSize(customFont.getCharSet().getRenderedSize());
        scoreText.setColor(ColorRGBA.Black);
        scoreText.setText("Score: " + Integer.toString(GameState.getPlrScore()));
        scoreText.setLocalTranslation(settings.getWidth() - 350, settings.getHeight() / 2 - 215, 2);
        guiNode.attachChild(scoreText);

    }

    private void debugText() {
        debugText = new BitmapText(customFont, false);
        debugText.setSize(50);
        debugText.setColor(ColorRGBA.Pink);
        debugText.setText("Debug: " + debugt);
        debugText.setLocalTranslation(0, debugText.getLineHeight(), 0);
        guiNode.attachChild(debugText);

    }

    public TowerDefenseMain getGame() {
        return game;
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        game.getGUIViewport().attachScene(guiNode);
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        guiNode.detachAllChildren();
        game.getGUIViewport().detachScene(guiNode);
    }

    @Override
    public void cleanup() {
        super.cleanup();

    }

    /**
     * UNUSED BUTTONS AND TEXT GO HERE.
     */
    private void chargeNumButton() {
        Picture budget = new Picture("Budget");
        budget.setImage(assetManager, "Interface/Budget.png", false);
        budget.setLocalTranslation(settings.getWidth() - 375, settings.getHeight() / 2 - 150, 0);
        budget.setWidth(375);
        budget.setHeight(150);
        guiNode.attachChild(budget);
    }

    private void towerLvlButton() {
        Picture budget = new Picture("Budget");
        budget.setImage(assetManager, "Interface/Budget.png", false);
        budget.setLocalTranslation(settings.getWidth() - 375, settings.getHeight() / 2 - 150, 0);
        budget.setWidth(375);
        budget.setHeight(150);
        guiNode.attachChild(budget);
    }

    private void bombsText() {
        BitmapText bombsText = new BitmapText(customFont, false);
        bombsText.setSize(customFont.getCharSet().getRenderedSize());
        bombsText.setColor(ColorRGBA.Black);
        bombsText.setText("Bombs: " + Integer.toString(GameState.getPlrBombs()));
        bombsText.setLocalTranslation(0, settings.getHeight() / 2 - 215, 2);
        guiNode.attachChild(bombsText);
    }

    private void bombsButtons() {
        Picture bombs = new Picture("Bombs");
        bombs.setImage(assetManager, "Interface/Kills.png", false);
        bombs.setLocalTranslation(0, settings.getHeight() / 2 - 325, 0);
        bombs.setWidth(375);
        bombs.setHeight(150);
        guiNode.attachChild(bombs);
    }
}
