package za.co.wethinkcode.toyworld.robot;

import za.co.wethinkcode.toyworld.interfaces.Blockable;
import za.co.wethinkcode.toyworld.interfaces.Directional;
import za.co.wethinkcode.toyworld.interfaces.Positional;
import za.co.wethinkcode.toyworld.world.Position;

public interface IPositionalRobot extends IRobot, Blockable, Positional, Directional {

    void setDirection(Direction direction);
    void setPosition(Position position);
    Position getPosition();
    Direction getDirection();

}
