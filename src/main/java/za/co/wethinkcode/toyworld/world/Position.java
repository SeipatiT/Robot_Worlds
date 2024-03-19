package za.co.wethinkcode.toyworld.world;

import za.co.wethinkcode.toyworld.robot.Direction;

import java.util.Objects;

public class Position {
    private final int x;
    private final int y;

    /**
     * Creates a Position at the specified co-ordinates
     * @param x the x co-ordinate
     * @param y the y co-ordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the x co-ordinate
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return the y co-ordinate
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    /**
     * Checks whether this position is within the area bounded by the positions provided
     * @param topLeft the Top Left Position of the area
     * @param bottomRight the Bottom Right Position of the area
     * @return true if in the area else false
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    /**
     * Checks whether this position is within the area bounded by the positions provided
     * @param bottomLeft the Bottom Left Position of the area
     * @param topRight the Top Right Position of the area
     * @return true if in the area else false
     */
    public boolean inArea(Position bottomLeft,Position topRight){
        Position topLeft = new Position(bottomLeft.getX(), topRight.getY());
        Position bottomRight = new Position(topRight.getX(),bottomLeft.getY());
        return isIn(topLeft,bottomRight);
    }

    /**
     * Checks what direction another position is from this position (only works for cardinal directions)
     * @param position the Position to calculate direction to
     * @return the Direction to the position from this position
     */
    public Direction directionOf(Position position){
        int yDistance = Math.abs(this.y-position.getY());
        int xDistance = Math.abs(this.x-position.getX());
        if (yDistance>=xDistance){
            return this.y < position.getY()? Direction.NORTH : Direction.SOUTH;
        }
        return this.x < position.getX() ? Direction.EAST: Direction.WEST;
    }

    /** Checks the Manhattan distance from this position to the specified position.
     * @param position the position to find distance to.
     * @return the distance between the positions.
     */
    public int distanceTo(Position position){
        return Math.abs(getY()- position.getY())+Math.abs(getX()- position.getX());
    }

    @Override
    public String toString() {
        return "[" +x+ ","+y+"]";
    }
}
