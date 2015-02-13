package ScSDK.GUI;

import ScSDK.TowerMapXML;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.extras.OSRViewPort;
import tonegod.gui.controls.lists.ComboBox;
import tonegod.gui.controls.text.TextField;
import tonegod.gui.controls.windows.DialogBox;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;

/**
 *
 * @author Connor Rice
 */
public class SDKGUI extends AbstractAppState {
    private SimpleApplication app;
    private Node guiNode;
    private Screen screen;
    private Window MainWindow;
    private AppStateManager stateManager;
    private int width;
    private int height;
    private Vector2f buttonSize;
    private Vector2f dialogSize;
    private int scaler;
    private ComboBox edit;
    private ComboBox file;
    private OSRViewPort vp;
    private ComboBox open;
    private String levelString = "";
    private ButtonAdapter runLevel;
    private ButtonAdapter returnButton;
    private TowerMapXML tMS;
    private ButtonAdapter buildAll;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.stateManager = this.app.getStateManager();
        this.width = this.app.getContext().getSettings().getWidth();
        this.height = this.app.getContext().getSettings().getHeight(); 
        buttonSize = new Vector2f(width / 16, height / 32);
        dialogSize = new Vector2f(width / 4, height / 4);
        
        scaler = width / 13;
        initScreen();
        setupGUI();

    }
    
    private void initScreen() {
        //screen = new Screen(app, "StyleDefs/MapXML/style_map.gui.xml");
        screen = new Screen(app);
        guiNode.addControl(screen);
        //screen.setUseKeyboardIcons(true);
    }
    
    private void setupGUI() {
        mainWindow();
        fileDrop();
        editDrop();
        levelSelectDrop();
        xmlTextField();
        runLevelButton();
    }
    
    private void mainWindow() {
        MainWindow = new Window(screen, new Vector2f(0,0), 
                new Vector2f(width, height));
        MainWindow.setWindowTitle("ShortCircuit Map XML");
        MainWindow.setWindowIsMovable(false);
        //MainWindow.setIsResizable(false);
        //MainWindow.setEffectZOrder(false);
        screen.addElement(MainWindow);
    }
    
    private void openDialog() {
        DialogBox dialog = new DialogBox(screen, "alert", 
                new Vector2f(width/4,height/3), dialogSize) {
            @Override
            public void onButtonCancelPressed(MouseButtonEvent evt, 
            boolean toggled) {
                screen.removeElement(this);
            }

            @Override
            public void onButtonOkPressed(MouseButtonEvent evt, 
            boolean toggled) {
                screen.removeElement(this);
            }

        };
        dialog.setWindowTitle("Open file...");
        TextField text = new TextField(screen, "text", new Vector2f(35,50));
        dialog.addChild(text);
        screen.addElement(dialog);
    }
    
    private void levelSelectDrop() {
        open = new ComboBox(screen, "open", new Vector2f(scaler*2, 35), 
                new Vector2f(scaler*3, buttonSize.y)) {
            @Override
            public void onChange(int selectedIndex, Object value) {
                setLevelString(value);
            }
        };
        open.setText("Levels...");
        buildLevels();
        MainWindow.addChild(open);
    }
    
    private void buildLevels() {
        File[] levelXMLfiles = new File("assets/XML/").listFiles();
        for (File file : levelXMLfiles){
            if (file.isFile()) {
                open.addListItem(file.getName(), file.getName());
            }
        }
    }
    
    private void runLevelButton() {
        runLevel = new ButtonAdapter(screen, "run", 
                new Vector2f(scaler *5, 35), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, 
            boolean toggled) {
                buildLevel();
            }
        };
        runLevel.setText("Run...");
        MainWindow.addChild(runLevel);
    }
    
    private void setLevelString(Object level) {
       levelString = (String) level;
    }
    
    private void buildLevel() {
        tMS = new TowerMapXML(levelString, this);
        returnButton();
        stateManager.attach(tMS);
        MainWindow.hide();

        //tMS.getRootNode();
    }
    
    private void buildAll() {
        buildAll = new ButtonAdapter(screen, "buildall", 
                new Vector2f(scaler *5, 35), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, 
            boolean toggled) {
                //for ( : open.get)
                buildLevel();
            }
        };
        buildAll.setText("Run...");
        MainWindow.addChild(buildAll);
    }
    
    private void returnButton() {
        returnButton = new ButtonAdapter(screen, "return", 
                new Vector2f(10, 10), buttonSize) {
            @Override
            public void onButtonMouseLeftDown(MouseButtonEvent evt, 
            boolean toggled) {
                String userHome = System.getProperty("user.home");
                BinaryExporter exporter = BinaryExporter.getInstance();
                File file = new File(
                        userHome+"/Documents/ShortCircuit/Assets/Models/"
                        +levelString+".j3o");
                try {
                    exporter.save(tMS.getRootNode(), file);
                } catch (IOException ex) {
            
                }
                stateManager.detach(tMS);
                
                MainWindow.show();
                buildLevels();
                screen.removeElement(this);
            }
        };
        returnButton.setText("Return");
        screen.addElement(returnButton);
    }
    
    private void checkLevelFormat() {
        
    }
    
    private void fileDrop() {
        file = new ComboBox(screen, "file", new Vector2f(10,35), buttonSize) {
            @Override
            public void onChange(int selectedIndex, Object value) {
                
            }
        };
        file.addListItem("New...", "NEW");
        file.addListItem("Open...", "OPEN");
        file.addListItem("Save...", "SAVE");
        file.setText("File...");
        MainWindow.addChild(file);
    }
    
    private void editDrop() {
        edit = new ComboBox(screen, "edit", new Vector2f(scaler,35),
                buttonSize) {
            @Override
            public void onChange(int selectedIndex, Object value) {
              
            }
        };
        edit.setText("Edit...");
        MainWindow.addChild(edit);
    }
    
    public void initViewPort(Node rootNode) {
       /* vp = new OSRViewPort(screen, "Preview", new Vector2f(100,100), 
                new Vector2f(640,480), new Vector4f(1,1,1,1), "Materials/clear.png");
        vp.setOSRBridge(rootNode, 400, 400);
        vp.setCameraDistance(tMS.getBuildState().getCameraLocation().z); */
    }
    
    private void xmlTextField() {
       
    }
    

    private void fromTemplateButton() {
        
    }
    
    @Override
    public void update(float tpf) {
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
}
