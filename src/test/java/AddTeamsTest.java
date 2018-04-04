import org.junit.Assert;
import org.junit.Test;


public class AddTeamsTest {

    public static void callSelectedTeamTest() {
        Assert.assertEquals("CallSelectedTeamTest", true,
                AddTeams.callSelectedTeam("Elfs").toString().toLowerCase().contains("lightteam"));

        Assert.assertEquals("CallSelectedTeamTest", true,
                AddTeams.callSelectedTeam("Orks").toString().toLowerCase().contains("darkteam"));
    }

    @Test
    public void main() {
        callSelectedTeamTest();
    }
}
