package com.app.features;

import com.app.Board;

import java.util.ArrayList;
import java.util.List;

public class KnightAllPossibleMoves {

    private final KnightMoveValidator knightMoveValidator;

    public KnightAllPossibleMoves(KnightMoveValidator knightMoveValidator) {
        this.knightMoveValidator = knightMoveValidator;
    }

    public List<Integer> getAllPossibleMoves(int position, Board board) {
        List<Integer> moves = new ArrayList<>();
        for (int move : KnightMoveValidator.KNIGHT_POSSIBLE_MOVES) {
            int nextSquare = position + move;

            if (knightMoveValidator.isValidMove(position, nextSquare, board)){
                moves.add(nextSquare);
            }
        }
        return moves;
    }
}