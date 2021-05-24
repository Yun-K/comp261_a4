
public abstract class Logic implements COND {
    protected COND cond1, cond2, singleCond_notOperator;

    /**
     * Constructor for And ,OR since they need 2 cond.
     * <p>
     * e.g. if(and(1==1,2==2))
     *
     * @param cond1
     *            1st cond.
     * @param cond2
     *            2nd cond
     */
    public Logic(COND cond1, COND cond2) {
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    public Logic(COND singleCond_notOperator) {
        this.singleCond_notOperator = singleCond_notOperator;
    }

    @Override
    public abstract String toString();
}