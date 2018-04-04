import java.util.List;

public class CheckRules {

    /**
     * this method checks priority in DB and select his ID, if priority 1 found, then addlog/ if don't found, then
     * calculate ID by random order
     *
     * @return ID
     */
    static int checkStatusSuper() throws Exception {
        int id;
        try {
            id = Integer.parseInt(DBase.readDBCommand("SELECT ID FROM " + Game.attackerTable + " WHERE Priority=1;"));
            if (id > 0) {
                AddLog.writeInFile("Character with status Super in " + Game.attackerTable + " found. His ID = " + id
                        + ", his attack = 150%");
                //DBase.updateDB(tableName, "Priority", 0, id);
                Game.moviesCount++;
            }
        } catch (java.sql.SQLException | NumberFormatException e) {
            id = 0;
        }
        return id;
    }

    static List checkPriorityRandom() throws Exception {
        List<Integer> list = DBase.readDBCommand2("SELECT ID FROM " + Game.attackerTable + " ORDER BY RANDOM();");

//        AddLog.writeInFile(
//                "Character with status Super in " + Game.attackerTable + " don't found. Randomize launch = " + 1);
        System.out.println(list.toString());
        return list;
    }

    /**
     * This method check health and count defendingCharacter. If health <=0, but count > 1, then count may present how
     * ?00 (where ? = count) else if count = 1 and health <=0 then delete character from database
     */
    static void checkHealth() throws Exception {
        int health = Integer
                .parseInt(DBase.readDBCommand("SELECT Health FROM " + Game.defendingTable + " WHERE ID= " + Game.defendingId));
        if (health <= 0 && Game.defendingCount > 1) {
            DBase.updateDB(Game.defendingTable, "Count", Game.defendingCount - 1, Game.defendingId);
            DBase.updateDB(Game.defendingTable, "Health", StartGameHeroes.STARTING_HEALTH + health, Game.defendingId);
        } else if (health <= 0 && Game.defendingCount == 1) {
            DBase.deleteStringInDB(Game.defendingTable);
        }
    }

    static int calculateHealth() throws Exception {
        int randomId;
        if (Game.attackerSkillName.toLowerCase().contains("add")) {
            randomId = Integer
                    .parseInt(DBase.readDBCommand("SELECT ID FROM " + Game.attackerTable + " ORDER BY RANDOM();"));
            DBase.updateDB(Game.attackerTable, "Priority", 1, randomId);
            AddLog.writeInFile("The effect is applied to the character, his ID = " + randomId);
        } else if (Game.attackerSkillName.toLowerCase().contains("off")) {
            int id;
            try {
                id = Integer
                        .parseInt(DBase.readDBCommand("SELECT ID FROM " + Game.defendingTable + " WHERE Priority=1;"));
                if (id > 0) {
                    DBase.updateDB(Game.defendingTable, "Priority", 0, id);
                    AddLog.writeInFile("Character in " + Game.defendingTable + " loses status Super. His ID = " + id
                            + ", his attack = 100%");
                }
            } catch (java.sql.SQLException | NumberFormatException e) {
                AddLog.writeInFile("Character with status Super in " + Game.defendingTable + " don't found.");
            }
        }
        return Game.defendingHealth - Game.attackerSkillEffect;
    }

    /**
     * set Priority 0 on attacker ID in database
     */
    static void removeStatusSuper() throws Exception {
        if (Game.attackerPriority == 1) {
            DBase.updateDB(Game.attackerTable, "Priority", 0, Game.attackerId);
        }
    }
}