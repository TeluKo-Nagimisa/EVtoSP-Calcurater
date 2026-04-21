package com.example.spcalculator;

import java.util.ArrayList;
import java.util.List;

public class Optimizer {

    private static final int MAX_REQUESTED_EV = 260;
    private static final int STEP_EV = 10;

    private final EVSimulator simulator;

    public Optimizer() {
        this.simulator = new EVSimulator();
    }

    public OptimizationResult optimize(TargetSP targetSP) {
        List<SimulationCandidate> bestCandidates = new ArrayList<>();
        int minTotalCost = Integer.MAX_VALUE;

        int[] activeIndexes = targetSP.getActiveIndexes();
        int[] upperBounds = calculateUpperBounds(targetSP, activeIndexes);

        List<int[]> allOrders = generateAllOrders(activeIndexes.length);

        List<int[]> requestedEVPatterns = new ArrayList<>();
        generateRequestedEVPatterns(upperBounds, 0, new int[activeIndexes.length], requestedEVPatterns);

        long checkedCount = 0;

        for (int[] activeRequestedEV : requestedEVPatterns) {

            int[] requestedEV = expendToSixStats(activeRequestedEV, activeIndexes);

            for (int[] activeOrder : allOrders) {
                int[] order = convertActiveOrderToActualOrder(activeOrder, activeIndexes);

                checkedCount++;

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

        return new OptimizationResult(minTotalCost, bestCandidates, upperBounds, checkedCount);
    }

    private int[] calculateUpperBounds(TargetSP targetSP, int[] activeIndexes) {
        int[] upperBounds = new int[activeIndexes.length];

        for (int i = 0; i < activeIndexes.length; i++) {
            int statIndex = activeIndexes[i];
            upperBounds[i] = calculateUpperRequestedEV(targetSP.get(statIndex));
        }

        return upperBounds;
    }

    private int calculateUpperRequestedEV(int targetSP) {
        if (targetSP <= 0) {
            return 0;
        }

        int evMin = targetSP * 8 - 4;
        int upper = ((evMin + 9) / 10) * 10;

        return Math.min(upper, 260);
    }

    private void generateRequestedEVPatterns(int[] upperBounds, int depth, int[] current, List<int[]> result) {
        if (depth == upperBounds.length) {
            result.add(current.clone());
            return;
        }

        for (int ev = 0; ev <= upperBounds[depth]; ev += STEP_EV) {
            current[depth] = ev;
            generateRequestedEVPatterns(upperBounds, depth + 1, current, result);
        }
    }

    private List<int[]> generateAllOrders(int size) {
        List<int[]> orders = new ArrayList<>();

        int[] current = new int[size];
        for (int i = 0; i < size; i++) {
            current[i] = i;
        }

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

    private int[] expendToSixStats(int [] activeRequestedEV, int[] activeIndexes) {
        int[] requestedEV = new int[6];

        for (int i = 0; i < activeIndexes.length; i++){
            requestedEV[activeIndexes[i]] = activeRequestedEV[i];
        }

        return  requestedEV;
    }

    private int[] convertActiveOrderToActualOrder(int[] activeOrder, int[] activeIndexes) {
        int[] actualOrder = new int[activeOrder.length];

        for (int i = 0; i < activeOrder.length; i++) {
            actualOrder[i] = activeIndexes[activeOrder[i]];
        }

        return actualOrder;
    }
}
