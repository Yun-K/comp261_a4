/**
 * Description: <br/>
 * Stage 1.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public class IF extends STMT implements RobotProgramNode {

    private RobotProgramNode CONDITION;

    private RobotProgramNode block;

    /**
     * A constructor. It construct a new instance of IF.
     *
     * @param node
     * @param cOND
     * @param block
     */
    public IF(RobotProgramNode cOND, RobotProgramNode block) {
        super(cOND);
        CONDITION = cOND;
        this.block = block;
    }

    public IF(RobotProgramNode parseBLOCK) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(Robot robot) {

    }

}
