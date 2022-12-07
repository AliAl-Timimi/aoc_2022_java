package be.kdg.challenges.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day3 {

    public static void run() {
        System.out.println("\n\nDay 3:");
        part1();
        part2();
    }

    private static void part1() {
        System.out.println("Part 1:");
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day3/rucksacks.txt"))) {
            var total = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] lineSplit = {line.substring(0, line.length() / 2), line.substring(line.length() / 2)};
                total += findSameChars(lineSplit[0].toCharArray(), lineSplit[1].toCharArray());
            }
            System.out.println(total);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int findSameChars(char[] left, char[] right) {
        for (char c : left) {
            for (char cc : right) {
                if (c == cc) {
                    return getPriority(c);
                }
            }
        }
        return 0;
    }

    private static int getPriority(char c) {
        if (c >= 'a' && c <= 'z') return c - 96;
        else if (c >= 'A' && c <= 'Z') return (c - 64 + 26);
        return 0;
    }


    private static void part2() {
        System.out.println("Part 2:");
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day3/rucksacks.txt"))) {
            var total = 0;
            while (sc.hasNextLine()) {
                char[] line1 = sc.nextLine().toCharArray();
                char[] line2 = sc.nextLine().toCharArray();
                char[] line3 = sc.nextLine().toCharArray();
                total += findSameCharIn3(line1, line2, line3);
            }
            System.out.println(total);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int findSameCharIn3(char[] first, char[] second, char[] third) {
        for (char c : first) {
            for (char cc : second) {
                if (c == cc) {
                    for (char ccc : third) {
                        if (c == ccc) {
                            return getPriority(c);
                        }
                    }
                }
            }
        }
        return 0;
    }
}
