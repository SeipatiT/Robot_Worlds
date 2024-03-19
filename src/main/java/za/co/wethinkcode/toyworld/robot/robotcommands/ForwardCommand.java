package za.co.wethinkcode.toyworld.robot.robotcommands;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.net.Socket;



public class ForwardCommand extends ClientCommand {


    @Override
    public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws IWorld.WorldException, MultiServers.UnknownClientException, MultiServers.UnknownRobotException, MultiServers.RequestException {
        System.out.println("foorward");


        IRobot robot = ClientCommand.getRobot(server,client,robotName);
        JSONObject data = new JSONObject();
        int nrSteps = Integer.parseInt(args[0]);
        System.out.println(nrSteps + "forward num");
        switch(server.getWorld().updatePosition(robot, nrSteps)){
            case SUCCESS:
                String Direction = String.valueOf(robot.getState().get("direction"));
                if(Direction.contains("NORTH")){data.put("message","At the NORTH edge");}
                else if (Direction.contains("EAST")) {data.put("message","At the EAST edge");}
                else if (Direction.contains("WEST")) {data.put("message","At the WEST edge");}
                else if (Direction.contains("SOUTH")) {data.put("message","At the SOUTH edge");}
//                System.out.println(robot.getState().get("direction") + "jjlk");
//                System.out.println(data + "on the side");

//                data.put("message","Done");
                break;
            case FAILED_OUTSIDE_WORLD:
                Direction = String.valueOf(robot.getState().get("direction"));
                if(Direction.contains("NORTH")){data.put("message","At the NORTH edge");}
                else if (Direction.contains("EAST")) {data.put("message","At the EAST edge");}
                else if (Direction.contains("WEST")) {data.put("message","At the WEST edge");}
                else if (Direction.contains("SOUTH")) {data.put("message","At the SOUTH edge");
//                data.put("message","Outside Border");
                }
                break;
            case FAILED_OBSTRUCTED:
                data.put("message","Obstructed");
                break;
        }
        return new StateCommand().execute(server, client, robotName, args).put("data",data);
    }

//    @Override
//    public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws IWorld.WorldException, MultiServers.RequestException {
//        System.out.println("foo");
//
//        robotna = robotName;
//
//        IRobot robot = ClientCommand.getRobot(server,client,robotName);
//        JSONObject data = new JSONObject();
//        int nrSteps = Integer.parseInt(args[0]);
//        switch(server.getWorld().updatePosition(robot, nrSteps)){
//            case SUCCESS:
//                data.put("message","Done");
//                break;
//            case FAILED_OUTSIDE_WORLD:
//                data.put("message","Outside Border");
//                break;
//            case FAILED_OBSTRUCTED:
//                data.put("message","Obstructed");
//                break;
//        }
//        return new StateCommand().execute(server, client, robotName, args).put("data",data);
//    }

}
