package za.co.wethinkcode.toyworld.server;

//import org.json.JSONObject;
import kong.unirest.json.JSONObject;
import za.co.wethinkcode.toyworld.interfaces.Resettable;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.world.AbstractWorld;
import za.co.wethinkcode.toyworld.world.Config;
import za.co.wethinkcode.toyworld.world.IWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;

public class MultiServers implements Runnable, Resettable {
    private final IWorld world;
    private final HashMap<Socket,HashMap<String,IRobot>> clients = new HashMap<>();
    private boolean runningServer;
    private final ServerSocket serverSocket = new ServerSocket(SimpleServer.PORT);


    public static List<String> getMyALL() {
        return myALL;
    }

    public static void setMyALL(List<String> myALL) {
        MultiServers.myALL = myALL;
    }

    /**
     * Trying to create JSON Array that will recognize every robot and store name
     *
     */
//    public JSONObject namesOfRobotsObject = new JSONObject();
//    public Collection<JSONObject> namesOfRobots = new ArrayList<JSONObject>();
//    public JSONObject items = new JSONObject();

    

    public static List<String> myALL = new ArrayList<String>();
    
    

    public JSONObject jsonRequest = new JSONObject();
    //  create and initialize an object
    public static JSONObject buildObject( final AbstractMap.SimpleEntry... entries ) {
        JSONObject object = new JSONObject();
        for( AbstractMap.SimpleEntry e : entries ) object.put((String) e.getKey(), e.getValue() );
        return object;
    }

    //  nest a list of objects inside another
    public static void putObjects( final JSONObject parentObject, final String key,
                                   final JSONObject... objects ) {
        List objectList = new ArrayList<JSONObject>();
        for( JSONObject o : objects ) objectList.add( o );
        parentObject.put( key, objectList );

    }




    public MultiServers() throws IOException {
        this(new Config());
    }

    public MultiServers(Config worldConfig) throws IOException{
        world = new AbstractWorld(worldConfig) {};
        this.runningServer = true;
        serverSocket.setSoTimeout(10);


        /*
        Adding each client to collection
         */
//        items.put("name", new JSONArray("none"));
//        namesOfRobots.add(items);
//        namesOfRobotsObject.put("all names",new JSONArray(0,1));

        putObjects(jsonRequest, "all names",
                buildObject(new AbstractMap.SimpleEntry("name", "none")));
        


    }

    @Override
    public void run() {
        System.out.println("Server running & waiting for client connections.");
        System.out.println("Enter server command: ");
        ArrayList<Thread> simpleServerThreads = new ArrayList<>();
        int numberOfThreads = 0;
        while(isRunning()) {
            try {
                Socket socket = serverSocket.accept();
                clients.put(socket,new HashMap<>());
                System.out.println("Connection: " + socket);
                Runnable r = new SimpleServer(socket,this);
                simpleServerThreads.add(new Thread(r));
                simpleServerThreads.get(numberOfThreads).start();
                numberOfThreads++;
            } catch (SocketTimeoutException ignored){

            } catch(IOException ex) {
                System.out.println("e");
                ex.printStackTrace();
            }
        }
        System.out.println("Shutting down Server");
        try {
            serverSocket.close();
            for (Thread thread : simpleServerThreads){
                thread.join();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopRunning(){
        myALL.clear();
        runningServer = false;
    }

    public boolean isRunning(){
        return runningServer;
    }

    public HashMap<Socket,HashMap<String,IRobot>> getClients() {
        return clients;
    }

    public IWorld getWorld(){
        return world;
    }

    @Override
    public void reset() {
        world.reset();
        clients.clear();

    }



    public static abstract class RequestException extends Exception{
        public RequestException (String message){
            super(message);
        }
    }


    public static class RobotNameAlreadyTakenException extends RequestException{
        public RobotNameAlreadyTakenException(){
            super("Too many of you in this world");
        }

    }

    public static class UnknownRobotException extends RequestException{
        public UnknownRobotException(){
            super("Robot cannot be found");
        }
    }

    public static class UnknownClientException extends RequestException{
        public UnknownClientException(){
            super("Unknown Client");
        }
    }
}