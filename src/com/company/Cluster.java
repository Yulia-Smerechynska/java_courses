package com.company;

import java.util.Random;

public class Cluster implements Clusterable {

    private Servers[] servers;

    public Cluster(int countOfServers) {
        this.servers = new Servers[countOfServers];

        for (int i = 0; i < countOfServers; i++) {
            this.servers[i] = new Servers(i, 10);
        }
    }

    public Servers[] getServers() {
        return this.servers;
    }

    /**
     * check if node failed
     * @param serverNumber int
     * @param nodeNumber int
     * @return boolean
     */
    public boolean isFailed(int serverNumber, int nodeNumber) {
        Node[] nodes = servers[serverNumber].getAllNodes();
        return nodes[nodeNumber].getStatus().equals(Node.FAILED);
    }

    /**
     * set "failed" to default node status and for all nodes in front
     */
    public void sendMessage() {
        Servers randomServer = this.getRandomServer();
        Node[] randomServerNodes = randomServer.getAllNodes();
        Node randomNode = this.getRandomNode(randomServerNodes);
        int randomNodeNumber = randomNode.getNumber();

        for (int k = randomNodeNumber; k < randomServerNodes.length; k++) {
            randomServerNodes[k].setStatus(false);
        }

        System.out.println("Set failed Server number: " + randomServer.getNumber());
        System.out.println("Set failed Node number: " + randomNodeNumber);

        for (int i = randomServer.getNumber() + 1; i < this.servers.length; i++) {
            Node[] currentServerNodes = this.servers[i].getAllNodes();
            for (Node currentNode : currentServerNodes) {
                currentNode.setStatus(false);
            }
        }
    }

    /**
     * get random server
     * @return array Servers[]
     */
    private Servers getRandomServer() {
        Random random = new Random();
        int randomServerNumber = random.nextInt(this.servers.length);
        return this.servers[randomServerNumber];
    }

    /**
     * get random node from the random server
     * @param currentServerNodes Node[]
     * @return Node node
     */
    private Node getRandomNode(Node[] currentServerNodes) {
        Random random = new Random();
        int randomNodeNumber = random.nextInt(currentServerNodes.length);
        return currentServerNodes[randomNodeNumber];
    }

}
