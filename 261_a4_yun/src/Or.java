

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