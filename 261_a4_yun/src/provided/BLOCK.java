package provided;

import java.util.List;

public class BLOCK implements RobotProgramNode {
    private List<RobotProgramNode> actions_program_node;

    public BLOCK(List<RobotProgramNode> actions_nodeList) {
        this.actions_program_node = actions_nodeList;
    }

    /**
     * Let the specificied robot to perform/execute the list of actions in order
     * 
     * @see RobotProgramNode#execute(Robot)
     */
    @Override
    public void execute(Robot robot) {
        //
        for (RobotProgramNode robotProgramNode : actions_program_node) {
            robotProgramNode.execute(robot);
        }

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("{\n");
        for (RobotProgramNode robotProgramNode : actions_program_node) {
            sb.append("\t");
            sb.append(robotProgramNode.toString());
            sb.append("\n");

        }
        sb.append("\n}");
        return sb.toString();
        // return super.toString();

    }

    /**
     * Get the actions_program_node.
     *
     * @return the actions_program_node
     */
    public List<RobotProgramNode> getActions_program_node() {
        return actions_program_node;
    }
}
