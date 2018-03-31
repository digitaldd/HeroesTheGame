public class Game {

    static int moviesCount; // for attacker side

    static String attackerTable;
    static int attackerId;
    static String attackerRace;
    static String attackerCharacter;
    static int attackerPriority;
    static int attackerHealth;
    static String attackerSkillName;
    static int attackerSkillEffect;
    static int attackerCount;

    static String defendingTable;
    static int defendingId;
    static String defendingRace;
    static String defendingCharacter;
    static int defendingPriority;
    static int defendingHealth;
    static String defendingSkillName;
    static int defendingSkillEffect;
    static int defendingCount;

    public static void main(String[] args) throws Exception {
        playGame();
    }

    static void playGame() throws Exception {
        DBase.conn(); // establishing connection with database

        do {
            selectSide(RandomNumber.getRandomSide()); // select attacker and defending side
            moviesCount = getCountStringsInTable(attackerTable);
            for (int i = moviesCount; i > 0; i--) {
                selectId(); //select attacker and defending characters
                collectAll(); // load all information about characters from database
                saveResult("Health", calculateHealth()); //save in database
                checkHealth(); //if health <=0 then delete from table
                calculateWinner();
                moviesCount--;
            }

        }
        while (getCountStringsInTable(attackerTable) > 0 || getCountStringsInTable(defendingTable) > 0);

        DBase.closeDB(); //close connections
    }

    // select side basis of a random number (RandomSide method)
    static void selectSide(int side) {
        AddLog.writeInFile("");
        if (side == 1) {
            AddLog.writeInFile("Get ready: LightTeam attacks");
            attackerTable = "LightTeam";
            defendingTable = "DarkTeam";
        } else if (side == 2) {
            AddLog.writeInFile("Get ready: DarkTeam attacks");
            attackerTable = "DarkTeam";
            defendingTable = "LightTeam";
        } else {
            System.out.println("Error");
        }
    }

    static void selectId() throws Exception {
        defendingId = Integer.parseInt(DBase.readDBCommand("SELECT ID FROM " + defendingTable + " ORDER BY RANDOM();"));
        attackerId = checkPriority(attackerTable);
    }

    static void collectAll() throws Exception {
        attackerRace = getFieldValue("Race", attackerTable, attackerId);
        defendingRace = getFieldValue("Race", defendingTable, defendingId);
        attackerCharacter = getFieldValue("Character", attackerTable, attackerId);
        defendingCharacter = getFieldValue("Character", defendingTable, defendingId);
        //attackerPriority = Integer.parseInt(getFieldValue("Priority", attackerTable, attackerId));
        //defendingPriority = Integer.parseInt(getFieldValue("Priority", defendingTable, defendingId));
        attackerHealth = Integer.parseInt(getFieldValue("Health", attackerTable, attackerId));
        defendingHealth = Integer.parseInt(getFieldValue("Health", defendingTable, defendingId));
        attackerSkillName = getFieldValue(selectSkill(), attackerTable, attackerId);
        defendingSkillName = getFieldValue(selectSkill(), defendingTable, defendingId);
        attackerSkillEffect = Integer.parseInt(getFieldValue(searchEffectBySkill(attackerSkillName), attackerTable, attackerId));
        defendingSkillEffect = Integer.parseInt(getFieldValue(searchEffectBySkill(defendingSkillName), defendingTable, defendingId));
        attackerCount = Integer.parseInt(getFieldValue("Count", attackerTable, attackerId));
        defendingCount = Integer.parseInt(getFieldValue("Count", defendingTable, defendingId));
        AddLog.writeInFile("Selected attacker character: " + attackerRace + " " + attackerCharacter + " Count: "
                + attackerCount + " Health: " + attackerHealth + " Uses the skill: " + attackerSkillName + " "
                + attackerSkillEffect);
        AddLog.writeInFile("Selected defending character: " + defendingRace + " " + defendingCharacter + " Count: "
                + defendingCount + " Health: " + defendingHealth);
    }

    static String getFieldValue(String fieldName, String tableName, int id) throws Exception {
        return DBase.readDBField(fieldName, tableName, id);
    }

    static int calculateHealth() {
        return defendingHealth - attackerSkillEffect;
    }

    static String selectSkill() {
        return (RandomNumber.getSkillNumber() == 1) ? "FirstSkillName" : "SecondSkillName";
    }

    static String searchEffectBySkill(String skill) {
        return (skill.toLowerCase().contains("primary")) ? "FirstSkillDamage" : "SecondSkillDamage";
    }

    static int getCountStringsInTable(String tableName) throws Exception {
        String dbCommandStrings = "SELECT COUNT(*) FROM " + tableName + ";";
        String countStrings = DBase.readDBCommand(dbCommandStrings);
        return Integer.parseInt(countStrings);
    }

    static void saveResult(String fieldName, int value) throws Exception {
        DBase.updateDB(defendingTable, fieldName, value, defendingId);
        AddLog.writeInFile("Saved done in " + fieldName + " " + value);
    }

    static int checkPriority(String tableName) throws Exception {
        int priority = 0;
        int id;
       // try {
         //   priority = Integer.parseInt(DBase.readDBCommand("SELECT ID FROM " + tableName + " WHERE Priority=1;"));
        //}
      //  catch (java.sql.SQLException e) {priority=0;}
        if (priority != 1) {
            id = Integer.parseInt(DBase.readDBCommand("SELECT ID FROM " + tableName + " ORDER BY RANDOM();"));
            AddLog.writeInFile("Character with status Super in " + tableName + " don't found. Randomize launch = " + id);
        } else {
            id = priority;
        }
        return id;
    }

    static void checkHealth() throws Exception {
        int health = Integer.parseInt(DBase.readDBCommand("SELECT Health FROM "+defendingTable+" WHERE ID= "+defendingId));
        if (health <= 0) {
            DBase.deleteStringInDB(defendingTable);
        }
    }

    static void calculateWinner() throws Exception {
        if (getCountStringsInTable(defendingTable) <= 0) {
            AddLog.writeInFile(attackerTable + " Winner!");
        }
    }
}
