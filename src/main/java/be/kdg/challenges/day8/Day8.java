package be.kdg.challenges.day8;

import be.kdg.util.ColoredPrint;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Day8 {

    private int[][] treeGrid;

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.BLUE_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 8");
        readData();
        part1();
        part2();
    }

    private void readData() {
        List<String> lines;
        try {
            lines = FileUtils.readLines(new File("src/main/java/be/kdg/challenges/day8/puzzle.txt"), "UTF-8");
            treeGrid = new int[lines.size()][lines.get(0).length()];
            for (var l : lines) {
                for (int i = 0; i < l.length(); i++)
                    treeGrid[lines.indexOf(l)][i] = Integer.parseInt(String.valueOf(l.charAt(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        boolean top, left, right, bottom;
        int outside = 0, inside = 0;
        int topI, leftI, rightI, bottomI;
        for (int i = 0; i < treeGrid.length; i++) {
            for (int j = 0; j < treeGrid[i].length; j++) {
                top = true;
                left = true;
                right = true;
                bottom = true;
                if (i == 0 || j == 0 || i == treeGrid.length - 1 || j == treeGrid[0].length - 1) outside++;
                else {
                    topI = i - 1;
                    leftI = j - 1;
                    rightI = j + 1;
                    bottomI = i + 1;
                    while (topI >= 0 || leftI >= 0 || rightI < treeGrid[0].length || bottomI < treeGrid.length) {
                        if (topI >= 0) {
                            if (top && treeGrid[topI][j] >= treeGrid[i][j]) top = false;
                            topI--;
                        }
                        if (leftI >= 0) {
                            if (left && treeGrid[i][leftI] >= treeGrid[i][j]) left = false;
                            leftI--;
                        }
                        if (rightI < treeGrid[0].length) {
                            if (right && treeGrid[i][rightI] >= treeGrid[i][j]) right = false;
                            rightI++;
                        }
                        if (bottomI < treeGrid.length) {
                            if (bottom && treeGrid[bottomI][j] >= treeGrid[i][j]) bottom = false;
                            bottomI++;
                        }
                    }
                    if (top || right || left || bottom) inside++;
                }
            }
        }
        ColoredPrint.println(outside + inside);
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        int top, left, right, bottom;
        boolean topB, leftB, rightB, bottomB;
        int topI, leftI, rightI, bottomI;
        int scenicScore =0;
        for (int i = 0; i < treeGrid.length; i++) {
            for (int j = 0; j < treeGrid[i].length; j++) {
                top = 0;
                left = 0;
                right = 0;
                bottom = 0;

                topB = true;
                leftB = true;
                rightB = true;
                bottomB = true;

                topI = i - 1;
                leftI = j - 1;
                rightI = j + 1;
                bottomI = i + 1;
                while (topI >= 0 || leftI >= 0 || rightI < treeGrid[0].length || bottomI < treeGrid.length) {
                    if (topB && topI >= 0) {
                        if (treeGrid[topI][j] >= treeGrid[i][j]) topB = false;
                        top++;
                    }
                    if (leftB && leftI >= 0) {
                        if (treeGrid[i][leftI] >= treeGrid[i][j]) leftB = false;
                        left++;
                    }
                    if (rightB && rightI < treeGrid[0].length) {
                        if (treeGrid[i][rightI] >= treeGrid[i][j]) rightB = false;
                        right++;
                    }
                    if (bottomB && bottomI < treeGrid.length) {
                        if (treeGrid[bottomI][j] >= treeGrid[i][j]) bottomB = false;
                        bottom++;
                    }
                    topI--;
                    leftI--;
                    rightI++;
                    bottomI++;
                }
                if (top * left * right * bottom > scenicScore) scenicScore = top * left * right * bottom;
            }
        }
        ColoredPrint.println(scenicScore);
    }
}