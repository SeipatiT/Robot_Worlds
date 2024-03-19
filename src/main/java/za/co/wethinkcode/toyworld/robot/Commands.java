//package za.co.wethinkcode.toyworld.robot;
//
//import org.json.JSONObject;
//import za.co.wethinkcode.toyworld.interfaces.*;
//import za.co.wethinkcode.toyworld.server.MultiServers;
//import za.co.wethinkcode.toyworld.world.Edge;
//import za.co.wethinkcode.toyworld.world.IWorld;
//import za.co.wethinkcode.toyworld.world.Position;
//import za.co.wethinkcode.toyworld.world.maze.Obstacle;
//
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Locale;
////
//public enum Commands {
////    STATE {
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException {
////            IRobot robot = Commands.getRobot(server,client,robotName);
////            JSONObject result = new JSONObject();
////            JSONObject data = new JSONObject();
////            data.put("message","Done");
////            return result.put("state",robot.getState()).put("data",data);
////        }
//}
//,LOOK {
//        @Override
//        public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {
//            IRobot robot = Commands.getRobot(server,client,robotName);
//            HashMap<Object, Position> objectPositionHashMap = server.getWorld().checkSurroundings(robot);
//            JSONObject data = new JSONObject();
//            Position robotPosition = server.getWorld().getPosition(robot);
//            for(Object object : objectPositionHashMap.keySet()){
//                int distance;
//                Direction directionOfObject;
//                Position objectPosition = objectPositionHashMap.get(object);
//                JSONObject  objectDetails = new JSONObject();
//                if(object instanceof IRobot){
//                    objectDetails.put("type","ROBOT");
//                } else if (object instanceof Edge) {
//                    objectDetails.put("type","EDGE");
//
//                } else if (object instanceof Obstacle){
//                    objectDetails.put("type","OBSTACLE");
//                }else{
//                    objectDetails.put("type",object.getClass().getSimpleName().toUpperCase(Locale.ROOT));
//                }
//                directionOfObject = robotPosition.directionOf(objectPosition);
//                distance = robotPosition.distanceTo(objectPosition);
//                objectDetails.put("direction",directionOfObject.name())
//                        .put("distance",Math.abs(distance));
//                data.append("objects",objectDetails);
//            }
//            return new StateCommand().execute(server, client, robotName, args)
//                    .put("data",data);
//        }
//    };
//
//    public abstract JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException, IWorld.WorldException;
//
//    private static IRobot getRobot(MultiServers server, Socket client, String robotName) throws MultiServers.UnknownClientException, MultiServers.UnknownRobotException {
//        try {
//            IRobot robot = server.getClients().get(client).get(robotName);
//            if(robot == null){
//                throw new MultiServers.UnknownRobotException();
//            }
//            return robot;
//        }catch (NullPointerException e){
//            throw new MultiServers.UnknownClientException();
//        }
//
//    }
//}
////    LAUNCH{
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException, IWorld.WorldException {
////            try{
////                HashMap<String,IRobot> robots = server.getClients().get(client);
////                if (robots.containsKey(robotName)){
////                    throw new MultiServers.RobotNameAlreadyTakenException();
////                }
////                IWorld world = server.getWorld();
////                IRobot robot = world.addRobot(robotName);
////                robots.put(robotName, robot);
////                JSONObject data = world.getConfig().getJSON().put("position",world.getPosition(robot));
////                return new StateCommand().execute(server, client, robotName, args).put("data",data);
////            } catch (NullPointerException e) {
////                throw new MultiServers.UnknownClientException();
////            }
////        }
////    },FORWARD {
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {
////        IRobot robot = Commands.getRobot(server,client,robotName);
////        JSONObject data = new JSONObject();
////        int nrSteps = Integer.parseInt(args[0]);
////        switch(server.getWorld().updatePosition(robot, nrSteps)){
////            case SUCCESS:
////                data.put("message","Done");
////                break;
////            case FAILED_OUTSIDE_WORLD:
////                data.put("message","Outside Border");
////                break;
////            case FAILED_OBSTRUCTED:
////                data.put("message","Obstructed");
////                break;
////        }
////        return new StateCommand().execute(server, client, robotName, args).put("data",data);
////        }
////
////
////    },BACK {
////        @Override
////        public JSONObject execute (MultiServers server, Socket client, String robotName, String[] args) throws
////            MultiServers.RequestException, IWorld.WorldException {
////            JSONObject data = new JSONObject();
////            IRobot robot = Commands.getRobot(server,client,robotName);
////            int nrSteps = Integer.parseInt(args[0]);
////            switch (server.getWorld().updatePosition(robot, -nrSteps)){
////                case SUCCESS:
////                    data.put("message","Done");
////                    break;
////                case FAILED_OBSTRUCTED:
////                    data.put("message","Obstructed");
////                    break;
////                case FAILED_OUTSIDE_WORLD:
////                    data.put("message","Outside Border");
////            }
////            return new StateCommand().execute(server, client, robotName, args).put("data",data);
////        }
////
////    },SHUTDOWN {
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {
////            IRobot robot = Commands.getRobot(server,client,robotName);
////            HashMap<String, IRobot> robots = server.getClients().get(client);
////            robot.setStatus(States.DEAD);
////            JSONObject response = new StateCommand().execute(server, client, robotName,args);
////            robots.remove(robotName);
////            return response;
////        }
////    },OFF {
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException, IWorld.WorldException {
////            return SHUTDOWN.execute(server, client, robotName, args);
////        }
////    },LOOK {
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {
////            IRobot robot = Commands.getRobot(server,client,robotName);
////            HashMap<Object, Position> objectPositionHashMap = server.getWorld().checkSurroundings(robot);
////            JSONObject data = new JSONObject();
////            Position robotPosition = server.getWorld().getPosition(robot);
////            for(Object object : objectPositionHashMap.keySet()){
////                int distance;
////                Direction directionOfObject;
////                Position objectPosition = objectPositionHashMap.get(object);
////                JSONObject  objectDetails = new JSONObject();
////                if(object instanceof IRobot){
////                    objectDetails.put("type","ROBOT");
////                } else if (object instanceof Edge) {
////                    objectDetails.put("type","EDGE");
////
////                } else if (object instanceof Obstacle){
////                    objectDetails.put("type","OBSTACLE");
////                }else{
////                    objectDetails.put("type",object.getClass().getSimpleName().toUpperCase(Locale.ROOT));
////                }
////                directionOfObject = robotPosition.directionOf(objectPosition);
////                distance = robotPosition.distanceTo(objectPosition);
////                objectDetails.put("direction",directionOfObject.name())
////                        .put("distance",Math.abs(distance));
////                data.append("objects",objectDetails);
////            }
////            return new StateCommand().execute(server, client, robotName, args)
////                    .put("data",data);
////        }
////    },STATE {
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException {
////            IRobot robot = Commands.getRobot(server,client,robotName);
////            JSONObject result = new JSONObject();
////            JSONObject data = new JSONObject();
////            data.put("message","Done");
////            return result.put("state",robot.getState()).put("data",data);
////
////        }
////    },SHOOT{
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException, IWorld.WorldException {
////            IRobot robot =Commands.getRobot(server,client,robotName);
////            if (robot.getStatus() == States.RELOAD){
////                robot.setStatus(States.NORMAL);
////                throw new MultiServers.RequestException("Currently Reloading Try Again") {};
////            }
////            try{
////                Damageable objectHit = server.getWorld().shoot(robot);
////                JSONObject data = new JSONObject();
////                if(objectHit instanceof StateHaving){
////                    data.put("state",((StateHaving) objectHit).getState());
////                }
////                if(objectHit instanceof Named){
////                    data.put(objectHit.getClass().getSimpleName(),((Named) objectHit).getName());
////                }else if(objectHit != null){
////                    data.put("type",objectHit.getClass().getSimpleName());
////                }
////                if(objectHit instanceof Positional && robot instanceof Positional){
////                    data.put("distance", ((Positional) robot).getPosition().distanceTo(
////                            ((Positional) objectHit).getPosition()));
////                }
////                if(objectHit == null){
////                    data.put("message","Miss");
////                }else{
////                    data.put("message","Hit");
////                }
////                return new JSONObject().put("state",robot.getState()).put("data",data);
////            }catch (Shooting.EmptyMagazineException e){
////                throw new MultiServers.RequestException(e.getMessage()) {
////                };
////            }
////        }
////    },REPAIR{
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {
////            IRobot robot =Commands.getRobot(server,client,robotName);
////            if(robot instanceof Damageable){
////                try{
////                    ((Damageable) robot).repair(Integer.parseInt(args[0]));
////                }catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
////                    ((Damageable) robot).repair(1);
////                }
////            }
////            robot.setStatus(States.REPAIR);
////            return new StateCommand().execute(server, client, robotName, args);
////        }
////    },RELOAD{
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {
////            IRobot robot =Commands.getRobot(server,client,robotName);
////            if(robot instanceof Shooting){
////                try{
////                    ((Shooting) robot).reload(Integer.parseInt(args[0]));
////                }catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
////                    ((Shooting) robot).reload(1);
////                }
////            }
////            robot.setStatus(States.RELOAD);
////            return new StateCommand().execute(server, client, robotName, args);
////        }
////    },TURN{
////        @Override
////        public JSONObject execute(MultiServers server, Socket client, String robotName, String[] args) throws MultiServers.RequestException, IWorld.WorldException {
////            boolean turnRight;
////            IRobot robot = Commands.getRobot(server, client, robotName);
////            try{
////                turnRight = args[0].equalsIgnoreCase("right");
////            }catch (ArrayIndexOutOfBoundsException e){
////                turnRight = true;
////            }
////            server.getWorld().updateDirection(robot,turnRight);
////            return new StateCommand().execute(server, client, robotName, args);
////        }
////    };
////
////    public abstract JSONObject execute(MultiServers server, Socket client, String robotName, String [] args) throws MultiServers.RequestException, IWorld.WorldException;
////
////    public static Commands getCommandWith(String commandName) throws UnknownRobotCommandException {
////        try{
////            return valueOf(commandName.toUpperCase(Locale.ROOT));
////        }catch (IllegalArgumentException e){
////            throw new UnknownRobotCommandException();
////        }
////    }
////
////    private static IRobot getRobot(MultiServers server, Socket client, String robotName) throws MultiServers.UnknownClientException, MultiServers.UnknownRobotException {
////        try {
////            IRobot robot = server.getClients().get(client).get(robotName);
////            if(robot == null){
////                throw new MultiServers.UnknownRobotException();
////            }
////            return robot;
////        }catch (NullPointerException e){
////            throw new MultiServers.UnknownClientException();
////        }
////
////    }
////
////    public static class UnknownRobotCommandException extends Exception{
////        public UnknownRobotCommandException(){
////            super("Unsupported command");
////        }
////    }
////}
