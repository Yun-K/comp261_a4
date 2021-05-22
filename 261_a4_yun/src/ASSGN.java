public abstract class ASSGN extends STMT {
    private int value;
    private String name;

    /**
     * assgn the integer value to the corresponding name.
     * 
     * @param value value to assign
     * @param name  name to assign
     */
    public ASSGN(String name, int value) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "$" + name + "=" + value + ";";
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
