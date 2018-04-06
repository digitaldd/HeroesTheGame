//import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class CheckRules {

    static String reducerTableName;
    private static List<Integer> reducerId = new ArrayList<>();

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
                Game.moviesCount++;
            }
        } catch (java.sql.SQLException | NumberFormatException e) {
            id = 0;
        }
        return id;
    }

    /**
     * @return list id's by random order from attackerTable
     */
    static ArrayList<Integer> checkPriorityRandom() throws Exception {
        return DBase.readDBCommand2("SELECT ID FROM " + Game.attackerTable + " ORDER BY RANDOM();");
    }

    /**
     * This method check health and count defendingCharacter. If health <=0, but count > 1, then count may present how
     * ?00 (where ? = count) else if count = 1 and health <=0 then delete character from database
     */
    static void checkHealth() throws Exception {
        int health = Integer
                .parseInt(DBase.readDBCommand(
                        "SELECT Health FROM " + Game.defendingTable + " WHERE ID= " + Game.defendingId));
        if (health <= 0 && Game.defendingCount > 1) {
            DBase.updateDB(Game.defendingTable, "Count", Game.defendingCount - 1, Game.defendingId);
            DBase.updateDB(Game.defendingTable, "Health", StartGameHeroes.STARTING_HEALTH
                    + health, Game.defendingId);
        } else if (health <= 0 && Game.defendingCount == 1) {
            DBase.deleteStringInDB(Game.defendingTable);
        }
    }

    /**
     * This method check by keywords attackerSkillName and execute actions in accordance with the description of the
     * skill
     *
     * @return residual from the difference defender health and attacker damage
     */
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

        } else if (Game.attackerSkillName.toLowerCase().contains("reduce")) {
            AddLog.writeInFile("Damage from character " + Game.defendingId + " " + Game.defendingTable
                    + " will be reducer by 50%");
            reducerTableName = Game.defendingTable;
            reducerId.add(Game.defendingId);
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

    /**
     * reduce damage character by 50% uses class var reducerTableName
     *
     * @param id selected character
     */
    static void reduceDamage(int id) {
        if (Game.attackerTable.equals(reducerTableName) && reducerId.contains(id)) {
            Game.attackerSkillEffect /= 2;
            reducerId.remove(reducerId.indexOf(id));
            AddLog.writeInFile("- Damage reducer by 50%");
        }
    }
}