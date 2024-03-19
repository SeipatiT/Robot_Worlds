package za.co.wethinkcode.toyworld.robot;

import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.robotcommands.*;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.net.Socket;


public abstract class ClientCommand {


    public static IRobot getRobot(MultiServers server, Socket client, String robotName) throws MultiServers.UnknownClientException, MultiServers.UnknownRobotException {
        try {
            IRobot robot = server.getClients().get(client).get(robotName);
            if (robot == null) {
                throw new MultiServers.UnknownRobotException();
            }
            return robot;
        } catch (NullPointerException e) {
            throw new MultiServers.UnknownClientException();
        }

    }

    public abstract JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws IWorld.WorldException, MultiServers.UnknownClientException, MultiServers.UnknownRobotException, MultiServers.RequestException;


    public static ClientCommand getCommandWith(String commandName) throws InvalidCommand {
        switch (commandName.toLowerCase()) {
            case "off":
            case "quit":
            case "shutdown":
                return new QuitCommand();
            case "state":
                return new StateCommand();
            case "look":
                return new LookCommand();
            case "launch":
                return new LaunchCommand();
            case "forward":
                return new ForwardCommand();
            case "reload":
                return new Reload();
            case "repair":
                return new Repair();

            case "turn":
                return new TurnCommand();
            case "back":
                return new BackCommand();
            case "fire":
                return new FireCommand();

            default:
                throw new InvalidCommand();
        }
//        catch (Exception e) {
//            return new InvalidCommand();
    }


    public static class InvalidCommand extends Exception {
        public InvalidCommand() {
            super("Unsupported command");
        }
    }
}



