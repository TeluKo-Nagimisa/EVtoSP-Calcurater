package com.example.spcalculator;

public class EVSimulator {
    private static final int MAX_EV_PER_STAT = 252;
    private static final int MAX_TOTAL_EV = 510;

    public SimulationResult simulate(TargetSP targetSP, int[] requestedEV, int[] order) {
        int[] finalEV = new int[4];
        int remainingTotalEV = MAX_TOTAL_EV;

        for (int index : order) {
            int requested = requestedEV[index];

            int addableByStat = MAX_EV_PER_STAT - finalEV[index];
            int actualAdd = Math.min(requested, Math.min(addableByStat, remainingTotalEV));

            finalEV[index] += actualAdd;
            remainingTotalEV -= actualAdd;
        }

        int[] actualSP = new int[4];
        int[] lackSP = new int[4];

        for (int i = 0; i < 4; i++) {
            actualSP[i] = convertEVtoSP(finalEV[i]);
            lackSP[i] = Math.max(targetSP.get(i) - actualSP[i], 0);
        }

        int svCost = 0;
        for (int value : requestedEV) {
            svCost += value / 10;
        }

        int cpCost = 0;
        for (int value : lackSP) {
            cpCost += value * 10;
        }

        int totalCost = svCost + cpCost;

        return new SimulationResult(finalEV, actualSP, lackSP, svCost, cpCost, totalCost);
    }

    private int convertEVtoSP(int ev) {
        return (ev + 4) / 8;
    }
}
