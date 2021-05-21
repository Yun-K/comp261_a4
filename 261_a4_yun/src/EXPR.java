/**
 * Description: <br/>
 * Represent the Expression Node.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public abstract class EXPR {
    protected Robot robot;

    /**
     * Description: <br/>
     * This method will return the integer val that the specificied Robot hold.
     * <p>
     * E.g.let's see lt(fuelLeft, 20)
     * <p>
     * SEN::fuelLeft::getValue(Robot) will return the fuel left that robot hold
     * <p>
     * And Num::getValue(Robot) will return 20 in this case.
     * 
     * @author Yun Zhou
     * @param robot
     * @return
     */
    public abstract int evaluate(Robot robot);

    public abstract String toString();

}

class NUM extends EXPR {
    /**
     * the int value that this NUM hold.
     */
    private int val;

    /**
     * A constructor. It construct a new instance of NUM.
     *
     * @param val
     *            the val to be set
     */
    NUM(int val) {
        assert val > -1;
        this.val = val;
    }

    @Override
    public int evaluate(Robot robot) {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
}
