package com.app;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Board {
    private static final int SIZE = 8;

    private final List<Square> squares;

    public Board() {
        this.squares = new ArrayList<>(SIZE * SIZE);
        for (int i = 0; i < SIZE * SIZE; i++) {
            this.squares.add(new Square());
        }
    }

    public Square getSquare(int index) {
        return squares.get(index);
    }

    public void setPieceTo(int index, Piece piece) {
        Square square = this.squares.get(index);
        if (square.isEmpty()) {
            square.setPiece(piece);
        }
        else {
            System.out.println("Zajete!");
        }

    }

    public void movePiece(int fromIndex, int toIndex) {
        Square fromSquare = this.squares.get(fromIndex);
        Square toSquare = this.squares.get(toIndex);
        if (fromSquare.isEmpty()) {
            throw new IllegalMoveException("No piece at source square: " + fromIndex);
        }

        Piece pieceToMove = fromSquare.getPiece();

        if (!toSquare.isEmpty() && toSquare.getPiece().color() == pieceToMove.color()) {
            throw new IllegalMoveException("Cannot move to square " + toIndex + " occupied by a piece of the same color");
        }

        toSquare.setPiece(pieceToMove);
        fromSquare.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int rank = SIZE - 1; rank >= 0; rank--) {
            sb.append(rank + 1).append(" | ");

            for (int file = 0; file < SIZE; file++) {
                // Logika się nie zmienia: (rząd * 8) + kolumna
                int position = (rank * SIZE) + file;
                Square square = squares.get(position);
                String pieceIndicator = (square.getPiece() == null) ? "." : square.getPiece().color().equals(PieceColor.WHITE) ? "W" : "B";
                sb.append(pieceIndicator).append(" ");
            }
            sb.append("\n");
        }
        sb.append("   ----------------\n");
        sb.append("    A B C D E F G H\n");

        return sb.toString();
    }
}


