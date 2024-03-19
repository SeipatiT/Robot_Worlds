package za.co.wethinkcode.toyworld.robot.robotcommands;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.net.Socket;



public class TurnCommand extends ClientCommand {
    @Override
    public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {

        boolean turnRight;
        IRobot robot = ClientCommand.getRobot(server, client, robotName);
        try{
            turnRight = args[0].equalsIgnoreCase("right");
        }catch (ArrayIndexOutOfBoundsException e){
            turnRight = true;
        }
        server.getWorld().updateDirection(robot,turnRight);
        return new StateCommand().execute(server, client, robotName, args);
    }

}
