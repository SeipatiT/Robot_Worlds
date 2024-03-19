package za.co.wethinkcode.toyworld.acceptance.Size2.noObstacles;

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

public class LaunchIterationTwoTests {
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
    void shouldSuccessfullyLaunchTwoClients() throws IOException {
        //  Given a world of size 2x2
        //  and robot "HAL" has already been launched into the world
        //  Launch first client
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("HAL","Launch"));
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result"));

        //  Launch second client
        client = new SimpleClient();
        response = new JSONObject(client.doRequest("BOB","Launch"));


        // Then I should get a second valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result"));
    }

    @Test
    void shouldNotLaunchTwoClientsWithSameName() throws IOException {
        //  Given a world of size 2x2
        //  and robot "HAL" has already been launched into the world
        //  Launch first client
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("HAL","Launch"));
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result"));

        //  Launch second client with same name
        client = new SimpleClient();
        response = new JSONObject(client.doRequest("HAL","Launch"));

        // Then I should get an error response from the server
        assertNotNull(response);
        assertEquals("ERROR", response.get("result"));

        // The message should be
        JSONObject data = (JSONObject) response.get("data");
        assertEquals("Too many of you in this world",data.get("message"));
    }


}