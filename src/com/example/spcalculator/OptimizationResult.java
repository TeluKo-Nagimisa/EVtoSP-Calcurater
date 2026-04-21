package com.example.spcalculator;

import java.util.ArrayList;
import java.util.List;

public class OptimizationResult {
    private final int minTotalCost;
    private final List<SimulationCandidate> bestCandidates;
    private final int[] upperBounds;
    private final long checkedCount;

    public OptimizationResult(int minTotalCost,
                              List<SimulationCandidate> bestCandidates,
                              int[] upperBounds,
                              long checkedCount) {
        this.minTotalCost = minTotalCost;
        this.bestCandidates = new ArrayList<>(bestCandidates);
        this.upperBounds = upperBounds.clone();
        this.checkedCount = checkedCount;
    }

    public int getMinTotalCost() {
        return minTotalCost;
    }

    public List<SimulationCandidate> getBestCandidates() {
        return new ArrayList<>(bestCandidates);
    }

    public int[] getUpperBounds() {
        return upperBounds.clone();
    }

    public long getCheckedCount() {
        return checkedCount;
    }
}
