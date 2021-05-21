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
public abstract class SEN extends EXPR {

}

class FuelLeftNode extends SEN {
    private Robot robot;

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
     * Gets the left-right-location of the other robot relative to the currentposition and
     * orientation.
     * 
     * @return:INFINITY if there isn't a second robot, -ve if to the left, +veif to the right
     *                  and 0 if directly in front or behind
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
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
}

class NumBarrels extends SEN {

    @Override
    public int evaluate(Robot robot) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
}

class BarrelLR extends SEN {

    @Override
    public int evaluate(Robot robot) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
}

class BarrelFB extends SEN {

    @Override
    public int evaluate(Robot robot) {
        // TODO Auto-generated method stub
        // return robot.getBarrelFB(n);
        return 0;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
}

class WallDist extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.getDistanceToWall();
    }

    @Override
    public String toString() {
        return "wallDist:";
    }
}
