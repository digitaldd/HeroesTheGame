import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 * all tests in this class needs new database, you need remove StartGameHeroes.db before start their
 */
public class DBaseTest {

    private static void updateDBTest() throws Exception {
        AddTeams.addTableLight();
        DBase.conn();
        DBase.statement = DBase.connection.createStatement();
        DBase.updateDB("LightTeam", "Priority", 1, 1);
        String priority = DBase.readDBCommand("SELECT ID FROM LightTeam WHERE Priority=1");
        Assert.assertEquals("UpdateDBTest1", "1", priority);
    }

    /**
     * testing row delete from database
     */
    private static void deleteStringInDBTest() throws Exception {
        String health3 = DBase
                .readDBCommand("SELECT ID FROM LightTeam WHERE Health=" + StartGameHeroes.STARTING_HEALTH);
        DBase.updateDB("LightTeam", "Health", 0, 3);
        DBase.deleteStringInDB("LightTeam");
        String health2 = DBase
                .readDBCommand("SELECT ID FROM LightTeam WHERE Health=" + StartGameHeroes.STARTING_HEALTH);
        boolean result = health2.equals(health3);
        Assert.assertEquals("DeleteStringInDBTest1", false, result);
    }

    /**
     * read from database
     */
    private static void readDBFieldTest() throws Exception {
        String health = DBase.readDBField("Health", "LightTeam", 1);
        Assert.assertEquals("ReadDBFieldTest", "100", health);
    }

    /**
     * read from database
     */
    private static void readDBCommandTest() throws Exception {
        String health = DBase.readDBCommand("SELECT ID FROM LightTeam WHERE Health=100");
        Assert.assertEquals("ReadDBCommandTest", "2", health);
    }

    /**
     * read from database list
     */
    private static void readDBCommand2Test() throws Exception {
        ArrayList<Integer> health = DBase.readDBCommand2("SELECT ID FROM LightTeam WHERE Health=100");
        boolean result = health.contains(2);
        Assert.assertEquals("ReadDBCommand2Test", true, result);
    }

    @Test
    public void main() throws Exception {
        updateDBTest();
        deleteStringInDBTest();
        readDBFieldTest();
        readDBCommandTest();
        readDBCommand2Test();
    }
}
