package unsw.gloriaromanus.victory;

public interface Observer {
    /**
     * Updates observers on any changes in the subject
     * 
     * @param obj faction subject
     */
    public void update(Subject obj);
}
