
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

class And extends Logic {

    public And(COND cond1, COND cond2) {
        super(cond1, cond2);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean evaluate(Robot robot) {
        return cond1.evaluate(robot) && cond2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "and(" + cond1.toString() + "," + cond2.toString() + ");";
    }
}

class Or extends Logic {

    public Or(COND cond1, COND cond2) {
        super(cond1, cond2);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean evaluate(Robot robot) {
        return cond1.evaluate(robot) || cond2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "or(" + cond1.toString() + "," + cond2.toString() + ");";
    }
}

class Not extends Logic {

    public Not(COND singleCond_notOperator) {
        super(singleCond_notOperator);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean evaluate(Robot robot) {
        return !singleCond_notOperator.evaluate(robot);
    }

    @Override
    public String toString() {
        return "not(" + singleCond_notOperator.toString() + ");";
    }
}