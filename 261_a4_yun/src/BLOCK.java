
import java.util.List;

//done
public class BLOCK implements RobotProgramNode {
    /**
     * List of STMT nodes
     */
    private List<STMT> stmt_actions_program_node;

    public BLOCK(List<STMT> stmt_actions_nodeList) {
        this.stmt_actions_program_node = stmt_actions_nodeList;
    }

    /**
     * Let the specificied robot to perform/execute the list of actions in order
     * 
     * @see STMT#execute(Robot)
     */
    @Override
    public void execute(Robot robot) {
        //
        for (STMT stmt : stmt_actions_program_node) {
            stmt.execute(robot);
        }

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("{\n");
        for (STMT stmt : stmt_actions_program_node) {
            sb.append("\t");// use tab to make output nicer
            sb.append(stmt.toString());
            sb.append("\n");

        }
        sb.append("\n}");
        return sb.toString();
        // return super.toString();

    }

    public void addSTMTNode(STMT program_node) {
        this.stmt_actions_program_node.add(program_node);
    }

    /**
     * Get the actions_program_node.
     *
     * @return the actions_program_node
     */
    public List<STMT> getActions_program_node() {
        return stmt_actions_program_node;
    }
}
