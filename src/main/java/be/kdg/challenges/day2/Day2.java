package be.kdg.challenges.day2;

import be.kdg.util.ColoredPrint;

import java.io.File;
import java.util.Scanner;

public class Day2 {
    public void run() {
        ColoredPrint.setColor(ColoredPrint.Color.RED_BOLD_BRIGHT);
        ColoredPrint.println("\n\nDay 2:");
        long start = System.currentTimeMillis();
        part1();
        part2();
        long end = System.currentTimeMillis();
        ColoredPrint.println("Time: " + (end - start) + "ms");
    }

    private void part1() {
        ColoredPrint.print("Part 1: ");
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day2/strategy.txt"))) {
            int total = 0;
            Game enemy;
            Game player;
            while (sc.hasNextLine()) {
                String[] move = sc.nextLine().split(" ");
                enemy = move[0].equalsIgnoreCase("A") ? Game.ROCK : move[0].equalsIgnoreCase("B") ? Game.PAPER : Game.SCISSORS;
                player = move[1].equalsIgnoreCase("X") ? Game.ROCK : move[1].equalsIgnoreCase("Y") ? Game.PAPER : Game.SCISSORS;
                total+= checkScore(player, enemy);
            }
            ColoredPrint.println(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void part2() {
        ColoredPrint.print("Part 2: ");
        try (Scanner sc = new Scanner(new File("src/main/java/be/kdg/challenges/day2/strategy.txt"))) {
            int total = 0;
            Game enemy;
            State state;
            Game player;
            while (sc.hasNextLine()) {
                String[] move = sc.nextLine().split(" ");
                enemy = move[0].equalsIgnoreCase("A") ? Game.ROCK : move[0].equalsIgnoreCase("B") ? Game.PAPER : Game.SCISSORS;
                state = move[1].equalsIgnoreCase("X") ? State.LOSE : move[1].equalsIgnoreCase("Y") ? State.DRAW : State.WIN;
                if (state == State.LOSE) {
                    if (enemy == Game.ROCK) player = Game.SCISSORS;
                    else if (enemy == Game.PAPER) player = Game.ROCK;
                    else player = Game.PAPER;
                }
                else if (state == State.DRAW)
                    player = enemy;
                else {
                    if (enemy == Game.ROCK) player = Game.PAPER;
                    else if (enemy == Game.PAPER) player = Game.SCISSORS;
                    else player = Game.ROCK;
                }
                total += checkScore(player, enemy);
            }
            ColoredPrint.println(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int checkScore(Game player, Game enemy) {
        int total = 0;
        if ((player.value == 1 && enemy.value == 3))total += 6;
        else if (enemy.value == 1 && player.value == 3)total += 0;
        else if (player.value > enemy.value)total += 6;
        else if (player.value == enemy.value) total += 3;
        return total + player.value;
    }

    public enum Game {
        ROCK(1), PAPER(2), SCISSORS(3);

        final int value;

        Game(int value) {
            this.value = value;
        }
    }

    public enum State {
        WIN(6), LOSE(0), DRAW(3);

        final int value;

        State(int value) {
            this.value = value;
        }
    }
}


