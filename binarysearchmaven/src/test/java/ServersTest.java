import org.junit.Test;

import static org.junit.Assert.*;

public class ServersTest {

    @Test
    public void getNumber() {
        Servers server = new Servers(5, 10);
        assertFalse(server.getNumber() != 5);
    }

    @Test
    public void getAllNodes() {
        Servers server = new Servers(5, 10);
        assertFalse(server.getAllNodes().size() == 0);
    }
}