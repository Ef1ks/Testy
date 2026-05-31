package com.app.doublers;

import com.app.Chessboard;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FakeChessboard implements ChessboardWriter, ChessboardReader {
    private final Map<String, Chessboard> virtualDisk = new HashMap<>();
    private String triggerErrorPath;
    private IOException errorToThrow;

    public void failOnWrite(String path, IOException e) {
        this.triggerErrorPath = path;
        this.errorToThrow = e;
    }

    public void addPredefinedBoard(String path, Chessboard board) {
        this.virtualDisk.put(path, board);
    }

    @Override
    public void save(Chessboard board, String filePath) throws IOException {
        if (filePath.equals(triggerErrorPath)) {
            throw errorToThrow;
        }
        virtualDisk.put(filePath, board);
    }

    @Override
    public Chessboard load(String filePath) throws IOException {
        if (!virtualDisk.containsKey(filePath)) {
            throw new FileNotFoundException("Wirtualny brak pliku: " + filePath);
        }
        return virtualDisk.get(filePath);
    }
}