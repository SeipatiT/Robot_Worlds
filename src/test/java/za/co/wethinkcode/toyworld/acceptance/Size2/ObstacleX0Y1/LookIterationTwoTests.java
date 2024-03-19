package za.co.wethinkcode.toyworld.acceptance.Size2.ObstacleX0Y1;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.client.SimpleClient;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.robot.PositionalRobot;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.server.RobotWorldClient;
import za.co.wethinkcode.toyworld.server.RobotWorldJsonClient;
import za.co.wethinkcode.toyworld.world.AbstractWorld;
import za.co.wethinkcode.toyworld.world.Config;
import za.co.wethinkcode.toyworld.world.IWorld;
import za.co.wethinkcode.toyworld.world.Position;
import za.co.wethinkcode.toyworld.world.maze.EmptyMaze;
import za.co.wethinkcode.toyworld.world.maze.SimpleMaze;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LookIterationTwoTests {
    private MultiServers server;
    private Config worldConfig;
    private Thread serverThread;

    @BeforeEach
    void connectToServer() throws IOException {
        worldConfig = new Config("p",5000,"-s",2,"o",1);
        server = new MultiServers(worldConfig);
        serverThread = new Thread(server);
        serverThread.start();
    }

    @AfterEach
    void disconnectFromServer() throws InterruptedException {
        server.stopRunning();
        serverThread.join();
    }

    @Test
    void seeAnObstacle() throws IWorld.WorldFullException, IOException {
        /* Mpho Please assist *\

        //Given a world of size 2x2
        //and the world has an obstacle at coordinate [0,1]
        //and I have successfully launched a robot into the world
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("HAL","Launch"));
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result"));



        //When I ask the robot to look
        //Then I should get a response back
        // with an object of type OBSTACLE at a distance of 1 step
        JSONObject response1 = new JSONObject(client.doRequest("HAL","look"));
        System.out.println(response1 + "hat we ");
        assertNotNull(response1.get("result"));
        assertEquals("OK", response1.get("result"));
        JSONObject data = (JSONObject) response1.get("data");
        assertEquals("{}",data.toString());





//        assertNotNull(response.get("result"));
//        assertEquals("OK", response.get("result").asText());
//        assertTrue(response.get("data").has("objects"));
//        JsonNode objects = response.get("data").get("objects");
//        System.out.println(response);




    }

//    @Test
//    void seeRobot() throws IWorld.WorldFullException, IWorld.UnknownRobotException {
//        //Given a world of size 2x2
//        //and the world has an obstacle at coordinate [0,1]
//
//        //and I have successfully launched 8 robots into the world
//       IWorld world = new AbstractWorld(new Config("-p",5000,"-s",2),new EmptyMaze()) {};
//
//        assertTrue(serverClient1.isConnected());
//        assertTrue(serverClient2.isConnected());
//
//
//        String request = "{" +
//                "  \"robot\": \"PEACHES\"," +
//                "  \"command\": \"launch\"," +
//                "  \"arguments\": [\" \"]" +
//                "}";
//
//        JsonNode response = serverClient1.sendRequest(request);
//        assertNotNull(response.get("result"));
//        world.addRobot("PEACHES");
//
//
//
//        String request1 = "{" +
//                "  \"robot\": \"paul\"," +
//                "  \"command\": \"launch\"," +
//                "  \"arguments\": [\" \"]" +
//                "}";
//
//        JsonNode response1 = serverClient2.sendRequest(request1);
//        assertNotNull(response1.get("result"));
//        world.addRobot("paul");
//
//        //When I ask the first robot to look
//        request = "{" +
//                "  \"robot\": \"PEACHES\"," +
//                "  \"command\": \"look\"," +
//                "  \"arguments\": []" +
//                "}";
//
//        response = serverClient1.sendRequest(request);
//        System.out.println(response);
//
//        //Then I should get a response back with
//        //one object being an OBSTACLE that is one step away
//        //and three objects should be ROBOTs that is one step away
//        assertNotNull(response.get("result"));
//
//        assertEquals("OK", response.get("result").asText());
//        assertTrue(response.get("data").has("objects"));
//        JsonNode objects = response.get("data").get("objects");
//        for (JsonNode i: objects) {
//            assertEquals("ROBOT", i.get("type").asText());
//        }
//        request = "{" +
//                "  \"robot\": \"PEACHES\"," +
//                "  \"command\": \"shutdown\"," +
//                "  \"arguments\": [\"\"]" +
//                "}";
//
//        response = serverClient1.sendRequest(request);
//        assertNotNull(response.get("result"));
//
//        request = "{" +
//                "  \"robot\": \"paul\"," +
//                "  \"command\": \"shutdown\"," +
//                "  \"arguments\": [\"\"]" +
//                "}";
//
//        response = serverClient2.sendRequest(request);
//        assertNotNull(response.get("result"));
//
//
//    }


}}

}}}}
         */


    }}