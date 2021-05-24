

class BarrelFB extends SEN {

    private EXPR exp = null;

    /**
     * Constructor for stage 3.
     * 
     * @param exp
     */
    public BarrelFB(EXPR exp) {
        if (exp == null) {
            String msg = "The arg for BarrelFB can not be null!" + "\n   @ ...";
            throw new ParserFailureException(msg + "...");
        }
        this.exp = exp;
    }

    /**
     * default constructor
     */
    public BarrelFB() {
    }

    @Override
    public int evaluate(Robot robot) {
        return exp == null ? robot.getClosestBarrelFB() : robot.getBarrelFB(exp.evaluate(robot));
    }

    @Override
    public String toString() {
        return "barrelFB";
    }
}