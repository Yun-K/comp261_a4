/**
 * Description: <br/>
 * Stage 1
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public interface COND extends RobotProgramNode {
    /**
     * Description: <br/>
     * Check if the CONDITION is met, if the condition is true, return true. otherwise return
     * false.
     * <p>
     * e.g. lt(fuelLeft, 20) will check if the fuelLeft of the specificied robot is less than
     * 20.
     * 
     * @author Yun Zhou
     * @param robot
     * @return true if the Sensor is less than numNode
     */
    public abstract boolean compute(Robot robot);

    /**
     * Should not execute the COND.execute() as well as the subClasses.
     * 
     * @see RobotProgramNode#execute(Robot)
     */
    @Override
    public default void execute(Robot robot) {
        assert false;
    }

}

class lt implements COND {

    private EXPR sensorNode, numExprNode;

    /**
     * A constructor. It construct a new instance of lt.
     *
     * @param sensorNode
     * @param expressionNode
     */
    public lt(EXPR sensorNode, EXPR expressionNode) {
        super();
        this.sensorNode = sensorNode;
        this.numExprNode = expressionNode;
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
    public boolean compute(Robot robot) {
        return this.sensorNode.compute(robot) < this.numExprNode.compute(robot) ? true : false;
    }
}