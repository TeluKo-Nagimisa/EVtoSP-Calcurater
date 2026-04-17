package com.example.spcalculator;

public class TargetSP {
    private final int[] values;

    public TargetSP(int a, int b, int c, int d) {
        this.values = new int[]{a, b, c, d};
    }

    public int get(int index) {
        return values[index];
    }

    public int[] toArray() {
        return values.clone();
    }
}
