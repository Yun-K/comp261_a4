import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br/>
 * Stage 1.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public class IF extends STMT {

    /**
     * condition list for holding conditions that if , elseIf, else got
     */
    private List<COND> conditionList = new ArrayList<COND>();

    /**
     * block list for holding blocks that if , elseIf, else got
     */
    private List<BLOCK> blockList = new ArrayList<BLOCK>();

    /** single condition, for stage 1 only */
    private COND singleCOND = null;

    /** single condition, for stage 1 only */
    private BLOCK singleBlock = null;

    public IF(COND parseCOND, BLOCK parseBLOCK) {
        this.singleBlock = parseBLOCK;
        this.singleCOND = parseCOND;
    }

    public IF(List<COND> singleCond, List<BLOCK> blockList) {
        this.conditionList = singleCond;
        this.blockList = blockList;
    }

    @Override
    public void execute(Robot robot) {

        // for (COND cond : CONDITION) {
        // check if the condition is met
        if (singleCOND.evaluate(robot)) {
            this.singleBlock.execute(robot);
        }
        // }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("if(");
        for (COND cond : conditionList) {
            sb.append(cond.toString());
        }
        sb.append("){\n");

        for (BLOCK b : this.blockList) {
            sb.append(b.toString());
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();

    }

}
