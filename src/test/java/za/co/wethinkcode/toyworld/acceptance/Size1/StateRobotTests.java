package za.co.wethinkcode.toyworld.acceptance.Size1;

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


class StateRobotTests {
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
    void RobotInWorld() throws IOException {
        //Given a launched robot
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("Bob","Launch"));
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result"));

        //Sending a second command to state should return a command done message
        JSONObject response1 = new JSONObject(client.doRequest("Bob","state"));
//        System.out.println(response1 + "hat we ");
        assertNotNull(response1.get("result"));
        assertEquals("OK", response1.get("result"));
        JSONObject data = (JSONObject) response1.get("data");
        assertEquals("Done",data.get("message"));

    }
    @Test
    void NoRobotInWorld() throws IOException {
        //Given a client who has not been launched into the world
        SimpleClient client = new SimpleClient();
        // A state command would fail
        JSONObject response = new JSONObject(client.doRequest("Bob","state"));
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result"));

        // It will return a Robot cannot be found message
//        System.out.println(response + "hat we ");
        JSONObject data = (JSONObject) response.get("data");
        assertEquals("Robot cannot be found",data.get("message"));

    }



}
