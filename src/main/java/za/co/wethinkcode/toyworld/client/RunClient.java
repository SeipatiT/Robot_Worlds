package za.co.wethinkcode.toyworld.client;

import za.co.wethinkcode.toyworld.robot.ClientCommand;
import za.co.wethinkcode.toyworld.robot.servercommands.RobotsCommand;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class RunClient {

    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        SimpleClient client;
        String name;
        String response;
        String instruction;
        try{
            client = new SimpleClient(args[0],Integer.parseInt(args[1]));}
        catch (IndexOutOfBoundsException | NumberFormatException e){
            client = new SimpleClient();
        }
        do{
            instruction = getInput("Welcome to Robot Worlds. \\r\\n" +
                    "To launch a robot please type *launch.");

        }while (!instruction.equalsIgnoreCase("launch"));
        while (instruction.equalsIgnoreCase("launch")) {
            name = getInput("What would you like to name your robot?");

            response = client.doRequest(name, instruction);
            System.out.println(response);
            if(response.contains("ERROR")){
                name = getInput("What would you like to name your robot?");
                response = client.doRequest(name, instruction);
                System.out.println(response);
            }
            System.out.println("here");
            do {
                instruction = getInput(name + "> What must I do next?").strip().toLowerCase(Locale.ROOT);
                response = client.doRequest(name, instruction);
                System.out.println(response);
            } while (response.contains("ERROR") || !response.contains("DEAD"));
        }


    }
    public static Boolean errorFound;
    private static Boolean foundError(String response){
        if (response.contains("ERROR")){
            errorFound = true;
        }
        else {errorFound
                = false; }
        return errorFound;}



//        catch (Exception e){
//            throw new ClientCommand.UnknownRobotCommandException();



//        if(command1.equalsIgnoreCase("launch")){
//        String name;
//        do{
//            name = getInput("What would you like to name your robot?");
//            response = client.doRequest(name,"launch");
//            System.out.println(response);
//        }while(response.contains("ERROR"));



    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
}







//package za.co.wethinkcode.toyworld.client;
//
//import org.json.JSONObject;
//import org.json.JSONString;
//import za.co.wethinkcode.toyworld.server.MultiServers;
//import za.co.wethinkcode.toyworld.server.RunServer;
//
//import java.awt.*;
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//import java.util.Scanner;
//
//public class RunClient {
//
//    private static Object nn;
//    private static boolean nameExists;
//    Robot rori;
//    public List<String> listRobots;
//    private static final Scanner scanner = new Scanner(System.in);
//
//    RunServer runServer;
//
//    public static void main(String[] args) throws IOException {
//        /** Refactored code to allow it to receive a launch command.
//         * It does not have an adequate JSON response when command fails.
//         * Started testing launch command in CommandTests
//         */
//        final SimpleClient client = new SimpleClient();
//        String name;
//        String response;
//        String instruction;
//        instruction = getInput("Welcome to Robot Worlds. To launch a robot " +
//                "type *launch*.");
//
//        while(!instruction.equalsIgnoreCase("launch")){
//            response = client.doRequest(name ="",instruction);
//            System.out.println(response);
//            instruction = getInput("> Command not supported. Please try again.");
//        }
//        while(instruction.equalsIgnoreCase("launch")){
//            name = getInput("What would you like to name your robot?");
//
//
//            //
////            nameExists = ;
//            if (checkNameExists(client, name).equals(true)) {
//                System.out.println(client.namesOfRobots + "my json");
//                new MultiServers.RobotNameAlreadyTakenException();
//                name = getInput("What would you like to name your robot?");
//            }
//
//
//
//            response = client.doRequest(name,"launch");
//            System.out.println(response);
//            do{
//                instruction = getInput(name+"> What must I do next?").strip().toLowerCase(Locale.ROOT);
//                response = client.doRequest(name,instruction);
//                System.out.println(response);
//            }while(response.contains("ERROR") || !response.contains("DEAD"));}
//
//
////        if(command1.equalsIgnoreCase("launch")){
////        String name;
////        do{
////            name = getInput("What would you like to name your robot?");
////            response = client.doRequest(name,"launch");
////            System.out.println(response);
////        }while(response.contains("ERROR"));
//
//
//    }
//
//    private static String getInput(String prompt) {
//        System.out.println(prompt);
//        String input = scanner.nextLine();
//
//        while (input.isBlank()) {
//            System.out.println(prompt);
//            input = scanner.nextLine();
//        }
//        return input;
//    }
//
//    static Boolean checkNameExists(SimpleClient client, String robotName){
//        try{
//            System.out.println(client.namesOfRobots);
////            nn = client.namesOfRobots.get("name");
//            String n = nn.toString();
//            if(n.contains(robotName)){
//            System.out.println(n + "real");
//            return nameExists = true;}
//            else {
//
////                var JsonString = namesOfRobots.
////                var obj = JSON.parse(client.namesOfRobots);
////                client.namesOfRobots.put("name", robotName);
//
////                        append("name",robotName);
//                nameExists = false;
//            }
//
//        }
//        catch (NullPointerException e){
//            nameExists = false;
//        }
//
//        return nameExists;
//    }
//}
