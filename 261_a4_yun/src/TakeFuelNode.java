

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