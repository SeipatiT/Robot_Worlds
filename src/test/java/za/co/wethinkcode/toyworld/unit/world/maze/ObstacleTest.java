package za.co.wethinkcode.toyworld.unit.world.maze;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.world.Position;
import za.co.wethinkcode.toyworld.world.maze.Obstacle;
import za.co.wethinkcode.toyworld.world.maze.SquareObstacle;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    void testEquality(){
        Obstacle x = new SquareObstacle(1,2);
        Obstacle y = new SquareObstacle(1,2);
        Obstacle z = new SquareObstacle(2,1);
        assertEquals(x,y);
        assertNotEquals(x,z);
    }

    @Test
    void testDefaultSizeIs5(){
        Obstacle x = new SquareObstacle(0,0);
        assertEquals(5,x.getSize());
    }

    @Test
    void testCanProvideSizeOfObstacle(){
        Obstacle x = new SquareObstacle(2,2,3);
        assertEquals(4,x.getSize());
    }

    @Test
    void testBottomLeftIsSuppliedValues(){
        Obstacle x = new SquareObstacle(3,5);
        assertEquals(3,x.getBottomLeftX());
        assertEquals(5,x.getBottomLeftY());
    }

    @Test
    void testBlocksBottomLeft(){
        Obstacle x = new SquareObstacle(3,4);
        assertTrue(x.blocksPosition(new Position(3,4)));
    }

    @Test
    void testDoesNotBlockPositionBelowBottom(){
        Obstacle x = new SquareObstacle(3,4);
        assertFalse(x.blocksPosition(new Position(3,3)));
    }

    @Test
    void testDoesNotBlockPositionLeftOfLeft(){
        Obstacle x = new SquareObstacle(3,4);
        assertFalse(x.blocksPosition(new Position(2,4)));
    }

    @Test
    void testBlocksPathThroughObstacleBothWays(){
        Obstacle obstacle = new SquareObstacle(3,4);
        Position a = new Position(3,10);
        Position b = new Position(3,-10);
        Position c = new Position(0,4);
        Position d = new Position(10,4);
        assertTrue(obstacle.blocksPath(a,b));
        assertTrue(obstacle.blocksPath(b,a));
        assertTrue(obstacle.blocksPath(c,d));
        assertTrue(obstacle.blocksPath(d,c));
    }


}