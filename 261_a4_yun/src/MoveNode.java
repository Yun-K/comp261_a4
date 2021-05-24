

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