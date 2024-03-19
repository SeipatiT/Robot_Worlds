package za.co.wethinkcode.toyworld.robot;

import za.co.wethinkcode.toyworld.world.Position;

public enum Direction {
    NORTH{
        @Override
        public Position[] getPathFrom(Position position, int steps) {
            if(steps>=0){
                Position [] path = new Position[steps+1];
                for (int y = position.getY(); y<= position.getY()+steps; y++){
                    path[y- position.getY()] = new Position(position.getX(), y);
                }
                return path;
            }else{
                return SOUTH.getPathFrom(position,-steps);
            }
        }
    },EAST {
        @Override
        public Position[] getPathFrom(Position position, int steps) {
            if(steps >=0){
                Position [] path = new Position[steps+1];
                for(int x = position.getX(); x<= position.getX()+steps; x++){
                    path[x- position.getX()] = new Position(x, position.getY());
                }
                return path;
            }else{
                return WEST.getPathFrom(position,-steps);
            }
        }
    },SOUTH {
        @Override
        public Position[] getPathFrom(Position position, int steps) {
            if(steps>=0){
                Position [] path = new Position[steps+1];
                for (int y = position.getY(); y>= position.getY()-steps; y--){
                    path[position.getY()-y] = new Position(position.getX(), y);
                }
                return path;
            }else{
                return NORTH.getPathFrom(position,-steps);
            }
        }
    },WEST {
        @Override
        public Position[] getPathFrom(Position position, int steps) {
            if(steps>=0){
                Position [] path = new Position[steps+1];
                for(int x = position.getX(); x>= position.getX()-steps; x--){
                    path[position.getX()-x] = new Position(x, position.getY());
                }
                return path;
            }else{
                return EAST.getPathFrom(position,-steps);
            }
        }
    };

    /** Generates a Position array of length steps+1, where each position is 1 step further along this Direction starting at the specified position.
     *
     * @param position the starting point of the path
     * @param steps the length of the path to generate
     * @return the generated path
     */
    public abstract Position[] getPathFrom(Position position, int steps);
}
