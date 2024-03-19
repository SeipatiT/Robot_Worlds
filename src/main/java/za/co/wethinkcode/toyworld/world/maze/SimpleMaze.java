package za.co.wethinkcode.toyworld.world.maze;

import za.co.wethinkcode.toyworld.world.Position;

import java.util.List;

public class SimpleMaze extends AbstractMaze{

    public SimpleMaze(){
        getObstacles().add(new SquareObstacle(1,1));
    }

}
