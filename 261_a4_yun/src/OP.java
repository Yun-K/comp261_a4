/**
 * Description: <br/>
 * OPerator.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public abstract class OP extends EXPR {
    protected EXPR childNode1, childNode2;

    public OP(EXPR child1, EXPR child2) {
        childNode1 = child1;
        childNode2 = child2;
    }

    /**
     * Get the childNode1.
     *
     * @return the childNode1
     */
    public EXPR getChildNode1() {
        return childNode1;
    }

    /**
     * Get the childNode2.
     *
     * @return the childNode2
     */
    public EXPR getChildNode2() {
        return childNode2;
    }

}

class Add extends OP {

    public Add(EXPR child1, EXPR child2) {
        super(child1, child2);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int evaluate(Robot robot) {
        return childNode1.evaluate(robot) + childNode2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "add(" + childNode1.toString() + "," + childNode2.toString() + ")";
    }
}

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

class Mul extends OP {

    public Mul(EXPR child1, EXPR child2) {
        super(child1, child2);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int evaluate(Robot robot) {
        return childNode1.evaluate(robot) * childNode2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "mul(" + childNode1.toString() + "," + childNode2.toString() + ")";
    }
}

class Div extends OP {

    public Div(EXPR child1, EXPR child2) {
        super(child1, child2);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int evaluate(Robot robot) {
        return childNode1.evaluate(robot) / childNode2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "div(" + childNode1.toString() + "," + childNode2.toString() + ")";
    }
}
