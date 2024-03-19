//package za.co.wethinkcode.toyworld.server;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
////Accidentally broke tests please do not remove yours//
//public class LaunchTest {
//    private final static int DEFAULT_PORT = 5000;
//    private final static String DEFAULT_IP = "localhost";
//    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
//    private final RobotWorldClient serverClient1 = new RobotWorldJsonClient();
//    private final RobotWorldClient serverClient2 = new RobotWorldJsonClient();
//    private final RobotWorldClient serverClient3 = new RobotWorldJsonClient();
//    private final RobotWorldClient serverClient4 = new RobotWorldJsonClient();
//    private final RobotWorldClient serverClient5 = new RobotWorldJsonClient();
//    private final RobotWorldClient serverClient6 = new RobotWorldJsonClient();
//    private final RobotWorldClient serverClient7 = new RobotWorldJsonClient();
//    private final RobotWorldClient serverClient8 = new RobotWorldJsonClient();
//    private final String requestOne = launchRequest("ficky");
//    private final String requestTwo = launchRequest("hal");
//    private final String requestThree = launchRequest("seipati");
//    private final String requestFour = launchRequest("mpho");
//    private final String requestFive = launchRequest("tyler");
//    private final String requestSix = launchRequest("liam");
//    private final String requestSeven = launchRequest("josh");
//    private final String requestEight = launchRequest("laighla");
//    private final String requestNine = launchRequest("andrea");
//
//    @BeforeEach
//    void connectToServer(){
//        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
//        serverClient1.connect(DEFAULT_IP, DEFAULT_PORT);
//        serverClient2.connect(DEFAULT_IP, DEFAULT_PORT);
//        serverClient3.connect(DEFAULT_IP, DEFAULT_PORT);
//        serverClient4.connect(DEFAULT_IP, DEFAULT_PORT);
//        serverClient5.connect(DEFAULT_IP, DEFAULT_PORT);
//        serverClient6.connect(DEFAULT_IP, DEFAULT_PORT);
//        serverClient7.connect(DEFAULT_IP, DEFAULT_PORT);
//        serverClient8.connect(DEFAULT_IP, DEFAULT_PORT);
//    }
//
//    @AfterEach
//    void disconnectFromServer(){
//        serverClient.disconnect();
//        serverClient1.disconnect();
//        serverClient2.disconnect();
//        serverClient3.disconnect();
//        serverClient4.disconnect();
//        serverClient5.disconnect();
//        serverClient6.disconnect();
//        serverClient7.disconnect();
//        serverClient8.disconnect();
//    }
//
//    @Test
//    void twoWorldRobotExistsShouldFail() {
//        // Given that I am connected to a running Robot.Robot Worlds server
//        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
//        assertTrue(serverClient.isConnected());
//
//
//        String request = launchRequest("ficky");
//        // When I send a launch command twice with the same name
//        serverClient.sendRequest(request);
//        JsonNode response = serverClient.sendRequest(request);
//
//        // Then I should get an error response from the server if a
//        // robot with the name already exists
//        doAssertNotNullResponse(response);
//        assertEquals("ERROR", response.get("result").asText());
//        assertEquals("Too many of you in this world", response.get("data").get("message").asText());
//    }
//
//    @Test
//    void twoWorldFullShouldFail() {
//        // Given that I am connected to a running Robot.Robot Worlds server
//        // And the world is of size 2x2 (The world is configured or hardcoded to this size)
//        assertTrue(serverClient.isConnected());
//
//        String requestServerAlreadyFull = launchRequest("R2D2");
//        sendEightRequests();
//        serverClient.sendRequest(requestNine);
//        JsonNode response = serverClient.sendRequest(requestServerAlreadyFull);
//
//        doAssertNotNullResponse(response);
//        doErrorNoSpaceAssertion(response);
//    }
//
//    @Test
//    void defaultWorldInvalidLaunchShouldFail(){
//        // if i am connected to a running server
//        assertTrue(serverClient.isConnected());
//
//        // When I send a invalid launch request with the command "luanch" instead of "launch"
//        String request = "{" +
//                "\"robot\": \"Ficky\"," +
//                "\"command\": \"launch\"," +
//                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                "}";
//        JsonNode response = serverClient.sendRequest(request);
//
//        doAssertNotNullResponse(response);
//
//        // Should get an error response and the message "Unsupported command"
//        assertEquals("ERROR", response.get("result").asText());
//        assertNotNull(response.get("data").get("message"));
//        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
//    }
//
//
//    @Test
//    void twoWorldLaunchTwoRobotsShouldPass() {
//        assertTrue(serverClient.isConnected());
//
////        String request = launchRequest("ficky");
//        JsonNode response1 = serverClient.sendRequest(requestOne);
//
////        String request2 = launchRequest("hal");
//        JsonNode response = serverClient.sendRequest(requestTwo);
//
//        assertEquals(response.get("result").textValue(),"OK");
//        assertEquals(response1.get("result").textValue(),"OK");
//    }
//
//
//    private String launchRequest(String name){
//        return "{" +
//                "\"robot\":\"" + name +"\"," +
//                "\"command\": \"launch\"," +
//                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                "}";
//    }
//
//    private void sendEightRequests(){
//        serverClient.sendRequest(requestOne);
//        serverClient1.sendRequest(requestTwo);
//        serverClient2.sendRequest(requestThree);
//        serverClient3.sendRequest(requestFour);
//        serverClient4.sendRequest(requestFive);
//        serverClient5.sendRequest(requestSix);
//        serverClient6.sendRequest(requestSeven);
//        serverClient7.sendRequest(requestEight);
//    }
//
//    private void doErrorNoSpaceAssertion(JsonNode response) {
//        assertEquals("ERROR", response.get("result").asText());
//        assertEquals("No more space in this world", response.get("data").get("message").asText());
//    }
//
//    private void doAssertNotNullResponse(JsonNode response){
//        assertNotNull(response.get("result"));
//        assertNotNull(response.get("data"));
//    }
//}
