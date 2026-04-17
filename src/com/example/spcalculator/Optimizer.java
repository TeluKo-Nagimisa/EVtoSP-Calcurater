package com.example.spcalculator;

import java.util.ArrayList;
import java.util.List;

public class Optimizer {
    private final EVSimulator simulator;

    public Optimizer() {
        this.simulator = new EVSimulator();
    }

    public OptimizationResult optimize(TargetSP targetSP) {
        List<SimulationCandidate> bestCandidates = new ArrayList<>();
        int minTotalCost = Integer.MAX_VALUE;

        List<int[]> allOrders = generateAllOrders();

        for (int ev1 = 0; ev1 <= 60; ev1 += 10) {
            for (int ev2 = 0; ev2 <= 60; ev2 += 10) {
                for (int ev3 = 0; ev3 <= 60; ev3 += 10) {
                    for (int ev4 = 0; ev4 <= 60; ev4 += 10) {
                        int[] requestedEV = {ev1, ev2, ev3, ev4};

                        for (int[] order : allOrders) {
                            SimulationResult result = simulator.simulate(targetSP, requestedEV, order);
                            int totalCost = result.getTotalCost();

                            SimulationCandidate candidate = new SimulationCandidate(requestedEV, order, result);

                            if (totalCost < minTotalCost) {
                                minTotalCost = totalCost;
                                bestCandidates.clear();
                                bestCandidates.add(candidate);
                            } else if (totalCost == minTotalCost) {
                                bestCandidates.add(candidate);
                            }
                        }
                    }
                }
            }
        }

        return new OptimizationResult(minTotalCost, bestCandidates);
    }

    private List<int[]> generateAllOrders() {
        List<int[]> orders = new ArrayList<>();
        int[] current = {0,1,2,3};
        permute(current, 0, orders);
        return orders;
    }

    private void permute(int[] array, int start, List<int[]> result) {
        if (start == array.length) {
            result.add(array.clone());
            return;
        }

        for (int i = start; i < array.length; i++) {
            swap(array, start, i);
            permute(array, start + 1, result);
            swap(array, start, i);
        }
    }

    private void  swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
