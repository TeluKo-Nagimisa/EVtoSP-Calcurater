package com.example.spcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 目標SPを4つ入力してください ===");
        int target1 = scanner.nextInt();
        int target2 = scanner.nextInt();
        int target3 = scanner.nextInt();
        int target4 = scanner.nextInt();

        TargetSP targetSP = new TargetSP(target1, target2, target3, target4);

        Optimizer optimizer = new Optimizer();
        OptimizationResult optimizationResult = optimizer.optimize(targetSP);

        System.out.println();
        System.out.println("=== 最適化結果 ===");
        System.out.println("最小総コスト: " + optimizationResult.getMinTotalCost());

        List<SimulationCandidate> bestCandidates = optimizationResult.getBestCandidates();

        for (int i = 0; i < bestCandidates.size(); i++) {
            SimulationCandidate candidate = bestCandidates.get(i);
            SimulationResult result = candidate.getSimulationResult();

            System.out.println();
            System.out.println("【最適候補 " + (i + 1) + "】");
            System.out.println("目標SP: " + Arrays.toString(targetSP.toArray()));
            System.out.println("投入量: " + Arrays.toString(candidate.getRequestedEV()));
            System.out.println("投入順: " + formatOrder(candidate.getOrder()));
            System.out.println("最終EV: " + Arrays.toString(result.getFinalEV()));
            System.out.println("換算SP: " + Arrays.toString(result.getActualSP()));
            System.out.println("不足SP: " + Arrays.toString(result.getLackSP()));
            System.out.println("SV側コスト: " + result.getSVCost());
            System.out.println("CP側コスト: " + result.getCPCost());
            System.out.println("総コスト: " + result.getTotalCost());
        }

        scanner.close();
    }

    private static String formatOrder(int[] order) {
        int[] displayOrder = new int[order.length];
        for (int i = 0; i < order.length; i++) {
            displayOrder[i] = order[i] + 1;
        }
        return Arrays.toString(displayOrder);
    }
}
