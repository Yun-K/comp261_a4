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

class FuelLeftNode extends SEN {

    public FuelLeftNode() {

    }

    public FuelLeftNode(Robot robot) {

    }

    @Override
    public String toString() {
        return "fuelLeft";// + getValue(robot);
    }

    @Override
    public int evaluate(Robot robot) {
        return robot.getFuel();
    }
}

/**
 * Description: <br/>
 *
 * @author Yun Zhou 300442776
 * @version
 */

class OppLR extends SEN {

    /**
     * Gets the left-right-location of the other robot relative to the
     * currentposition and orientation.
     * 
     * @return:INFINITY if there isn't a second robot, -ve if to the left, +veif to
     *                  the right and 0 if directly in front or behind
     * 
     * @see EXPR#evaluate(Robot)
     */
    @Override
    public int evaluate(Robot robot) {
        return robot.getOpponentLR();
    }

    @Override
    public String toString() {
        return "oppLR";
    }
}

class OppFB extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.getOpponentFB();
    }

    @Override
    public String toString() {
        return "oppFB";
    }
}

class NumBarrels extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.numBarrels();
    }

    @Override
    public String toString() {
        return "numBarrels";
    }
}

class BarrelLR extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.getClosestBarrelLR();
    }

    @Override
    public String toString() {
        return "barrelLR";
    }
}

class BarrelFB extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.getClosestBarrelFB();
    }

    @Override
    public String toString() {
        return "barrelFB";
    }
}

class WallDist extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.getDistanceToWall();
    }

    @Override
    public String toString() {
        return "wallDist";
    }
}
