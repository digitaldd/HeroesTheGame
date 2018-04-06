import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for work with database
 */
public class DBase {

    static Connection connection;
    static Statement statement;
    private static ResultSet ResultSet;

    /**
     * establishing connecting with database
     */
    public static void conn() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:StartGameHeroes.db");
        AddLog.writeInFile("DataBase connected");
    }

    /**
     * creating table in database
     *
     * @param table contains command for execute in sqlite
     */
    public static void createDB(String table) throws Exception {
        statement = connection.createStatement();
        statement.execute(table);
        AddLog.writeInFile("Table created done");
    }

    /**
     * add fields in table
     *
     * @param team contains commands for create fields in table
     */
    // fill table
    public static void writeDB(List<String> team) throws Exception {
        for (String aTeam : team) {
            statement.execute(aTeam);
        }
        AddLog.writeInFile("Fill table done");
    }

    /**
     * change field value
     *
     * @param tableName table name - wherein contains required field
     * @param fieldName field name - wherein need set new value
     * @param value - new value
     * @param id - character
     */
    public static void updateDB(String tableName, String fieldName, int value, int id) throws Exception {
        statement.executeUpdate("UPDATE " + tableName + " SET " + fieldName + "=" + value + " WHERE id = " + id);
        AddLog.writeInFile("Fill table done");
    }

    /**
     * delete row from database
     *
     * @param tableName - name of table, wherein need delete row
     */
    public static void deleteStringInDB(String tableName) throws Exception {
        statement.executeUpdate("DELETE FROM " + tableName + " WHERE Health <= 0");
        AddLog.writeInFile("Deleted from table done");
    }

    /**
     * output information from database
     *
     * @param tableName name of table wherein need output info
     */
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

    /**
     * read value of field
     *
     * @param fieldName wherein need reed value
     * @param tableName name of table, wherein contains required field
     * @param id character
     * @return value of field in string format
     */
    public static String readDBField(String fieldName, String tableName, int id) throws Exception {
        ResultSet = statement.executeQuery("SELECT " + fieldName + " FROM " + tableName + " WHERE ID = " + id + ";");
        String fieldValue = "";
        while (ResultSet.next()) {
            fieldValue = ResultSet.getString(fieldName);
        }
        return fieldValue;
    }

    /**
     * this method get result of execution command in sqlite and return his value in string format
     *
     * @param command - what need execute
     * @return value in string format
     */
    public static String readDBCommand(String command) throws Exception {
        statement = connection.createStatement();
        ResultSet = statement.executeQuery(command);
        String result = "";
        while (ResultSet.next()) {
            result = ResultSet.getObject(1).toString();
        }
        return result;
    }

    /**
     * this method = readDBCommand, but return result in list integer format
     *
     * @param command - what need execute
     * @return value in string format
     */
    public static ArrayList<Integer> readDBCommand2(String command) throws Exception {
        statement = connection.createStatement();
        ResultSet = statement.executeQuery(command);
        ArrayList<Integer> list = new ArrayList<>();
        while (ResultSet.next()) {
            list.add(Integer.parseInt(ResultSet.getObject(1).toString()));
        }
        return list;
    }

    /**
     * close connection from with database
     */
    public static void closeDB() throws Exception {
        ResultSet.close();
        statement.close();
        connection.close();
        AddLog.writeInFile("All connections closed");
    }
}