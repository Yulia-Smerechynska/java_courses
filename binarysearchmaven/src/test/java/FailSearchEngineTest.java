import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FailSearchEngineTest {

    @Test
    public void search() {
        Cluster cluster = new Cluster(10);
        ArrayList<Integer> failedDataWasSet = cluster.sendMessage();
        FailSearchEngine failSearchEngine = new FailSearchEngine(cluster);
        ArrayList<Integer> failedDataWasGot =  failSearchEngine.search();
        assertArrayEquals(failedDataWasSet.toArray(), failedDataWasGot.toArray());
    }
}