package be.kdg.challenges.day1;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day1 {
    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.BLUE_BOLD_BRIGHT);
        ColoredPrint.println("Day 1:");
        long start = System.currentTimeMillis();
        part1();
        long end = System.currentTimeMillis();
        ColoredPrint.println("Time: " + (end - start) + "ms");
    }

    private void part1() {
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day1/calories.txt"))) {
            List<Elf> elves = new ArrayList<>();
            Elf elf = new Elf();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) {
                    if (elf.calories != 0)
                        elves.add(elf);
                    elf = new Elf();
                } else
                    elf.addCalories(Integer.parseInt(line));
            }
            ColoredPrint.print("Part 1: ");
            ColoredPrint.println(elves.stream().max(Comparator.comparingInt(o -> o.calories)).get().calories);
            ColoredPrint.print("Part 2: ");
            int calories = 0;
            elves = elves.stream().sorted(Comparator.comparingInt(Elf::getCalories)).toList();
            for (int i = elves.size() - 1; i >= elves.size() - 3; i--)
                calories += elves.get(i).calories;
            ColoredPrint.println(calories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Elf {
        private int calories;

        public Elf() {
            this.calories = 0;
        }

        public int getCalories() {
            return calories;
        }

        public void addCalories(int calrories) {
            this.calories += calrories;
        }
    }
}
