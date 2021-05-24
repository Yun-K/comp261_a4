/**
 * Description: <br/>
 * OPerator.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public abstract class OP implements EXPR {
    protected EXPR childNode1, childNode2;

    public OP(EXPR child1, EXPR child2) {
        childNode1 = child1;
        childNode2 = child2;
    }

    /**
     * Get the childNode1.
     *
     * @return the childNode1
     */
    public EXPR getChildNode1() {
        return childNode1;
    }

    /**
     * Get the childNode2.
     *
     * @return the childNode2
     */
    public EXPR getChildNode2() {
        return childNode2;
    }

}
