package com.app.doublers;

import com.app.Chessboard;

import java.util.List;
import com.app.Position;

public interface AttackCalculator {
    List<Position> calculateAttack(Chessboard board);
}
