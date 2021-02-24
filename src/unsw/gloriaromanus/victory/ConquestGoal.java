package unsw.gloriaromanus.victory;

import unsw.gloriaromanus.faction.Faction;

public class ConquestGoal extends Leaf implements Observer {
    private boolean allConquered;

    /**
     * Constructs a Conquest Goal for the game
     * 
     * @param allConquered a boolean that is true if all of provinces have been
     *                     conquered by that faction
     */
    public ConquestGoal(boolean allConquered) {
        this.allConquered = allConquered;
    }

    /**
     * Evaluates whether the conquest goal has been reached
     */
    public boolean evaluate() {
        return allConquered;
    }

    /**
     * Updates conquest goal on any changes in the faction's allConquered boolean
     * value
     * 
     * @param obj faction subject
     */
    @Override
    public void update(Subject obj) {
        if (obj instanceof Faction) {
            this.allConquered = ((Faction) obj).isAllConquered();
            System.out.println(evaluate());
        }
    }
}
