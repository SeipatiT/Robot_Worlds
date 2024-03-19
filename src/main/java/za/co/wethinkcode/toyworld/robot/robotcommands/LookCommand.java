package za.co.wethinkcode.toyworld.robot.robotcommands;
import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.Direction;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.Edge;
import za.co.wethinkcode.toyworld.world.IWorld;
import za.co.wethinkcode.toyworld.world.Position;
import za.co.wethinkcode.toyworld.world.maze.Obstacle;

import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;


public class LookCommand extends ClientCommand {
    private static String name ;
    private String roboName;


    @Override
    public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws IWorld.WorldException, MultiServers.UnknownClientException, MultiServers.UnknownRobotException, MultiServers.RequestException {
        roboName = robotName;
        IRobot robot = ClientCommand.getRobot(server,client,robotName);
        HashMap<Object, Position> objectPositionHashMap = server.getWorld().checkSurroundings(robot);
        JSONObject data = new JSONObject();
        Position robotPosition = server.getWorld().getPosition(robot);
        for(Object object : objectPositionHashMap.keySet()){
            int distance;
            Direction directionOfObject;
            Position objectPosition = objectPositionHashMap.get(object);
            JSONObject  objectDetails = new JSONObject();
            if(object instanceof IRobot){
                objectDetails.put("type","ROBOT");
            } else if (object instanceof Edge) {
                objectDetails.put("type","EDGE");

            } else if (object instanceof Obstacle){
                objectDetails.put("type","OBSTACLE");
            }else{
                objectDetails.put("type",object.getClass().getSimpleName().toUpperCase(Locale.ROOT));
            }
            directionOfObject = robotPosition.directionOf(objectPosition);
            distance = robotPosition.distanceTo(objectPosition);
            objectDetails.put("direction",directionOfObject.name())
                    .put("distance",Math.abs(distance));
            data.append("objects",objectDetails);
        }
        return new StateCommand().execute(server, client, robotName, args)
                .put("data",data);

    }

}

