package be.kdg.challenges.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day4 {
    public static void run() {
        System.out.println("\n\nDay 4:");
        part1();
        part2();
    }

    private static void part1() {
        System.out.println("Part 1:");
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day4/pairs.txt"))){
            var total = 0;
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                int[] left = splitLine(line[0]);
                int[] right = splitLine(line[1]);
                if (left[0] <= right[0] && left[1] >= right[1]) {
                    total++;
                } else if (right[0] <= left[0] && right[1] >= left[1]) {
                    total++;
                }
            }
            System.out.println(total);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void part2() {
        System.out.println("Part 2:");
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day4/pairs.txt"))){
            var total = 0;
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                int[] left = splitLine(line[0]);
                int[] right = splitLine(line[1]);
                if (left[0] <= right[0] && left[1] >= right[0]) {
                    total++;
                } else if (right[0] <= left[0] && right[1] >= left[0]) {
                    total++;
                }
            }
            System.out.println(total);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int[] splitLine(String line) {
        return new int[]{Integer.parseInt(line.split("-")[0]), Integer.parseInt(line.split("-")[1])};
    }
}
