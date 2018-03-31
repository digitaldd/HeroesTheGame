public class RandomNumber {

    public static void main(String[] args) {
        getRandomLightTeam();
        getRandomDarkTeam();
        getSkillNumber();
    }

    //select LightTeam
    public static int getRandomLightTeam() {
        int lightTeam = 1 + (int) (Math.random() * StartGameHeroes.COUNT_LIGHT_TEAMS);
        AddLog.writeInFile("Selected Light Team:" + lightTeam);
        return lightTeam;
    }

    //select DakTeam
    public static int getRandomDarkTeam() {
        int darkTeam = 1 + (int) (Math.random() * StartGameHeroes.COUNT_DARK_TEAMS);
        AddLog.writeInFile("Selected Dark Team:" + darkTeam);
        return darkTeam;
    }

    //select Skill
    public static int getSkillNumber() {
        return 1 + (int) (Math.random() * 2); //First (1) or Second Skill (2)
    }

    //select Side light or dark
    public static int getRandomSide() {
        return 1 + (int) (Math.random() * StartGameHeroes.COUNT_PARTIES);
    }
}