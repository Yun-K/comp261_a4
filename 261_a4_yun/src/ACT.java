
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