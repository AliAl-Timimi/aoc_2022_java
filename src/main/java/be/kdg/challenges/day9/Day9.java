package be.kdg.challenges.day9;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day9 {
    private static final int KNOTS_PART1 = 2;
    private static final int KNOTS_PART2 = 10;
    private static final int SIZE_X = 30;
    private static final int SIZE_Y = 22;
    List<String> moves = new ArrayList<>();

    Rope head = new Rope(new Coordinate(0, 0));
    List<Rope> followers = new ArrayList<>();

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.RED_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 9");
        readData();
        part1();
        part2();
    }

    private void readData() {
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day9/puzzle.txt"))) {
            while (sc.hasNextLine()) {
                moves.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        createKnots(KNOTS_PART1);
        doMoves();
        ColoredPrint.println(followers.get(0).visitedCoords.size());
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        head.setCurrentCoord(new Coordinate(0, 0));
        createKnots(KNOTS_PART2);
        doMoves();
        ColoredPrint.println(followers.get(followers.size() - 1).visitedCoords.size());
    }

    private void printTailTrial() {
        boolean somethingPrinted = false;
        for (int i = SIZE_X / 2; i > SIZE_X / 2 * -1; i--) {
            for (int j = (SIZE_Y / 2) * -1; j < SIZE_Y / 2; j++) {
                if (head.getCurrentCoord().equals(new Coordinate(i, j))) {
                    ColoredPrint.print("H");
                    somethingPrinted = true;
                } else {
                    for (Rope follower : followers) {
                        if (!somethingPrinted && follower.getCurrentCoord().equals(new Coordinate(i, j))) {
                            ColoredPrint.print(followers.indexOf(follower) + 1);
                            somethingPrinted = true;
                        }
                    }
                }
                if (!somethingPrinted)
                    ColoredPrint.print(".");
                somethingPrinted = false;
            }
            ColoredPrint.println("");
        }
        System.out.println("\n\n");
    }

    private void doMoves() {
        for (var move : moves)
            move(move);
    }

    private void createKnots(int knots) {
        followers.clear();
        for (int i = 0; i < knots - 1; i++)
            followers.add(new Rope(new Coordinate(head.getCurrentCoord())));
    }

    private void move(String line) {
        char dir = line.charAt(0);
        int steps = Integer.parseInt(line.split(" ")[1]);
        for (int i = 0; i < steps; i++) {
            switch (dir) {
                case 'U' -> head.moveUp();
                case 'D' -> head.moveDown();
                case 'L' -> head.moveLeft();
                case 'R' -> head.moveRight();
            }
            for (int j = 0; j < followers.size(); j++) {
                if (j == 0) follow(head, followers.get(j));
                else follow(followers.get(j - 1), followers.get(j));
            }
        }
    }

    private void follow(Rope lead, Rope follow) {
        int vertical = lead.getCurrentCoord().getX() - follow.getCurrentCoord().getX();
        int horizontal = lead.getCurrentCoord().getY() - follow.getCurrentCoord().getY();
        if (vertical == 2) {
            if (horizontal == 0)
                follow.moveUp();
            else if (horizontal >= 1)
                follow.moveUp().moveRight();
            else
                follow.moveUp().moveLeft();

        } else if (vertical == -2) {
            if (horizontal == 0)
                follow.moveDown();
            else if (horizontal >= 1)
                follow.moveRight().moveDown();
            else
                follow.moveLeft().moveDown();

        } else if (horizontal == 2) {
            if (vertical == 0)
                follow.moveRight();
            else if (vertical >= 1)
                follow.moveUp().moveRight();
            else
                follow.moveDown().moveRight();

        } else if (horizontal == -2) {
            if (vertical == 0)
                follow.moveLeft();
            else if (vertical >= 1)
                follow.moveUp().moveLeft();
            else
                follow.moveDown().moveLeft();
        }
        follow.addCoord(new Coordinate(follow.getCurrentCoord()));
    }

    public static class Coordinate {
        int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinate(Coordinate c) {
            this.x = c.x;
            this.y = c.y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }

    public static class Rope {
        private Coordinate currentCoord;

        private final HashSet<Coordinate> visitedCoords;

        public Rope(Coordinate coord) {
            this.currentCoord = coord;
            visitedCoords = new HashSet<>();
        }

        public Coordinate getCurrentCoord() {
            return currentCoord;
        }

        public void setCurrentCoord(Coordinate currentCoord) {
            this.currentCoord = currentCoord;
        }

        public void addCoord(Coordinate coord) {
            visitedCoords.add(coord);
        }

        public Rope moveUp() {
            currentCoord.setX(currentCoord.getX() + 1);
            return this;
        }

        public Rope moveDown() {
            currentCoord.setX(currentCoord.getX() - 1);
            return this;
        }

        public Rope moveLeft() {
            currentCoord.setY(currentCoord.getY() - 1);
            return this;
        }

        public Rope moveRight() {
            currentCoord.setY(currentCoord.getY() + 1);
            return this;
        }
    }


}
