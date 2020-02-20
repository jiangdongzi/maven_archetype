package com.algo;

import org.junit.Test;

public class MergeStones_1000 {


    public static void main(String[] args) {
    }










    @Test
    public void testA() {
        System.out.println("abc");
    }

    public int mergeStones(int[] stones, int K) {
        int n = stones.length;

        if ((n - 1) % (K - 1) != 0) {
            return -1;
        }

        int[][][] cache = new int[n][n][K + 1];

        return 0;
    }

    private int dfs(int s, int e, int piles, int[][] stones, int[][][] cache) {
        if (cache[s][e][piles] != 0) {
            return cache[s][e][piles];
        }
        if (s == e) {
            return 0;
        }

        int res = Integer.MAX_VALUE;
        for (int i = s; i < e - piles + 1; i++) {




        }
        return 0;
    }
}
