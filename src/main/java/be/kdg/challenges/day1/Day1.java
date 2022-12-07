package be.kdg.challenges.day1;

import java.io.File;
import java.util.*;

public class Day1 {
    public static void run() {
        System.out.println("Day 1:");
        part1();
    }

    private static void part1() {
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
            System.out.println("Part 1:");
            System.out.println(elves.stream().max(Comparator.comparingInt(o -> o.calories)).get().calories);
            System.out.println("Part 2:");
            int calories = 0;
            elves = elves.stream().sorted(Comparator.comparingInt(Elf::getCalories)).toList();
            for (int i = elves.size() - 1; i >= elves.size() - 3; i--)
                calories += elves.get(i).calories;
            System.out.println(calories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Elf {
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
