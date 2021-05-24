

class BarrelLR extends SEN {

    private EXPR exp = null;

    /**
     * Constructor for stage 3.
     * 
     * @param exp
     */
    public BarrelLR(EXPR exp) {
        if (exp == null) {
            String msg = "The arg for BarrelLR can not be null!" + "\n   @ ...";
            throw new ParserFailureException(msg + "...");
        }
        this.exp = exp;
    }

    /**
     * default constructor
     */
    public BarrelLR() {
    }

    @Override
    public int evaluate(Robot robot) {
        return exp == null ? robot.getClosestBarrelLR() : robot.getBarrelLR(exp.evaluate(robot));
    }

    @Override
    public String toString() {
        return "barrelLR";
    }
}