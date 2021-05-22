
public abstract class ACT extends STMT implements RobotProgramNode {
    /** represent the action Node */
    protected ACT actionNode;

    public ACT() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ACT(ACT actionNode) {
        this.actionNode = actionNode;
    }

    @Override
    public abstract void execute(Robot robot);

    @Override
    public abstract String toString();

}

// TODO
class MoveNode extends ACT {
    public MoveNode() {
        super();
    }

    public MoveNode(ACT actNode) {
        super(actNode);
    }

    private EXPR expr = null;

    public MoveNode(EXPR expr) {
        this.expr = expr;
    }

    @Override
    public void execute(Robot robot) {
        if (expr != null) {
            // move the number of the expr.evaluate()
            for (int i = 0; i < expr.evaluate(robot); i++) {
                robot.move();
            }
        }

        // no expr, move robot once
        robot.move();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "move;";
    }
}

// done!
class TurnLNode extends ACT implements RobotProgramNode {

    public TurnLNode() {
        super();
    }

    TurnLNode(TurnLNode actionLNode) {
        super(actionLNode);
    }

    @Override
    public void execute(Robot robot) {
        robot.turnLeft();
    }

    @Override
    public String toString() {
        // return super.toString();
        return "turnL;";
    }
}

// done
class TurnRNode extends ACT implements RobotProgramNode {

    TurnRNode() {
        super();
    }

    TurnRNode(TurnRNode node) {
        super(node);
    }

    @Override
    public void execute(Robot robot) {
        robot.turnRight();
    }

    @Override
    public String toString() {
        return "turnR;";
    }

}

// done
class TakeFuelNode extends ACT {
    public TakeFuelNode() {

        super();
    }

    public TakeFuelNode(TakeFuelNode act_node) {

        super(act_node);
    }

    @Override
    public void execute(Robot robot) {
        robot.takeFuel();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "takeFuel;";
    }
}

// TODO
class WaitNode extends ACT {
    public WaitNode() {
        super();
    }

    private EXPR expr;

    public WaitNode(EXPR expr) {
        this.expr = expr;
    }

    // }
    @Override
    public void execute(Robot robot) {
        if (expr != null) {
            for (int i = 0; i < expr.evaluate(robot); i++) {
                robot.idleWait();
            }
            // try {
            // robot.wait(expr.evaluate(robot));
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
        } else {
            robot.idleWait();
        }

        // try {
        // robot.wait();
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "wait;";
    }
}

class turnAround extends ACT {

    @Override
    public void execute(Robot robot) {
        robot.turnAround();
    }

    @Override
    public String toString() {
        return "turnAround;";
    }
}

class shieldOn extends ACT {

    @Override
    public void execute(Robot robot) {
        robot.setShield(true);// ();
    }

    @Override
    public String toString() {
        return "shieldOn;";
    }
}

class shieldOff extends ACT {

    @Override
    public void execute(Robot robot) {
        robot.setShield(false);

    }

    @Override
    public String toString() {
        return "shieldOff;";
    }
}