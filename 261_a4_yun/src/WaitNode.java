

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