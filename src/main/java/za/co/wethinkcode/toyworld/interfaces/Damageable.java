package za.co.wethinkcode.toyworld.interfaces;

public interface Damageable {

    void takeDamage() throws NoMoreShieldsException;

    void repair(int shieldsToRepair);


    class NoMoreShieldsException extends Exception{
        public NoMoreShieldsException(){
            super("Out of shields");
        }
    }
}
