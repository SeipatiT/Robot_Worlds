package za.co.wethinkcode.toyworld.robot.robotcommands;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.IWorld;
import java.net.Socket;

public class BackCommand extends ClientCommand {
        @Override
        public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws IWorld.WorldException, MultiServers.RequestException {
            System.out.println("back");


            IRobot robot = ClientCommand.getRobot(server,client,robotName);
            JSONObject data = new JSONObject();
            int nrSteps = Integer.parseInt(args[0]);
            switch(server.getWorld().updatePosition(robot, -nrSteps)){
                case SUCCESS:
                    data.put("message","Done");
                    break;
                case FAILED_OUTSIDE_WORLD:
                    data.put("message","Outside Border");
                    break;
                case FAILED_OBSTRUCTED:
                    data.put("message","Obstructed");
                    break;
            }
            return new StateCommand().execute(server, client, robotName, args).put("data",data);
        }
}
