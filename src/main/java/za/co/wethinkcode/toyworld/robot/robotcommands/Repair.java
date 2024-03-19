package za.co.wethinkcode.toyworld.robot.robotcommands;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.interfaces.Damageable;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.robot.States;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.net.Socket;



public class Repair extends ClientCommand {
    private static String robotna;


    @Override
    public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {
        robotna = robotName;
        IRobot robot = ClientCommand.getRobot(server,client,robotName);
        if(robot instanceof Damageable){
            try{
                ((Damageable) robot).repair(Integer.parseInt(args[0]));
            }catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
                ((Damageable) robot).repair(1);
            }
        }
        robot.setStatus(States.REPAIR);
        return new StateCommand().execute(server, client, robotName, args);
    }

}
