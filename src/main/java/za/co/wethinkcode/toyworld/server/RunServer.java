package za.co.wethinkcode.toyworld.server;

import za.co.wethinkcode.toyworld.world.Config;
import java.io.IOException;
import java.util.Scanner;

public class RunServer {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
//        System.out.println(PORT);
////        int[] configValues = new int[args.length];
//        For(m i: args)
//        for(int x = 1; x < args.length ; x++){
//            configValues[x] = Integer.parseInt(args[x]);
//        }
        final Config config;
        Config config1;

        try{
            config1 = new Config(args[0],Integer.parseInt(args[1]),args[2],Integer.parseInt(args[3]),args[4],Integer.parseInt(args[3]));
        }catch(IndexOutOfBoundsException e5){
            System.out.println("No Obstacles value ");
            try {
                config1 = new Config(args[0],Integer.parseInt(args[1]),args[2],Integer.parseInt(args[3]));
            }catch (IndexOutOfBoundsException e3){
                System.out.println("No obstacles at all");
                try {
                    config1 = new Config(args[0],Integer.parseInt(args[1]));
                }catch (IndexOutOfBoundsException e1){
                    System.out.println("Only port given");
                    config1 = new Config();
                }
            }

        }
        config = config1;
        System.out.println(config.getport());
        final MultiServers server = new MultiServers(config);
        //here
//        System.out.println(ROBOTS.execute(server) + "tick tok");
        //me
        Thread serverThread = new Thread(server);
        serverThread.start();
        while(server.isRunning()){
            try{
                String serverCommand = scanner.nextLine();
                System.out.println(Commands.getCommandWith(serverCommand).execute(server));
            }catch (Commands.UnknownServerCommandException e) {
                e.printStackTrace();
            }
        }
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
