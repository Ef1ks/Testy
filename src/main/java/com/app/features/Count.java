package com.app.features;

import com.app.Board;
import com.app.Square;

import java.util.List;
import java.util.Scanner;

public class Count {
    public void count(Board board, Scanner scanner) {
        System.out.println("Liczenie");

        List<Square> moves = new Filter().filter(board, scanner);
        int choice = new ChoiceOfPiece().choicePiece(moves, scanner);
        Square selectedSquare = moves.get(choice - 1);
        int positionInBoard = board.getSquares().indexOf(selectedSquare);
        System.out.printf("Wybrałeś pionka na polu: %s (Indeks na planszy: %d)%n", selectedSquare, positionInBoard);
        KnightAllPossibleMoves knightAllPossibleMoves = new KnightAllPossibleMoves(new KnightMoveValidator());
        List<Integer> list = knightAllPossibleMoves.getAllPossibleMoves(positionInBoard, board);
        System.out.println("Liczba możliwych ruchów dla tego pionka: " + list.size());
    }
    public void calculateAttack(Board board, Scanner scanner) {
        System.out.println("Kalkulacja ataku");

        List<Square> moves = new Filter().filter(board, scanner);
        int choice = new ChoiceOfPiece().choicePiece(moves, scanner);
        Square selectedSquare = moves.get(choice - 1);
        int positionInBoard = board.getSquares().indexOf(selectedSquare);
        System.out.printf("Wybrałeś pionka na polu: %s (Indeks na planszy: %d)%n", selectedSquare, positionInBoard);
        KnightAllPossibleMoves knightAllPossibleMoves = new KnightAllPossibleMoves(new KnightMoveValidator());
        List<Integer> list = knightAllPossibleMoves.getAllPossibleMoves(positionInBoard, board);
        System.out.println(list.toString());
    }
}
