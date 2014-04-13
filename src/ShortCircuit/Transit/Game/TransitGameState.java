package ShortCircuit.Transit.Game;

import ShortCircuit.Transit.Controls.PlayerControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Connor
 */
public class TransitGameState extends AbstractAppState {

    private SimpleApplication app;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePy;
    private RigidBodyControl playerControl;
    private Node rootNode;
    private Node sceneNode = new Node("Scene");
    private Node playerNode = new Node("Player");
    private Sphere plrSphere = new Sphere(32, 32, 1f);
    private CameraNode camNode;
    private Vector3f walkDirection = new Vector3f(0,0,0);
    private Vector3f viewDirection = new Vector3f(0,0,1);
    boolean rotateLeft = false, rotateRight = false, forward = false, backward = false;
    private Geometry plrGeo;
    private AssetManager assetManager;
    private InputManager inputManager;
    private FlyByCamera flyCam;
    private Camera cam;
    private float speed = 10.0f;
    private AppStateManager stateManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.stateManager = this.app.getStateManager();
        this.rootNode = this.app.getRootNode();
        this.flyCam = this.app.getFlyByCamera();
        this.cam = this.app.getCamera();
        flyCam.setEnabled(false);
 
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        initScene();
        initSphere();
        
        initBindings();
        

    }

    private void initBindings() {
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addListener(analogListener, "Forward", "Left", "Back", "Right", "Up");
    }

    private void initSphere() {
        plrGeo = new Geometry("Player", plrSphere);
        plrGeo.setMaterial(assetManager.loadMaterial("Materials/Plain/Base.j3m"));
        playerControl = new RigidBodyControl(0f);
        playerNode.setLocalTranslation(0,3,0);
        playerNode.attachChild(plrGeo);
        playerNode.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);
        camNode = new CameraNode("CameraNode", cam);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0,5,0);
        playerNode.attachChild(camNode);
        sceneNode.attachChild(playerNode);
    }

    private void initScene() {
        sceneNode.attachChild(assetManager.loadModel("TransitScene.j3o"));
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(4f));
        sceneNode.addLight(al);
        scenePy = new RigidBodyControl(0);
        sceneNode.addControl(scenePy);
        bulletAppState.getPhysicsSpace().add(sceneNode);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0,-1.8f,0));
        rootNode.attachChild(sceneNode);
    }

    @Override
    public void update(float tpf) {
        //camNode.lookAt(plrGeo.getLocalTranslation(), Vector3f.UNIT_Z);
        //Vector3f plrTrans = plrGeo.getLocalTranslation();
        //camNode.setLocalTranslation(plrTrans.x, plrTrans.y, plrTrans.z-6);

    }
    
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String binding, boolean keyPressed, float tpf) {
            if (isEnabled()) {
                if (binding.equals("Left")) {
                    playerControl.applyImpulse(new Vector3f(-1f, 0, 0), plrGeo.getLocalTranslation());
                }
                else if (binding.equals("Right")) {
                    rotateRight = keyPressed;
                }
                else if (binding.equals("Forward")) {
                    forward = keyPressed;
                }
                else if (binding.equals("Back")) {
                    backward = keyPressed;
                }
                else if (binding.equals("Up")) {
                    //playerControl.jump();
                }
            }
        }
    };
   /* private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Forward")) {
                Vector3f v = playerNode.getLocalTranslation();
                playerNode.setLocalTranslation(v.x, v.y, v.z - value * speed);
            }
            if (name.equals("Left")) {
                Vector3f v = playerNode.getLocalTranslation();
                playerNode.setLocalTranslation(v.x+ value * speed, v.y, v.z );
            }
            if (name.equals("Right")) {
                Vector3f v = playerNode.getLocalTranslation();
                playerNode.setLocalTranslation(v.x- value * speed, v.y, v.z );
            }
            if (name.equals("Back")) {
                Vector3f v = playerNode.getLocalTranslation();
                playerNode.setLocalTranslation(v.x, v.y, v.z + value * speed);
            }
            if (name.equals("Up")) {
                Vector3f v = playerNode.getLocalTranslation();
                playerNode.setLocalTranslation(v.x, v.y+ value * speed, v.z );
            }
        }
    };*/
    
        private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Forward")) {
                Vector3f v = plrGeo.getLocalTranslation();
                plrGeo.setLocalTranslation(v.x, v.y, v.z - value * speed);

            }
            if (name.equals("Left")) {
                Vector3f v = plrGeo.getLocalTranslation();
                plrGeo.setLocalTranslation(v.x+ value * speed, v.y, v.z );
            }
            if (name.equals("Right")) {
                Vector3f v = plrGeo.getLocalTranslation();
                plrGeo.setLocalTranslation(v.x- value * speed, v.y, v.z );
            }
            if (name.equals("Back")) {
                Vector3f v = plrGeo.getLocalTranslation();
                plrGeo.setLocalTranslation(v.x, v.y, v.z + value * speed);
            }
            if (name.equals("Up")) {
                Vector3f v = plrGeo.getLocalTranslation();
                plrGeo.setLocalTranslation(v.x, v.y+ value * speed, v.z );
            }
            playerNode.setLocalTranslation(plrGeo.getLocalTranslation().add(0.0f,2.0f,-3.0f));
        }
    };

    @Override
    public void cleanup() {
        super.cleanup();

    }
}
