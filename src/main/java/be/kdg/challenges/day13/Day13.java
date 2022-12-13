package be.kdg.challenges.day13;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13 {
    private static final String path = "src/main/java/be/kdg/challenges/day13/test.txt";

    private final List<Pair> pairs = new ArrayList<>();

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.CYAN_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 13");
        readData();
        part1();
        part2();
    }

    private void part1() {
        ColoredPrint.println("Part 1: ");
    }

    private void part2() {
        ColoredPrint.println("Part 2: ");
    }

    private void readData() {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String left = scanner.nextLine();
                if (!left.isEmpty()) {
                    String right = scanner.nextLine();
                    pairs.add(new Pair(left.substring(1, left.length() - 1), right.substring(1, right.length() - 1)));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class Pair {
        private String left;
        private String right;

        public Pair(String left, String right) {
            this.left = left;
            this.right = right;
        }

        public boolean checkOrder() {

            return true;
        }
    }

}
