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

    private boolean gotElse = false;

    private boolean gotElseIf = false;
    /** single condition, for stage 1 only */
    private COND singleCOND = null;

    /** single condition, for stage 1 only */
    private BLOCK singleBlock = null;

    public IF(COND parseCOND, BLOCK parseBLOCK) {
        this.singleBlock = parseBLOCK;
        this.singleCOND = parseCOND;

        this.blockList.add(parseBLOCK);
        this.conditionList.add(parseCOND);
    }

    public IF(List<COND> condList, List<BLOCK> blockList, boolean gotElse, boolean gotElseIf) {
        this.conditionList = condList;
        this.blockList = blockList;
        this.gotElse = gotElse;
        this.gotElseIf = gotElseIf;

        // if (conditionList.size() == 1) {
        // this.singleCOND = conditionList.get(0);
        // }
    }

    @Override
    public void execute(Robot robot) {
        // in the case of there is only one COND,
        // which means we only got :
        // if with else OR if without else
        if (this.conditionList.size() == 1) {
            // check if the condition is met
            if (this.conditionList.get(0).evaluate(robot)) {
                this.blockList.get(0).execute(robot);
            }
            // the condition is not met for the if(COND),
            // so execute else block if gotElse
            else {
                if (this.blockList.size() > 1) {
                    if (this.gotElse) {
                        // execute else block
                        this.blockList.get(blockList.size() - 1).execute(robot);
                    }
                }
            }
        }
        /*
         * got elif
         */
        else {
            boolean isTrue = false;
            for (int i = 0; i < this.conditionList.size(); i++) {
                isTrue = this.conditionList.get(i).evaluate(robot);
                if (isTrue) {
                    this.blockList.get(i).execute(robot);
                    break;
                }
            }
            // execute else block if none of the if, elseif conditions are true
            if (this.gotElse) {
                // we've got else, so execute else block
                this.blockList.get(blockList.size() - 1).execute(robot);
            }
        }
        // // check if the condition is met
        // if (singleCOND.evaluate(robot)) {
        // this.singleBlock.execute(robot);
        // }
    }

    /**
     * need to fix this since
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("if(").append(this.conditionList.get(0).toString()).append(")")
                .append(blockList.get(0).toString());
        if (gotElse) {
            sb.append("else\t").append(blockList.get(blockList.size() - 1).toString());
        }

        return sb.toString();

    }

}
