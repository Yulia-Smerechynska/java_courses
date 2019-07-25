package com.company;

import java.util.Arrays;

public class FailSearchEngine {

    private Clusterable currentCluster;
    private Servers[] currentClusterServers;
    private Servers currentServer;
    private Node[] allServerNodes;
    private boolean isSetNodeNumber = false;
    private int currentNodeNumber;
    private boolean failedNode = false;
    private int middleServerIndex;

    public FailSearchEngine(Clusterable cluster) {
        this.currentCluster = cluster;
        this.getCurrentServers();
    }

    public void search() {
        do {
            this.findFailNode();
        } while (!this.failedNode);
        System.out.println("Failed Server: " + this.currentServer.getNumber());
        System.out.println("Failed Node: " + this.currentNodeNumber);
    }

    /**
     * find filed node
     * @return boolean
     */
    private boolean findFailNode() {
        this.getCurrentServer();
        this.getAllNodes();

        int leftBound = 0;
        int rightBound = this.allServerNodes.length - 1;
        int step = 0;
        while (!this.failedNode && step < this.allServerNodes.length) {
            step++;
            int middleValue = (rightBound + leftBound) / 2;
            Node middleNode = this.allServerNodes[middleValue];
            boolean result = this.checkNode(this.currentServer.getNumber(), middleNode.getNumber());
            if (result) {
                if (middleValue != 0) {
                    Node prevNode = this.allServerNodes[middleValue - 1];
                    if (!this.checkNode(this.currentServer.getNumber(), prevNode.getNumber())) {
                        this.failedNode = true;
                        this.setFailedNodeNumber(middleNode.getNumber());
                        break;
                    } else {
                        rightBound = prevNode.getNumber();
                    }
                } else {
                    if (this.currentServer.getNumber() != 0) {
                        Servers prevServer = this.currentCluster.getServers()[this.currentServer.getNumber() - 1];
                        Node[] prevServerNodes = prevServer.getAllNodes();
                        if (!this.checkNode(prevServer.getNumber(), prevServerNodes[prevServerNodes.length - 1].getNumber())) {
                            this.failedNode = true;
                            this.setFailedNodeNumber(middleNode.getNumber());
                            break;
                        }
                    }
                }
            } else {
                leftBound = middleValue + 1;
            }
        }

        if (this.failedNode) {
            return true;
        } else {
            this.cutServersFrom();
            this.findFailNode();
            return false;
        }
    }

    /**
     * check if node is fail
     * @param serverNumber int
     * @param nodeNumber int
     * @return boolean
     */
    private boolean checkNode(int serverNumber, int nodeNumber) {
        if (this.currentCluster.isFailed(serverNumber, nodeNumber)) {
            if (nodeNumber != 0) {
                return true;
            } else {
                Servers previousServer = this.getPreviousServer();
                Node[] prevServerNodes = previousServer.getAllNodes();

                if (this.currentServer.getNumber() == 0) {
                    this.failedNode = true;
                    this.setFailedNodeNumber(nodeNumber);
                    return true;
                }

                Node lastNode = prevServerNodes[prevServerNodes.length - 1];
                if (lastNode.getStatus().equals(Node.ACTIVE)) {
                    this.failedNode = true;
                    this.setFailedNodeNumber(nodeNumber);
                    return true;
                } else {
                    this.cutServersTo();
                    this.findFailNode();
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * set failed node number
     * @param nodeNumber int
     */
    private void setFailedNodeNumber(int nodeNumber) {
        if (!this.isSetNodeNumber) {
            this.isSetNodeNumber = true;
            this.currentNodeNumber = nodeNumber;
        }
    }

    /**
     * copy range of the servers array elements
     */
    private void cutServersTo() {
        this.middleServerIndex = this.middleServerIndex == 1 ? this.middleServerIndex : this.middleServerIndex + 1;
        this.currentClusterServers = Arrays.copyOfRange(this.currentClusterServers, 0, this.middleServerIndex);
    }

    /**
     * copy range of the servers array elements
     */
    private void cutServersFrom() {
        this.middleServerIndex = this.middleServerIndex == 1 ? this.middleServerIndex : this.middleServerIndex - 1;
        this.currentClusterServers = Arrays.copyOfRange(this.currentClusterServers, this.middleServerIndex, this.currentClusterServers.length);
    }

    /**
     * get all nodes of the current server
     */
    private void getAllNodes() {
        this.allServerNodes = currentServer.getAllNodes();
    }

    /**
     * get current server
     */
    private void getCurrentServer() {
        this.getMiddleElement();
        this.currentServer = this.currentClusterServers[this.middleServerIndex];
    }

    /**
     * get previous server
     * @return Servers
     */
    private Servers getPreviousServer() {
        this.middleServerIndex = this.middleServerIndex > 1 ? this.middleServerIndex - 1 : 0;
        return this.currentClusterServers[this.middleServerIndex];
    }

    /**
     * get middle element index of the current cluster servers
     */
    private void getMiddleElement() {
        this.middleServerIndex = Math.round(this.currentClusterServers.length / 2);
    }

    /**
     * get all servers of the current Cluster
     */
    private void getCurrentServers() {
        this.currentClusterServers = this.currentCluster.getServers();
    }

}
