
public abstract class RELOP implements COND {
    protected EXPR sensorNode, numExprNode;

    public RELOP(EXPR child1, EXPR child2) {
        sensorNode = child1;
        numExprNode = child2;
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
        StringBuffer sb = new StringBuffer("lt(")
                .append(sensorNode.toString())
                .append(",")
                .append(numExprNode.toString())
                .append(")");
        return sb.toString();

    }

    @Override
    public boolean evaluate(Robot robot) {
        return sensorNode.evaluate(robot) < numExprNode.evaluate(robot) ? true : false;
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
        return sensorNode.evaluate(robot) > numExprNode.evaluate(robot) ? true : false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("gt(")
                .append(sensorNode.toString())
                .append(",")
                .append(numExprNode.toString())
                .append(")");
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
        return sensorNode.evaluate(robot) == numExprNode.evaluate(robot) ? true : false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("eq(")
                .append(sensorNode.toString())
                .append(",")
                .append(numExprNode.toString())
                .append(")");
        return sb.toString();

    }
}
