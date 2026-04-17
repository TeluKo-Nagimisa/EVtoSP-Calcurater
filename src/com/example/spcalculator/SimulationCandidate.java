package com.example.spcalculator;

public class SimulationCandidate {
    private final int[] requestedEV;
    private final int[] order;
    private final SimulationResult simulationResult;

    public SimulationCandidate(int[] requestedEV, int[] order, SimulationResult simulationResult) {
        this.requestedEV = requestedEV.clone();
        this.order = order.clone();
        this.simulationResult = simulationResult;
    }

    public int[] getRequestedEV() {
        return requestedEV.clone();
    }

    public int[] getOrder() {
        return order.clone();
    }

    public SimulationResult getSimulationResult() {
        return simulationResult;
    }
}
