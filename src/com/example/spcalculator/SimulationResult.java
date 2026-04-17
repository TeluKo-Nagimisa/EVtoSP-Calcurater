package com.example.spcalculator;

public class SimulationResult {
    private final int[] finalEv;
    private final int[] actualSP;
    private final int[] lackSP;
    private final int svCost;
    private final int cpCost;
    private final int totalCost;

    public SimulationResult(int[] finalEV, int[] actualSP, int[] lackSP, int svCost, int cpCost, int totalCost) {
        this.finalEv = finalEV;
        this.actualSP = actualSP;
        this.lackSP = lackSP;
        this.svCost = svCost;
        this.cpCost = cpCost;
        this.totalCost = totalCost;
    }

    public int[] getFinalEV() {
        return finalEv.clone();
    }

    public int[] getActualSP() {
        return actualSP.clone();
    }

    public int[] getLackSP() {
        return lackSP.clone();
    }

    public int getSVCost() {
        return svCost;
    }

    public int getCPCost() {
        return cpCost;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
