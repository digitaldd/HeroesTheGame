import java.util.ArrayList;
import java.util.List;

public class AddTeams {

    public static void addTableLight() throws Exception {
        DBase.conn();
        AddLog.writeInFile("Begin create table LightTeam");
        String tableLightTeam = "CREATE TABLE if not exists 'LightTeam' ('id' INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "'Race' text, 'Priority' int, 'Character' text, 'FirstSkillName' text, 'FirstSkillDamage' int," +
                " 'SecondSkillName' text, 'SecondSkillDamage' int, 'Health' int, 'Count' int);";
        DBase.createDB(tableLightTeam);
        List<String> lightCharacters = callSelectedTeam(selectRandomTeam("light"));
        DBase.writeDB(lightCharacters);
        DBase.readDB("LightTeam");
        DBase.closeDB();
    }

    public static void addTableDark() throws Exception {
        DBase.conn();
        AddLog.writeInFile("Begin create table DarkTeam");
        String tableDarkTeam = "CREATE TABLE if not exists 'DarkTeam' ('id' INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "'Race' text, 'Priority' int, 'Character' text, 'FirstSkillName' text, 'FirstSkillDamage' int," +
                " 'SecondSkillName' text, 'SecondSkillDamage' int, 'Health' int, 'Count' int);";
        DBase.createDB(tableDarkTeam);
        List<String> darkCharacters = callSelectedTeam(selectRandomTeam("dark"));
        DBase.writeDB(darkCharacters);
        DBase.readDB("DarkTeam");
        DBase.closeDB();
    }


    /*
     *Create Team Elfs
     *return command for fill table in sqlite
     */
    public static List<String> createLightTeamElfs() {
        String dbCommand = "INSERT INTO 'LightTeam' ('Race', 'Priority','Character','FirstSkillName', " +
                "'FirstSkillDamage','SecondSkillName','SecondSkillDamage','Health','Count')";
        String charMagicianElf = "VALUES ('Elf', '0', 'Magician', 'Primary: Add status Super on character','0'," +
                "'Magic damage','10', '100', '" + Integer.toString(StartGameHeroes.COUNT_MAGES_ON_TEAM) + "')";
        String charArcherElf = "VALUES ('Elf', '0', 'Archer', 'Primary: Archery','7', 'Attack','3'," +
                "'100', '" + Integer.toString(StartGameHeroes.COUNT_ARCHERS_ON_TEAM) + "')";
        String charWarriorElf = "VALUES ('Elf', '0', 'Warrior', 'Primary: Attack with a sword', '15'," +
                "'Attack with a sword', '15', '100','" + Integer.toString(StartGameHeroes.COUNT_WARRIORS_ON_TEAM) + "')";
        List<String> elfsTeamChar = new ArrayList<>();
        elfsTeamChar.add(dbCommand + charMagicianElf);
        elfsTeamChar.add(dbCommand + charArcherElf);
        elfsTeamChar.add(dbCommand + charWarriorElf);
        return elfsTeamChar;
    }

    public static List<String> createLightTeamPeoples() {
        String dbCommand = "INSERT INTO 'LightTeam' ('Race', 'Priority','Character','FirstSkillName', " +
                "'FirstSkillDamage','SecondSkillName','SecondSkillDamage','Health','Count')";
        String charMagicianPeople = "VALUES ('Peoples', '0', 'Magician', 'Primary: Add status Super on character','0'," +
                "'Magic damage', '4', '100','" + Integer.toString(StartGameHeroes.COUNT_MAGES_ON_TEAM) + "')";
        String charArcherPeople = "VALUES ('Peoples', '0', 'Crossbowman', 'Primary: Crossbow shoot','5'," +
                "'Attack','3', '100','" + Integer.toString(StartGameHeroes.COUNT_ARCHERS_ON_TEAM) + "')";
        String charWarriorPeople = "VALUES ('Peoples', '0', 'Warrior', 'Primary: Attack with a sword', '18'," +
                "'Attack with a sword', '18', '100','" + Integer.toString(StartGameHeroes.COUNT_WARRIORS_ON_TEAM) + "')";
        List<String> peoplesTeamChar = new ArrayList<>();
        peoplesTeamChar.add(dbCommand + charMagicianPeople);
        peoplesTeamChar.add(dbCommand + charArcherPeople);
        peoplesTeamChar.add(dbCommand + charWarriorPeople);
        return peoplesTeamChar;
    }

    public static List<String> createDarkTeamOrks() {
        String dbCommand = "INSERT INTO 'DarkTeam' ('Race', 'Priority','Character','FirstSkillName', " +
                "'FirstSkillDamage','SecondSkillName','SecondSkillDamage','Health','Count')";
        String charMagicianOrk = "VALUES ('Orks', '0', 'Shaman', 'Primary: Add status Super on character his team','0'," +
                "'Take off status Super on character another team', '0', '100','" + Integer
                .toString(StartGameHeroes.COUNT_MAGES_ON_TEAM) + "')";
        String charArcherOrk = "VALUES ('Orks', '0', 'Archer','Primary: Archery', '3','Attack with knife', '2', '100','"
                + Integer.toString(StartGameHeroes.COUNT_ARCHERS_ON_TEAM) + "')";
        String charWarriorOrk = "VALUES ('Orks', '0', 'Goblin', 'Primary: Attack with a truncheon', '20'," +
                "'Attack with a sword', '18', '100','" + Integer.toString(StartGameHeroes.COUNT_WARRIORS_ON_TEAM) + "')";
        List<String> orksTeamChar = new ArrayList<>();
        orksTeamChar.add(dbCommand + charMagicianOrk);
        orksTeamChar.add(dbCommand + charArcherOrk);
        orksTeamChar.add(dbCommand + charWarriorOrk);
        return orksTeamChar;
    }

    public static List<String> createDarkTeamUndead() {
        String dbCommand = "INSERT INTO 'DarkTeam' ('Race', 'Priority','Character','FirstSkillName', " +
                "'FirstSkillDamage','SecondSkillName','SecondSkillDamage','Health','Count')";
        String charMagicianUndead =
                "VALUES ('Undead', '0', 'Necromancer', 'Primary: Reduce damage of another team member by 50% (1 move)','0'," +
                        "'Attack', '5', '100','" + Integer.toString(StartGameHeroes.COUNT_MAGES_ON_TEAM) + "')";
        String charArcherUndead =
                "VALUES ('Undead', '0', 'Hunter', 'Primary: Archery', '4', 'Attack', '2'," +
                        "'100','" + Integer.toString(StartGameHeroes.COUNT_ARCHERS_ON_TEAM) + "')";
        String charWarriorUndead = "VALUES ('Undead', '0', 'Zombie', 'Primary: Attack with a spear', '18'," +
                "'Attack with a spear', '18', '100','" + Integer.toString(StartGameHeroes.COUNT_WARRIORS_ON_TEAM) + "')";
        List<String> undeadTeamChar = new ArrayList<>();
        undeadTeamChar.add(dbCommand + charMagicianUndead);
        undeadTeamChar.add(dbCommand + charArcherUndead);
        undeadTeamChar.add(dbCommand + charWarriorUndead);
        return undeadTeamChar;
    }

    public static String selectRandomTeam(String team) {
        int random = 0;
        String raceTeam = "";
        if (team.equals("light")) {
            random = RandomNumber.getRandomLightTeam();
            if (random == 1) {
                raceTeam = "Elfs";
            } else if (random == 2) {
                raceTeam = "Peoples";
            } else {
                System.out.println("Something went wrong, call developer");
            }
        } else if (team.equals("dark")) {
            random = RandomNumber.getRandomDarkTeam();
            if (random == 1) {
                raceTeam = "Orks";
            } else if (random == 2) {
                raceTeam = "Undead";
            } else {
                System.out.println("Something went wrong, call developer");
            }
        }
        AddLog.writeInFile(raceTeam);
        return raceTeam;
    }

    public static List<String> callSelectedTeam(String team) {
        List<String> calledTeam = new ArrayList<>();
        switch (team) {
            case "Elfs":
                calledTeam = createLightTeamElfs();
                break;
            case "Peoples":
                calledTeam = createLightTeamPeoples();
                break;
            case "Orks":
                calledTeam = createDarkTeamOrks();
                break;
            case "Undead":
                calledTeam = createDarkTeamUndead();
                break;
            default:
                System.out.println("Error");
        }
        return calledTeam;
    }
}