package unsw.gloriaromanus.battleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.cavalry.Elephant;
import unsw.gloriaromanus.unit.cavalry.HorseArcher;
import unsw.gloriaromanus.unit.cavalry.MeleeCavalry;
import unsw.gloriaromanus.unit.infantry.JavelinSkirmisher;
import unsw.gloriaromanus.province.Province;
import unsw.gloriaromanus.faction.Faction;

public class BattleResolver {
    private Province invadingProvince;
    private int numInvading;
    private Province defendingProvince;
    private int numDefending;
    private List<Unit> invadingArmy = new ArrayList<Unit>();
    private List<Unit> defendingArmy = new ArrayList<Unit>();
    private Random random;

    /**
     * constructor for the battle resolver
     * 
     * @param invadingProvince  the invading province
     * @param numInvading       the number of units the invading province will
     *                          utilise
     * @param defendingProvince the defending province
     * @param numDefending      the number of units the defending province will
     *                          utilise
     */
    public BattleResolver(Province invadingProvince, int numInvading, Province defendingProvince, int numDefending,
            Random random) {
        this.invadingProvince = invadingProvince;
        this.numInvading = numInvading;
        this.defendingProvince = defendingProvince;
        this.numDefending = numDefending;
        this.random = random;

        for (int i = 0; i < numInvading; i++) {
            if (invadingProvince.getAvailableUnits().size() > 0) {
                int j = random.nextInt(invadingProvince.getAvailableUnits().size());
                Unit unit = invadingProvince.getAvailableUnits().get(j);
                invadingArmy.add(unit);
                unit.setAvailable(false);
                System.out.println("available set to false: "+unit);
                System.out.println("available units: "+invadingProvince.getAvailableUnits());

            }
            
        }

        for (int i = 0; i < numDefending && i < defendingProvince.getUnits().size(); i++) {
            Unit unit = defendingProvince.getUnits().get(i);
            if (unit.isAvailable()) {
                defendingArmy.add(unit);
            }
        }
    }

    /**
     * Apply corresponding special ability for abilities that only affect the single
     * unit
     * 
     * @param unit unit to apply special ability to
     * @return copy of the unit with the applied special ability
     */
    public List<Unit> applySpecialAbilities(Unit invadingUnit, Unit defendingUnit, boolean ranged) {
        List<Unit> units = new ArrayList<Unit>();
        Unit copyInvading = invadingUnit.clone(invadingUnit);
        units.add(copyInvading);
        Unit copyDefending = defendingUnit.clone(defendingUnit);
        units.add(copyDefending);
        
        // Apply "Legionary Eagle" special abilities 
        if (copyDefending.getFaction() != null && copyInvading.getFaction() != null){
            if (copyDefending.getFaction().getName().equals("Rome")){
                copyDefending.setMorale(copyDefending.getMorale() + 1);
            }
            if (copyInvading.getFaction().getName().equals("Rome")){
                copyInvading.setMorale(copyInvading.getMorale() + 1);
            }
        }
        // Apply "Heroic charge" special abilities
        if (copyInvading instanceof MeleeCavalry && invadingArmy.size() < (defendingArmy.size() / 2)) {
            copyInvading.setMorale(copyInvading.getMorale() * 1.5);
            copyInvading.setChargeStat(copyInvading.getChargeStat()*2);
        }
        if (copyDefending instanceof MeleeCavalry && defendingArmy.size() < (invadingArmy.size() / 2)) {
            copyDefending.setMorale(copyDefending.getMorale() * 1.5);
            copyDefending.setChargeStat(copyDefending.getChargeStat()*2);
        }
        // Apply "Skirmisher anti-armour" special abilities
        if (copyInvading instanceof JavelinSkirmisher && ranged) {
            copyDefending.setArmour(copyInvading.getArmour() * 0.5);

        }
        if (copyDefending instanceof JavelinSkirmisher && ranged) {
            copyInvading.setArmour(copyInvading.getArmour() * 0.5);

        }
        // Apply "Cantabrian circle" special abilities
        if (copyInvading instanceof HorseArcher && copyDefending.isRanged()) {
            copyDefending.setRangedAttackDamage(copyDefending.getRangedAttackDamage() * 0.5);

        }
        else if (copyDefending instanceof HorseArcher && copyInvading.isRanged()) {
            copyInvading.setRangedAttackDamage(copyInvading.getRangedAttackDamage() * 0.5);

        }
        // Apply "Druidic Ferver" special abilities
        int diffDruids = invadingProvince.getDruitUnits() - defendingProvince.getDruitUnits();
        if (diffDruids > 5) {
            diffDruids = 5;
        }
        if (diffDruids < -5) {
            diffDruids = -5;
        }
        if (diffDruids > 0){
            copyInvading.setMorale((1 + 0.1*diffDruids)*copyInvading.getMorale());
            copyDefending.setMorale((1 - 0.05*diffDruids)*copyDefending.getMorale());

        }
        if (diffDruids < 0){
            copyDefending.setMorale((1 - 0.1*diffDruids)*copyDefending.getMorale());
            copyInvading.setMorale((1 + 0.05*diffDruids)*copyInvading.getMorale());
        }
        return units;
    }

    /**
     * Determine the type of engagement
     * 
     * @param unit1 the first unit involved in the engagement
     * @param unit2 the second unit involved in the engagement
     * @return true for ranged engagement, false for melee engagement
     */
    public boolean engagementType(Unit unit1, Unit unit2) {
        if (unit1.isRanged() && unit2.isRanged()) {
            return true;
        } else if (!unit1.isRanged() && !unit2.isRanged()) {
            return false;
        } else {
            // 10% x (speed of melee unit - speed of missile unit)
            double chance = 50;
            Unit ranged, melee;
            if (unit1.isRanged()) {
                ranged = unit1;
                melee = unit2;
            } else {
                ranged = unit2;
                melee = unit1;
            }
            chance = chance + 10 * (melee.getSpeed() - ranged.getSpeed());
            if (chance < 5) {
                chance = 5;
            }
            if (chance > 95) {
                chance = 95;
            }
            int i = random.nextInt(100);
            if (i < chance) {
                return false;
            }
            return true;
        }
    }

    /**
     * manage the battle
     * 
     * @return true if the invading army successfully conquers the province, false
     *         otherwise
     */
    public boolean manageBattle() {
        int numEngagements = 0;
        if (invadingArmy.size() == 0) {
            return false;
        }
        if (defendingArmy.size() == 0) {
            System.out.println("got here!!!!");
            updateProvinces(); // provinceConquered
            return true;
        }
        while (numEngagements < 200) {
            numEngagements = manageSkirmish(invadingArmy, defendingArmy, numEngagements, random);
            if (defendingArmy.size() == 0) {
                updateProvinces(); // provinceConquered
                return true;
            }
            if (invadingArmy.size() == 0) {
                return false;
            }
        }
        return false;
    }

    /**
     * updates the units in the province when an army invades another army
     */
    public void updateProvinces() {
        // System.out.println("inv faction:" +invadingProvince.getFaction());
        // System.out.println("def faction:" +defendingProvince.getFaction());
        Faction f1 = invadingProvince.getFaction();
        Faction f2 = defendingProvince.getFaction();
        // f1.addProvince(province);
        // defendingProvince.setFaction(invadingProvince.getFaction());
        f1.addProvince(defendingProvince);
        f2.removeProvince(defendingProvince);
        System.out.println("def faction:" +defendingProvince.getFaction().getName());
        
        List<Unit> newUnits = new ArrayList<Unit>();
        defendingProvince.setUnits(newUnits);
        // System.out.println(invadingProvince.getAvailableUnits());
        for (Unit unit : invadingArmy) {
            unit.setProvince(defendingProvince);
            defendingProvince.addUnit(unit);
            invadingProvince.removeUnit(unit);
        }
       
        System.out.println("inv faction:" +f1);
        System.out.println("def faction:" +f2);
    }

    /**
     * Manage each skirmish
     * 
     * @param invadingArmy   the invading army
     * @param defendingArmy  the defending army
     * @param numEngagements the number of engagements already in the battle
     * @param random         the random object
     * @return the number of engagements after this skirmish
     */
    public int manageSkirmish(List<Unit> invadingArmy, List<Unit> defendingArmy, int numEngagements, Random random) {
        Unit unit1, unit2, copyUnit1, copyUnit2;
        int i1 = random.nextInt(invadingArmy.size());
        int i2 = random.nextInt(defendingArmy.size());
        unit1 = invadingArmy.get(i1);
        unit2 = defendingArmy.get(i2);

        boolean skirmishOver = false;
        while (!skirmishOver && numEngagements < 200) {
            numEngagements++;
            boolean ranged = engagementType(unit1, unit2);
            List<Unit> copies = applySpecialAbilities(unit1, unit2, ranged);
            copyUnit1 = copies.get(0);
            copyUnit2 = copies.get(1);

            if (unit1 instanceof Elephant || unit2 instanceof Elephant) {
                int num = random.nextInt(100);
                if (num < 10) {
                    if (unit1 instanceof Elephant) {
                        int i3 = random.nextInt(invadingArmy.size());
                        unit2 = invadingArmy.get(i3);
                        copies = applySpecialAbilities(unit1, unit2, ranged);
                        copyUnit1 = copies.get(0);
                        copyUnit2 = copies.get(1);
                    } 
                    else {
                        int i3 = random.nextInt(defendingArmy.size());
                        unit1 = defendingArmy.get(i3);
                        copies = applySpecialAbilities(unit1, unit2, ranged);
                        copyUnit1 = copies.get(0);
                        copyUnit2 = copies.get(1);
                    }
                }
            }
    
            Engagement engagement;
            if (ranged) {
                engagement = new RangedEngagement(copyUnit1, copyUnit2, random);
            } else {
                engagement = new MeleeEngagement(copyUnit1, copyUnit2, random);
            }

            // update the number of troops after the battle
            int troops1a = copyUnit1.getNumTroops();
            unit1.setNumTroops(troops1a - engagement.getCasualty1());
            copyUnit1.setNumTroops(troops1a - engagement.getCasualty1());
            int troops2a = unit2.getNumTroops();
            unit2.setNumTroops(troops2a - engagement.getCasualty2());
            copyUnit2.setNumTroops(troops2a - engagement.getCasualty2());

            if (unit1.getNumTroops() <= 0) {
                invadingArmy.remove(unit1);
                invadingProvince.removeUnit(unit1);
                skirmishOver = true;
            }
            if (unit2.getNumTroops() <= 0) {
                defendingArmy.remove(unit2);
                defendingProvince.removeUnit(unit2);
                skirmishOver = true;
            }

            // check if either side broke after the battle
            boolean break1, break2;
            break1 = engagement.breaking1(troops1a, troops2a);
            break2 = engagement.breaking2(troops1a, troops2a);

            if (break1 && break2) {
                skirmishOver = true;
                invadingArmy.remove(unit1);
                defendingArmy.remove(unit2);
            } else if (break1 && flee(unit1, unit2)) {
                invadingArmy.remove(unit1);
                skirmishOver = true;
            } else if (break2 && flee(unit2, unit1)) {
                defendingArmy.remove(unit2);
                skirmishOver = true;
            }
        }
        return numEngagements;
    }

    /**
     * returns whether or not a fleeing unit successfully flees
     * 
     * @param fleeing  the unit that is fleeing
     * @param pursuing the unit that is pursuing
     * @return true if the fleeing unit successfully flees, false otherwise
     */
    public boolean flee(Unit fleeing, Unit pursuing) {
        double chanceFlee = 50 + 10 * (fleeing.getSpeed() - pursuing.getSpeed());
        if (chanceFlee < 10) {
            chanceFlee = 10;
        }
        if (chanceFlee > 100) {
            chanceFlee = 100;
        }
        int i = random.nextInt(100);
        if (i < chanceFlee) {
            return true;
        }
        return false;
    }

    /**
     * getter for the invading army
     * 
     * @return a list of the units invading
     */
    public List<Unit> getInvadingArmy() {
        return invadingArmy;
    }

    /**
     * getter for the defending army
     * 
     * @return a list of the units defending
     */
    public List<Unit> getDefendingArmy() {
        return defendingArmy;
    }

    @Override
    public String toString() {
        return getClass().toString() + ", Invading province: " + invadingProvince + ", number of units invading"
                + numInvading + ", number of units defending: " + numDefending;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BattleResolver b = (BattleResolver) obj;
        return (invadingProvince == b.invadingProvince && numInvading == b.numInvading
                && defendingProvince == b.defendingProvince && numDefending == b.numDefending);
    }
}
