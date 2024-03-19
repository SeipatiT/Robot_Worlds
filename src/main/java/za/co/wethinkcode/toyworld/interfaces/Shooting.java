package za.co.wethinkcode.toyworld.interfaces;

import za.co.wethinkcode.toyworld.robot.IShootingRobot;

public interface Shooting extends Directional, Positional {

    void reload(int shotsToReload);
    void shoot()throws IShootingRobot.EmptyMagazineException;

    class EmptyMagazineException extends Exception{
        public EmptyMagazineException(){
            super("No more shots left");
        }
    }
}
