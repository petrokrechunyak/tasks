package com.testtask.task3;

import java.math.BigInteger;

public class Task3 {

    private static final int N = 100;

    public static void main(String[] args) {

        System.out.println("Sum of the digits in " + N + "! is: " + sumOfDigitsInFactorial(N));
    }

    /**
     * Calculates the sum of the digits in the factorial of a number.
     *
     * @param n the number to calculate the factorial for
     * @return the sum of the digits in n!
     */
    public static int sumOfDigitsInFactorial(int n) {
        BigInteger factorial = calculateFactorial(n);
        return factorial.toString().length();
    }

    /**
     * Calculates the factorial of a number using BigInteger to handle large values.
     *
     * @param n the number to calculate the factorial for
     * @return the factorial of n as a BigInteger
     */
    public static BigInteger calculateFactorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

}
