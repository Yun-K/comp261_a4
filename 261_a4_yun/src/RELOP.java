
public abstract class RELOP implements COND {
    protected EXPR childNode1, childNode2;

    public RELOP(EXPR child1, EXPR child2) {
        childNode1 = child1;
        childNode2 = child2;
    }

    @Override
    public abstract String toString();

}

/**
 * Description: <br/>
 * less than.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
class lt extends RELOP {

    /**
     * A constructor. It construct a new instance of lt.
     *
     * @param sensorNode
     * @param expressionNode
     */
    public lt(EXPR sensorNode, EXPR expressionNode) {
        super(sensorNode, expressionNode);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("lt(").append(childNode1.toString()).append(",")
                .append(childNode2.toString()).append(")");
        return sb.toString();

    }

    @Override
    public boolean evaluate(Robot robot) {
        return childNode1.evaluate(robot) < childNode2.evaluate(robot) ? true : false;
    }
}

/**
 * Description: <br/>
 * Greater than.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
class Gt extends RELOP {

    public Gt(EXPR child1, EXPR child2) {
        super(child1, child2);
    }

    @Override
    public boolean evaluate(Robot robot) {
        return childNode1.evaluate(robot) > childNode2.evaluate(robot) ? true : false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("gt(").append(childNode1.toString()).append(",")
                .append(childNode2.toString()).append(")");
        return sb.toString();

    }

}

/**
 * Description: <br/>
 * Equal to.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
class Eq extends RELOP {

    public Eq(EXPR child1, EXPR child2) {
        super(child1, child2);
    }

    @Override
    public boolean evaluate(Robot robot) {
        return childNode1.evaluate(robot) == childNode2.evaluate(robot) ? true : false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("eq(").append(childNode1.toString()).append(",")
                .append(childNode2.toString()).append(")");
        return sb.toString();

    }
}
