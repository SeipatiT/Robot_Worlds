package za.co.wethinkcode.toyworld.robot.robotcommands;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.net.Socket;

public class StateCommand extends ClientCommand {

    @Override
    public  JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws IWorld.WorldException, MultiServers.UnknownClientException, MultiServers.UnknownRobotException, MultiServers.RequestException {
//        robotna = robotName;
        IRobot robot = ClientCommand.getRobot(server, client, robotName);

        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        System.out.println(data + "results");
        data.put("message", "Done");
        return result.put("state", robot.getState()).put("data", data);
    }

}