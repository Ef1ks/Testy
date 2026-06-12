package com.app.features;

import com.app.Board;
import com.app.PieceColor;
import com.app.Square;

import java.util.stream.IntStream;

public class KnightMoveValidator {
    private static final int SIZE = 8;
    // Upubliczniamy wektory, by generator ruchów mógł z nich korzystać
    public static final int[] KNIGHT_POSSIBLE_MOVES = {17, 15, 10, 6, -6, -10, -15, -17};

    public boolean isValidMove(int from, int to, Board board) {
        // 1. Walidacja granic planszy (Out of Bounds)
        if (from == to || from < 0 || from >= SIZE * SIZE || to < 0 || to >= SIZE * SIZE) {
            return false;
        }

        // 2. Walidacja obecności figury na polu startowym
        final Square fromSquare = board.getSquare(from);
        if (fromSquare.isEmpty()) {
            return false;
        }

        // 3. Walidacja bicia (zapobieganie bicia własnych figur)
        final Square toSquare = board.getSquare(to);
        if (!toSquare.isEmpty()) {
            final PieceColor movingPieceColor = fromSquare.getPiece().color();
            final PieceColor targetPieceColor = toSquare.getPiece().color();

            if (movingPieceColor == targetPieceColor) {
                return false;
            }
        }

        // 4. Walidacja wektora ruchu szachowego
        final int difference = to - from;
        final boolean isValidVector = IntStream.of(KNIGHT_POSSIBLE_MOVES)
                .anyMatch(move -> move == difference);

        if (!isValidVector) {
            return false;
        }

        // 5. Walidacja zawijania krawędzi na jednowymiarowej tablicy
        final int startCol = from % SIZE;
        final int endCol = to % SIZE;
        final int difCol = Math.abs(startCol - endCol);

        return difCol == 1 || difCol == 2;
    }
}