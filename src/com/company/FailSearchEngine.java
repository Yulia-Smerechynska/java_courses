package com.company;

import java.util.Arrays;

public class FailSearchEngine {

    private Clusterable currentCluster;
    private Servers[] currentClusterServers;
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
        int rightServer = this.currentClusterServers.length - 1;
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
            System.out.println("Failed Node: " + this.failedNodeNumber);
        }
    }

    /**
     * find filed node
     *
     * @return boolean
     */
    private boolean findFailNode(int middleServer) {
        this.isCurrentServerHasFailedNodes = false;
        this.currentServer = this.currentClusterServers[middleServer];
        Node[] currentServerNodes = this.currentServer.getAllNodes();

        int leftBound = 0;
        int rightBound = currentServerNodes.length - 1;

        while (!this.failedNode) {
            if (rightBound - leftBound > 1) {

                int middleValue = (rightBound + leftBound) / 2;
                Node middleNode = currentServerNodes[middleValue];
                boolean isFailed = this.currentCluster.isFailed(this.currentServer.getNumber(), middleNode.getNumber());

                if (isFailed) {
                    this.isCurrentServerHasFailedNodes = true;
                    boolean isPrevNodeActive = this.isPrevNodeActive(this.currentServer.getNumber(), middleNode.getNumber());
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

        boolean isFirstFailed = this.currentCluster.isFailed(this.currentServer.getNumber(), leftBound);
        boolean isSecondFailed = this.currentCluster.isFailed(this.currentServer.getNumber(), rightBound);

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
            return this.isPrevNodeActive(this.currentServer.getNumber(), leftBound);
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
            Servers previousServer = this.getPreviousServer(serverNumber);
            Node[] prevServerNodes = previousServer.getAllNodes();
            Node lastNode = prevServerNodes[prevServerNodes.length - 1];
            if (lastNode.getStatus().equals(Node.ACTIVE)) {
                this.setFailedNodeNumber(nodeNumber);
                return true;
            } else {
                return false;
            }
        } else {
            int prevNodeNumber = nodeNumber - 1;
            if (this.currentServer.getAllNodes()[prevNodeNumber].getStatus().equals(Node.ACTIVE)) {
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
    private Servers getPreviousServer(int serverNumber) {
        serverNumber = serverNumber > 1 ? serverNumber - 1 : 0;
        return this.currentClusterServers[serverNumber];
    }

    /**
     * get all servers of the current Cluster
     */
    private void getCurrentServers() {
        this.currentClusterServers = this.currentCluster.getServers();
    }

}
