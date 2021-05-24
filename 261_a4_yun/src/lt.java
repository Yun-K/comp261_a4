

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