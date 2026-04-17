package com.example.spcalculator;

import java.util.ArrayList;
import java.util.List;

public class OptimizationResult {
    private final int minTotalCost;
    private final List<SimulationCandidate> bestCandidates;

    public OptimizationResult(int minTotalCost, List<SimulationCandidate> bestCandidates) {
        this.minTotalCost = minTotalCost;
        this.bestCandidates = new ArrayList<>(bestCandidates);
    }

    public int getMinTotalCost() {
        return minTotalCost;
    }

    public List<SimulationCandidate> getBestCandidates() {
        return new ArrayList<>(bestCandidates);
    }
}
