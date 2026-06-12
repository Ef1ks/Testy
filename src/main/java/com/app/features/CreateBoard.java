package com.app.features;

import com.app.*;
import com.sun.source.doctree.EscapeTree;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CreateBoard {
    public void create(Board board, Scanner scanner, SavedBoard savedBoard) {

        CheckPiece.addPiece(scanner, board);
        Piece piece = new Piece(PieceColor.BLACK, PieceType.KNIGHT);
        Piece piece2 = new Piece(PieceColor.WHITE, PieceType.KNIGHT);
            board.setPieceTo(43, piece);
            int randomIdx = ThreadLocalRandom.current().nextInt(0, 64);
            board.setPieceTo( randomIdx, piece2);
        System.out.println(board);
        Count counter = new Count();
        CheckPiece.checkPiece(scanner, board, counter, savedBoard);
    }
}
