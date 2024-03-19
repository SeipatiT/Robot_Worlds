package za.co.wethinkcode.toyworld.robot;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.world.Position;

public class ShootingRobot extends PositionalRobot implements IShootingRobot{

    private int currentShots;
    private final int maxShots;
    public ShootingRobot(String name, Position position, int maxShots) {
        super(name, position);
        this.maxShots = maxShots;
        this.currentShots = maxShots;
    }

    @Override
    public JSONObject getState() {
        return super.getState().put("shots",currentShots);
    }

    @Override
    public void reload(int shotsToReload) {
        currentShots += shotsToReload;
        currentShots = Math.min(currentShots,maxShots);
    }

    @Override
    public void shoot() throws EmptyMagazineException {
        if(currentShots>0){
            currentShots--;
        }else {
            throw new EmptyMagazineException();
        }

    }
}
