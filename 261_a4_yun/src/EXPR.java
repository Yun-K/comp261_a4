/**
 * Description: <br/>
 * Represent the Expression Node.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public interface EXPR {
    // protected Robot robot;

    /**
     * Description: <br/>
     * This method will return the integer val that the specificied Robot hold.
     * <p>
     * E.g.let's see lt(fuelLeft, 20)
     * <p>
     * SEN::fuelLeft::evaluate(Robot) will return the fuel left that robot hold
     * <p>
     * And Num::evaluate(Robot) will return 20 in this case.
     * 
     * @author Yun Zhou
     * @param robot
     * @return
     */
    public abstract int evaluate(Robot robot);

    public abstract String toString();

}

class NUM implements EXPR {
    /**
     * the int value that this NUM hold.
     */
    private int val;

    /**
     * A constructor. It construct a new instance of NUM.
     *
     * @param val the val to be set
     */
    NUM(int val) {
        // assert val > -1;
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

/**
 * Var is adapter for STMT and EXPR
 */
class VAR extends STMT implements EXPR {
    private String variableName;

    private EXPR expr_value;

    /**
     * @param variableName
     * @param value
     */
    public VAR(String variableName, EXPR value) {
        this.variableName = variableName;
        this.expr_value = value;
    }

    @Override
    public int evaluate(Robot robot) {
        return expr_value.evaluate(robot);
    }

    @Override
    public String toString() {
        return variableName + " = " + expr_value.toString() + " ;";
    }

}