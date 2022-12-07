package be.kdg;

import be.kdg.challenges.day1.Day1;
import be.kdg.challenges.day2.Day2;
import be.kdg.challenges.day3.Day3;
import be.kdg.challenges.day4.Day4;
import be.kdg.challenges.day5.Day5;
import be.kdg.challenges.day6.Day6;
import be.kdg.challenges.day7.Day7;


// * This is the main class of the application.
public class Main {
    public static void main(String[] args) {
        Day1.run();
        Day2.run();
        Day3.run();
        Day4.run();
        Day5.run();
        Day6.run();
        new Day7().run();
    }
}