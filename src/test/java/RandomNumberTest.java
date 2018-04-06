import org.junit.Assert;
import org.junit.Test;

/**
 * this class contains tests for random methods
 */
public class RandomNumberTest {

    /**
     * key var COUNT_LIGHT_TEAMS = 2
     */
    private static void getRandomLightTeamTest() {
        int team = RandomNumber.getRandomLightTeam();
        boolean result = team == 1 || team == 2;
        Assert.assertEquals("GetRandomLightTeamTest", true, result);
    }

    /**
     * key var COUNT_DARK_TEAMS = 2
     */
    private static void getRandomLightDarkTest() {
        int team = RandomNumber.getRandomDarkTeam();
        boolean result = team == 1 || team == 2;
        Assert.assertEquals("GetRandomDarkTeamTest", true, result);
    }

    /**
     * Math.random () 1-2
     */
    private static void getSkillNumberTest() {
        int skill = RandomNumber.getSkillNumber();
        boolean result = skill == 1 || skill == 2;
        Assert.assertEquals("GetSkillNumberTest", true, result);
    }

    /**
     * Math.random() 1 to key var COUNT_PARTIES = 2
     */
    private static void getRandomSideTest() {
        int side = RandomNumber.getSkillNumber();
        boolean result = side == 1 || side == 2;
        Assert.assertEquals("GetRandomSideTest", true, result);
    }

    @Test
    public void main() {
        getRandomLightTeamTest();
        getRandomLightDarkTest();
        getSkillNumberTest();
        getRandomSideTest();
    }
}