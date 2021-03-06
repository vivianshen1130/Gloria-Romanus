package unsw.gloriaromanus.victory;

public class LogicalOr extends Composite{
    private Component comp1;
    private Component comp2;

    /**
     * @param comp1 the first component that is passed in to the logical OR operator
     * @param comp2 the second component that is passed in to the logical OR operator
     */
    public LogicalOr(Component comp1, Component comp2){
        super(comp1, comp2);
        this.comp1 = comp1;
        this.comp2 = comp2;
    }

    @Override
    public boolean evaluate() {
        return (comp1.evaluate() || comp2.evaluate());
    }
}
