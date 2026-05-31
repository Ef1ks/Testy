package com.app;

public record Position(int row, int col) {
    public Position {
        if (row < 1 || row > 8 || col < 1 || col > 8) {
            throw new IllegalArgumentException("Pozycja poza szachownicą (1-8)!");
        }
    }

    @Override
    public String toString() {
        return "[" + row + "," + col + "]";
    }
}
