

class NUM implements EXPR {
    /**
     * the int value that this NUM hold.
     */
    private int val;

    /**
     * A constructor. It construct a new instance of NUM.
     *
     * @param val the val to be set
     */
    NUM(int val) {
        // assert val > -1;
        this.val = val;
    }

    @Override
    public int evaluate(Robot robot) {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
}