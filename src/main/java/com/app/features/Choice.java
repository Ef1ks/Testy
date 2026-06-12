package com.app.features;

import com.app.Board;

import java.util.Scanner;

public class Choice {
    private final Scanner scanner;
    private Board board;

    public Choice(Scanner scanner) {
        this.scanner = scanner;
        this.board = new Board();
    }

    public void choice(String choice, SavedBoard savedBoard) {
        switch (choice) {
            case "1":
                new CreateBoard().create(board, scanner, savedBoard);
                return;
            case "2":
                new LoadBoard().load(savedBoard, scanner);
                break;
            default:
                System.out.println("Nieprawidlowa opcja");
                return;
        }
    }
}
