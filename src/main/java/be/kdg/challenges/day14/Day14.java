package be.kdg.challenges.day14;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day14 {
    private static final String PATH = "src/main/java/be/kdg/challenges/day14/puzzle.txt";
    private int width;
    private int length;

    private int smallestX = Integer.MAX_VALUE;
    private int smallestY = Integer.MAX_VALUE;

    private int largestX;
    private int largestY;
    private char[][] cave;

    private boolean top = false;

    private List<Coordinate> coordinates = new ArrayList<>();


    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.YELLOW_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 14");
        long start = System.currentTimeMillis();
        readData();
        createCave();
        part1();
        createCave2();
        part2();
        long end = System.currentTimeMillis();
        ColoredPrint.println("Time: " + (end - start) + "ms");
    }

    private boolean dropSand(int x, int y) {
        if (x - 1 < 0 || x + 1 >= width || y + 1 >= length)
            return false;
        while (cave[x][y + 1] == '.')
            y++;

        // * Fall left
        if (x - 1 >= 0 && cave[x - 1][y + 1] == '.') {
            return dropSand(x - 1, y + 1);
        }
        // * Fall right
        else if (x + 1 <= width + 1 && cave[x + 1][y + 1] == '.') {
            return dropSand(x + 1, y + 1);
        }
        // * Fall down
        else {
            cave[x][y] = 'o';
            if (x == 500 - smallestX && y == 0)
                top = true;
            return true;
        }
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        boolean cont = true;
        int i = 0;
        while (cont) {
            cont = dropSand(500 - smallestX, 0);
            if (cont) i++;
        }
        ColoredPrint.println(i);
        // printCave();
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        int i = 0;
        boolean cont;
        while (!top) {
            cont = dropSand(500 - smallestX, 0);
            if (cont) i++;
            else expandArray();

            // printCave();
        }
        ColoredPrint.println(i);
        //  printCave();
    }

    private void expandArray() {
        final int expansion = 6;
        char[][] newCave = new char[width + expansion][length];
        for (int i = 0; i < width + expansion; i++)
            Arrays.fill(newCave[i], '.');

        for (int i = 0; i < cave.length; i++)
            System.arraycopy(cave[i], 0, newCave[i + expansion / 2], 0, cave[i].length);

        cave = newCave;
        width += expansion;
        smallestX -= expansion / 2;

        for (int i = 0; i < width; i++)
            cave[i][length - 1] = '#';
    }

    private void createCave2() {
        width = largestX - smallestX + 1;
        length = largestY + 3;

        createAndFillCave(true);
    }

    private void readData() {
        coordinates = new ArrayList<>();
        Coordinate current;
        try (Scanner sc = new Scanner(new File(PATH))) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" -> ");
                current = new Coordinate(Integer.parseInt(line[0].split(",")[0]), Integer.parseInt(line[0].split(",")[1]));
                coordinates.add(current);
                for (int i = 1; i < line.length; i++) {
                    Coordinate c = new Coordinate(Integer.parseInt(line[i].split(",")[0]), Integer.parseInt(line[i].split(",")[1]));
                    current.setNext(c);
                    current = c;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Coordinate coordinate : coordinates) {
            updateSizes(coordinate);
            while (coordinate.hasNext()) {
                coordinate = coordinate.getNext();
                updateSizes(coordinate);
            }
        }

    }

    private void createCave() {
        width = largestX - smallestX + 1;
        length = largestY + 1;

        createAndFillCave(false);
    }

    private void createAndFillCave(boolean bottomRocks) {
        cave = new char[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++)
                cave[i][j] = '.';
        }

        for (Coordinate coordinate : coordinates) {
            var coord2 = coordinate.getNext();
            while (coord2 != null) {
                if (coordinate.getX() == coord2.getX()) {
                    if (coordinate.getY() < coord2.getY()) {
                        for (int i = coordinate.getY(); i <= coord2.getY(); i++)
                            cave[coordinate.getX() - smallestX][i] = '#';
                    } else {
                        for (int i = coord2.getY(); i <= coordinate.getY(); i++)
                            cave[coordinate.getX() - smallestX][i] = '#';
                    }
                } else {
                    if (coordinate.getX() < coord2.getX()) {
                        for (int i = coordinate.getX(); i <= coord2.getX(); i++)
                            cave[i - smallestX][coordinate.getY()] = '#';
                    } else {
                        for (int i = coord2.getX(); i <= coordinate.getX(); i++)
                            cave[i - smallestX][coordinate.getY()] = '#';
                    }
                }
                coordinate = coord2;
                coord2 = coordinate.getNext();
            }
        }
        cave[500 - smallestX][0] = '+';

        if (bottomRocks) {
            for (int i = 0; i < width; i++)
                cave[i][length - 1] = '#';
        }
    }

    private void printCave() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                ColoredPrint.print(String.valueOf(cave[j][i]));
            }
            ColoredPrint.println();
        }
    }

    private void updateSizes(Coordinate coordinate) {
        if (coordinate.getX() < smallestX)
            smallestX = coordinate.getX();
        else if (coordinate.getX() > largestX)
            largestX = coordinate.getX();

        if (coordinate.getY() > largestY)
            largestY = coordinate.getY();
        else if (coordinate.getY() < smallestY)
            smallestY = coordinate.getY();
    }

    public static class Coordinate {
        private final int x;
        private final int y;

        private Coordinate next;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Coordinate getNext() {
            return next;
        }

        public void setNext(Coordinate next) {
            this.next = next;
        }

        public boolean hasNext() {
            return next != null;
        }
    }
}
