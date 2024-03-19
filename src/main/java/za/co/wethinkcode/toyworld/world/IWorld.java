package za.co.wethinkcode.toyworld.world;

import za.co.wethinkcode.toyworld.interfaces.Damageable;
import za.co.wethinkcode.toyworld.interfaces.Resettable;
import za.co.wethinkcode.toyworld.interfaces.Shooting;
import za.co.wethinkcode.toyworld.world.maze.IMaze;
import za.co.wethinkcode.toyworld.robot.Direction;
import za.co.wethinkcode.toyworld.robot.IRobot;

import java.util.HashMap;

/**
 * An Interface of World Functionality
 */
public interface IWorld extends Resettable {

    static void setStatus(String s) {
    }

    /**
     * Enum that indicates response for updatePosition request
     */
    enum UpdateResponse {
        SUCCESS, //position was updated successfully
        FAILED_OUTSIDE_WORLD, //robot will go outside world limits if allowed, so it failed to update the position
        FAILED_OBSTRUCTED, //robot obstructed by at least one obstacle, thus cannot proceed.
    }
    Position CENTRE = new Position(0,0);

    Position getTOP_LEFT();
    Position getTOP_RIGHT();
    Position getBOTTOM_RIGHT();
    Position getBOTTOM_LEFT();

    /**
     * Attempts to move the specified robot in the direction it is currently facing.
     *
     * @param robot   the robot to move
     * @param nrSteps steps to move in current direction
     * @return UpdateResponse based on outcome of the attempt SUCCESS if no problem
     * FAILED_OUTSIDE_WORLD if moving would place robot outside world, else FAILED_BLOCKED if blocked by anything
     * @throws UnknownRobotException if the specified robot is not in this world.
     */
    UpdateResponse updatePosition(IRobot robot, int nrSteps) throws UnknownRobotException;

    /**Updates the current direction then specified robot is facing in the world by cycling through the directions UP, RIGHT, BOTTOM, LEFT.
     * @param robot the robot to turn
     * @param turnRight if true, then turn 90 degrees to the right, else turn left.
     * @throws UnknownRobotException if the specified robot is not in the world
     */
    void updateDirection(IRobot robot,boolean turnRight) throws UnknownRobotException;

    /**
     * Retrieves the current position of the  specified robot
     * @throws UnknownRobotException if the specified robot is not in this world.
     */
    Position getPosition(IRobot robot) throws UnknownRobotException;

    /**
     * Gets the current direction the specified robot is facing in relation to a world edge.
     * @return Direction.NORTH, EAST, SOUTH, or WEST
     * @throws UnknownRobotException if the specified robot is not in this world.
     */
    Direction getCurrentDirection(IRobot robot) throws UnknownRobotException;

    /**
     * Checks if the new position will be allowed, i.e. falls within the constraints of the world, and does not overlap an obstacle.
     * @param position the position to check
     * @return true if it is allowed, else false
     */
    boolean isNewPositionAllowed(Position position);

    /**
     * Checks if the robot is at one of the edges of the world
     * @return true if the robot's current is on one of the 4 edges of the world
     * @throws UnknownRobotException if the robot is not in this world
     */
    boolean isAtEdge(IRobot robot) throws UnknownRobotException;

    /**
     * Reset the world by:
     * - moving current robot position to center 0,0 coordinate
     * - removing all obstacles
     * - setting current direction to UP
     */
    void reset();

    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    IMaze getMaze();

    IRobot addRobot(String name)throws WorldFullException;



    /** Attempts to fire a shot from the specified robot and find what that shot hit.
     * @param robot the robot to shoot with.
     * @return the Damageable Object that was hit from shooting, null if nothing was hit.
     * @throws Shooting.EmptyMagazineException if the specified robot is out of ammunition
     * @throws UnknownRobotException if the specified robot is not in the world
     */
    Damageable shoot(IRobot robot) throws Shooting.EmptyMagazineException, UnknownRobotException;

    /**Checks the surroundings of the specified robot and finds all the objects it can see
     *
     * @param robot the robot to check surroundings of
     * @return A Hashmap of all the visible Objects and their respective positions.
     * @throws UnknownRobotException if the specified robot is not in this world.
     */
    HashMap<Object,Position> checkSurroundings(IRobot robot) throws UnknownRobotException;

    abstract class WorldException extends Exception{
        public WorldException(String message){
            super(message);
        }
    }

    class UnknownRobotException extends WorldException{
        public UnknownRobotException(){
            super("Robot not in world");
        }
    }

    class WorldFullException extends WorldException{
        public WorldFullException(){
            super("No more space in this world");
        }

    }


    Config getConfig();

}
