/**
 * Description: <br/>
 * For satge 1:
 * <p>
 * "fuelLeft" | "oppLR" | "oppFB" | "numBarrels"
 * <p>
 * | "barrelLR" | "barrelFB" | "wallDist"
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public abstract class SEN implements EXPR {

    public SEN() {
    }

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
