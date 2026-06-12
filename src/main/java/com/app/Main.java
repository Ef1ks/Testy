package com.app;

import com.app.doublers.AttackCalculator;
import com.app.doublers.AttackCounter;
import com.app.doublers.ChessboardReader;
import com.app.doublers.ChessboardWriter;
import com.app.features.SavedBoard;
import com.app.features.Terminal;

import java.io.IOException;
import java.util.Scanner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Main {
    private static Board board;

    static void main() throws IOException {
        AttackCounter mockCounter = mock(AttackCounter.class);
        AttackCalculator mockCalculator = mock(AttackCalculator.class);
        ChessboardWriter mockWriter = mock(ChessboardWriter.class);
        ChessboardReader mockReader = mock(ChessboardReader.class);
        ChessboardEditor editor = new ChessboardEditor(mockCounter, mockCalculator, mockWriter, mockReader);

        Terminal terminal = new Terminal();
        SavedBoard savedBoard = new SavedBoard();

        while (true) {
            boolean shouldContinue = terminal.print(savedBoard);
            if (!shouldContinue) {
                break;
            }
        }

//        String choice = scanner.nextLine();
//        Board board = null;
//        switch (choice) {
//            case "1":
////                board = new Board();
////                // cos tam
////                when(mockWriter.save(board, "test.txt")).thenReturn(null); // tu zapisze plansze do pliku
//                break;
//            case "2":
//                System.out.println("Podaj ścieżkę do pliku");
//                String filePath = scanner.nextLine();
//                when(mockReader.load(filePath)).thenReturn(new Chessboard()); // tu wczytam plansze z pliku
//                break;
//            default:
//                System.out.println("Nieprawidlowa opcja");
//                return;
//        }
//
//        while (true)
//        {
//            System.out.println("Podaj pozycje (np. A1, B2, C3, ...):");
//            String input = scanner.nextLine();
//            if (input.equals("exit")) {
//                System.out.println(board);
//                return;
//            }
//            int position = parsePositionToIndex(input);
//            System.out.println(position);
//            board.setPieceTo(position, new Piece(PieceColor.WHITE, PieceType.KNIGHT));
//
//
//
//        }
    }

    public static int parsePositionToIndex(String input) {
        if (input == null || input.trim().length() != 2) {
            throw new IllegalArgumentException("Nieprawidłowy format. Oczekiwano 2 znaków (np. 'A1').");
        }

        String normalized = input.trim().toUpperCase();
        char fileChar = normalized.charAt(0);
        char rankChar = normalized.charAt(1);

        if (fileChar < 'A' || fileChar > 'H') {
            throw new IllegalArgumentException("Nieprawidłowa kolumna: '" + fileChar + "'. Oczekiwano liter A-H.");
        }
        if (rankChar < '1' || rankChar > '8') {
            throw new IllegalArgumentException("Nieprawidłowy rząd: '" + rankChar + "'. Oczekiwano cyfr 1-8.");
        }
        int file = fileChar - 'A';
        int rank = rankChar - '1';
        return (rank * 8) + file;
    }
}
