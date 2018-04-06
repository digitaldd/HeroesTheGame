import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 * all tests in this class needs new database, you need remove StartGameHeroes.db before start their
 */
public class CheckRulesTest {

    /**
     * priority 1 don't found
     */
    private static void checkStatusSuperTest() throws Exception {
        DBase.conn();
        Assert.assertEquals("CheckStatusSuperTest1", 0, CheckRules.checkStatusSuper());
    }

    /**
     * list contains random numbers 1-3
     */
    private static void checkPriorityRandomTest() throws Exception {
        AddTeams.addTableLight();
        DBase.conn();
        Game.attackerTable = "LightTeam";
        ArrayList<Integer> list = CheckRules.checkPriorityRandom();
        Assert.assertEquals("checkPriorityRandomTest1", true, list.contains(1));
        Assert.assertEquals("checkPriorityRandomTest2", true, list.contains(2));
        Assert.assertEquals("checkPriorityRandomTest3", true, list.contains(3));
        DBase.closeDB();
    }

    /**
     * test keywords and health-damage
     */
    private static void calculateHealthTest() throws Exception {
        //test -reduce- and health
        DBase.conn();
        Game.defendingHealth = 50;
        Game.attackerSkillEffect = 10;
        Game.defendingTable = "LightTeam";
        Game.attackerSkillName = "REDUCE";
        Assert.assertEquals("CalculateHealthTest1", 40, CheckRules.calculateHealth());
        Assert.assertEquals("CalculateHealthTest2", "LightTeam", CheckRules.reducerTableName);
        //test -add-
        Game.attackerTable = "LightTeam";
        Game.attackerSkillName = "aDD";
        CheckRules.calculateHealth();
        String priority = DBase.readDBCommand("SELECT ID FROM LightTeam WHERE Priority=1");
        boolean result = priority.equals("1") || priority.equals("2") || priority.equals("3");
        Assert.assertEquals("CalculateHealthTest3", true, result);
        //test -off-
        Game.defendingTable = "LightTeam";
        Game.attackerSkillName = "ofF";
        CheckRules.calculateHealth();
        String priorityOff = DBase.readDBCommand("SELECT Priority FROM LightTeam WHERE ID=" + priority + "");
        Assert.assertEquals("CalculateHealthTest4", "0", priorityOff);
        DBase.closeDB();
    }

    @Test
    public void main() throws Exception {
        checkStatusSuperTest();
        checkPriorityRandomTest();
        calculateHealthTest();
    }
}