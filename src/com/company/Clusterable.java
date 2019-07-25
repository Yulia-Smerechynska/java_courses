package com.company;

public interface Clusterable {
    boolean isFailed(int serverNumber, int nodeNumber);
    Servers[] getServers();
}
