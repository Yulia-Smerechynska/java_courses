import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Cluster implements Clusterable {

    private ArrayList<Optional<Servers>> servers = new ArrayList<>();

    public Cluster(int countOfServers) {
        ArrayList<Optional<Servers>> temporaryServers = new ArrayList<>();
        for (int i = 0; i < countOfServers; i++) {
            Random r = new Random();
            temporaryServers.add(new Optional<>(r.nextBoolean() ? new Servers(i, 10) : null));
        }

        for (Optional<Servers> temporaryServer : temporaryServers) {
            try {
                if (temporaryServer.isPresent()) servers.add(temporaryServer);
            } catch (NoSuchElementException e) {
            }
        }

        if (this.servers.size() == 0) {
            new Cluster(countOfServers);
        }

    }

    public ArrayList<Optional<Servers>> getServers() {
        return this.servers;
    }

    /**
     * check if node failed
     *
     * @param serverNumber int
     * @param nodeNumber   int
     * @return boolean
     */
    public boolean isFailed(int serverNumber, int nodeNumber) {
        ArrayList<Optional<Node>> nodes = servers.get(serverNumber).get().getAllNodes();
        return nodes.get(nodeNumber).get().getStatus().equals(Node.FAILED);
    }

    /**
     * set "failed" to default node status and for all nodes in front
     */
    public ArrayList<Integer> sendMessage() throws NullPointerException {
        ArrayList<Integer> errorsWasSet = new ArrayList<Integer>();

        Optional<Servers> randomServer = this.getRandomServer();
        ArrayList<Optional<Node>> randomServerNodes;
        try {
            randomServerNodes = randomServer.get().getAllNodes();
        } catch (NoSuchElementException e) {
            return errorsWasSet;
        }
        if (randomServerNodes.size() > 0) {
            Optional<Node> randomNode = this.getRandomNode(randomServerNodes);
            int randomNodeNumber = -1;
            try {
                randomNodeNumber = randomServerNodes.indexOf(randomNode);
            } catch (NoSuchElementException e) {
                return errorsWasSet;
            }

            for (int k = randomNodeNumber; k < randomServerNodes.size(); k++) {
                try {
                    randomServerNodes.get(k).get().setStatus(false);
                } catch (NoSuchElementException e) {
                }
            }

            System.out.println("Set failed Server number: " + randomServer.get().getNumber());
            System.out.println("Set failed Node index: " + randomNodeNumber);
            errorsWasSet.add(randomServer.get().getNumber());
            errorsWasSet.add(randomNodeNumber);

            for (int i = this.servers.indexOf(randomServer) + 1; i < this.servers.size(); i++) {
                ArrayList<Optional<Node>> currentServerNodes = new ArrayList<Optional<Node>>();
                try {
                    currentServerNodes = this.servers.get(i).get().getAllNodes();
                } catch (NoSuchElementException e) {
                }
                if (currentServerNodes.size() > 0) {
                    for (Optional<Node> currentNode : currentServerNodes) {
                        try {
                            currentNode.get().setStatus(false);
                        } catch (NoSuchElementException e) {
                        }
                    }
                }
            }
        }

        return errorsWasSet;
    }

    /**
     * get random server
     *
     * @return array Servers[]
     */
    public Optional<Servers> getRandomServer() {
        Random random = new Random();
        int randomServerNumber = random.nextInt(this.servers.size());
        try {
            return this.servers.get(randomServerNumber).isPresent() && this.servers.get(randomServerNumber).get().getAllNodes().size() > 0
                    ? this.servers.get(randomServerNumber) :
                    this.getRandomServer();
        } catch (NoSuchElementException e) {
            return this.getRandomServer();
        }
    }

    /**
     * get random node from the random server
     *
     * @param currentServerNodes Optional<Node>
     * @return Node node
     */
    public Optional<Node> getRandomNode(ArrayList<Optional<Node>> currentServerNodes) {
        Random random = new Random();
        int randomNodeNumber = random.nextInt(currentServerNodes.size());

        try {
            return currentServerNodes.get(randomNodeNumber).isPresent() ? currentServerNodes.get(randomNodeNumber) : this.getRandomNode(currentServerNodes);
        } catch (NoSuchElementException e) {
            return this.getRandomNode(currentServerNodes);
        }

    }

}
