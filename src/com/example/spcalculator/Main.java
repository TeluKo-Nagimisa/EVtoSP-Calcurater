package com.example.spcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 目標SPを6つ入力してください ===");
        System.out.println("順番: H A B C D S");
        System.out.println("例: 1 32 1 32 0 0");

        int[] targetValues = new int[6];

        for (int i = 0; i < targetValues.length; i++){
            targetValues[i] = scanner.nextInt();
        }

        TargetSP targetSP;

        try {
            targetSP = new TargetSP(targetValues);
        } catch (IllegalArgumentException e) {
            System.out.println("入力エラー：" + e.getMessage());
            scanner.close();
            return;
        }

        System.out.println();
        System.out.println("=== 入力確認 ===");
        System.out.println("目標SP: " + Arrays.toString(targetSP.toArray()));
        System.out.println("目標SP合計: " + targetSP.getTotalSP());
        System.out.println("有効能力数: " + targetSP.getActiveStatCount());
        System.out.println("有効能力index: " + Arrays.toString(targetSP.getActiveIndexes()));

        Optimizer optimizer = new Optimizer();
        OptimizationResult optimizationResult = optimizer.optimize(targetSP);

        System.out.println();
        System.out.println("=== 最適化結果 ===");
        System.out.println("探索上限EV: " + Arrays.toString(optimizationResult.getUpperBounds()));
        System.out.println("探索件数: " + optimizationResult.getCheckedCount());
        System.out.println("最小総コスト: " + optimizationResult.getMinTotalCost());

        List<SimulationCandidate> bestCandidates = optimizationResult.getBestCandidates();

        int displayLimit = Math.min(bestCandidates.size(), 1);

        System.out.println("最適候補数: " + bestCandidates.size());
        System.out.println("表示件数: " + displayLimit);

        for (int i = 0; i < displayLimit; i++) {
            SimulationCandidate candidate = bestCandidates.get(i);
            SimulationResult result = candidate.getSimulationResult();

            System.out.println();
            System.out.println("【最適候補 " + (i + 1) + "】");
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
