public class StartGameHeroes {

    static final int COUNT_PARTIES = 2;
    static final int COUNT_LIGHT_TEAMS = 2;
    static final int COUNT_DARK_TEAMS = 2;
    static final int COUNT_MAGES_ON_TEAM = 1;
    static final int COUNT_ARCHERS_ON_TEAM = 3;
    static final int COUNT_WARRIORS_ON_TEAM = 4;

    public static void main(String[] args) throws Exception {
        AddTeams.addTableLight();
        AddTeams.addTableDark();
        Game.playGame();

        DBase.closeDB();
    }
}
