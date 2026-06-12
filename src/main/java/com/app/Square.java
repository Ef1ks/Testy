package com.app;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Square {
    private Piece piece;

    public Square(Piece piece) {
        this.piece = piece;
    }
    public boolean isEmpty() {
        return this.piece == null;
    }

    public void clear() {
        this.piece = null;
    }
}
