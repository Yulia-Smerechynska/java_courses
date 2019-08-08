package com.company;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Cluster implements Clusterable {

    private ArrayList<Optional<Servers>> servers = new ArrayList<>();

    public Cluster(int countOfServers) {

        for (int i = 0; i < countOfServers; i++) {
            Optional<Servers> server = new Optional<>(new Servers(i, 10));
            this.servers.add(server);
        }
    }

    public ArrayList<Optional<Servers>> getServers() {
        return this.servers;
    }

    /**
     * check if node failed
     * @param serverNumber int
     * @param nodeNumber int
     * @return boolean
     */
    public boolean isFailed(int serverNumber, int nodeNumber) {
        ArrayList<Optional<Node>> nodes = servers.get(serverNumber).get().getAllNodes();
        return nodes.get(nodeNumber).get().getStatus().equals(Node.FAILED);
    }

    /**
     * set "failed" to default node status and for all nodes in front
     */
    public void sendMessage() throws NullPointerException {
        Servers randomServer = this.getRandomServer();
        ArrayList<Optional<Node>> randomServerNodes;
        try {
            randomServerNodes = randomServer.getAllNodes();
        } catch (NoSuchElementException e) {
            return;
        }
        if(randomServerNodes.size() > 0) {
            Optional<Node> randomNode = this.getRandomNode(randomServerNodes);
            int randomNodeNumber = -1;
            try {
                randomNodeNumber = randomServerNodes.indexOf(randomNode);
            } catch (NoSuchElementException e) {
                return;
            }

            for (int k = randomNodeNumber; k < randomServerNodes.size(); k++) {
                try {
                    randomServerNodes.get(k).get().setStatus(false);
                } catch (NoSuchElementException e) {
                }
            }

            System.out.println("Set failed Server number: " + randomServer.getNumber());
            System.out.println("Set failed Node index: " + randomNodeNumber);

            for (int i = randomServer.getNumber() + 1; i < this.servers.size(); i++) {
                ArrayList<Optional<Node>> currentServerNodes = new ArrayList<Optional<Node>>();
                try {
                    currentServerNodes = this.servers.get(i).get().getAllNodes();
                } catch (NoSuchElementException e) {
                }
                if(currentServerNodes.size() > 0) {
                    for (Optional<Node> currentNode : currentServerNodes) {
                        try {
                            currentNode.get().setStatus(false);
                        } catch (NoSuchElementException e) {
                        }
                    }
                }
            }
        }
    }

    /**
     * get random server
     * @return array Servers[]
     */
    private Servers getRandomServer() {
        Random random = new Random();
        int randomServerNumber = random.nextInt(this.servers.size());
        try{
            this.servers.get(randomServerNumber).get();
        } catch (NoSuchElementException e) {
            return this.getRandomServer();
        }
        return this.servers.get(randomServerNumber).get();
    }

    /**
     * get random node from the random server
     * @param currentServerNodes Node[]
     * @return Node node
     */
    private Optional<Node> getRandomNode(ArrayList<Optional<Node>> currentServerNodes) {
        Random random = new Random();
        int randomNodeNumber = random.nextInt(currentServerNodes.size());
        try{
            currentServerNodes.get(randomNodeNumber).get();
        } catch (NoSuchElementException e) {
            return this.getRandomNode(currentServerNodes);
        }
        return currentServerNodes.get(randomNodeNumber);
    }

}
