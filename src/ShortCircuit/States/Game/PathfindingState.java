package ShortCircuit.States.Game;

import ShortCircuit.DataStructures.Graph;
import ShortCircuit.DataStructures.Heuristics.jMEHeuristic;
import ShortCircuit.DataStructures.Interfaces.PathFinder;
import ShortCircuit.PathFinding.AStarPathFinder;
import ShortCircuit.PathFinding.JMEEdgeBuilder;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * TODO: Method for creating plots on the map
 * TODO: A* pathfinding
 * @author Connor Rice
 */
public class PathfindingState extends AbstractAppState {
    private SimpleApplication app;
    private Node rootNode;
    private Spatial floor;
    private Graph<String> worldGraph;
    private float precision;
    private jMEHeuristic Heuristic;
    private AStarPathFinder pathFinder;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.floor = this.rootNode.getChild("Floor");
        this.worldGraph = new Graph<String>(1200);
        this.Heuristic = new jMEHeuristic();
        this.precision = .5f;
        createPathNodes();
        createPathFinder();
    }

    private void createPathNodes() {
        Vector3f translation = floor.getLocalTranslation();
        Vector3f dimensions = floor.getLocalScale();
        for (float i = dimensions.x; i > -dimensions.x; i-=precision) {
            for (float j = dimensions.y; j > -dimensions.y; j-=precision) {
                worldGraph.addNode(toString(i)+','+toString(j));
            }
        }
        addEdges();
    }
    
    private void createPathFinder() {
        pathFinder = new AStarPathFinder(Heuristic, worldGraph, 100);
    }
    
    public PathFinder getPathFinder() {
        return pathFinder;
    }
    
    private void addEdges() {
        JMEEdgeBuilder edgeBuilder = new JMEEdgeBuilder(worldGraph, rootNode, precision);
        edgeBuilder.addEdges();
            

    }
    
    private String toString(Object o) {
        return o.toString();
    }
    
    @Override
    public void update(float tpf) {
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
}
