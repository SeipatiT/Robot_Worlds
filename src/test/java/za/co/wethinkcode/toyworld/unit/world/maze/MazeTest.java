package za.co.wethinkcode.toyworld.unit.world.maze;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.world.Position;
import za.co.wethinkcode.toyworld.world.maze.IMaze;
import za.co.wethinkcode.toyworld.world.maze.SimpleMaze;
import za.co.wethinkcode.toyworld.world.maze.SquareObstacle;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    void testSimpleMazeHasOneDefaultSquareObstacleAtX1Y1(){
        IMaze maze = new SimpleMaze();
        assertEquals(1,maze.getObstacles().size());
        Assertions.assertEquals(new SquareObstacle(1,1),maze.getObstacles().get(0));
    }

    @Test
    void testSimpleMazeBlocksPathThroughX1Y1(){
        IMaze maze = new SimpleMaze();
        Position a = new Position(0,1);
        Position b = new Position(5,1);
        assertTrue(maze.blocksPath(a,b));
        assertTrue(maze.blocksPath(b,a));
    }

}