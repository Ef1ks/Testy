package com.app;

import java.util.HashMap;
import java.util.Map;

public class Chessboard {
    private final Map<Position, String> pieces = new HashMap<>();

    public void addPiece(Position pos, String pieceType) {
        if (pieces.containsKey(pos)) {
            throw new IllegalStateException("Pole " + pos + " jest już zajęte przez: " + pieces.get(pos));
        }
        pieces.put(pos, pieceType);
    }

    public Map<Position, String> getPieces() { return pieces; }

    @Override
    public String toString() {
        return "Szachownica z figurami: " + pieces;
    }
}
