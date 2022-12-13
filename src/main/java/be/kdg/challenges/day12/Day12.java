package be.kdg.challenges.day12;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12 {
    private static final String path = "src/main/java/be/kdg/challenges/day12/puzzle.txt";
    private final List<Cell> cells = new ArrayList<>();

    private int rows = 0;
    private int columns = 0;

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.PURPLE_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 12");
        readData();
        part1();
        part2();
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        var queue = new ArrayDeque<Cell>();
        var visited = new boolean[rows][columns];
        var parent = new Cell[rows][columns];
        var found = false;
        var shortestPath = 0;
        var start = cells.stream().filter(Cell::isStart).findFirst().get();
        var end = cells.stream().filter(Cell::isEnd).findFirst().get();
        queue.add(start);
        visited[start.getX()][start.getY()] = true;
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.getX() == end.getX() && current.getY() == end.getY()) {
                found = true;
                break;
            }
            var neighbors = getOption(current);
            for (var neighbor : neighbors) {
                if (!visited[neighbor.getX()][neighbor.getY()]) {
                    queue.add(neighbor);
                    visited[neighbor.getX()][neighbor.getY()] = true;
                    parent[neighbor.getX()][neighbor.getY()] = current;
                }
            }
        }
        if (found) {
            var current = new Cell(end.getX(), end.getY(), end.getHeight());
            while (current.getX() != start.getX() || current.getY() != start.getY()) {
                shortestPath++;
                current = parent[current.getX()][current.getY()];
            }
        }
        ColoredPrint.println(shortestPath);
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        var shortest = Integer.MAX_VALUE;
        var starts = cells.stream().filter(c -> c.getHeight() == 'a' && getOption(c).stream().anyMatch(cell -> cell.getHeight() == 'b')).toList();
        for (Cell cell : starts) {
            var queue = new ArrayDeque<Cell>();
            var visited = new boolean[rows][columns];
            var parent = new Cell[rows][columns];
            var found = false;
            var shortestPath = 0;
            var end = cells.stream().filter(Cell::isEnd).findFirst().get();
            queue.add(cell);
            visited[cell.getX()][cell.getY()] = true;
            while (!queue.isEmpty()) {
                var current = queue.poll();
                if (current.getX() == end.getX() && current.getY() == end.getY()) {
                    found = true;
                    break;
                }
                var neighbors = getOption(current);
                for (var neighbor : neighbors) {
                    if (!visited[neighbor.getX()][neighbor.getY()]) {
                        queue.add(neighbor);
                        visited[neighbor.getX()][neighbor.getY()] = true;
                        parent[neighbor.getX()][neighbor.getY()] = current;
                    }
                }
            }
            if (found) {
                var current = new Cell(end.getX(), end.getY(), end.getHeight());
                while (current.getX() != cell.getX() || current.getY() != cell.getY()) {
                    shortestPath++;
                    current = parent[current.getX()][current.getY()];
                }
                if (shortestPath < shortest) shortest = shortestPath;
            }
        }
        ColoredPrint.println(shortest);
    }

    private List<Cell> getOption(Cell cell) {
        var possibleDir = new ArrayList<Cell>();
        if (cells.stream().anyMatch(c -> c.getX() == cell.getX() - 1 && c.getY() == cell.getY())) {
            var cellAbove = cells.stream().filter(c -> c.getX() == cell.getX() - 1 && c.getY() == cell.getY()).findFirst().get();
            if (cellAbove.getHeight() <= cell.getHeight() || cellAbove.getHeight() == cell.getHeight() + 1)
                possibleDir.add(cellAbove);
        }

        if (cells.stream().anyMatch(c -> c.getX() == cell.getX() && c.getY() == cell.getY() + 1)) {
            var cellRight = cells.stream().filter(c -> c.getX() == cell.getX() && c.getY() == cell.getY() + 1).findFirst().get();
            if (cellRight.getHeight() <= cell.getHeight() || cellRight.getHeight() == cell.getHeight() + 1)
                possibleDir.add(cellRight);
        }

        if (cells.stream().anyMatch(c -> c.getX() == cell.getX() + 1 && c.getY() == cell.getY())) {
            var cellBelow = cells.stream().filter(c -> c.getX() == cell.getX() + 1 && c.getY() == cell.getY()).findFirst().get();
            if (cellBelow.getHeight() <= cell.getHeight() || cellBelow.getHeight() == cell.getHeight() + 1)
                possibleDir.add(cellBelow);
        }

        if (cells.stream().anyMatch(c -> c.getX() == cell.getX() && c.getY() == cell.getY() - 1)) {
            var cellLeft = cells.stream().filter(c -> c.getX() == cell.getX() && c.getY() == cell.getY() - 1).findFirst().get();
            if (cellLeft.getHeight() <= cell.getHeight() || cellLeft.getHeight() == cell.getHeight() + 1)
                possibleDir.add(cellLeft);
        }

        return possibleDir;
    }

    private void readData() {
        try (Scanner scanner = new Scanner(new File(path))) {
            int j = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                columns = line.length();
                for (int i = 0; i < line.length(); i++) {
                    var cell = new Cell(j, i, line.charAt(i));
                    if (line.charAt(i) == 'S') {
                        cell.setHeight('a');
                        cell.setStart(true);
                    } else if (line.charAt(i) == 'E')
                        cell.setEnd(true).setHeight('z');
                    cells.add(cell);
                }
                j++;
            }
            rows = j;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class Cell {
        private int x;
        private int y;
        private char height;
        private boolean start;

        private boolean end;

        public Cell(int x, int y, char height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }

        public char getHeight() {
            return height;
        }

        public void setHeight(char height) {
            this.height = height;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isEnd() {
            return end;
        }

        public Cell setEnd(boolean end) {
            this.end = end;
            return this;
        }

        public boolean isStart() {
            return start;
        }

        public void setStart(boolean start) {
            this.start = start;
        }

    }
}
