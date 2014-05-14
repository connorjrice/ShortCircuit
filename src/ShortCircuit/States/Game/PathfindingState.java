package ShortCircuit.States.Game;

import ShortCircuit.DataStructures.Graph;
import ShortCircuit.DataStructures.Queue;
import ShortCircuit.PathFinding.JEdgeManipulator;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * TODO: Make walls, make towers "solid"
 * @author Connor Rice
 */
public class PathfindingState extends AbstractAppState {
    private SimpleApplication app;
    private Node rootNode;
    private Node targetNode = new Node("Targets");
    private Spatial floor;
    private Graph<String> worldGraph;
    private Queue<String> wallQueue;
    private float precision;
    private AssetManager assetManager;
    private Sphere targetMesh = new Sphere(32, 32, 1f);
    private Material targetMat;
    private Material startMat;
    private Material endMat;
    private Material pathMat;
    private ColorRGBA color;
    private Material blockedMat;
    private Vector3f floorDimensions;
    private DecimalFormat numFormatter;
    private String[] blockedGeom;
    private GameState GameState;
    private HashMap geomHash;
    private JEdgeManipulator edgeMani;
    private GraphicsState GraphicsState;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();
        this.floor = this.rootNode.getChild("Floor");
        this.worldGraph = new Graph<String>(1400);
        this.precision = 1.0f; // works with 1.0f, .5f
        this.GameState = stateManager.getState(GameState.class);
        this.GraphicsState = stateManager.getState(GraphicsState.class);
        this.geomHash = GameState.getGeomHash();
        initAssets();
        createPathNodes();
        edgeMani = new JEdgeManipulator(worldGraph, targetNode, geomHash, precision);
        addGeometry();
        addEdges();
        rootNode.attachChild(targetNode);
    }
    
    private void initAssets() {
        targetMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        targetMat.setColor("Color", ColorRGBA.White);

        startMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        startMat.setColor("Color", ColorRGBA.Yellow);

        endMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        endMat.setColor("Color", ColorRGBA.Pink);

        blockedMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        blockedMat.setColor("Color", ColorRGBA.Black);

        pathMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        pathMat.setColor("Color", color);
        
        numFormatter = new DecimalFormat("0.0");
        wallQueue = new Queue<String>();
    }

    private void createPathNodes() {
        //Vector3f translation = floor.getLocalTranslation();
        floorDimensions = floor.getLocalScale();
        float xAxis = Math.round(floorDimensions.x);
        float yAxis = Math.round(floorDimensions.y);
        for (float i = xAxis; i > -xAxis; i-=precision) {
            for (float j = yAxis; j > -yAxis; j-=precision) {
                String is = formatRoundNumber(i);
                String js = formatRoundNumber(j);
                worldGraph.addNode(is+','+js);
                createTargetGeom(is,js);
            }
        }

    }
    
    public void nextVec(Vector3f nextVec) {
        String formattedVec = formatVector3f(nextVec);
        Spatial removedNode = rootNode.getChild(formattedVec);
        removedNode = new Geometry("Post", new Box(1,1,1));
        removedNode.setMaterial(GraphicsState.getMaterial("Tower3Beam"));
        removedNode.setLocalTranslation(nextVec);
        removedNode.setLocalScale(new Vector3f(0.3f,0.2f,0.4f));
        removeEdge(formattedVec);
        rootNode.attachChild(removedNode);
    }
    

    private void createTargetGeom(String x, String y) {
        String targetName = x + "," + y;
        Geometry target = new Geometry(targetName, targetMesh);
        target.setMaterial(targetMat);
        target.setLocalScale(.05f, .05f, .1f);
        target.setLocalTranslation(Float.parseFloat(x), Float.parseFloat(y), 0.0f);
/*        if (geomHash.get(targetName)!= null) {
            target.setUserData("Name", targetName);
        } else {
            target.setUserData("Name", "Unblocked");
        }*/
        target.setUserData("Name", targetName);
        targetNode.attachChild(target);
    }
    
    private void addGeometry() {
        
    }
    
    private String formatVector3f(Vector3f in) {
        return formatRoundNumber(in.x)+","+formatRoundNumber(in.y);
    }
    
    private String formatNumber(Float value) {
        return numFormatter.format(value);
    }
    
    private String formatRoundNumber(Float value) {
        return numFormatter.format(Math.round(value));
    }
    
    private void addEdges() {
        edgeMani.addInitialEdges();
    }
    
    private void removeEdge(String target) {
        edgeMani.remove4Edge(target);
        
    }
    

    public Graph getWorldGraph() {
        return worldGraph;
    }
    
    private String toString(Object o) {
        return o.toString();
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
}
