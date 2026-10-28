package ru.mentee.power.devtools;

public class LiveCodingDemo {

  public static void main(String[] args) {
    System.out.println("--- FizzBuzz ---");
    printFizzBuzz(15);

    System.out.println("--- SumEven ---");
    System.out.println(sumEven(new int[]{1, 2, 3, 4, 5, 6}));

    System.out.println("--- FindMax ---");
    System.out.println(findMax(new int[]{3, 7, 2, 9, 1}));
  }

  public static void printFizzBuzz(int n) {
    for (int i = 1; i <= n; i++) {
      if (i % 3 == 0 && i % 5 == 0) {
        System.out.println("FizzBuzz");
      } else if (i % 3 == 0) {
        System.out.println("Fizz");
      } else if (i % 5 == 0) {
        System.out.println("Buzz");
      } else {
        System.out.println(i);
      }
    }
  }

  public static int sumEven(int[] numbers) {
    int sum = 0;
    for (int num : numbers) {
      if (num % 2 == 0) {
        sum += num;
      }
    }
    return sum;
  }

  public static int findMax(int[] numbers) {
    if (numbers == null || numbers.length == 0) {
      return Integer.MIN_VALUE;
    }
    int max = numbers[0];
    for (int i = 1; i < numbers.length; i++) {
      if (numbers[i] > max) {
        max = numbers[i];
      }
    }
    return max;
  }
}