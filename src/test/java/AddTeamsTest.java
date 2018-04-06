import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class AddTeamsTest {

    private static void callSelectedTeamTest() {
        Assert.assertEquals("CallSelectedTeamTest", true,
                AddTeams.callSelectedTeam("Elfs").toString().toLowerCase().contains("lightteam"));

        Assert.assertEquals("CallSelectedTeamTest", true,
                AddTeams.callSelectedTeam("Orks").toString().toLowerCase().contains("darkteam"));
    }

    private static void selectRandomTeamTest() {
        List<String> list = new ArrayList<>();
        list.add("Elfs");
        list.add("Peoples");
        list.add("Orks");
        list.add("Undead");
        boolean result = list.contains(AddTeams.selectRandomTeam("light"));
        boolean result2 = list.contains(AddTeams.selectRandomTeam("dark"));
        Assert.assertEquals("SelectRandomTeamTest", true, result);
        Assert.assertEquals("SelectRandomTeamTest", true, result2);
    }

    @Test
    public void main() {
        callSelectedTeamTest();
        selectRandomTeamTest();
    }
}