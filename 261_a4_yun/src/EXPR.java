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