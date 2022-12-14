package be.kdg.challenges.day13;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day13 {
    private static final String path = "src/main/java/be/kdg/challenges/day13/puzzle.txt";
    private static final String REGEX = "(\\[([\\[0-9\\]]+,?)*\\])";
    private static final Pattern pattern = Pattern.compile(REGEX);

    private final List<Pair> pairs = new ArrayList<>();


    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.CYAN_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 13");
        long start = System.currentTimeMillis();
        readData();
        part1();
        part2();
        long end = System.currentTimeMillis();
        ColoredPrint.println("Time: " + (end - start) + "ms");
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        int correct = 0;
        for (Pair pair : pairs) {
            if (pair.checkOrder() == -1) {
                correct += pairs.indexOf(pair) + 1;
            }
        }
        ColoredPrint.println(correct);
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        var list = new ArrayList<>();
        var divider2 = (List.of(List.of(2)));
        var divider6 = (List.of(List.of(6)));
        for (Pair pair : pairs) {
            list.add(pair.left);
            list.add(pair.right);
        }
        list.add(divider2);
        list.add(divider6);

        list = list.stream().sorted(Pair::checkList).collect(Collectors.toCollection(ArrayList::new));
        ColoredPrint.println((list.indexOf(divider2) + 1) * (list.indexOf(divider6) + 1));

    }

    private void readData() {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String left = scanner.nextLine();
                if (!left.isEmpty()) {
                    String right = scanner.nextLine();
                    pairs.add(new Pair(left, right));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class Pair {
        private List<Object> left;
        private List<Object> right;

        public Pair(String left, String right) {
            this.left = parseList(left);
            this.right = parseList(right);
        }

        private static List<Object> toList(Object o) {
            return o instanceof List ? (List<Object>) o : Collections.singletonList(o);
        }

        public static int checkList(Object left, Object right) {
            if (left instanceof Integer && right instanceof Integer) {
                return ((Integer) left).compareTo((Integer) right);
            }

            Iterator<Object> leftI = toList(left).iterator(), rightI = toList(right).iterator();
            while (true) {
                boolean hasNextLeft = leftI.hasNext(), hasNextRight = rightI.hasNext();
                if (!hasNextLeft && !hasNextRight) {
                    return 0;
                } else if (!hasNextLeft && hasNextRight) {
                    return -1;
                } else if (hasNextLeft && !hasNextRight) {
                    return 1;
                } else {
                    int result = checkList(leftI.next(), rightI.next());
                    if (result != 0) {
                        return result;
                    }
                }

            }
        }

        private List<Object> parseList(String s) {
            String converted = s.replace(',', ' ').replace('[', ' ').replace(']', ' ');
            Deque<List<Object>> stack = new ArrayDeque<>();
            stack.push(new ArrayList<>());
            for (int i = 1; i < s.length() - 1; i++) {
                switch (s.charAt(i)) {
                    case ',':
                        break;
                    case ']':
                        stack.pop();
                        break;
                    case '[':
                        List<Object> newList = new ArrayList<>();
                        stack.peek().add(newList);
                        stack.push(newList);
                        break;
                    default:
                        int end = converted.indexOf(' ', i + 1);
                        stack.peek().add(Integer.parseInt(converted.substring(i, end)));
                        i = end - 1;
                }
            }
            return stack.pop();
        }

        public int checkOrder() {
            return checkList(left, right);
        }
    }
}
