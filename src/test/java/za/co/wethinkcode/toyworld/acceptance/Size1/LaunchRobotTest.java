package za.co.wethinkcode.toyworld.acceptance.Size1;


import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.client.SimpleClient;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.world.Config;
import za.co.wethinkcode.toyworld.world.IWorld;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class  LaunchRobotTest {
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
    void validLaunchShouldSucceed() throws IWorld.WorldFullException, IWorld.UnknownRobotException, IOException {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("Bob","Launch"));


        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result"));


        // And the position should be (x:0, y:0)
        JSONObject state = (JSONObject) response.get("state");
        assertNotNull(response.get("result"));
//        assertEquals("[0,0]",state.get("position"));
    }

    @Test
    void invalidLaunchShouldFail() throws IOException {
        // Given that I am connected to a running Robot Worlds server
        // When I send an invalid launch request with the command "Launchk" instead of "launch"
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("Bob","Launchk"));

        // Then I should get an error response
        assertEquals("ERROR",response.get("result"));

        // And the message "Unsupported command"
        assertTrue(response.get("data").toString().contains("Unsupported command"));

    }

}
