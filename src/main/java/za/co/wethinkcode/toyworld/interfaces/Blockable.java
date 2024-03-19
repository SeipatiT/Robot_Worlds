package za.co.wethinkcode.toyworld.interfaces;

import za.co.wethinkcode.toyworld.world.Position;

public interface Blockable {

    /**
     * Checks if this object blocks the path that goes from Position A to Position B.
     * @param a first position
     * @param b second position
     * @return `true` if this object is anywhere along the path
     */
    boolean blocksPath(Position a, Position b);

    /**
     * Checks if this object blocks access to the specified position.
     * @param position the position to check
     * @return return `true` if the x,y coordinate falls within the obstacle's area
     */
    boolean blocksPosition(Position position);
}
