/**
 * This class contains methods for get random numbers
 */
public class RandomNumber {

    public static void main(String[] args) {
        getRandomLightTeam();
        getRandomDarkTeam();
        getSkillNumber();
    }

    /**
     * select random LightTeam
     *
     * @return team number
     */
    public static int getRandomLightTeam() {
        int lightTeam = 1 + (int) (Math.random() * StartGameHeroes.COUNT_LIGHT_TEAMS);
        AddLog.writeInFile("Selected Light Team:" + lightTeam);
        return lightTeam;
    }

    /**
     * select random DarkTeam
     *
     * @return team number
     */
    public static int getRandomDarkTeam() {
        int darkTeam = 1 + (int) (Math.random() * StartGameHeroes.COUNT_DARK_TEAMS);
        AddLog.writeInFile("Selected Dark Team:" + darkTeam);
        return darkTeam;
    }

    /**
     * @return First (1) or Second Skill (2)
     */
    public static int getSkillNumber() {
        return 1 + (int) (Math.random() * 2);
    }

    /**
     * @return select random number to determine the party
     */
    public static int getRandomSide() {
        return 1 + (int) (Math.random() * StartGameHeroes.COUNT_PARTIES);
    }
}