package unsw.gloriaromanus.victory;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class CampaignVictory {
    List<String> campaignGoals;

    public CampaignVictory() {
        // this.campaignGoals = createCampaignVictory();
    }

    public CampaignVictory(List<String> victory) {
        this.campaignGoals = victory;
    }

    /**
     * using the list of campaign victory conditions, returns true or false
     * depending on whether the player has met the conditions to win the game
     * 
     * @param victory         a list that contains the victory conditions for the
     *                        game
     * @param allConquered    a boolean to check whether the faction has conquered
     *                        all of the other factions
     * @param factionWealth   a double with the total wealth of the faction
     * @param factionTreasury a double with the treasury value of the faction
     * @return true if the player has won the game and false otherwise
     */
    public boolean checkCampaignVictory(List<String> victory, boolean allConquered, double factionWealth,
            double factionTreasury) {

        List<Component> goalList = new ArrayList<Component>();
        Component campaignVictory = null;
        Component goal = null;
        for (int i = 0; i < victory.size(); i = i + 2) {
            switch (victory.get(i)) {
                case "CONQUEST":
                    goal = new ConquestGoal(allConquered);
                    goalList.add(goal);
                    break;
                case "WEALTH":
                    goal = new WealthGoal(factionWealth);
                    goalList.add(goal);
                    break;
                case "TREASURY":
                    goal = new TreasuryGoal(factionTreasury);
                    goalList.add(goal);
                    break;
            }
        }
        if (goalList.size() == 1) {
            campaignVictory = goalList.get(0);
        } else if (goalList.size() == 2) {
            switch (victory.get(1)) {
                case "AND":
                    campaignVictory = new LogicalAnd(goalList.get(0), goalList.get(1));
                    break;
                case "OR":
                    campaignVictory = new LogicalOr(goalList.get(0), goalList.get(1));
                    break;
            }

        } else if (goalList.size() == 3) {
            Component logic1 = null;
            switch (victory.get(3)) {
                case "AND":
                    logic1 = new LogicalAnd(goalList.get(1), goalList.get(2));
                    break;
                case "OR":
                    logic1 = new LogicalOr(goalList.get(1), goalList.get(2));
                    break;
            }
            switch (victory.get(1)) {
                case "AND":
                    campaignVictory = new LogicalAnd(goalList.get(0), logic1);
                    break;
                case "OR":
                    campaignVictory = new LogicalOr(goalList.get(0), logic1);
                    break;
            }

        }
        return campaignVictory.evaluate();
    }

    /**
     * At the start of the game, this function is called to create the campaign
     * victory conditions
     * 
     * @return a list of strings that contains the campaign victory conditions
     */
    public List<String> createCampaignVictory() {
        Random random = new Random();
        List<String> list = new ArrayList<String>(Arrays.asList("AND", "OR"));
        List<String> list2 = new ArrayList<String>(Arrays.asList("CONQUEST", "WEALTH", "TREASURY"));

        List<String> goalList = new ArrayList<String>();
        int goals = random.nextInt(3);
        int logic = random.nextInt(2);
        int n = random.nextInt(2);

        for (int i = 0; i <= n; i++) {
            goals = random.nextInt(3);
            while (goalList.contains(list2.get(goals))) {
                goals = random.nextInt(3);
            }
            goalList.add(list2.get(goals));
            logic = random.nextInt(2);
            goalList.add(list.get(logic));
        }
        goals = random.nextInt(3);
        while (goalList.contains(list2.get(goals))) {
            goals = random.nextInt(3);
        }
        goalList.add(list2.get(goals));
        return goalList;
    }

    /**
     * getter for the campaign goals
     * 
     * @return a list of the campaign goals
     */
    public List<String> getCampaignGoals() {
        return campaignGoals;
    }

}
