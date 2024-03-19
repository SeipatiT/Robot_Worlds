package za.co.wethinkcode.toyworld.acceptance.Size2.ObstacleX1Y1;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.client.SimpleClient;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.server.RobotWorldClient;
import za.co.wethinkcode.toyworld.server.RobotWorldJsonClient;
import za.co.wethinkcode.toyworld.world.Config;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForwardIterationTwoTests {
    private MultiServers server;
    private Config worldConfig;
    private Thread serverThread;

    @BeforeEach
    void connectToServer() throws IOException {

        worldConfig = new Config("p",5000,"-s",2);
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
    void moveForwardFiveSteps() throws IOException {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("HAL","Launch"));
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result"));

        //original coordinates
        JSONObject state = (JSONObject) response.get("state");

//        When I send a command for "HAL" to move forward by 5 steps
        JSONObject response1 = new JSONObject(client.doRequest
                ("HAL","forward 5"));
        assertNotNull(response1.get("result"));
        assertEquals("OK", response1.get("result"));
        JSONObject data = (JSONObject) response1.get("data");
        JSONObject state1 = (JSONObject) response1.get("state");
        assertEquals("At the NORTH edge",data.get("message"));
        //The coordinates will not change as 5 steps is outside the border
        assertEquals(state.get("position"), state1.get("position"));

//        System.out.println(state.toString() + state1.toString() + "haj");













////        When I send a command for "HAL" to move forward by 5 steps
//        assertNotNull(response.get("result"));
//        assertNotNull(response.get("data"));
//        System.out.println(response.get("result").asText() + "should not say pk");
//        assertEquals("OK", response.get("result").asText());
//
//        System.out.println(response.get("state").get("position").toString() + "new position");
//        assertEquals("At the NORTH edge", response.get("data").get("message").asText());
////


//        assertEquals("[0,0]", response.get("state").get("position").asText());
//        JsonNode array;


//        request = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"shutdown\"," +
//                "  \"arguments\": [\"\"]" +
//                "}";
//
//        response = serverClient.sendRequest(request);
//        assertNotNull(response.get("result"));

//        Then I should get an "OK" response with the message "At the NORTH edge"
//        and the position information returned should be at co-ordinates [0,0]

    }




}
