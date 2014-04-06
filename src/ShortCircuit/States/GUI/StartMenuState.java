package ShortCircuit.States.GUI;

import ShortCircuit.TowerDefenseMain;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
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
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.ui.Picture;

/**
 * TODO: Document/reimplement
 *
 * @author Connor Rice
 *
 * @param game
 */
public class StartMenuState extends AbstractAppState {

    private final static String MAPPING_ACTIVATE = "Touch";
    private static final Trigger TRIGGER_ACTIVATE = new MouseButtonTrigger(
            MouseInput.BUTTON_LEFT);
    private TowerDefenseMain game;
    private Node guiNode = new Node("Gui Node");
    private Node buttonNode = new Node("Buttons");
    private AssetManager assetManager;
    public BitmapText startT;
    private InputManager inputManager;
    public BitmapFont customFont;
    public BitmapText titleText;
    private BitmapText debugT;
    private BitmapText continueT;
    private boolean cont = false;
    private Camera cam;
    private SimpleApplication app;

    public StartMenuState(TowerDefenseMain game) {
        this.game = game;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.cam = this.app.getCamera();
        initTouch();
        setupGUI();
    }

    public void initTouch() {
        inputManager.addMapping(MAPPING_ACTIVATE, TRIGGER_ACTIVATE);
        inputManager.addListener(actionListener, new String[]{MAPPING_ACTIVATE});
        guiNode.setQueueBucket(Bucket.Gui);
        customFont = game.getAssetManager().loadFont("Interface/Fonts/DejaVuSans.fnt");
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        guiNode.updateLogicalState(tpf);
        guiNode.updateGeometricState();

    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (isEnabled()) {
                if (keyPressed) {
                    Vector2f click2d = inputManager.getCursorPosition();
                    for (int i = 0; i < guiNode.getChildren().size(); i++) {
                        System.out.println("Name: " + guiNode.getChild(i).getName() + " Bound: " + guiNode.getChild(i).getWorldBound());
                        if (guiNode.getChild("Start").getWorldBound().intersects(new Vector3f(click2d.getX(), click2d.getY(), -0.5f))) {
                            onStart();
                        } else if (guiNode.getChild("Debug").getWorldBound().intersects(new Vector3f(click2d.getX(), click2d.getY(), -0.5f))) {
                            onDebug();
                        } else if (cont) {
                            if (guiNode.getChild("Continue").getWorldBound().intersects(new Vector3f(click2d.getX(), click2d.getY(), -0.5f))) {
                                game.continueFromPaused();
                            }
                        }
                    }
                }
            }
        }
    };



    public void setupGUI() {
            createLight();
            startButton();
            debugButton();
            background();
            titleText();
        }

        public void attachContinueButton() {
            cont = true;
            continueButton();
        }

        public void onStart() {
            cont = false;
            game.detachGameStates();
            game.startGame(false, "Level1");
        }

        public void onDebug() {
            cont = false;
            game.detachGameStates();
            game.startGame(true, null);
        }

        /**
         * Creates the light for the game world.
         */
        public void createLight() {
            AmbientLight ambient = new AmbientLight();
            ambient.setColor(ColorRGBA.White.mult(5f));

            DirectionalLight dir = new DirectionalLight();
            dir.setColor(ColorRGBA.White.mult(10f));
            dir.setDirection(new Vector3f(1f, 0f, 1f));
            guiNode.addLight(ambient);
            guiNode.addLight(dir);
        }

        public void background() {
            Picture background = new Picture("Backgrosdund");
            background.setImage(assetManager, "Interface/MainScreen.jpg", false);
            background.setWidth(1920);
            background.setHeight(1200);
            background.setLocalTranslation(0, 0, -1.9f);

            buttonNode.attachChild(background);
            guiNode.attachChild(background);
        }

        public void startButton() {
            Geometry startB = new Geometry("Start", new Box(1, 1, 1));
            startB.setMaterial(assetManager.loadMaterial("Materials/MediumCreep.j3m"));
            startB.setLocalScale(256, 128, 20f);
            startB.setLocalTranslation(375, 200, -0.5f);
            startT = new BitmapText(customFont, false);
            startT.setSize(customFont.getCharSet().getRenderedSize()+30);
            startT.setColor(ColorRGBA.White);
            startT.setText("New Game");
            startT.setLocalTranslation(125, 275, 1);
            guiNode.attachChild(startB);
            guiNode.attachChild(startT);
        }

        public void continueButton() {
            Geometry continueB = new Geometry("Continue", new Box(1, 1, 1));
            continueB.setMaterial(assetManager.loadMaterial("Materials/StartButton.j3m"));
            continueB.setLocalScale(256, 128, 20f);
            continueB.setLocalTranslation(1200, 200, -0.5f);
            guiNode.attachChild(continueB);
        }

        public void debugButton() {
            Geometry debugB = new Geometry("Debug", new Box(1, 1, 1));
            debugB.setMaterial(assetManager.loadMaterial("Materials/SmallCreep.j3m"));
            debugB.setLocalScale(256, 128, 20f);
            debugB.setLocalTranslation(375, 500, -0.5f);
            guiNode.attachChild(debugB);
        }

        public void titleText() {
            titleText = new BitmapText(customFont, false);
            titleText.setSize(100);
            titleText.setColor(ColorRGBA.White);
            titleText.move(600, 1000, 1);
            titleText.setText("Tower Defense");
            guiNode.attachChild(titleText);
        }

        @Override
        public void stateAttached(AppStateManager stateManager) {
            game.getGUIViewport().attachScene(guiNode);
        }

        @Override
        public void stateDetached(AppStateManager stateManager) {
            game.getGUIViewport().detachScene(guiNode);
        }

        @Override
        public void cleanup() {
            super.cleanup();
            inputManager.removeListener(actionListener);
            guiNode.detachAllChildren();
        }
    }
