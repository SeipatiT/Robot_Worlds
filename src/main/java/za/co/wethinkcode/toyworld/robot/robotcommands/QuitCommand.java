package za.co.wethinkcode.toyworld.robot.robotcommands;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.ClientCommand;

import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.robot.States;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.net.Socket;
import java.util.HashMap;


public class QuitCommand extends ClientCommand {
    private static String robotna;
    

    @Override
    public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws IWorld.WorldException, MultiServers.UnknownClientException, MultiServers.UnknownRobotException, MultiServers.RequestException {
        robotna = robotName;
        IRobot robot = ClientCommand.getRobot(server,client,robotName);
        HashMap<String, IRobot> robots = server.getClients().get(client);
        robot.setStatus(States.DEAD);
        JSONObject response = new StateCommand().execute(server, client, robotName,args);
        robots.remove(robotName);
        return response;
    }
}
