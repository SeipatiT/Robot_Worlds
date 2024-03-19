package za.co.wethinkcode.toyworld.robot;

import org.json.JSONObject;

public class Robot implements IRobot{
    private final String name;
    private States status = States.NORMAL;


    public Robot(String name){
        this.name = name;
    }

//    public static Robot create(String s, String unknown) {
//        Robot robot = new Robot();
//        robot.setText(text);
//        robot.setName(name);
//        return robot;
//    }

    @Override
    public String getName() {
        return name;
    }

    public void setStatus(States status) {
        this.status = status;
    }

    @Override
    public States getStatus() {
        return status;
    }

    @Override
    public JSONObject getState() {
        return new JSONObject()
                .put("status",this.status.name());
    }

}
