package sc.states.game;

import DataStructures.Graph;
import sc.pathfinding.EdgeManipulator;
import sc.pathfinding.JEdgeManipulator;
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

/**
 * @author Connor Rice
 */
public class PathfindingState extends AbstractAppState {

    private SimpleApplication app;
    private AssetManager assetManager;
    private GameState GameState;
    private Sphere targetMesh = new Sphere(32, 32, 1f);
    private Spatial floor;
    private Graph<String> worldGraph;
    private String[] blockedNodes;
    private Node rootNode;
    private Node targetNode = new Node("Targets");
    private DecimalFormat numFormatter;
    private EdgeManipulator edgeMani;
    private Material blockedMat;
    private Material targetMat;
    private Material startMat;
    private Material endMat;
    private Material pathMat;
    private ColorRGBA color;
    private Vector3f floorDimensions;
    private float precision;

    public PathfindingState() {
    }

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
        this.blockedNodes = GameState.getBlockedNodes();
        initAssets();
        createPathNodes();
        edgeMani = new JEdgeManipulator(worldGraph, targetNode, blockedNodes, precision);
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
    }

    private void createPathNodes() {
        floorDimensions = floor.getLocalScale();
        float xAxis = Math.round(floorDimensions.x);
        float yAxis = Math.round(floorDimensions.y);
        for (float i = xAxis; i > -xAxis; i -= precision) {
            for (float j = yAxis; j > -yAxis; j -= precision) {
                String is = formatRoundNumber(i);
                String js = formatRoundNumber(j);
                worldGraph.addNode(is + ',' + js);
                //createTargetGeom(is,js);
            }
        }

    }

    public void nextVec(Vector3f nextVec) {
        String formattedVec = formatVector3fString(nextVec);
        Spatial newWall = new Geometry("Wall", new Box(1, 1, 1));
        newWall.setMaterial(assetManager.loadMaterial("Materials/Circuit/Tower3Beam.j3m"));
        newWall.setLocalTranslation(roundVector3f(nextVec));
        newWall.setLocalScale(new Vector3f(0.3f, 0.2f, 0.4f));
        removeEdge(formattedVec);
        rootNode.attachChild(newWall);
    }

    private void createTargetGeom(String x, String y) {
        String targetName = x + "," + y;
        Geometry target = new Geometry(targetName, targetMesh);
        target.setMaterial(targetMat);
        target.setLocalScale(.05f, .05f, .1f);
        target.setLocalTranslation(Float.parseFloat(x), Float.parseFloat(y), 0.0f);
        target.setUserData("Name", targetName);
        //targetNode.attachChild(target);
    }

    private Vector3f roundVector3f(Vector3f in) {
        return new Vector3f(Math.round(in.x), Math.round(in.y), Math.round(in.z));
    }

    private String formatVector3fString(Vector3f in) {
        return formatRoundNumber(in.x) + "," + formatRoundNumber(in.y);
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
