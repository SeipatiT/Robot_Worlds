package za.co.wethinkcode.toyworld.Helper;

import za.co.wethinkcode.toyworld.world.maze.Obstacle;
import za.co.wethinkcode.toyworld.world.maze.SquareObstacle;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class SQLITE_HELPER {

    private Connection connection;

    public void connect(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:worlds.db");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean worldExists(String name){
        String  selectSQL = "SELECT * from worlds";
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                if (resultSet.getString("name").equals(name)) {
                    return true;
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public void  EnterWorldData(String name, int size, List<SquareObstacle> obstacles){

        if (worldExists(name)){
            System.out.println("The world "+name+" already exists " +
                    "would you like to override this save? Y/n ");
            Scanner in = new Scanner(System.in);
            String overwritePrompt = in.nextLine().toLowerCase();

            if(overwritePrompt.equals("y")|| overwritePrompt.equalsIgnoreCase("yes")){
                updateWorld(name, size);
                clearWorldData(name);
                InsertObject(name, obstacles);
                System.out.println("Configure data for world "+name+ " has been overwritten!");
            } else {
                System.out.println("This world will not be saved");
            }
        }else {
            InsertWorld(name,size);
            InsertObject(name, obstacles);
            System.out.println("Configuration data for world: " +name+ " has been saved!");
        }

    }

    private void updateWorld(String name, int size) {
        try {
            String insertSQL = "UPDATE  worlds SET size = ? WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, size);
            preparedStatement.setString(2,name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void InsertWorld(String name, int size) {
        try {
            String insertSQL = "INSERT INTO worlds(name, size) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2, size);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearWorldData(String name) {
        try {
            String deleteSQL = "DELETE FROM worlds WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void InsertObject(String name, List<SquareObstacle> obstacles) {
        try {
            for (Obstacle obstacle : obstacles) {
                String insertSQL = "INSERT INTO worlds_objects(name, position_x, position_y, type, size) VALUES(?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, obstacle.getBottomLeftX());
                preparedStatement.setInt(3, obstacle.getBottomLeftY());
                preparedStatement.setString(4, "obstacle");
                preparedStatement.setInt(5, obstacle.getSize());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


/** previous code...redundant
//public class SQLITE_HELPER {
//    private Connection connection;
//
//    public void Connect() {
//        try{
//            connection = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }
//    public void closeConnection(){
//        try{
//            connection.close();
//        }catch (SQLException e){
//            throw new RuntimeException();
//        }
//    }
//
//    //redundant
////    public SQLITE_HELPER (Connection connect){
////        this.connect = connect;
////    }
////
//    public boolean worldExists(String name){
//        String  selectSQL = "SELECT * from worlds";
//        Statement statement;
//        try {
//            statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(selectSQL);
//            while (resultSet.next()) {
//                if (resultSet.getString("name").equals(name)) {
//                    return true;
//                }
//            }
//        } catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//        return false;
//    }
////inserts a world into the database
//    private void insertWorld(String name, int size){
//        try {
//            String Worldsql = "Insert into worlds(Name,Size) Values(?,?)";
//            PreparedStatement prep_state = connection.prepareStatement(Worldsql);
//            prep_state.setString(1,name);
//            prep_state.setInt(2,size);
//            prep_state.executeUpdate();
//        }catch( SQLException i){
//            throw new RuntimeException(i);
//        }
//    }
//    //Update or overide an existing world
////    private void updateWorld(String name, int size) {
////        try {
////            String Worldsql = " Update worlds SET Size = ? WHERE Name = ?";
////            PreparedStatement prep_state = connection.prepareStatement(Worldsql);
////            prep_state.setInt(1, size);
////            prep_state.setString(2, name);
////            prep_state.executeUpdate();
////        } catch (SQLException i) {
////            throw new RuntimeException(i);
////        }
////    }
//    public void clearWorld(String name) {
//        try {
//            String deleteSQL = "DELETE FROM worlds WHERE name=?";
//            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
//            preparedStatement.setString(1, name);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void insertObstacles(String name, List<Obstacle> obstacles, int size) {
//        try {
//            for (Obstacle obstacle : obstacles) {
//                String Worldsql = "Insert into Obstacles(Name,pos_x,pos_y,TYpe,Size) Values(?,?,?,?,?)";
//                PreparedStatement prep_state = connection.prepareStatement(Worldsql);
//                prep_state.setString(1, name);
//                prep_state.setInt(2, obstacle.getBottomLeftX());
//                prep_state.setInt(3, obstacle.getBottomLeftY());
//                prep_state.setString(4, "obstacle");
//                prep_state.setInt(5, size);
//                prep_state.executeUpdate();
//            }
//        } catch (SQLException i) {
//            throw new RuntimeException(i);
//        }
//    }
////    private void updateObstacles(String name, List<Obstacle> obstacles, int size) {
////        try {
////            String Worldsql = " Update worlds SET Size = ? WHERE Name = ?";
////            PreparedStatement prep_state = connection.prepareStatement(Worldsql);
////            prep_state.setInt(1, size);
////            prep_state.setString(2, name);
////            prep_state.executeUpdate();
////        } catch (SQLException i) {
////            throw new RuntimeException(i);
////        }
////    }
//}
 */