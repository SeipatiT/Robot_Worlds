package za.co.wethinkcode.toyworld.robot;


import org.json.JSONObject;
import za.co.wethinkcode.toyworld.world.Position;

public class PositionalRobot extends Robot implements IPositionalRobot {
    private Position position;
    private Direction direction;
    public PositionalRobot(String name,Position position) {
        super(name);
        this.position = position;
        this.direction = Direction.NORTH;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        return position.isIn(a,b);
    }

    @Override
    public boolean blocksPosition(Position position) {
        return position == this.position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Direction getDirection(){
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public JSONObject getState() {
        return super.getState().put("position",this.position)
                .put("direction",this.direction);
    }
}
