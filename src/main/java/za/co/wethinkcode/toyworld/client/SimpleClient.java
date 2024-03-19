package za.co.wethinkcode.toyworld.client;

import org.json.JSONObject;
import java.io.*;
import java.net.Socket;

public class SimpleClient{
    private final Socket socket;
    private final PrintStream out;
    private final BufferedReader in;


    public SimpleClient() throws IOException {
        this("localhost",5000);
    }

    public SimpleClient(String host,int port) throws IOException {
        socket = new Socket(host,port);
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    public String doRequest(String robotName,String instruction) throws IOException {
        if (socket.isClosed()){
            throw new IOException();
        }
        JSONObject request = new JSONObject();
        String [] args = instruction.split(" ");
        String [] additionalArgs = new String [args.length-1];
        System.arraycopy(args, 1, additionalArgs, 0, args.length - 1);
        request
                .put("robot",robotName)
                .put("command",args[0])
                .put("arguments",additionalArgs);

        out.println(request);
        out.flush();
        return new JSONObject(in.readLine()).toString(4);
    }
}







//package za.co.wethinkcode.toyworld.client;
//
//import org.json.JSONObject;
//import java.io.*;
//import java.net.Socket;
//
//public class SimpleClient{
//
//
//    private final Socket socket;
//    private final PrintStream out;
//    private final BufferedReader in;
//
//    JSONObject request;
//
//
//
//
//    public SimpleClient() throws IOException {
//        this("localhost",5001);
//    }
//
//    public SimpleClient(String host,int port) throws IOException {
//        socket = new Socket(host,port);
//        out = new PrintStream(socket.getOutputStream());
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//    }
//
//    public String doRequest(String robotName,String instruction) throws IOException {
//        if (socket.isClosed()){
//            throw new IOException();
//        }
//        JSONObject request = new JSONObject();
//        String [] args = instruction.split(" ");
//        String [] additionalArgs = new String [args.length-1];
//        System.arraycopy(args, 1, additionalArgs, 0, args.length - 1);
//        request
//                .put("robot",robotName)
//                .put("command",args[0])
//                .put("arguments",additionalArgs);
//
//        out.println(request);
//        out.flush();
//        return new JSONObject(in.readLine()).toString(4);
//    }
//}
//
//
//
//
//
//
//
////package za.co.wethinkcode.toyworld.client;
////
////import org.json.JSONArray;
////import org.json.JSONObject;
////import java.io.*;
////import java.net.Socket;
////import java.util.ArrayList;
////import java.util.Collection;
////import java.util.List;
////
////public class SimpleClient{
////    public JSONObject namesOfRobotsObject = new JSONObject();
////
////    Collection<JSONObject> namesOfRobots = new ArrayList<JSONObject>();
////    JSONObject item1 = new JSONObject();
////
////
////    private final Socket socket;
////    private final PrintStream out;
////    private final BufferedReader in;
////
////    JSONObject request;
////    public List<String> listRobots = new ArrayList<>();
////
////
////
////
////
////
////
////
////    public SimpleClient() throws IOException {
////        this("localhost",5001);
////    }
////
////    public SimpleClient(String host,int port) throws IOException {
////        socket = new Socket(host,port);
////        out = new PrintStream(socket.getOutputStream());
////        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////        item1.put("name", new JSONArray("none"));
////        namesOfRobots.add(item1);
////        namesOfRobotsObject.put("all names",new JSONArray(item1));
////
////    }
////
////    public String doRequest(String instruction) throws IOException {
////        if (socket.isClosed()){
////            throw new IOException();
////        }
////        JSONObject request = new JSONObject();
////        String [] args = instruction.split(" ");
////        String [] additionalArgs = new String [args.length-1];
////        System.arraycopy(args, 1, additionalArgs, 0, args.length - 1);
////        request
////                .put("command",args[0])
////                .put("arguments",additionalArgs);
////
////        out.println(request);
////        out.flush();
////        return new JSONObject(in.readLine()).toString(4);
////    }
////
////    public String doRequest(String robotName,String instruction) throws IOException {
////        if (socket.isClosed()){
////            throw new IOException();
////        }
////        JSONObject request = new JSONObject();
////        String [] args = instruction.split(" ");
////        String [] additionalArgs = new String [args.length-1];
////        System.arraycopy(args, 1, additionalArgs, 0, args.length - 1);
////        request
////                .put("robot",robotName)
////                .put("command",args[0])
////                .put("arguments",additionalArgs);
////
////        out.println(request);
////        System.out.println(request.get("robot") + "inside");
////        out.flush();
////        return new JSONObject(in.readLine()).toString(4);
////    }
////}
////
//////System.out.println(requestJSON.getString("robot")+ "here second");
//////        String robotToMake = requestJSON.getString("robot");
//////
//////
//////        if(listRobots.contains(robotToMake)){
//////
//////        throw new MultiServers.RobotNameAlreadyTakenException();
//////        }
//////        else{listRobots.add(requestJSON.getString("robot"));}
//////        System.out.println(listRobots + "list");