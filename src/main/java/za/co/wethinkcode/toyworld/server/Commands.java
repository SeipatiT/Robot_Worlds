package za.co.wethinkcode.toyworld.server;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.IRobot;

import java.util.HashMap;
import java.util.Locale;

public enum Commands {
    QUIT{
        @Override
        public JSONObject execute(MultiServers server) {
            server.stopRunning();
            return new JSONObject();
        }
    },DUMP {

        @Override
        public JSONObject execute(MultiServers server){
            JSONObject dump = ROBOTS.execute(server);
            dump.put("obstacles",server.getWorld().getMaze().getObstacles());
            return dump;
        }
    },ROBOTS {
        public JSONObject execute(MultiServers server){
            JSONObject robots = new JSONObject();
            for(HashMap<String, IRobot> client:server.getClients().values() ){
                for(IRobot robot:client.values()){
                    JSONObject robotDetails = new JSONObject();
                    robotDetails.put("name",robot.getName());
                    robotDetails.put("status",robot.getState());
                    robots.append("robots",robotDetails);
                }
            }
            return robots;
//        clients.forEach(
//                (socket,robots) -> robots.forEach(
//                        (name,robot)->robotJSON.append("robots",robot)));
//        return robotJSON;
        }
    };


    public abstract JSONObject execute(MultiServers server);

    public static Commands getCommandWith(String commandName) throws UnknownServerCommandException {
        try{
            return valueOf(commandName.toUpperCase(Locale.ROOT));
        }catch (IllegalArgumentException e){
            throw new UnknownServerCommandException(commandName);
        }
    }

    public static class UnknownServerCommandException extends Exception{
        public UnknownServerCommandException(String command){
            super("Unknown Server Command: "+command);
        }
    }
}
