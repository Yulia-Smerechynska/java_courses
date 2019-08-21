import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Servers implements Countable {
    private int number;
    private ArrayList<Optional<Node>> nodes = new ArrayList<>();

    public Servers(int internalNumber, int countOfNodes) {
        this.number = internalNumber;

        for (int i = 0; i < countOfNodes; i++) {
            Random r = new Random();
            this.nodes.add(new Optional<>(r.nextBoolean() ? new Node(i) : null));
        }
    }

    /**
     * get number of current server
     * @return int
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * get active server nodes
     * @return ArrayList<Optional < Node>>
     */
    public ArrayList<Optional<Node>> getAllNodes() {
        ArrayList<Optional<Node>> realNodes = new ArrayList<>();
        for (Optional<Node> node : this.nodes) {
            try {
                if (node.isPresent()) realNodes.add(node);
            } catch (NoSuchElementException e) {
            }
        }
        return realNodes;
    }
}
