

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