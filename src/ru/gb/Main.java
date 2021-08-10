package ru.gb;

import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    static float[] arr = new float[SIZE];

    public static void main(String[] args) {
        float[] arr1 = new float[HALF];
        Thread tr1 = new Thread(() -> {
            Arrays.fill(arr, 1);
            System.arraycopy(arr, 0, arr1, 0, HALF);
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        tr1.start();

        try {
            testTwoThreads();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testOneThread();
    }

    private static void testTwoThreads() throws InterruptedException {
        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        Thread tr1 = new Thread(() -> {

            System.arraycopy(arr, 0, arr1, 0, HALF);
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        tr1.start();
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        for (int i = 0; i < arr2.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) * Math.cos(0.4f + i + (HALF) / 2));
        }

        tr1.join();
        System.arraycopy(arr1, 0, arr, 0, arr1.length);
        System.arraycopy(arr2, 0, arr, HALF, arr1.length);

        System.out.println("Метод с двумя потоками был в работе " + (System.currentTimeMillis() - a));
    }


    private static void testOneThread() {
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Метод с одним потоком был в работе " + (System.currentTimeMillis() - a));
    }
}
