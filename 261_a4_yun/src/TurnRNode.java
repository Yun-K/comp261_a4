

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