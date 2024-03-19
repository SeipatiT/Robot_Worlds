package za.co.wethinkcode.toyworld.client;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class RunBasicClient {
    public static void main(String[] args) throws IOException {
        SimpleClient client;
        try{
            client = new SimpleClient(args[0],Integer.parseInt(args[1]));
        }catch (IndexOutOfBoundsException | NumberFormatException e){
            client = new SimpleClient();
        }
        Scanner inputScanner = new Scanner(System.in);
        String toQuit;
        do{
            System.out.println("Which Robot would you like to control?");
            String name = inputScanner.nextLine();
            System.out.println("What would you like "+name+" to do?");
            String instruction = inputScanner.nextLine().toUpperCase(Locale.ROOT);
            System.out.println(client.doRequest(name,instruction));
            System.out.println("Type q to quit (simply press enter to continue)");
            toQuit = inputScanner.nextLine();
        } while(!toQuit.equals("q"));
    }
}
