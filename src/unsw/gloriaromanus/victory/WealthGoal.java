package unsw.gloriaromanus.victory;

import unsw.gloriaromanus.faction.Faction;

public class WealthGoal extends Leaf implements Observer {
    private double wealth;

    /**
     * Constructs a wealth goal for our game
     * 
     * @param wealth the total wealth of the faction
     */
    public WealthGoal(double wealth) {
        this.wealth = wealth;
    }

    /**
     * Evaluates whether the wealth goal has been reached
     */
    public boolean evaluate() {
        return (wealth >= 400000);
    }

    /**
     * Updates wealth goal on any changes in the faction's wealth value
     * 
     * @param obj faction subject
     */
    @Override
    public void update(Subject obj) {
        if (obj instanceof Faction) {
            this.wealth = ((Faction) obj).getWealth();
        }
    }
}
