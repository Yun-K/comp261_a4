

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