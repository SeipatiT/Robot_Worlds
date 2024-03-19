//package za.co.wethinkcode.toyworld.WebApi;
//
//import io.javalin.http.Context;
//import io.javalin.http.HttpCode;
//import io.javalin.http.NotFoundResponse;
//import org.json.JSONObject;
//import za.co.wethinkcode.toyworld.robot.Robot;
//import za.co.wethinkcode.toyworld.server.Commands;
//
//public class RobotApiHandler {
////    private static final robotdb database = new Testrobotdb();
//
//    /**
//     * Get all quotes
//     *
//     * @param context The Javalin Context for the HTTP GET Request
//     */
////    public static void getAll(Context context) {
////        context.json(database.all());
////    }
//
//
//    public static void handleCommand(Context context) throws Commands.UnknownServerCommandException {
//        JSONObject object = new JSONObject(context.body());
//        Commands commands = Commands.getCommandWith(object.getString("command"));
//        //TODO: Handle the command. Meaning create the command from the user's input or request and use it to create the command.
//        //TODO: With the created command execute the command and send the response back to the user.
//    }
//
//    /**
//     * Create a new quote
//     *
//     * @param context The Javalin Context for the HTTP POST Request
//     */
////    public static void create(Context context) {
////        Robot robot = context.bodyAsClass(Robot.class);
////        Robot newRobot = database.add(robot);
////        context.header("Location", "/robot/" + newRobot.getId());
////        context.status(HttpCode.CREATED);
////        context.json(newRobot);
////    }
//}