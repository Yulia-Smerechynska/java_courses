package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class FailSearchEngine {

    private Clusterable currentCluster;
    private ArrayList<Optional<Servers>> currentClusterServers;
    private Servers currentServer;
    private int failedNodeNumber = -1;
    private boolean failedNode = false;
    private boolean isCurrentServerHasFailedNodes = false;

    public FailSearchEngine(Clusterable cluster) {
        this.currentCluster = cluster;
        this.getCurrentServers();
    }

    public void search() {

        int leftServer = 0;
        int rightServer = this.currentClusterServers.size() - 1;
        while (!this.failedNode) {
            if (rightServer - leftServer > 1) {
                int middleServer = (rightServer + leftServer) / 2;
                this.findFailNode(middleServer);
                if (this.isCurrentServerHasFailedNodes) {
                    rightServer = middleServer;
                } else {
                    leftServer = middleServer;
                }
            } else {
                if (!this.findFailNode(leftServer)) {
                    this.findFailNode(rightServer);
                }
                this.failedNode = true;
            }

        }

        if (this.failedNodeNumber == -1) {
            System.out.println("All Nodes work fine");
        } else {
            System.out.println("================================");
            System.out.println("Failed Server: " + this.currentServer.getNumber());
            System.out.println("Failed Node index: " + this.failedNodeNumber);
        }
    }

    /**
     * find filed node
     *
     * @return boolean
     */
    private boolean findFailNode(int middleServer) throws NoSuchElementException {
        this.isCurrentServerHasFailedNodes = false;
        try {
            this.currentServer = this.currentClusterServers.get(middleServer).get();
        } catch (NoSuchElementException e) {
            return false;
        }

        ArrayList<Optional<Node>> currentServerNodes = this.currentServer.getAllNodes();

        int leftBound = 0;
        int rightBound = currentServerNodes.size() - 1;

        while (!this.failedNode) {
            if (rightBound - leftBound > 1) {

                int middleValue = (rightBound + leftBound) / 2;
                Node middleNode;
                Optional<Node> middleNodes;
                try {
                    middleNode = currentServerNodes.get(middleValue).get();
                    middleNodes = currentServerNodes.get(middleValue);
                } catch (NoSuchElementException e) {
                    return false;
                }

                boolean isFailed = this.currentCluster.isFailed(this.currentServer.getNumber(), currentServerNodes.indexOf(middleNodes));
                if (isFailed) {
                    this.isCurrentServerHasFailedNodes = true;
                    boolean isPrevNodeActive = this.isPrevNodeActive(this.currentServer.getNumber(), currentServerNodes.indexOf(middleNodes));
                    if (isPrevNodeActive) {
                        this.setFailedNodeNumber(middleNode.getNumber());
                        break;
                    } else {
                        rightBound = middleValue;
                    }

                } else {
                    leftBound = middleValue;
                }

            } else {
                return this.checkNeighbours(leftBound, rightBound);
            }
        }
        return this.failedNode;
    }

    /**
     * check last two closest nodes
     *
     * @param leftBound int
     * @param rightBound int
     * @return boolean
     */
    private boolean checkNeighbours(int leftBound, int rightBound) {

        Optional<Node> leftBoundNodes;
        Optional<Node> rightBoundNodes;
        ArrayList<Optional<Node>> currentServerNodes;
        try {
            currentServerNodes = this.currentServer.getAllNodes();
            leftBoundNodes = currentServerNodes.get(leftBound);
            rightBoundNodes = currentServerNodes.get(rightBound);
        } catch (NoSuchElementException e) {
            return false;
        }

        boolean isFirstFailed = this.currentCluster.isFailed(this.currentServer.getNumber(), currentServerNodes.indexOf(leftBoundNodes));
        boolean isSecondFailed = this.currentCluster.isFailed(this.currentServer.getNumber(), currentServerNodes.indexOf(rightBoundNodes));
        if (isFirstFailed && this.currentServer.getNumber() == 0) {
            this.setFailedNodeNumber(leftBound);
            return true;
        }

        if (!isFirstFailed && isSecondFailed) {
            this.setFailedNodeNumber(rightBound);
            return true;
        }

        if (isFirstFailed && !isSecondFailed) {
            this.setFailedNodeNumber(leftBound);
            return true;
        }

        if (isFirstFailed && isSecondFailed) {
            return this.isPrevNodeActive(this.currentServer.getNumber(), currentServerNodes.indexOf(leftBoundNodes));
        }
        return false;
    }

    /**
     * check if node is active
     *
     * @param serverNumber int
     * @param nodeNumber   int
     * @return boolean
     */
    private boolean isPrevNodeActive(int serverNumber, int nodeNumber) {
        if (nodeNumber == 0) {
            Optional<Servers> previousServer = this.getPreviousServer(serverNumber);
            Servers prevServer = previousServer.get();
            ArrayList<Optional<Node>> prevServerNodes = prevServer.getAllNodes();
            int lastNodePosition = prevServerNodes.size() - 1;

            Node lastNode;
            try {
                lastNode = prevServerNodes.get(lastNodePosition).get();
            } catch (NoSuchElementException e) {
                return this.isPrevNodeActive(prevServer.getNumber(), lastNodePosition);
            }
            if (lastNode.getStatus().equals(Node.ACTIVE)) {
                this.setFailedNodeNumber(nodeNumber);
                return true;
            } else {
                return false;
            }
        } else {

            int prevNodeNumber = nodeNumber - 1;
            Node previouseNode;
            try {
                previouseNode = this.currentServer.getAllNodes().get(prevNodeNumber).get();
            } catch (NoSuchElementException e) {
                return this.isPrevNodeActive(serverNumber, prevNodeNumber);
            }

            if (previouseNode.getStatus().equals(Node.ACTIVE)) {
                this.setFailedNodeNumber(nodeNumber);
                return false;
            } else {
                return false;
            }
        }
    }

    /**
     * set failed node number
     *
     * @param nodeNumber int
     */
    private void setFailedNodeNumber(int nodeNumber) {
        this.failedNode = true;
        this.failedNodeNumber = nodeNumber;
    }


    /**
     * get previous server
     *
     * @return Servers
     */
    private Optional<Servers> getPreviousServer(int serverNumber) {
        serverNumber = serverNumber > 1 ? serverNumber - 1 : 0;
        return this.currentClusterServers.get(serverNumber);
    }

    /**
     * get all servers of the current Cluster
     */
    private void getCurrentServers() {
        this.currentClusterServers = this.currentCluster.getServers();
    }

}
