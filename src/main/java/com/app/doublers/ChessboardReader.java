package com.app.doublers;

import com.app.Chessboard;

import java.io.IOException;

public interface ChessboardReader {
    Chessboard load(String filePath) throws IOException;
}
