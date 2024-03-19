package za.co.wethinkcode.toyworld.world;
import za.co.wethinkcode.toyworld.interfaces.*;
import za.co.wethinkcode.toyworld.robot.*;
import za.co.wethinkcode.toyworld.world.maze.IMaze;
import za.co.wethinkcode.toyworld.world.maze.Obstacle;
import za.co.wethinkcode.toyworld.world.maze.RandomMaze;

import java.util.*;

public abstract class AbstractWorld implements IWorld{
    public static Object get;
    private final List<IRobot> robots = new ArrayList<>();
    private final Position TOP_LEFT,TOP_RIGHT,BOTTOM_LEFT,BOTTOM_RIGHT;
    private final Config config;
    private final IMaze maze;
    private final int size;

    public AbstractWorld(Config config){
        this(config, new RandomMaze());
    }

    public AbstractWorld(){
        this(new Config());
    }

    public AbstractWorld(Config config, IMaze maze){
        this.config = config;
        this.maze = maze;
        int xRange = config.getxLength();
        int yRange = config.getyLength();
        this.size = xRange;
       if (xRange == 2){
            this.TOP_LEFT = new Position(1,-1);
            this.TOP_RIGHT = new Position(1,1);
            this.BOTTOM_LEFT = new Position(-1,-1);
            this.BOTTOM_RIGHT = new Position(-1,1);

        }
//
        else{
            this.TOP_LEFT = new Position(xRange,-yRange);
            this.TOP_RIGHT = new Position(xRange,yRange);
            this.BOTTOM_LEFT = new Position(-xRange,-yRange);
            this.BOTTOM_RIGHT = new Position(-xRange,yRange);

        }
}

    /**
     *
     * @return the bottom left corner of the world
     */
    @Override
    public Position getBOTTOM_LEFT() {
        return BOTTOM_LEFT;
    }

    /**
     *
     * @return the bottom right corner of the world
     */
    @Override
    public Position getBOTTOM_RIGHT() {
        return BOTTOM_RIGHT;
    }

    /**
     *
     * @return the top left corner of the world.
     */
    @Override
    public Position getTOP_LEFT() {
        return TOP_LEFT;
    }

    /**
     *
     * @return the top right corner of the world.
     */
    @Override
    public Position getTOP_RIGHT() {
        return TOP_RIGHT;
    }

    /**Attempts to move the specified robot in the direction it is currently facing.
     * @param robot the robot to move
     * @param nrSteps steps to move in current direction
     * @return UpdateResponse based on outcome of the attempt SUCCESS if no problem
     * FAILED_OUTSIDE_WORLD if moving would place robot outside world, else FAILED_BLOCKED if blocked by anything
     * @throws UnknownRobotException if the specified robot is not in this world.
     */
    @Override
    public UpdateResponse updatePosition(IRobot robot, int nrSteps) throws UnknownRobotException {
        IRobot validRobot = validateRobot(robot);
        Position[] path = getCurrentDirection(validRobot).getPathFrom(getPosition(validRobot),nrSteps);
        for(Position position: path){
            if(isNewPositionAllowed(position) || objectAt(position)==validRobot){
                ((Positional) validRobot).setPosition(position);
            }else{
                if(maze.blocksPosition(position)){
                    return UpdateResponse.FAILED_OBSTRUCTED;
                }else{
                    return UpdateResponse.FAILED_OUTSIDE_WORLD;
                }
            }
        }
        return UpdateResponse.SUCCESS;
    }

    /**Updates the current direction then specified robot is facing in the world by cycling through the directions UP, RIGHT, BOTTOM, LEFT.
     * @param robot the robot to turn
     * @param turnRight if true, then turn 90 degrees to the right, else turn left.
     * @throws UnknownRobotException if the specified robot is not in the world
     */
    @Override
    public void updateDirection(IRobot robot,boolean turnRight) throws UnknownRobotException {
        IRobot validRobot = validateRobot(robot);
        if (validRobot instanceof Directional){
            Direction currentDirection = ((Directional) validRobot).getDirection();
            int nextIndex = turnRight? 1: -1;
            nextIndex += currentDirection.ordinal()+Direction.values().length;
            nextIndex = nextIndex%Direction.values().length;
            ((Directional) validRobot).setDirection(Direction.values()[nextIndex]);
            return;
        }
        throw new IllegalArgumentException("Requires a Directional Robot");
    }

    /** Attempts to fire a shot from the specified robot and find what that shot hit.
     * @param robot the robot to shoot with.
     * @return the Damageable Object that was hit from shooting, null if nothing was hit.
     * @throws Shooting.EmptyMagazineException if the specified robot is out of ammunition
     * @throws UnknownRobotException if the specified robot is not in the world
     */
    public Damageable shoot(IRobot robot) throws Shooting.EmptyMagazineException, UnknownRobotException {
        IRobot validRobot = validateRobot(robot);
        if(! (validRobot instanceof Shooting)){
            throw new IllegalArgumentException("Requires a robot with Shooting abilities");
        }
        Shooting shootingRobot = (Shooting) validRobot;
        shootingRobot.shoot();
        Position[] flightPath= (shootingRobot).getDirection().getPathFrom(shootingRobot.getPosition(),config.getShotRange());
        for (Position position: flightPath){
            Object object = objectAt(position);
            if(object == robot){
                continue;//Don't shoot yourself
            }
            if(object instanceof Damageable){
                try{
                    ((Damageable) object).takeDamage();
                } catch (Damageable.NoMoreShieldsException e) {
                    if(object instanceof IRobot){
                        ((IRobot) object).setStatus(States.DEAD);
                        robots.remove(object);
                    } else if (object instanceof Obstacle) {
                        maze.getObstacles().remove(object);
                    }
                }
                return (Damageable) object;
            }
            if(object instanceof Blockable){
                break;
            }
        }
        return null;

    }

    /** Attempts to get the position of the specified robot in the world
     * @param robot the robot to get the position of
     * @return the current position of the specified robot
     * @throws UnknownRobotException if the robot is not in this world.
     */
    @Override
    public Position getPosition(IRobot robot) throws UnknownRobotException {
        IRobot validRobot = validateRobot(robot);
        if(validRobot instanceof Positional){
            return ((Positional) validRobot).getPosition();
        }
        throw new IllegalArgumentException("Requires Positional Robot");
    }

    /** Attempts to get the current direction of the specified robot
     * @param robot the robot to get the direction of
     * @return the current direction of the specified robot
     * @throws UnknownRobotException if the robot is not in the world.
     */
    @Override
    public Direction getCurrentDirection(IRobot robot) throws UnknownRobotException {
        IRobot validRobot = validateRobot(robot);
        if(validRobot instanceof Directional){
            return ((Directional) validRobot).getDirection();
        }
        throw new IllegalArgumentException("Requires Directional Robot");
    }


    /**
     * Checks if the new position will be allowed, i.e. falls within the constraints of the world, and does not overlap an obstacle.
     * @param position the position to check
     * @return true if it is allowed, else false
     */
    @Override
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT,BOTTOM_RIGHT) && !maze.blocksPosition(position);

    }

    /** Checks if the robot is at one of the edges of the world
     * @param robot the robot to check if at edge
     * @return true if the robot's current is on one of the 4 edges of the world
     * @throws UnknownRobotException if the robot is not in this world.
     */
    @Override
    public boolean isAtEdge(IRobot robot) throws UnknownRobotException {
        IRobot validRobot = validateRobot(robot);
        if(validRobot instanceof Positional){
            Position position = ((Positional) validRobot).getPosition();
            boolean atTop = position.inArea(TOP_LEFT,TOP_RIGHT);
            boolean atRight = position.inArea(BOTTOM_RIGHT,TOP_RIGHT);
            boolean atBottom = position.inArea(BOTTOM_LEFT,BOTTOM_RIGHT);
            boolean atLeft = position.inArea(BOTTOM_LEFT,TOP_LEFT);
            return atTop || atRight || atBottom || atLeft;
        }
        throw new IllegalArgumentException("Requires a Positional robot");
    }

    /**Checks the surroundings of the specified robot and finds all the objects it can see
     *
     * @param robot the robot to check surroundings of
     * @return A Hashmap of all the visible Objects and their respective positions.
     * @throws UnknownRobotException if the specified robot is not in this world.
     */
    public HashMap<Object,Position> checkSurroundings(IRobot robot) throws UnknownRobotException {
        IRobot validRobot = validateRobot(robot);
        if(validRobot instanceof Positional){
            Position robotPosition = ((Positional) validRobot).getPosition();
            HashMap<Object,Position> objects = new HashMap<>();
            for(Direction direction : Direction.values()){
                for(Position position : direction.getPathFrom(robotPosition,config.getVisibilityLength())){
                    Object object = objectAt(position);
                    if(object == robot){
                        continue;//no need to look at ourselves
                    }
                    if (object!=null && !objects.containsKey(object)){
                        objects.put(object,position);
                    }
                    if(object instanceof Blockable){
                        break;
                    }
                }
            }
            return objects;
        }
        throw new IllegalArgumentException("Requires a Positional robot");
    }

    /** Searches through objects in the world to locate the object at the specified position
     * @param position the position to check for an object at
     * @return the object located at that position returns null if no object found
     */
    private Object objectAt(Position position){
        List<Obstacle> obstacles = getMaze().getObstacles();
        for (Obstacle obstacle : obstacles){
            if (obstacle.blocksPosition(position)){
                return obstacle;
            }
        }
        for (IRobot robot : robots){
            if(robot instanceof Positional){
                if(position.equals(((Positional) robot).getPosition())){
                    return robot;
                }
            }
        }
        if(isAtEdge(position)){
            return new Edge(position.getX(),position.getY());
        }
        return null;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public void reset() {

    }

    @Override
    public IMaze getMaze() {
        return maze;
    }

    /**Checks if the specified position falls on any of the edges of the world
     * @param position the position to check
     * @return true if on any of the edges else false
     */
    public boolean isAtEdge(Position position){
        boolean topEdge = position.isIn(TOP_LEFT,TOP_RIGHT);
        boolean rightEdge = position.isIn(TOP_RIGHT,BOTTOM_RIGHT);
        boolean bottomEdge = position.isIn(BOTTOM_LEFT,BOTTOM_RIGHT);
        boolean leftEdge = position.isIn(TOP_LEFT,BOTTOM_LEFT);
        return topEdge||rightEdge||bottomEdge||leftEdge;
    }

    /**Attempts to create a robot in the world with the specified name
     * @param name the name of the robot to create
     * @return the robot that was created
     * @throws WorldFullException if there is no valid locations to create a robot at
     */

    @Override
    public IRobot addRobot(String name) throws WorldFullException {
        ArrayList<Position> positions = new ArrayList<>();
//        int y = 0;
        for (int x = BOTTOM_LEFT.getX(); x <= TOP_RIGHT.getX(); x++){
            for(int y = BOTTOM_LEFT.getY(); y<= TOP_RIGHT.getY(); y++){
                positions.add(new Position(x,y));
            }
        }
        IRobot added = newRobot(name,positions);
        if (added != null){
            return added;
        }
        throw new WorldFullException();
    }

    private IRobot newRobot(String name, ArrayList<Position> positions){
        Collections.shuffle(positions);
        for(Position position : positions){
            if (objectAt(position)== null){
                IRobot robot = new DamageableRobot(
                        name,
                        position,
                        config.getMaxShots(),
                        config.getMaxShields());
                robots.add(robot);
                return robot;
            }
        }
        return null;
    }

//    @Override
//    public IRobot addRobot(String name) throws WorldFullException{
//        ArrayList<Position> positions = new ArrayList<>();
//
////        int y = 0;
//        if (size == 1){
//            positions.add(new Position(0,0));
//        } else if (size == 2) {
//            for (int x = BOTTOM_LEFT.getX(); x <= TOP_RIGHT.getX(); x++) {
//                for (int y = BOTTOM_LEFT.getY(); y <= TOP_RIGHT.getY(); y++) {
//
//                    positions.add(new Position(x, y));
//
//        }}}
//        else {
//            for (int x = BOTTOM_LEFT.getX(); x <= TOP_RIGHT.getX(); x++) {
//                for (int y = BOTTOM_LEFT.getY(); y <= TOP_RIGHT.getY(); y++) {
//
//                    positions.add(new Position(0, 0));
//                }
//            }
//        }
////        ArrayList<Position> positions = new ArrayList<>();
////        int x = 0;
////        int y = 0;
////        positions.add(new Position(x,y));
//        Collections.shuffle(positions);
//        for(Position position : positions){
//            if (objectAt(position) != new Edge(position.getX(),position.getY()) && robots.size() != positions.size()){
//                IRobot robot = new DamageableRobot(
//                        name,
//                        position,
//                        config.getMaxShots(),
//                        config.getMaxShields());
//                System.out.println(robots.size());
//                System.out.println(positions.size());
//
//                robots.add(robot);
////                robots.add(robot);
//                System.out.println(robots + "lis of robo");
//                return robot;
//            }
//        }
//        throw new WorldFullException();
//    }

    /** Checks whether a robot is a part of this world and returns the robot in this world if it is
     * @param robot the robot to check
     * @return the robot that is in the world
     * @throws UnknownRobotException if the robot is not in this world
     */
    private IRobot validateRobot(IRobot robot) throws UnknownRobotException {
        int robotIndex = robots.indexOf(robot);
        if(robotIndex == -1){
            throw new UnknownRobotException();
        }
        return robots.get(robotIndex);
    }

    public  List<IRobot> getRobot(){
        return this.robots;
    }

}
