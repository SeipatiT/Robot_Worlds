package za.co.wethinkcode.toyworld.robot.robotcommands;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.interfaces.*;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.robot.States;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.net.Socket;

public class FireCommand extends ClientCommand {

    @Override
    public JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException, IWorld.WorldException {
        IRobot robot = ClientCommand.getRobot(server,client,robotName);
        if (robot.getStatus() == States.RELOAD){
            robot.setStatus(States.NORMAL);
            throw new MultiServers.RequestException("Currently Reloading Try Again") {};
        }
        try{
            Damageable objectHit = server.getWorld().shoot(robot);
            JSONObject data = new JSONObject();
            if(objectHit instanceof StateHaving){
                data.put("state",((StateHaving) objectHit).getState());
            }
            if(objectHit instanceof Named){
                data.put(objectHit.getClass().getSimpleName(),((Named) objectHit).getName());
            }else if(objectHit != null){
                data.put("type",objectHit.getClass().getSimpleName());
            }
            if(objectHit instanceof Positional && robot instanceof Positional){
                data.put("distance", ((Positional) robot).getPosition().distanceTo(
                        ((Positional) objectHit).getPosition()));
            }
            if(objectHit == null){
                data.put("message","Miss");
            }else{
                data.put("message","Hit");
            }
            return new JSONObject().put("state",robot.getState()).put("data",data);
        }catch (Shooting.EmptyMagazineException e){
            throw new MultiServers.RequestException(e.getMessage()) {
            };
        }
    }



}
