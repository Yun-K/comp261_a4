package provided;

import java.util.List;

/**
 * Description: <br/>
 * PROG, means initial start.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public class PROG implements RobotProgramNode {

    private List<RobotProgramNode> stmt_node_list;

    PROG(List<RobotProgramNode> stmt) {
        this.stmt_node_list = stmt;
    }

    /**
     * As the Start, this toString() will loop through all the children
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (RobotProgramNode robotProgramNode : stmt_node_list) {
            sb.append("\n");
            sb.append(robotProgramNode.toString());
        }
        return sb.toString();
    }

    /**
     * Execute all the node from the list.
     * 
     * @see provided.RobotProgramNode#execute(provided.Robot)
     */
    @Override
    public void execute(Robot robot) {
        for (RobotProgramNode robotProgramNode : stmt_node_list) {
            robotProgramNode.execute(robot);
        }
    }

}
