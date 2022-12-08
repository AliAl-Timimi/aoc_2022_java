package be.kdg.challenges.day6;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day6 {
    private static String packetStream;

    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.CYAN_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 6:");
        readData();
        part1();
        part2();
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        int count = 0;
        for(int i = 0; i< packetStream.length(); i++) {
            if (i+4 < packetStream.length()) {
                String start = packetStream.substring(i, i+4);
                if (uniqueCharacters(start)){
                    count = i+4;
                    break;
                }
            }
        }
        ColoredPrint.println(count);
    }

    private boolean uniqueCharacters(String str)
    {
        for (int i = 0; i < str.length(); i++)
            for (int j = i + 1; j < str.length(); j++)
                if (str.charAt(i) == str.charAt(j))
                    return false;
        return true;
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        int count = 0;
        for(int i = 0; i< packetStream.length(); i++) {
            if (i+4 < packetStream.length()) {
                String start = packetStream.substring(i, i+14);
                if (uniqueCharacters(start)){
                    count = i+14;
                    break;
                }
            }
        }
        ColoredPrint.println(count);
    }

    private void readData() {
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day6/packets.txt"))) {
            packetStream = sc.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
