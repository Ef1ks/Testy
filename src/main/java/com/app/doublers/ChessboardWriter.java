package com.app.doublers;

import com.app.Chessboard;

import java.io.IOException;

public interface ChessboardWriter {
    void save(Chessboard board, String filePath) throws IOException;
}
