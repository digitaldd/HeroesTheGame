import org.junit.Assert;
import org.junit.Test;

/**
 * some tests in this class needs new database, you need remove StartGameHeroes.db before start their
 */
public class GameTest {

    /**
     * if set 1 then light and dark, else if 2, then dark and light
     */
    private static void selectSideTest() {
        Game.selectSide(1);
        boolean result = Game.attackerTable.equals("LightTeam") && Game.defendingTable.equals("DarkTeam");
        Assert.assertEquals("SelectSideTest", true, result);
        Game.selectSide(2);
        boolean result1 = Game.attackerTable.equals("DarkTeam") && Game.defendingTable.equals("LightTeam");
        Assert.assertEquals("SelectSideTest", true, result1);
    }

    /**
     * for this test need crate and fill table LightTeam in database testing random select id
     */
    private static void selectIdTest() throws Exception {
        AddTeams.addTableLight();
        DBase.conn();
        Game.defendingTable = "LightTeam";
        int id = Game.selectId();
        boolean result = id == 1 || id == 2 || id == 3;
        Assert.assertEquals("SelectIdTest", true, result);
        DBase.closeDB();
    }

    /**
     * if random number == 1, then FirstSkillName, else SecondSkillName
     */
    private static void selectSkillTest() {
        String skill = Game.selectSkill();
        boolean result = skill.equals("FirstSkillName") || skill.equals("SecondSkillName");
        Assert.assertEquals("SelectSkillTest", true, result);
    }

    /**
     * if primary, then FirstSkillDamage
     */
    private static void searchEffectBySkillTest() {
        String skill = Game.searchEffectBySkill("primary");
        boolean result = skill.equals("FirstSkillDamage");
        Assert.assertEquals("SearchEffectBySkillTest", true, result);
    }

    /**
     * for this test need create and fill database
     */
    private static void getCountStringsInTableTest() throws Exception {
        DBase.conn();
        int rows = Game.getCountStringsInTable("LightTeam");
        boolean result = rows == 3;
        Assert.assertEquals("GetCountStringsInTable", true, result);
        DBase.closeDB();
    }

    /**
     * damage must be 150%
     */
    private static void setSuperDamageTest() {
        Game.attackerSkillEffect = 100;
        int damage = Game.setSuperDamage();
        Assert.assertEquals("SetSuperDamageTest", 150, damage);
    }

    /**
     * if table contains rows, then false, else true
     */
    private static void calculateWinnerTest() throws Exception {
        DBase.conn();
        Assert.assertEquals("CalculateWinnerTest", false, Game.calculateWinner());
        DBase.closeDB();
    }

    @Test
    public void main() throws Exception {
        selectSideTest();
        selectIdTest();
        selectSkillTest();
        searchEffectBySkillTest();
        getCountStringsInTableTest();
        setSuperDamageTest();
        calculateWinnerTest();
    }
}