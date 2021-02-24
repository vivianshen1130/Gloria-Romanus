package unsw.gloriaromanus.victory;

import unsw.gloriaromanus.faction.Faction;

public class TreasuryGoal extends Leaf implements Observer {
    private double treasury;

    /**
     * 
     * @param treasury the total treasury value of the faction
     */
    public TreasuryGoal(double treasury) {
        this.treasury = treasury;
    }

    /**
     * Evaluates whether the treasury goal has been reached
     */
    public boolean evaluate() {
        return (treasury >= 100000);
    }

    /**
     * Updates treasury goal on any changes in the faction's treasury value
     * 
     * @param obj faction subject
     */
    @Override
    public void update(Subject obj) {
        if (obj instanceof Faction) {
            this.treasury = ((Faction) obj).getTreasury();
        }
    }
}
