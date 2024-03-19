package za.co.wethinkcode.toyworld.robot;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.interfaces.Damageable;
import za.co.wethinkcode.toyworld.world.Position;

public class DamageableRobot extends ShootingRobot implements Damageable {
    
    private final int maxShields;
    private int currentShields;
    public DamageableRobot(String name, Position position, int maxShots, int maxShields) {
        super(name, position, maxShots);
        this.maxShields = maxShields;
        currentShields = maxShields;
    }

    @Override
    public void takeDamage() throws NoMoreShieldsException {
        if(currentShields ==0){
            throw new NoMoreShieldsException();
        }
        currentShields--;
    }

    @Override
    public JSONObject getState() {
        return super.getState().put("shields",currentShields);
    }

    @Override
    public void repair(int shieldsToRepair) {
        currentShields += shieldsToRepair;
        currentShields = Math.min(currentShields,maxShields);
    }
}
