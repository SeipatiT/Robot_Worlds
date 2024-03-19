package za.co.wethinkcode.toyworld.unit.server;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.client.SimpleClient;
import za.co.wethinkcode.toyworld.server.Commands;
import za.co.wethinkcode.toyworld.server.MultiServers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class MultiServersTest {
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
    void singleClientLaunchSingleRobot() throws IOException {
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("Bob","Launch"));
        assertEquals("OK",response.get("result"));
        JSONObject state = (JSONObject) response.get("state");
//        assertEquals("NORTH",state.get("direction"));
        assertEquals("NORMAL",state.get("status"));
    }

    @Test
    void singleClientCannotLaunch2RobotsWithSameName() throws IOException {
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("Bob","Launch"));
        assertEquals("OK",response.get("result"));
        JSONObject state = (JSONObject) response.get("state");
//        assertEquals("NORTH",state.get("direction"));
        assertEquals("NORMAL",state.get("status"));

        response = new JSONObject(client.doRequest("Bob","Launch"));
        assertEquals("ERROR",response.get("result"));
        JSONObject data = (JSONObject) response.get("data");
        assertEquals("Too many of you in this world",data.get("message"));
    }

    @Test
    void singleClientCanLaunchMultipleDifferentRobots() throws IOException {
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("Bob","Launch"));
        assertEquals("OK",response.get("result"));
        JSONObject state = (JSONObject) response.get("state");
//        assertEquals("NORTH",state.get("direction"));
        assertEquals("NORMAL",state.get("status"));

        response = new JSONObject(client.doRequest("Harold","Launch"));
        assertEquals("OK",response.get("result"));
        state = (JSONObject) response.get("state");
//        assertEquals("NORTH",state.get("direction"));
        assertEquals("NORMAL",state.get("status"));
    }

    @Test
    void multipleClientsCanLaunchDifferentRobots() throws IOException {
        SimpleClient client1 = new SimpleClient();
        JSONObject response = new JSONObject(client1.doRequest("Bob","Launch"));
        assertEquals("OK",response.get("result"));
        JSONObject state = (JSONObject) response.get("state");
//        assertEquals("NORTH",state.get("direction"));
        assertEquals("NORMAL",state.get("status"));


        SimpleClient client2 = new SimpleClient();
        response = new JSONObject(client2.doRequest("Harold","Launch"));
        assertEquals("OK",response.get("result"));
        state = (JSONObject) response.get("state");
//        assertEquals("NORTH",state.get("direction"));
        assertEquals("NORMAL",state.get("status"));
    }

//    @Test
//    void multipleClientsCanLaunchRobotsWithSameName() throws IOException {
//
//        SimpleClient client1 = new SimpleClient();
//        JSONObject response = new JSONObject(client1.doRequest("Bob","Launch"));
//        assertEquals("OK",response.get("result"));
//        JSONObject state = (JSONObject) response.get("state");
////        assertEquals("NORTH",state.get("direction"));
//        assertEquals("NORMAL",state.get("status"));
//
//
//        SimpleClient client2 = new SimpleClient();
//        response = new JSONObject(client2.doRequest("Bob","Launch"));
//        assertEquals("error",response.get("result"));
//        state = (JSONObject) response.get("state");
////        assertEquals("NORTH",state.get("direction"));
//        assertEquals("NORMAL",state.get("status"));
//    }

    @Test
    void clientCannotUseRobotBeforeLaunch() throws IOException {
        SimpleClient client = new SimpleClient();
        JSONObject response = new JSONObject(client.doRequest("Bob","Shutdown"));
        assertEquals("ERROR",response.get("result"));
        JSONObject data = (JSONObject) response.get("data");
        assertEquals("Robot cannot be found", data.get("message"));
    }

    @Test
    void clientCannotInteractWithDeadRobot() throws IOException {
        SimpleClient client = new SimpleClient();
        client.doRequest("Bob","Launch");
        client.doRequest("Bob","Shutdown");
        JSONObject response = new JSONObject(client.doRequest("Bob", "Shutdown"));
        assertEquals("ERROR",response.get("result"));
        JSONObject data = (JSONObject) response.get("data");
        assertEquals("Robot cannot be found", data.get("message"));

    }

    @Test
    void robotCommandEmptyWhenNoRobots(){
        JSONObject result = Commands.ROBOTS.execute(server);
        assertTrue(new JSONObject().similar(result));
    }

}