package com.company;

public class Main {

    public static void main(String[] args) {
        Cluster cluster = new Cluster(10);
        cluster.sendMessage();
        FailSearchEngine failSearchEngine = new FailSearchEngine(cluster);
        failSearchEngine.search();
    }
}
