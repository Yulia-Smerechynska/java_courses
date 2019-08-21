import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClusterTest {

    @org.junit.Test
    public void getServers() {
        Cluster cluster = new Cluster(10);
        ArrayList<Optional<Servers>> servers = cluster.getServers();
        assertFalse(servers.size() == 0);
    }

    @org.junit.Test
    public void isFailed() {
        Cluster cluster = new Cluster(10);
        Optional<Servers> currentServer = cluster.getRandomServer();
        ArrayList<Optional<Servers>> clusterServers = cluster.getServers();
        ArrayList<Optional<Node>> serverNodes =currentServer.get().getAllNodes();
        Optional<Node>currentNode = cluster.getRandomNode(serverNodes);
        currentNode.get().setStatus(false);
        boolean ifFailedFounded =  cluster.isFailed(clusterServers.indexOf(currentServer), serverNodes.indexOf(currentNode));

        assertFalse(!ifFailedFounded);
    }

    @org.junit.Test
    public void sendMessage() {
        Cluster cluster = new Cluster(10);
        ArrayList<Integer> failedDataWasSet = cluster.sendMessage();
        assertFalse(failedDataWasSet.size() == 0);
    }
}