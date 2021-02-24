package unsw.gloriaromanus.victory;

public class Composite implements Component {
    private Component comp1;
    private Component comp2;

    /**
     * @param comp1 the first component that is passed in to the logical AND operator
     * @param comp2 the second component that is passed in to the logical AND operator
     */
    public Composite(Component comp1, Component comp2){
        this.comp1 = comp1;
        this.comp2 = comp2;
    }

    public boolean evaluate() {
        return false;
    }

    @Override
    public String toString() {
        return getClass().toString() + ", component 1: " + comp1 + ", component 2: " + comp2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Composite c = (Composite) obj;
        return (comp1 == c.comp1 && comp2 == c.comp2);
    }
}
