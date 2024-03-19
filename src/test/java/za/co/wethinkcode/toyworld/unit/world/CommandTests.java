package za.co.wethinkcode.toyworld.unit.world;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.client.SimpleClient;
import za.co.wethinkcode.toyworld.server.MultiServers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTests {
    private MultiServers server;
    private Thread serverThread;


    @BeforeEach
    void setUpServer() throws IOException {
        server = new MultiServers();
        serverThread = new Thread(server);
        serverThread.start();
    }

    @AfterEach
    void closeServer() throws InterruptedException {
        server.stopRunning();
        serverThread.join();

    }

    @Test
    void launchCommandWorks() throws IOException{
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("","Launch"));
        assertEquals("OK",response.get("result"));
        JSONObject state = (JSONObject) response.get("state");
        assertEquals("NORMAL",state.get("status"));
    }

    @Test
    void launchCommandFails() throws IOException{
        /* I did not use JSON response to show a wrong launch command*/
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("","Try"));
        assertEquals("ERROR",response.get("result"));

    }


//    @Test
//    void lookCommandPasses() throws IOException{
//        // Given that I am connected to a running Robot Worlds server
//        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
//        assertTrue(serverClient.isConnected());
//
//        // When I send a valid launch request to the server
//
//
//    }

}
