package com.example.spcalculator;

import java.util.ArrayList;
import java.util.List;

public class TargetSP {
    private static final int MAX_STAT_COUNT = 6;
    private static final int MIN_ACTIVE_STAT_COUNT = 3;
    private static final int MAX_ACTIVE_STAT_COUNT = 6;
    private static final int MAX_SP_PER_STAT = 32;
    private static final int MAX_TOTAL_SP = 66;

    private final int[] values;

    public TargetSP(int [] values) {
        validate(values);
        this.values = values.clone();
    }

    public int get(int index) {
        return values[index];
    }

    public int size() {
        return values.length;
    }

    public int[] toArray() {
        return values.clone();
    }

    public int getTotalSP() {
        int total = 0;
        for (int value : values) {
            total += value;
        }
        return total;
    }

    public int getActiveStatCount() {
        int count = 0;
        for (int value : values) {
            count++;
        }
        return count;
    }
    public int[] getActiveIndexes() {
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            if (values[i] > 0) {
                indexes.add(i);
            }
        }

        int [] result = new int[indexes.size()];
        for (int i = 0; i < indexes.size(); i++) {
            result[i] = indexes.get(i);
        }

        return result;
    }

    private void validate(int[] values) {
        if (values == null || values.length != MAX_STAT_COUNT) {
            throw new IllegalArgumentException("目標SPは6能力分入力してください。");
        }

        int total = 0;
        int activeCount = 0;

        for (int value : values) {
            if (value < 0) {
                throw new IllegalArgumentException("目標SPに負の値は入力できません。");
            }

            if (value > MAX_SP_PER_STAT) {
                throw new IllegalArgumentException("1能力の目標SP上限は32です。");
            }

            if (value > 0) {
                activeCount++;
            }

            total += value;
        }

        if (activeCount < MIN_ACTIVE_STAT_COUNT || activeCount > MAX_ACTIVE_STAT_COUNT) {
            throw new IllegalArgumentException("目標SPが1以上の能力数は3〜6にしてください。");
        }

        if (total > MAX_TOTAL_SP) {
            throw new IllegalArgumentException("目標SPの合計上限は66です。");
        }
    }
}
