package za.co.wethinkcode.toyworld.server;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.world.Config;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleServer implements Runnable {
    public static int PORT= Config.port;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private final MultiServers servers;
    private final Socket clientSocket;

    public SimpleServer(Socket socket,MultiServers servers) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        clientSocket = socket;
        System.out.println("Connection from " + clientMachine);
        this.servers = servers;
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");

    }

    public void run() {
        try {
            System.out.println(PORT);
            String requestString;
            while(servers.isRunning()) {
                if (in.ready()){
                    requestString = in.readLine();
                }else{
                    continue;
                }
                if(requestString == null){
                    break;
                }
                System.out.println("Message \"" + requestString + "\" from " + clientMachine);
                out.println(generateResponse(requestString));
            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }


    private JSONObject generateResponse(String request){
        System.out.println(request+ "here first");

        JSONObject requestJSON = new JSONObject(request);
        try{
            String name = requestJSON.getString("robot");
            return ClientCommand.getCommandWith(requestJSON.getString("command"))
                    .execute(servers,clientSocket,name,getArgs(requestJSON))
                    .put("result","OK");
        }catch (IWorld.WorldException | MultiServers.RequestException | ClientCommand.InvalidCommand e) {
            return generateErrorResponse(e);
        }
    }


//
//    private JSONObject generateResponse(String request){
//        JSONObject requestJSON = new JSONObject(request);
//        try{
//            String name = requestJSON.getString("robot");
//            return Commands.getCommandWith(requestJSON.getString("command"))
//                    .execute(servers,clientSocket,name,getArgs(requestJSON))
//                    .put("result","OK");
//        }catch (IWorld.WorldException | MultiServers.RequestException | Commands.UnknownRobotCommandException e){
//            return generateErrorResponse(e);
//        }
//    }


    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ignored) {}
    }

    private JSONObject generateErrorResponse(Exception error){
        JSONObject errorResponse = new JSONObject();
        HashMap<String,String> data = new HashMap<>();
        data.put("message",error.getMessage());
        return errorResponse.put("result","ERROR")
                .put("data",data);
    }

    private String [] getArgs(JSONObject request){
        JSONArray jsonArgs = request.getJSONArray("arguments");
        String [] args = new String[jsonArgs.length()];
        for (int i = 0; i <jsonArgs.length() ; i++){
            args[i] = jsonArgs.getString(i);
        }
        return args;
    }
}

//System.out.println(requestJSON.getString("robot")+ "here second");
//        String robotToMake = requestJSON.getString("robot");
//
//
//        if(listRobots.contains(robotToMake)){
//
//        throw new MultiServers.RobotNameAlreadyTakenException();
//        }
//        else{listRobots.add(requestJSON.getString("robot"));}
//        System.out.println(listRobots + "list");