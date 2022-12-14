package be.kdg.challenges.day5;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Day5 {
    private List<String> moves;
    private HashMap<Integer, ArrayList<String>> stacks;

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.PURPLE_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 5:");
        long start = System.currentTimeMillis();
        readInput();
        part1();
        readInput();
        part2();
        long end = System.currentTimeMillis();
        ColoredPrint.println("Time: " + (end - start) + "ms");
    }

    private void readInput() {
        stacks = new HashMap<>();
        moves = new ArrayList<>();
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day5/crates.txt"))) {
            boolean movesB = false;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!movesB && line.charAt(0) == ' ') {
                    movesB = true;
                    line = sc.nextLine();
                }
                if (!movesB) {
                    int index = 1;
                    for (int i = 0; i < line.length() + 1; i += 4) {
                        String box;
                        if (line.length() >= i + 4) box = line.substring(i, i + 4);
                        else box = line.substring(i);
                        if (!box.isBlank()) {
                            box = box.trim().replaceAll("[\\[\\]]", "");
                            if (stacks.containsKey(index)) stacks.get(index).add(box);
                            else {
                                var list = new ArrayList<String>();
                                list.add(box);
                                stacks.put(index, list);
                            }

                        }
                        index++;
                    }
                } else moves.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        for (String line : moves) {
            if (!line.isBlank()) {
                String[] split = line.split(" ");
                int count = Integer.parseInt(split[1]);
                int from = Integer.parseInt(split[3]);
                int to = Integer.parseInt(split[5]);
                for (int i = 0; i < count; i++) {
                    var crate = stacks.get(from).get(0);
                    stacks.get(from).remove(0);
                    stacks.get(to).add(0, crate);
                }
            }
        }
        printTopCrates();
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        for (String line : moves) {
            if (!line.isBlank()) {
                String[] split = line.split(" ");
                int count = Integer.parseInt(split[1]);
                int from = Integer.parseInt(split[3]);
                int to = Integer.parseInt(split[5]);
                for (int i = 0; i < count; i++) {
                    var crate = stacks.get(from).get(0);
                    stacks.get(from).remove(0);
                    stacks.get(to).add(i, crate);
                }
            }
        }
        printTopCrates();
    }

    private void printTopCrates() {
        var sb = new StringBuilder();
        for (int i = 1; i <= stacks.size(); i++) {
            sb.append(stacks.get(i).get(0));
        }
        ColoredPrint.println(sb.toString());
    }
}
