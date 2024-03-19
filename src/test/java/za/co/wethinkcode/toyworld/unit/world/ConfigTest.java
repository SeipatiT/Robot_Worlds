package za.co.wethinkcode.toyworld.unit.world;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyworld.world.Config;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void noArgumentsDefaultValues(){
        Config config = new Config();
        assertEquals(5000, config.getport());
        assertEquals(100,config.getworldSize());
    }
    @Test
    void onlyPortArgumentWithDefaultSize(){
        Config config = new Config("-p",5252);
        assertEquals(5252,config.getport());
        assertEquals(100,config.getworldSize());
    }
    @Test
    void onlyPortandWorldSize() {
        Config config = new Config("-p",1234,"-s",20);
        assertEquals(1234, config.getport());
        assertEquals(20, config.getworldSize());
    }
    @Test
    void XandYvaluesDefault() {
        Config config = new Config();
        assertEquals(100, config.getxLength());
        assertEquals(100, config.getyLength());
    }

    @Test
    void XandYdefinedValues(){
        Config config = new Config("-p",5000,"-s",5);
        assertEquals(5,config.getyLength());
        assertEquals(5,config.getxLength());
    }

    @Test
    void defaultVisibility(){
        Config config = new Config();
        assertEquals(100,config.getVisibilityLength());
    }

    @Test
    void definedVisibity(){
        Config config = new Config("-p",5000,"-s",15);
        assertEquals(15,config.getVisibilityLength());
    }

//    @Test
//    void canSpecifyConfigWithXYAndVisibility(){
//        Config config = new Config(10,20,2);
//        assertEquals(2,config.getVisibilityLength());
//        assertEquals(config.getVisibilityLength(),config.getShotRange());
//        assertEquals(Config.defaultShots,config.getMaxShots());
//    }
//
//    @Test
//    void canSpecifyConfigWithXYVisibilityAndShotRange(){
//        Config config = new Config(10,20,2);
//        assertEquals(5,config.getShotRange());
//        assertEquals(Config.defaultShots,config.getMaxShots());
//    }
//
//    @Test
//    void canSpecifyConfigWithXYVisibilityShotRangeAndMaxShots(){
//        Config config = new Config(10,20,2);
//        assertEquals(25,config.getMaxShots());
//    }

}