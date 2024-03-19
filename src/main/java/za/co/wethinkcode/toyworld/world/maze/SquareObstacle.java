package za.co.wethinkcode.toyworld.world.maze;

import za.co.wethinkcode.toyworld.world.Position;

import java.util.Objects;

public class SquareObstacle implements Obstacle{
    private final Position BOTTOM_LEFT;
    private final int size;
    public SquareObstacle(int x, int y){
        this(x,y,4);

    }

    public SquareObstacle(int x, int y, int size){
        BOTTOM_LEFT = new Position(x,y);
        this.size = size;
    }
    @Override
    public int getBottomLeftX() {
        return BOTTOM_LEFT.getX();
    }

    @Override
    public int getBottomLeftY() {
        return BOTTOM_LEFT.getY();
    }

    @Override
    public int getSize() {
        return size+1;
    }

    @Override
    public boolean blocksPosition(Position position) {
        Position topLeft = new Position(getBottomLeftX(),getBottomLeftY()+size);
        Position bottomRight = new Position(getBottomLeftX()+size,getBottomLeftY());
        return position.isIn(topLeft,bottomRight);
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        for(int x = Math.min(a.getX(),b.getX()); x<=Math.max(a.getX(),b.getX()); x++){
            if(blocksPosition(new Position(x,a.getY()))){
                return true;
            }
        }
        for(int y = Math.min(a.getY(),b.getY()); y<= Math.max(a.getY(),b.getY()); y++){
            if(blocksPosition(new Position(a.getX(),y))){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SquareObstacle)) return false;
        SquareObstacle that = (SquareObstacle) o;
        return getSize() == that.getSize() && BOTTOM_LEFT.equals(that.BOTTOM_LEFT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BOTTOM_LEFT, getSize());
    }

    @Override
    public String toString() {
        return "SquareObstacle{" +
                "BOTTOM_LEFT=" + BOTTOM_LEFT +
                ", size=" + size +
                '}';
    }
}
