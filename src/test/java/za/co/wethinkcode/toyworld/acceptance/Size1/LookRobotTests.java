package za.co.wethinkcode.toyworld.acceptance.Size1;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import za.co.wethinkcode.toyworld.client.SimpleClient;
import za.co.wethinkcode.toyworld.robot.IRobot;
import za.co.wethinkcode.toyworld.server.MultiServers;
import za.co.wethinkcode.toyworld.server.RobotWorldClient;
import za.co.wethinkcode.toyworld.server.RobotWorldJsonClient;
import za.co.wethinkcode.toyworld.world.AbstractWorld;
import za.co.wethinkcode.toyworld.world.Config;
import za.co.wethinkcode.toyworld.world.IWorld;
import za.co.wethinkcode.toyworld.world.maze.EmptyMaze;

import java.io.IOException;

class LookRobotTests {
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
    void EmptyWorld() throws IOException {
        //Given a launched robot
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("Bob","Launch"));
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result"));

        //Sending a second command to look should return an empty message
        //as the world only has one robot
        JSONObject response1 = new JSONObject(client.doRequest("Bob","look"));
        System.out.println(response1 + "hat we ");
        assertNotNull(response1.get("result"));
        assertEquals("OK", response1.get("result"));
        JSONObject data = (JSONObject) response1.get("data");
        assertEquals("{}",data.toString());

    }
}


