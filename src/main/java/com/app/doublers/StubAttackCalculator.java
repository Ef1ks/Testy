package com.app.doublers;

import com.app.Chessboard;
import com.app.Position;
import java.util.Collections;
import java.util.List;

public class StubAttackCalculator implements AttackCalculator {
    private List<Position> positions = Collections.emptyList();

    public void stubPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public List<Position> calculateAttack(Chessboard board) {
        return this.positions;
    }
}