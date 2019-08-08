package com.company;

import java.util.ArrayList;

public interface Clusterable {
    boolean isFailed(int serverNumber, int nodeNumber);
    ArrayList<Optional<Servers>> getServers();
}
