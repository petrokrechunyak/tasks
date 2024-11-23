package com.testtask.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task1 {
    private static List<String> result;

    /**
     * Recursively generates all valid combinations of brackets given the number of open and close brackets.
     *
     * @param open    The number of open brackets remaining to be used.
     * @param close   The number of close brackets remaining to be used.
     * @param current The current string representing the partially formed brackets expression.
     **/
    public static void countBrackets(int open, int close, String current) {
        // if there are no open and close brackets left, then current result added to list.
        if (open == 0 && close == 0) {
            result.add(current);
            return;
        }

        // if there are any opening brackets, then add them
        if (open > 0) {
            countBrackets(open - 1, close, current + "(");
        }

        //add closing brackets to reach equality with opening
        if (close > open) {
            countBrackets(open, close - 1, current + ")");
        }
    }


    public static void main(String[] args) {
        int n = getIntFromConsole("Enter the number of pairs of brackets: ");

        // create result list and start calculating method
        result = new ArrayList<>();
        countBrackets(n, n, "");

        if (result.isEmpty()) {
            System.out.println("There are no brackets");
            return;
        }

        System.out.println("Number of valid parenthetical expressions: " + result.size());
        System.out.println("Valid expressions:");
        for (String expression : result) {
            System.out.println(expression);
        }
    }

    /**
     * This method offers us to enter number, and returns it in integer type
     * In case, if entered data is not number - error message will be shown, and another try to enter number will be given
     *
     * @param textToShow text, that will be shown before accessing us to enter data.
     * @return entered number but in integer type
     */
    private static int getIntFromConsole(String textToShow) {
        int number;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(textToShow);
            try {
                number = Integer.parseInt(scanner.nextLine());
                // if number is invalid, then show error message and give
                if (number < 0) {
                    System.out.println("You entered invalid number!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("You need to enter integer number!");
            }

        } while (true);
        scanner.close();
        return number;
    }


}
