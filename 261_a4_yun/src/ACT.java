
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

    @Override
    public void execute(Robot robot) {
        robot.move();// move the robot
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "move";
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
        return "turnL";
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
        return "turnR";
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
        return "takeFuel";
    }
}

// TODO
class WaitNode extends ACT {
    public WaitNode() {
        super();
    }

    // }
    @Override
    public void execute(Robot robot) {
        robot.idleWait();
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
        return "wait";
    }
}