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

    boolean gotElse = false;

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

    public IF(List<COND> condList, List<BLOCK> blockList, boolean gotElse) {
        this.conditionList = condList;
        this.blockList = blockList;
        this.gotElse = gotElse;

        if (conditionList.size() == 1) {
            this.singleCOND = conditionList.get(0);
        }
    }

    @Override
    public void execute(Robot robot) {
        // in the case of the there is only one COND,
        // which means we only got :
        // if OR if else
        if (this.conditionList.size() == 1) {
            // check if the condition is met
            if (this.conditionList.get(0).evaluate(robot)) {
                this.blockList.get(0).execute(robot);
            } else {
                if (this.blockList.size() > 1) {
                    if (this.gotElse && this.blockList.size() == 2) {
                        // execute else block
                        this.blockList.get(0).execute(robot);
                    }
                }
            }
        }
        /*
         * got elif
         */
        else {
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
        StringBuffer sb = new StringBuffer("if(").append(this.conditionList.get(0).toString()).append("){\n")
                .append(blockList.get(0).toString()).append("\n}");
        if (gotElse) {
            sb.append("else{").append(blockList.get(blockList.size() - 1).toString()).append("}");
        }

        return sb.toString();

    }

}
