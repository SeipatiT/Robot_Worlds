package za.co.wethinkcode.toyworld.unit.world;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.interfaces.Damageable;
import za.co.wethinkcode.toyworld.interfaces.Shooting;
import za.co.wethinkcode.toyworld.robot.Direction;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.robot.PositionalRobot;
import za.co.wethinkcode.toyworld.world.*;
import za.co.wethinkcode.toyworld.world.maze.EmptyMaze;
import za.co.wethinkcode.toyworld.world.maze.SimpleMaze;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class IWorldTest {

    @Test
    void testCentreIsX0Y0(){
        Assertions.assertEquals(IWorld.CENTRE,new Position(0,0));
    }

//    @Test
//    void testWorldSizeIsConfigurable(){
//        Config config1 = new Config("-p",5000,"-s",4);
//        Config config2 = new Config("-p",5000,"-s",10);
//        IWorld world1 = new AbstractWorld(config1) {};
//        IWorld world2 = new AbstractWorld(config2){};
//        assertEquals(new Position(2,2), world1.getTOP_RIGHT(),"edges are at half the config's specified lengths");
//        assertEquals(new Position(5,5), world2.getTOP_RIGHT(),"edges are at half the config's specified lengths");
//        Position inside2AndOutside1 = new Position( 5,5);
//        assertFalse(world1.isNewPositionAllowed(inside2AndOutside1));
//        assertTrue(world2.isNewPositionAllowed(inside2AndOutside1));
//    }

    @Test
    void testUpdateDirection() throws IWorld.WorldFullException, IWorld.UnknownRobotException {
        IWorld world = new AbstractWorld() {};
        IRobot test =world.addRobot("Test");
        assertEquals(Direction.NORTH, world.getCurrentDirection(test),"default Direction is NORTH");
        world.updateDirection(test,true);
        assertEquals(Direction.EAST, world.getCurrentDirection(test));
        world.updateDirection(test,false);
        assertEquals(Direction.NORTH, world.getCurrentDirection(test));
    }


//    @Test
//    void testCheckSurroundingsDefaultCanSeeAll4Edges() throws IWorld.WorldFullException, IWorld.UnknownRobotException {
//        IWorld world = new AbstractWorld(new Config(), new EmptyMaze()) {};
//        IRobot test=world.addRobot("test");
//        HashMap<Object,Position> objectPositionHashMap = world.checkSurroundings(test);
//        assertEquals(4,objectPositionHashMap.size());
//        for(Object object : objectPositionHashMap.keySet()){
//            assertTrue(object instanceof Edge);
//        }
//    }

//    @Test
//    void testUpdatePositionAllowed() throws IWorld.UnknownRobotException, IWorld.WorldFullException {
//        IWorld world = new AbstractWorld(new Config(),new EmptyMaze()) {};
//        IRobot test =world.addRobot("name");
//        ((PositionalRobot) test).setPosition(new Position(0,0));
//        world.updatePosition(test,10);
//        assertEquals(new Position(0,10),world.getPosition(test));
//    }

    @Test
    void testUpdatePositionOutsideArea() throws IWorld.UnknownRobotException, IWorld.WorldFullException {
        IWorld world = new AbstractWorld(new Config("-p",5000,"-s", 2),new EmptyMaze()) {};
        IRobot test = world.addRobot("test");
        ((PositionalRobot) test).setPosition(new Position(0,0));
        assertEquals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD,world.updatePosition(test,20));
//        assertEquals(new Position(0,1),world.getPosition(test));
    }

//    @Test
//    void testUpdatePositionBlocked() throws IWorld.UnknownRobotException, IWorld.WorldFullException {
//        IWorld world = new AbstractWorld(new Config(), new SimpleMaze()) {};
//        IRobot test = world.addRobot("test");
//        ((PositionalRobot) test).setPosition(new Position(1,10));
//        assertEquals(IWorld.UpdateResponse.FAILED_OBSTRUCTED,world.updatePosition(test,-10));
//        assertEquals(new Position(1,6),world.getPosition(test));
//    }

    @Test
    void testShootMiss() throws Shooting.EmptyMagazineException, IWorld.UnknownRobotException, IWorld.WorldFullException {
        IWorld world = new AbstractWorld(
                new Config("-p",5000,"-s",15),new EmptyMaze()) {};
        IRobot test = world.addRobot("test");
        assertNull(world.shoot(test), "shoot returns null on miss");
    }

    @Test
    void testShootHit() throws IWorld.WorldFullException, Shooting.EmptyMagazineException, IWorld.UnknownRobotException {
        IWorld world = new AbstractWorld(new Config(),new EmptyMaze()) {};
        IRobot testShooter = world.addRobot("test");
        ((PositionalRobot)testShooter).setPosition(new Position(1,0));
        IRobot testHit = world.addRobot("test");
        ((PositionalRobot)testHit).setPosition(new Position(1,6));
        assertEquals(testHit,world.shoot(testShooter));

    }
    @Test
    void testShootBlockedByObstacle() throws IWorld.WorldFullException, Shooting.EmptyMagazineException, IWorld.UnknownRobotException {
        IWorld world = new AbstractWorld(new Config(),new SimpleMaze()) {};
        IRobot testShooter = world.addRobot("test");
        ((PositionalRobot)testShooter).setPosition(new Position(1,0));
        IRobot testHit = world.addRobot("test");
        ((PositionalRobot)testHit).setPosition(new Position(1,6));
        assertNull(world.shoot(testShooter));
    }

//    @Test
//    void testShootRangeMatters() throws IWorld.WorldFullException, Shooting.EmptyMagazineException, IWorld.UnknownRobotException {
//        IWorld world = new AbstractWorld(new Config("-p",5000,"-s",15),new EmptyMaze()) {};
//        IRobot testShooter = world.addRobot("test");
//        ((PositionalRobot)testShooter).setPosition(new Position(1,0));
//        IRobot testHit = world.addRobot("test");
//        ((PositionalRobot)testHit).setPosition(new Position(1,6));
//        assertNull(world.shoot(testShooter));
//
//    }

    @Test
    void testCannotShootWhenOutOfShots(){
        IWorld world = new AbstractWorld(new Config("-p",5000,"-s",15)) {};
    }

}