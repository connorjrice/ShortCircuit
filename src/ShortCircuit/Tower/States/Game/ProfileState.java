/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Controls.TowerControl;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Development
 */
public class ProfileState extends AbstractAppState {
    private FriendlyState HelperState;
    
    private float profileUpgradeTimer = 0f;
    private float profileBombTimer;

    private float profileEmptyTimer;
    private float profileChargerTimer;
    private float profileEndTimer;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.HelperState = stateManager.getState(FriendlyState.class);

    }
        @Override
    public void update(float tpf) {
        if (isProfile) {
            profileUpgradeTowers(tpf);
            profileDropBombs(tpf);
            profileEmptyTowers(tpf);
            profileBuildCharger(tpf);
            profileEndTimer(tpf);
        }
    }
    
    private void profileUpgradeTowers(float tpf) {
        if (profileUpgradeTimer > 7.0) {
            for (int i = 0; i < TowerState.getTowerList().size(); i++) { 
                TowerState.selectedTower = i;
                TowerState.upgradeTower();
            }
            profileUpgradeTimer = 0;
        }
        else {
            profileUpgradeTimer += tpf;
        }
    }
    
    private void profileDropBombs(float tpf) {
        if (profileBombTimer > .5) {
            GameState.dropBomb(EnemyState.getCreepList().get(0).getLocalTranslation(), .2f);
            profileBombTimer = 0;
        }
        else {
            profileBombTimer += tpf;
        }
    }
    
    private void profileEmptyTowers(float tpf) {
        if (profileEmptyTimer > 10.0) {
            for (int i = 0; i < TowerState.getTowerList().size(); i++) {
                TowerState.getTowerList().get(i).getControl(TowerControl.class).charges.clear();
            }
            profileEmptyTimer = 0;
        }
        else {
            profileEmptyTimer += tpf;
        }
    }
    
    private void profileBuildCharger(float tpf) {
        if (profileChargerTimer > 1.0) {
            HelperState.createCharger();
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
