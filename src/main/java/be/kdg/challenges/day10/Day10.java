package be.kdg.challenges.day10;

import be.kdg.util.ColoredPrint;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day10 {
    private List<String> data = new ArrayList<>();
    private final List<Integer> cycles = List.of(20, 60, 100, 140, 180, 220);
    private final List<Integer> CRTs = List.of(40,80, 120, 160, 200, 240);

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.WHITE_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 10");
        readData();
        part1();
        part2();
    }

    private void readData() {
        try {
            data = FileUtils.readLines(new File("src/main/java/be/kdg/challenges/day10/puzzle.txt"), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        List<Operation> operations = new ArrayList<>();
        int signals = 0;
        int x = 1;
        for (int i = 0; i < data.size() || operations.size() > 0; i++) {
            if (i < data.size()) {
                String s = data.get(i).split(" ")[0];
                if (s.equals("noop"))
                    operations.add(new Operation(1, 0));
                else
                    operations.add(new Operation(2, Integer.parseInt(data.get(i).split(" ")[1])));
            }
            if (cycles.contains(i + 1))
                signals += x * (i + 1);
            operations.get(0).decreaseCyclesLeft();
            if (operations.get(0).getCyclesLeft() == 0)
                x += operations.remove(0).getStrength();
        }
        System.out.println(signals);
    }

    private void part2() {
        ColoredPrint.println("Part 2: ");
        List<Operation> operations = new ArrayList<>();
        int x = 1;
        for (int i = 0; i < data.size() || operations.size() > 0; i++) {
            if (i < data.size()) {
                String s = data.get(i).split(" ")[0];
                if (s.equals("noop"))
                    operations.add(new Operation(1, 0));
                else
                    operations.add(new Operation(2, Integer.parseInt(data.get(i).split(" ")[1])));
            }
            if (CRTs.contains(i))
                System.out.println();

            if (x-1 == i%40 || x== i%40 || x+1 ==i%40 )
                ColoredPrint.print("|");
            else
                ColoredPrint.print(" ");
            operations.get(0).decreaseCyclesLeft();
            if (operations.get(0).getCyclesLeft() == 0)
                x += operations.remove(0).getStrength();

        }
    }

    public static class Operation {
        private int cyclesLeft;
        private final int strength;

        public Operation(int cyclesLeft, int strength) {
            this.cyclesLeft = cyclesLeft;
            this.strength = strength;
        }

        public int getCyclesLeft() {
            return cyclesLeft;
        }

        public int getStrength() {
            return strength;
        }

        public void decreaseCyclesLeft() {
            cyclesLeft--;
        }
    }


}
