package be.kdg.challenges.day11;

import be.kdg.util.ColoredPrint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {
    private static final List<Monkey> monkeys = new ArrayList<>();

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.GREEN_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 11");
        readData();
        part1();
        readData();
        part2();
    }

    private void readData() {
        monkeys.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/be/kdg/challenges/day11/puzzle.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                int id = Integer.parseInt(line.replaceAll(":", "").split(" ")[1]);
                line = reader.readLine().trim();
                List<Item> items = Arrays.stream(line.split(":")[1].trim().split(",")).map(i -> new Item(Integer.parseInt(i.trim()))).collect(Collectors.toList());
                line = reader.readLine().trim();
                char operation = line.split("=")[1].trim().split(" ")[1].charAt(0);
                Operation op;
                switch (operation) {
                    case '*' -> op = Operation.MULTIPLY;
                    case '+' -> op = Operation.ADD;
                    case '-' -> op = Operation.SUBTRACT;
                    default -> op = Operation.DIVIDE;
                }
                int opNumber;
                if (line.split("=")[1].trim().split(" ")[2].equals("old")) opNumber = -1;
                else
                    opNumber = Integer.parseInt(line.split("=")[1].trim().split(" ")[2]);
                int divide = Integer.parseInt(reader.readLine().trim().split(" by ")[1]);

                int monkeyTrue = Integer.parseInt(reader.readLine().trim().split(" monkey ")[1]);
                int monkeyFalse = Integer.parseInt(reader.readLine().trim().split(" monkey ")[1]);
                monkeys.add(new Monkey(id, items, op, opNumber, divide, monkeyTrue, monkeyFalse));
                reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        monkeyBusiness(1, 20);
        monkeys.sort((m1, m2) -> m2.getItemsInspected() - m1.getItemsInspected());
        ColoredPrint.println(monkeys.get(0).itemsInspected * monkeys.get(1).getItemsInspected());
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        monkeyBusiness(2, 10000);
        monkeys.sort((m1, m2) -> m2.getItemsInspected() - m1.getItemsInspected());
        ColoredPrint.println(String.valueOf(BigInteger.valueOf(monkeys.get(0).itemsInspected).multiply(BigInteger.valueOf(monkeys.get(1).getItemsInspected()))));
    }

    private void monkeyBusiness(int part, int rounds) {
        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys) {
                int items = monkey.getItems().size();
                for (int j = 0; j < items; j++) {
                    int id = monkey.throwItem(part);
                    monkeys.stream().filter(m -> m.getId() == id).findFirst().get().getItems().add(monkey.getItems().remove(0));
                }
            }
        }
    }

    public enum Operation {
        MULTIPLY, ADD, SUBTRACT, DIVIDE;
    }

    public class Monkey {
        private final int testOperationNumber;
        private final int id;
        private final List<Item> items;
        private final Operation operation;
        private final int operationNumber;
        private final int monkeyOnTestSuccess;
        private final int monkeyOnTestFail;
        private int itemsInspected = 0;

        public Monkey(int id, List<Item> items, Operation operation, int operationNumber, int testOperationNumber, int monkeyOnTestSuccess, int monkeyOnTestFail) {
            this.id = id;
            this.items = items;
            this.operation = operation;
            this.operationNumber = operationNumber;
            this.testOperationNumber = testOperationNumber;
            this.monkeyOnTestSuccess = monkeyOnTestSuccess;
            this.monkeyOnTestFail = monkeyOnTestFail;
        }

        public int throwItem(int part) {
            itemsInspected++;
            items.get(0).operate(operation, operationNumber);
            if (part == 1)
                items.get(0).relieve();
            if (items.get(0).getWorryLevel() % testOperationNumber == 0)
                return monkeyOnTestSuccess;
            return monkeyOnTestFail;
        }

        public int getTestOperationNumber() {
            return testOperationNumber;
        }

        @Override
        public String toString() {
            return String.format("Monkey %d: ", id) +
                    items.stream().map(i -> String.valueOf(i.getWorryLevel())).collect(Collectors.joining(","));
        }

        public String itemsInspected() {
            return String.format("Monkey %d inspected %d items", id, itemsInspected);
        }

        public int getItemsInspected() {
            return itemsInspected;
        }

        public List<Item> getItems() {
            return items;
        }

        public int getId() {
            return id;
        }
    }

    public class Item {
        private long worryLevel;

        public Item(int worryLevel) {
            this.worryLevel = worryLevel;
        }

        public void relieve() {
            worryLevel = worryLevel / 3;
        }

        public void operate(Operation operation, long number) {
            if (number == -1)
                number = worryLevel;
            switch (operation) {
                case MULTIPLY -> worryLevel = worryLevel * number;
                case ADD -> worryLevel = worryLevel + number;
                case SUBTRACT -> worryLevel = worryLevel - number;
                case DIVIDE -> worryLevel = worryLevel / number;
            }
            int div = 1;
            for (Monkey m : monkeys) {
                div *= m.getTestOperationNumber();
            }
            worryLevel = worryLevel % div;
        }

        public long getWorryLevel() {
            return worryLevel;
        }
    }
}
