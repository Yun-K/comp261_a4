

class Sub extends OP {

    public Sub(EXPR child1, EXPR child2) {
        super(child1, child2);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int evaluate(Robot robot) {
        return childNode1.evaluate(robot) - childNode2.evaluate(robot);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "sub(" + childNode1.toString() + "," + childNode2.toString() + ")";

    }
}