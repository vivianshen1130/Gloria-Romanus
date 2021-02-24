package unsw.gloriaromanus.battleResolver;

import java.util.Random;
import unsw.gloriaromanus.unit.*;

public class RangedEngagement extends Engagement {
    private Unit unit1;
    private Unit unit2;
    private Random random;

    /**
     * constructor for a ranged engagement
     * @param unit1 the first unit involved in the engagement
     * @param unit2 the second unit involved in the engagement
     */
    public RangedEngagement(Unit unit1, Unit unit2, Random random){
        super(unit1, unit2, random);
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.random = random;
    }

    /**
     * return the number of casualties to the first unit
     */
    public int getCasualty1(){
        double casualty1 = unit2.getNumTroops()*0.1*(unit1.getRangedAttackDamage()/(unit2.getArmour()+unit2.getShieldDefense()))*(random.nextGaussian()+1);
        if (casualty1 < 0){
            casualty1 = 0;
        }
        if (casualty1 > unit1.getNumTroops()){
            casualty1 = unit1.getNumTroops();
        }
        return (int) casualty1;
    }

    /**
     * return the number of casualties to the second unit
     */
    public int getCasualty2(){
        double casualty2 = unit1.getNumTroops()*0.1*(unit2.getRangedAttackDamage()/(unit1.getArmour()+unit1.getShieldDefense()))*(random.nextGaussian()+1);
        if (casualty2 < 0){
            casualty2 = 0;
        }
        if (casualty2 > unit2.getNumTroops()){
            casualty2 = unit2.getNumTroops();
        }
        return (int) casualty2;
    }

}
