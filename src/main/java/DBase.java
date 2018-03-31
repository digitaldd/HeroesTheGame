import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DBase {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet ResultSet;

    // connecting
    public static void conn() throws Exception {
       // connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:StartGameHeroes.db");

        AddLog.writeInFile("DataBase connected");
    }

    // create table
    public static void createDB(String table) throws Exception {
        statement = connection.createStatement();
        statement.execute(table);

        AddLog.writeInFile("Table created done");
    }

    // fill table
    public static void writeDB(List<String> team) throws Exception {
        for (String aTeam : team) {
            statement.execute(aTeam);
        }
        AddLog.writeInFile("Fill table done");
    }

    public static void updateDB(String tableName, String fieldName, int value, int id) throws Exception {
        statement.executeUpdate("UPDATE " + tableName + " SET " + fieldName + "=" + value + " WHERE id = " + id);
        AddLog.writeInFile("Fill table done");
    }

    public static void deleteStringInDB(String tableName) throws Exception {
        statement.executeUpdate("DELETE FROM " + tableName + " WHERE Health <= 0");
        AddLog.writeInFile("Deleted from table done");
    }

    // output table
    public static void readDB(String tableName) throws Exception {
        ResultSet = statement.executeQuery("SELECT * FROM " + tableName);

        while (ResultSet.next()) {
            int id = ResultSet.getInt("id");
            String race = ResultSet.getString("race");
            String count = ResultSet.getString("count");
            String character = ResultSet.getString("Character");
            AddLog.writeInFile("ID = " + id);
            AddLog.writeInFile("Race = " + race);
            AddLog.writeInFile("Character = " + character);
            AddLog.writeInFile("Count = " + count);
            AddLog.writeInFile("");
        }
        AddLog.writeInFile("Table output done");
    }

    public static String readDBField(String fieldName, String tableName, int id) throws Exception {
        statement = connection.createStatement();
        ResultSet = statement.executeQuery("SELECT " + fieldName + " FROM " + tableName + " WHERE ID = " + id + ";");
        String fieldValue = "";
        while (ResultSet.next()) {
            fieldValue = ResultSet.getString(fieldName);
        }
        return fieldValue;
    }

    public static String readDBCommand(String command) throws Exception {
        statement = connection.createStatement();
        ResultSet = statement.executeQuery(command);
        String result = "";
        while (ResultSet.next()) {
            result = ResultSet.getObject(1).toString();
        }
        return result;
    }

    // close all
    public static void closeDB() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite:StartGameHeroes.db");

        ResultSet.close();
        statement.close();
        connection.close();
        AddLog.writeInFile("All connections closed");
    }
}
