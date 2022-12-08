package be.kdg.challenges.day7;

import be.kdg.util.ColoredPrint;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7 {
    private static final int TOTAL_SIZE = 70_000_000;
    private static final int TOTAL_REQUIRED = 30_000_000;
    private Directory root;
    private Directory currentDir;
    private int totalFree;
    private int smallestDirToDelete;

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.YELLOW_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 7:");
        readInput();
        // printFileSystem(root);
        part1();
        part2();
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        ColoredPrint.println(totalOfDirsUnder100_000(root));
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        totalFree = TOTAL_SIZE - root.getSize();
        smallestDirToDelete = root.getSize();
        sizeOfSmallestDirToDelete(root);
        ColoredPrint.println(smallestDirToDelete);
    }

    private void sizeOfSmallestDirToDelete(Directory dir) {
        int dirSize = dir.getSize();
        if (totalFree + dirSize >= TOTAL_REQUIRED) {
            for (var d : dir.getChildren()) {
                if (d instanceof Directory) sizeOfSmallestDirToDelete((Directory) d);
            }
            if (dirSize < smallestDirToDelete) smallestDirToDelete = dirSize;
        }
    }

    private void readInput() {
        try (Scanner sc = new Scanner(new java.io.File("src/main/java/be/kdg/challenges/day7/puzzle.txt"))) {
            boolean command;
            boolean cd;
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" ");
                command = line[0].equals("$");
                if (command) {
                    cd = line[1].equals("cd");
                    if (cd) {
                        if (line[2].equals("..")) {
                            currentDir = currentDir.getParent();
                        } else {
                            if (currentDir == null) {
                                root = new Directory(line[2], new ArrayList<>(), null);
                                currentDir = root;
                            } else currentDir = dirExists(line[2]);
                        }
                    }
                } else {
                    if (line[0].equals("dir"))
                        currentDir.addFileOrDirectory(new Directory(line[1], new ArrayList<>(), currentDir));
                    else currentDir.addFileOrDirectory(new File(line[1], Integer.parseInt(line[0])));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private int totalOfDirsUnder100_000(Directory dir) {
        int total = 0;
        for (var d : dir.getChildren()) {
            if (d instanceof Directory) {
                if (d.getSize() < 100_000) total += d.getSize();
                total += totalOfDirsUnder100_000((Directory) d);
            }
        }
        return total;
    }

    private void printFileSystem(Directory dir) {
        ColoredPrint.printf("%s%n", dir);
        printFileSystem(dir, 1);

    }

    private void printFileSystem(Directory dir, int depth) {
        for (var dirOrFile : dir.getChildren()) {
            if (dirOrFile instanceof Directory) {
                ColoredPrint.printf("%s%s%n", StringUtils.repeat(" ", depth * 2), dirOrFile);
                printFileSystem((Directory) dirOrFile, depth + 1);
            } else {
                ColoredPrint.printf("%s%s%n", StringUtils.repeat(" ", depth * 2), dirOrFile);
            }
        }
    }

    private Directory dirExists(String dirName) {
        return dirExists(dirName, currentDir);
    }

    private Directory dirExists(String dirName, Directory dirToCheck) {
        Directory dir = null;
        if (dirToCheck.getChildren().stream().anyMatch(d -> d.getName().equals(dirName))) {
            if (dirToCheck.getChildren().stream().filter(d -> d.getName().equals(dirName)).findFirst().get() instanceof Directory)
                dir = (Directory) dirToCheck.getChildren().stream().filter(d -> d.getName().equals(dirName)).findFirst().get();
        }
        return dir;
    }
}
