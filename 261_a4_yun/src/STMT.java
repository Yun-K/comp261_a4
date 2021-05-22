
/**
 * Description: <br/>
 * looks like dont need this since we are doing AST not CST
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public class STMT implements RobotProgramNode {
    private RobotProgramNode childNOdes;

    /**
     * A constructor. It construct a new instance of STMT.
     *
     * @param node
     *            should be either LOOP or ACT
     */
    public STMT(RobotProgramNode node) {
        this.childNOdes = node;
    }

    public STMT() {
    }

    @Override
    public void execute(Robot robot) {
        childNOdes.execute(robot);
    }

    public String toString() {
        return childNOdes.toString();
    }
}

