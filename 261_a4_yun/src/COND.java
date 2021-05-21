/**
 * Description: <br/>
 * Stage 1
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public interface COND {// extends RobotProgramNode {
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
    public abstract boolean evaluate(Robot robot);

}
