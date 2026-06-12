package com.app.features;

import com.app.Board;

import java.util.Scanner;

public class Terminal {

    public boolean print(SavedBoard savedBoard) {

        Scanner scanner = new Scanner(System.in);
        Choice choice = new Choice(scanner);
        System.out.println("Chcesz stworzyc nową szachownice czy wczytac istniejaca?");
        System.out.println("1. Stworz");
        System.out.println("2. Wczytaj");
        System.out.println("3. Wyjscie");

        String next = scanner.nextLine();

        if (next.equals("3")) {return false;}
        Board board = null;
        choice.choice(next, savedBoard);

        return true;
    }
}
