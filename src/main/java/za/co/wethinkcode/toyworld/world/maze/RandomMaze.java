package za.co.wethinkcode.toyworld.world.maze;

import java.util.ArrayList;
import java.util.Random;
import za.co.wethinkcode.toyworld.world.Position;

public class RandomMaze extends AbstractMaze {

    private final ArrayList<Position> randomMazeObstacle = new ArrayList<>();
    private final int obstaclesNumber;

    public RandomMaze(){
        this.obstaclesNumber = 10;
        generateRandomObstacles();
        setUpObstacles();
    }

    public RandomMaze(int obstaclesNumber){
        this.obstaclesNumber = obstaclesNumber;
        generateRandomObstacles();
        setUpObstacles();
    }


    private void generateRandomObstacles() {
        Random random = new Random();
        for(int x = 1;x <= this.obstaclesNumber;x++){
            int xValues,yValues;

            xValues = random.nextInt(100);
            if (!positive()){
                xValues--;
            }
            yValues = random.nextInt(200);
            if (!positive()){
                yValues--;
            }
            while(randomMazeObstacle.contains(new Position(xValues,yValues))){
                xValues = random.nextInt(100);
                if (!positive()){
                    xValues--;
                }
                yValues = random.nextInt(200);
                if (!positive()){
                    yValues--;
                }
            }
            randomMazeObstacle.add(new Position(xValues, yValues));
        }
    }

    private boolean positive() {
        Random random = new Random();
        return random.nextInt(10)>5;
    }


    @Override
    public String toString(){return "RandomMaze";}

    public void setUpObstacles() {
        for (Position position:this.randomMazeObstacle){
            getObstacles().add(new SquareObstacle(position.getX(),position.getY()));
        }
    }
}
