package unsw.gloriaromanus.battleResolver;

import java.util.Random;
import unsw.gloriaromanus.unit.*;

public abstract class Engagement {
    private Unit unit1;
    private Unit unit2;
    private Random random;

    /**
     * constructor for an engagement
     * @param unit1 the first unit involved in the engagement
     * @param unit2 the second unit involved in the engagement
     */
    public Engagement(Unit unit1, Unit unit2, Random random){
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.random = random;
    }

    public abstract int getCasualty1();
    public abstract int getCasualty2();
    
    /**
     * return true if the first unit breaks after the casualties are inflicted, false otherwise
     */
    public boolean breaking1(int troops1a, int troops2a){
        double probToBreak1 = 100 - 10*unit1.getMorale();
        if (troops2a-unit2.getNumTroops() > 0 && troops2a > 0 && troops1a > 0) {
            probToBreak1 = probToBreak1 + ((troops1a-unit1.getNumTroops()/troops1a)/(troops2a-unit2.getNumTroops())/troops2a)*10;
        }
        if (probToBreak1 < 5){
            probToBreak1 = 5;
        }
        if (probToBreak1 > 95){
            probToBreak1 = 95;
        }
        int i = random.nextInt(100);
        if (i < probToBreak1) {
            return true;
        }
        return false;
    }

    /**
     * return true if the second unit breaks after the casualties are inflicted, false otherwise
     */
    public boolean breaking2(int troops1a, int troops2a){
        double probToBreak2 = 100 - 10*unit2.getMorale();
        if (troops1a-unit1.getNumTroops() > 0 && troops2a > 0 && troops1a > 0) {
            probToBreak2 = probToBreak2 + ((troops2a-unit2.getNumTroops()/troops2a)/(troops1a-unit1.getNumTroops())/troops1a)*10;
        }
        if (probToBreak2 < 5){
            probToBreak2 = 5;
        }
        if (probToBreak2 > 95){
            probToBreak2 = 95;
        }
        int i = random.nextInt(100);
        if (i < probToBreak2) {
            return true;
        }
        return false;
    }

}
