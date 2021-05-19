package provided;

public abstract class ACT implements RobotProgramNode {
    /** represent the action Node */
    protected ACT actionNode;

    public ACT() {
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

class MoveNode extends ACT implements RobotProgramNode {

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

class TurnRNode extends ACT implements RobotProgramNode {

    @Override
    public void execute(Robot robot) {
        robot.turnRight();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "turnR";
    }

}

class TakeFuelNode extends ACT implements RobotProgramNode {
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

class WaitNode extends ACT implements RobotProgramNode {

    @Override
    public void execute(Robot robot) {
        try {
            robot.wait();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "wait";
    }
}