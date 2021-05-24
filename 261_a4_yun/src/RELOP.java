
public abstract class RELOP implements COND {
    protected EXPR childNode1, childNode2;

    public RELOP(EXPR child1, EXPR child2) {
        childNode1 = child1;
        childNode2 = child2;
    }

    @Override
    public abstract String toString();

}
