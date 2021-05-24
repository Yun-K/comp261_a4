

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