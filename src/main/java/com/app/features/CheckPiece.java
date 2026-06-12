package com.app.features;

import com.app.Board;
import com.app.Piece;
import com.app.PieceColor;
import com.app.PieceType;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CheckPiece {

    public static void checkPiece(Scanner scanner, Board board, Count counter, SavedBoard savedBoard) {
        while (true) {
            System.out.println("1.Policz liczbe atakowanych pol");
            System.out.println("2.Wykonaj analize dostepych pol");
            System.out.println("3.Zapisz tablice i wyjdz");
            System.out.println("4.Dodaj pionki");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    counter.count(board, scanner);
                    break;
                case "2":
                    counter.calculateAttack(board, scanner);
                    break;
                case "3":
                    System.out.println("Podaj sciezke do zapisu");
                    input = scanner.nextLine();
                    savedBoard.saveBoard(input, board);
                    return;
                case "4":
                    addPiece(scanner, board);
                    Piece piece = new Piece(PieceColor.BLACK, PieceType.KNIGHT);
                    int randomIdx = ThreadLocalRandom.current().nextInt(0, 64);
                    board.setPieceTo( randomIdx, piece);
                    break;
                default:
                    System.out.println("Nieprawidlowa opcja");
            }
        }
    }

    public static void addPiece(Scanner scanner, Board board) {
        while (true)
        {
            System.out.println("Wprowadz pozycje pionka i jego kolor");
            System.out.println("Wykonaj kalkukacje - wpisz 'calculate'");
            String next = scanner.nextLine();
            if (next.equals("calculate")) {
                break;
            }
        }
    }

}
