import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void getNumber() {
        Optional<Node> node = new Optional<Node>(new Node(5));
        assertFalse(node.get().getNumber() != 5);
    }

    @Test
    public void setStatus() {
        Optional<Node> node = new Optional<Node>(new Node(5));
        node.get().setStatus(false);
        assertFalse(node.get().getStatus() != Node.FAILED);
    }

    @Test
    public void getStatus() {
        Optional<Node> node = new Optional<Node>(new Node(5));
        node.get().setStatus(false);
        assertFalse(node.get().getStatus() != Node.FAILED);
    }
}