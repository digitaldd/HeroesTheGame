import java.util.ArrayList;
import java.util.List;

public class Game {


    static boolean attackerSuper = true;
    static int defendingSuper;
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

    /**
     * leave the hope to understand it
     */
    static void playGame() throws Exception {
        List<Integer> listCheck = new ArrayList<>();
        DBase.conn(); // establishing connection with database
        do {
            selectSide(RandomNumber.getRandomSide()); // select attacker and defending side
            boolean gameStart = getCountStringsInTable(attackerTable) > 0 && getCountStringsInTable(defendingTable) > 0;
            listCheck.clear();
            if (gameStart && !calculateWinner()) {
                List<Integer> list = CheckRules.checkPriorityRandom();
                System.out.println(list.toString());
                moviesCount = getCountStringsInTable(attackerTable);
               // for (int i = moviesCount - 1; i > 0; i--) {
                for (int i = 0; i < list.size(); i++) {
                    attackerId = CheckRules.checkStatusSuper();
                    if (attackerId == 0 && !listCheck.contains(attackerId)) {
                        attackerId = list.get(i);
                    } else {
                        listCheck.add(attackerId);

                        //System.out.println(list.indexOf(i));
                        list.remove(i);
                        i--;
                    }
                    defendingId = selectId();//select defending character
                    try {
                        collectAll(); /* load all information about characters from database*/
                    } catch (NumberFormatException e) {
                        break;
                    }
                    if (attackerPriority == 1) {
                        attackerSkillEffect = setSuperDamage();
                        System.out.println(attackerSkillEffect);
                    }
                    //checkCharacter();
                    saveResult("Health", CheckRules.calculateHealth()); //save in database
                    CheckRules.removeStatusSuper();
                    CheckRules.checkHealth(); //if health <=0 then delete from table
                    calculateWinner(); // check quantity rows in defendingTable
                    //moviesCount--;
                }
            } else if (calculateWinner()) {
                System.out.println("Game over");
                DBase.closeDB();
                break;
            }
        }
        while (!calculateWinner());
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

    static int selectId() throws Exception {
        int id;
        try {
            id = Integer.parseInt(DBase.readDBCommand("SELECT ID FROM " + defendingTable + " ORDER BY RANDOM();"));
        } catch (java.lang.NumberFormatException e) {
            id = 0;
        }
        return id;
    }

    /**
     * this method collect all information about known id in known table and passes their values in class vars
     */
    private static void collectAll() throws Exception {
        attackerRace = getFieldValue("Race", attackerTable, attackerId);
        defendingRace = getFieldValue("Race", defendingTable, defendingId);
        attackerCharacter = getFieldValue("Character", attackerTable, attackerId);
        defendingCharacter = getFieldValue("Character", defendingTable, defendingId);
        attackerPriority = Integer.parseInt(getFieldValue("Priority", attackerTable, attackerId));
        //defendingPriority = Integer.parseInt(getFieldValue("Priority", defendingTable, defendingId));
        attackerHealth = Integer.parseInt(getFieldValue("Health", attackerTable, attackerId));
        defendingHealth = Integer.parseInt(getFieldValue("Health", defendingTable, defendingId));
        attackerSkillName = getFieldValue(selectSkill(), attackerTable, attackerId);
        defendingSkillName = getFieldValue(selectSkill(), defendingTable, defendingId);
        attackerSkillEffect = Integer
                .parseInt(getFieldValue(searchEffectBySkill(attackerSkillName), attackerTable, attackerId));
        defendingSkillEffect = Integer
                .parseInt(getFieldValue(searchEffectBySkill(defendingSkillName), defendingTable, defendingId));
        attackerCount = Integer.parseInt(getFieldValue("Count", attackerTable, attackerId));
        defendingCount = Integer.parseInt(getFieldValue("Count", defendingTable, defendingId));
        AddLog.writeInFile("Selected attacker character: " + attackerRace + " " + attackerCharacter + " Count: "
                + attackerCount + " Health: " + attackerHealth + " Uses the skill: " + attackerSkillName + " "
                + attackerSkillEffect);
        AddLog.writeInFile("Selected defending character: " + defendingRace + " " + defendingCharacter + " Count: "
                + defendingCount + " Health: " + defendingHealth);
    }

    /**
     * this method create query to database (uses DBase.readDBField) for read value which field
     *
     * @param fieldName what field need readed
     * @param tableName what table need readed
     * @param id what row contains value
     * @return field value in String format
     */
    static String getFieldValue(String fieldName, String tableName, int id) throws Exception {
        return DBase.readDBField(fieldName, tableName, id);
    }

    /**
     * this method select skill for attacker character (character have two skills)
     *
     * @return select random skillName first or second
     */
    static String selectSkill() {
        return (RandomNumber.getSkillNumber() == 1) ? "FirstSkillName" : "SecondSkillName";
    }

    /**
     * this method select effectSkill in database, appropriate the chosen skill in method selectSkill()
     *
     * @param skill - return selectSkill()
     * @return name field in database
     */
    static String searchEffectBySkill(String skill) {
        return (skill.toLowerCase().contains("primary")) ? "FirstSkillDamage" : "SecondSkillDamage";
    }

    /**
     * this method calculate quantity rows in table
     *
     * @param tableName what table need calculate
     * @return int number rows in table
     */
    static int getCountStringsInTable(String tableName) throws Exception {
        String dbCommandStrings = "SELECT COUNT(*) FROM " + tableName + ";";
        String countStrings = DBase.readDBCommand(dbCommandStrings);
        return Integer.parseInt(countStrings);
    }

    /**
     * save something in database (uses DBase.updateDB)
     *
     * @param fieldName what field need correct
     * @param value what value need save
     */
    static void saveResult(String fieldName, int value) throws Exception {
        DBase.updateDB(defendingTable, fieldName, value, defendingId);
        AddLog.writeInFile("Saved done in " + fieldName + " " + value);
    }

    /**
     * calculate damage 150% for characters with priority 1 (status Super)
     *
     * @return 150% without dot
     */
    static int setSuperDamage() {
        int damage = attackerSkillEffect;
        return attackerSkillEffect / 2 + damage;
    }

    /**
     * This method check quantity rows in defending table if rows = 0, then add to log
     *
     * @return true or false
     */
    static boolean calculateWinner() throws Exception {
        boolean winner = getCountStringsInTable(defendingTable) == 0;
        if (winner) {
            AddLog.writeInFile(attackerTable + " Winner!");
        }
        return winner;
    }
}
