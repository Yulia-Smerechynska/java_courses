package com.company;

public class Servers implements Countable {
    private int number;
    private Node[] nodes;

    public Servers(int internalNumber, int countOfNodes) {
        this.number = internalNumber;
        this.nodes = new Node[countOfNodes];

        for (int i = 0; i < countOfNodes; i++) {
            this.nodes[i] = new Node(i);
        }
    }

    /**
     * get number of current server
     * @return int
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * get all server nodes
     * @return Node[]
     */
    public Node[] getAllNodes() {
        return this.nodes;
    }
}
