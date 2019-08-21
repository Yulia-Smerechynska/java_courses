public class Node implements Countable {
    private int number;
    static final String ACTIVE = "active";
    static final String FAILED = "failed";
    private String status = Node.ACTIVE;

    public Node(int internalNumber) {
        this.number = internalNumber;
    }

    /**
     * get number of current node
     * @return int
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * set status of current node
     */
    public void setStatus(boolean newStatus) {
        this.status = newStatus ? Node.ACTIVE : Node.FAILED;
    }

    /**
     * set status for current node
     * @return String
     */
    public String getStatus() {
        return this.status;
    }
}
