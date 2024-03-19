package za.co.wethinkcode.toyworld.world.maze;

import za.co.wethinkcode.toyworld.world.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMaze implements IMaze{
    private final List<Obstacle> obstacles = new ArrayList<>();

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        for (Obstacle obstacle : obstacles){
            if (obstacle.blocksPath(a,b)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean blocksPosition(Position position) {
        for(Obstacle obstacle : obstacles){
            if (obstacle.blocksPosition(position)){
                return true;
            }
        }
        return false;
    }
}
