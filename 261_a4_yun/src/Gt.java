

/**
 * Description: <br/>
 * Greater than.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
class Gt extends RELOP {

    public Gt(EXPR child1, EXPR child2) {
        super(child1, child2);
    }

    @Override
    public boolean evaluate(Robot robot) {
        return childNode1.evaluate(robot) > childNode2.evaluate(robot) ? true : false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("gt(").append(childNode1.toString()).append(",")
                .append(childNode2.toString()).append(")");
        return sb.toString();

    }

}