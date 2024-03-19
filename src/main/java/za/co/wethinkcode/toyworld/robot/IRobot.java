package za.co.wethinkcode.toyworld.robot;

import za.co.wethinkcode.toyworld.interfaces.Named;
import za.co.wethinkcode.toyworld.interfaces.StateHaving;

public interface IRobot extends StateHaving, Named {


    void setStatus(States state);

    States getStatus();


}
