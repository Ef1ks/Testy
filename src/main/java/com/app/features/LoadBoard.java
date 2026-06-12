package com.app.features;

import com.app.Board;

import java.util.Scanner;

public class LoadBoard {
    public void load(SavedBoard savedBoard, Scanner scanner) {
        savedBoard.printAllSavedBoards();
        System.out.println("Podaj siezke do zaladowania pliku");
        String input = scanner.nextLine();
        Board board = savedBoard.loadBoard(input);
        if (board == null) return;
        Count counter = new Count();
        System.out.println(board);
        CheckPiece.checkPiece(scanner, board, counter, savedBoard);
    }
}
