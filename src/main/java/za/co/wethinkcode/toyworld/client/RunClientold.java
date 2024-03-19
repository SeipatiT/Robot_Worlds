//package za.co.wethinkcode.toyworld.client;
//
//import java.awt.*;
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//import java.util.Scanner;
//
//public class RunClient {
//    Robot rori;
//    public List<String> listRobots;
//    private static final Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) throws IOException {
//        /** Refactored code to allow it to receive a launch command.
//         * It does not have an adequate JSON response when command fails.
//         * Started testing launch command in CommandTests
//         */
//        SimpleClient client = new SimpleClient();
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
//            //
////            nameExists = checkNameTry(server, robotName);
////            if (nameExists.equals(true)) {
////                throw new MultiServers.RobotNameAlreadyTakenException();
////            }
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
////    Boolean checkNameTry(MultiServers server, String robotName){
////        try{
////            nn = (String) runServer.namesOfRobots.get("name");
////            System.out.println(nn + "real");
////            nameExists = true;
////
////        }
////        catch (NullPointerException e){
////            nameExists = false;
////        }
////
////        return nameExists;
////    }
//}
