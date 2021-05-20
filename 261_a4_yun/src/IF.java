import java.util.Iterator;
import java.util.List;

/**
 * Description: <br/>
 * Stage 1.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public class IF extends STMT implements RobotProgramNode {

    private List<COND> CONDITION;

    private List<BLOCK> block;

    public IF(RobotProgramNode parseBLOCK) {
        // TODO Auto-generated constructor stub
    }

    public IF(COND parseCOND, BLOCK parseBLOCK) {
        // TODO Auto-generated constructor stub
    }

    public IF(List<COND> conditions, List<BLOCK> block2) {
        // TODO Auto-generated constructor stub
        this.CONDITION = conditions;
        this.block = block2;
    }

    @Override
    public void execute(Robot robot) {
        for (int i = 0; i < this.CONDITION.size(); i++) {
            COND cond = CONDITION.get(i);
            // if (cond.) {
            //
            // }

        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("if(");
        for (COND cond : CONDITION) {
            sb.append(cond.toString());
        }
        sb.append("){\n");

        for (BLOCK b : this.block) {
            sb.append(b.toString());
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();

    }

}
