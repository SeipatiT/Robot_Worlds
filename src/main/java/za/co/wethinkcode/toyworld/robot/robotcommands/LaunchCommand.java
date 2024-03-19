package za.co.wethinkcode.toyworld.robot.robotcommands;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.server.RunServer;
import za.co.wethinkcode.toyworld.world.IWorld;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static za.co.wethinkcode.toyworld.server.MultiServers.buildObject;


public class LaunchCommand extends ClientCommand {
    Boolean nameExists;
    String nn;
    RunServer runServer;

    public List<String> listRobots = new ArrayList<>();
//    private static String robotna;

        @Override
        public JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException, IWorld.WorldException {
            try{
                HashMap<String,IRobot> robots = server.getClients().get(client);
                if (robots.containsKey(robotName)||server.getMyALL().contains(robotName) ){
                    throw new MultiServers.RobotNameAlreadyTakenException();

                }
                //
                server.putObjects(server.jsonRequest, "all names",
                        buildObject(new AbstractMap.SimpleEntry("name", robotName)));
                server.getMyALL().add(robotName);
//                System.out.println(server.myALL + "nope");
//                System.out.println(server.jsonRequest);
                //
                IWorld world = server.getWorld();
                IRobot robot = world.addRobot(robotName);
                robots.put(robotName, robot);
                JSONObject data = world.getConfig().getJSON().put("position",world.getPosition(robot));
                return new StateCommand().execute(server, client, robotName, args).put("data",data);
            } catch (NullPointerException e) {
                throw new MultiServers.UnknownClientException();
            }
        }}
