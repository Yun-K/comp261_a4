package provided;
/**
 * Interface for all nodes that can be executed, including the top level program node
 */

interface RobotProgramNode {
    /**
     * Description: <br/>
     * Let the robot to perform/execute the command.
     * 
     * @author Yun Zhou
     * @param robot
     */
    public void execute(Robot robot);
}
