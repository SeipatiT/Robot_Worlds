package za.co.wethinkcode.toyworld.world;

import org.json.JSONObject;

public class Config {

    public static final int defaultLength = 100;
    public static final int defaultShots = 10;
    public static final int defaultShields = 5;

    public static final int defaultPort = 5000;
    private final int xLength;
    private final int yLength;
    private final int visibilityLength;
    private final int shotRange=10;
    private final int maxShots = defaultShots;
    private final int maxShields= defaultShields;

    //    private final String serverPort;
    public static int port;
    //    private final String worldSize;
    private final int size;
    //    private final String obstacles;
    private final int obstacle;

    public Config(String p,int port,String s,int size,String o,int obstacle){
        this.port = port;
        this.size = size;
        this.obstacle = obstacle;
        this.xLength= size;
        this.yLength = size;
        this.visibilityLength=size;
    }
    public Config(String p,int port,String s,int size){
        this.port = port;
        this.size = size;
        this.xLength= size;
        this.yLength = size;
        this.visibilityLength=size;
        this.obstacle= 0;
    }
    public Config(String p,int port){
        this.port = port;
        this.size = defaultLength;
        this.xLength= defaultLength;
        this.yLength = defaultLength;
        this.visibilityLength=size;
        this.obstacle= 0;
    }

    public Config(){
        this.port = defaultPort;
        this.size = defaultLength;
        this.xLength= size;
        this.yLength = size;
        this.visibilityLength=size;
        this.obstacle= 0;

    }
//    public Config(int xLength, int yLength, int visibilityLength, int shotRange, int maxShots, int maxShields) {
//        this.xLength = xLength;
//        this.yLength = yLength;
//        this.visibilityLength = visibilityLength;
//        this.shotRange = shotRange;
//        this.maxShots = maxShots;
//        this.maxShields = maxShields;
//    }
//
//    public Config(){
//        this(defaultLength,defaultLength);
//    }
//
//    public Config(int xLength,int yLength){
//        this(xLength,yLength,defaultLength);
//    }
//
//    public Config(int xLength, int yLength, int visibilityLength){
//        this(xLength,yLength,visibilityLength,visibilityLength);
//    }
//
//    public Config(int xLength, int yLength, int visibilityLength, int shotRange){
//        this(xLength,yLength,visibilityLength,shotRange,defaultShots);
//    }
//
//    public Config(int xLength, int yLength, int visibilityLength, int shotRange, int maxShots){
//        this(xLength,yLength,visibilityLength,shotRange,maxShots, defaultShields);
//    }


    public int getxLength() {
        return xLength;
    }

    public int getyLength() {
        return yLength;
    }
    public int getworldSize(){
        return size;
    }
    public int getport(){
        return port;
    }
    public int getVisibilityLength() {
        return visibilityLength;
    }

    public int getShotRange() {
        return shotRange;
    }

    public int getMaxShots() {
        return maxShots;
    }

    public int getMaxShields() {return maxShields; }

    public JSONObject getJSON(){
        JSONObject data = new JSONObject();
        return data.put("visibility",visibilityLength)
                .put("shields", maxShields)
                .put("shot range",shotRange)
                .put("shots",maxShots);
    }
}
