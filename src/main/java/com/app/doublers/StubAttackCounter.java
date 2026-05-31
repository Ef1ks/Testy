package com.app.doublers;

import com.app.Chessboard;

public class StubAttackCounter implements AttackCounter {
    private int value;

    public void stubValue(int value) {
        this.value = value;
    }

    @Override
    public int count(Chessboard board) {
        return this.value;
    }
}