package com.app.features;

import com.app.Board;
import com.app.Square;

import java.util.List;
import java.util.Scanner;

public class Filter {
    public List<Square> filter(Board board, Scanner scanner) {
        List<Square> moves = board.getSquares().stream().filter(s -> !s.isEmpty()).toList();
        if (moves.isEmpty()) {
            System.out.println("Brak pionków na planszy!");
            return null;
        }
        return moves;
    }
}
