package be.kdg.challenges.day4;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day4 {
    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.GREEN_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 4:");
        long start = System.currentTimeMillis();
        part1();
        part2();
        long end = System.currentTimeMillis();
        ColoredPrint.println("Time: " + (end - start) + "ms");
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
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
            ColoredPrint.println(total);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
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
            ColoredPrint.println(total);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int[] splitLine(String line) {
        return new int[]{Integer.parseInt(line.split("-")[0]), Integer.parseInt(line.split("-")[1])};
    }
}
