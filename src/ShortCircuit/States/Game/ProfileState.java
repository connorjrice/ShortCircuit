package ShortCircuit.States.Game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Development
 */
public class ProfileState extends AbstractAppState {
    private FriendlyState FriendlyState;
    
    private float profileUpgradeTimer = 0f;
    private float profileBombTimer;

    private float profileEmptyTimer;
    private float profileChargerTimer;
    private float profileEndTimer;
    private SimpleApplication app;
    private AppStateManager stateManager;
    private EnemyState EnemyState;
    private GraphicsState GraphicsState;
    
    public ProfileState() {}
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.FriendlyState = this.stateManager.getState(FriendlyState.class);
        this.EnemyState = this.stateManager.getState(EnemyState.class);

    }
    
    @Override
    public void update(float tpf) {
        profileUpgradeTowers(tpf);
        profileDropBombs(tpf);
        profileEmptyTowers(tpf);
        profileBuildCharger(tpf);
        profileEndTimer(tpf);
    }
    
    private void profileUpgradeTowers(float tpf) {
        if (profileUpgradeTimer > 7.0) {
            for (int i = 0; i < FriendlyState.getTowerList().size(); i++) { 
                FriendlyState.selectedTower = i;
                FriendlyState.upgradeTower();
            }
            profileUpgradeTimer = 0;
        }
        else {
            profileUpgradeTimer += tpf;
        }
    }
    
    private void profileDropBombs(float tpf) {
        if (profileBombTimer > .5) {
            GraphicsState.dropBomb(EnemyState.getCreepList().get(0).getLocalTranslation(), .2f);
            profileBombTimer = 0;
        }
        else {
            profileBombTimer += tpf;
        }
    }
    
    private void profileEmptyTowers(float tpf) {
        if (profileEmptyTimer > 10.0) {
            for (int i = 0; i < FriendlyState.getTowerList().size(); i++) {
                FriendlyState.getTowerList().get(i).getControl().charges.clear();
            }
            profileEmptyTimer = 0;
        }
        else {
            profileEmptyTimer += tpf;
        }
    }
    
    private void profileBuildCharger(float tpf) {
        if (profileChargerTimer > 1.0) {
            FriendlyState.createCharger();
            profileChargerTimer = 0;
        }
        else {
            profileChargerTimer += tpf;
        }
    }
    
    private void profileEndTimer(float tpf) {
        if (profileEndTimer > 30) {
            app.stop();
        }
        else {
            profileEndTimer += tpf;
        }
    }
    
    
    @Override
    public void cleanup() {
        super.cleanup();

    }
}
