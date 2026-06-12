package com.app;

import com.app.doublers.AttackCalculator;
import com.app.doublers.AttackCounter;
import com.app.doublers.ChessboardReader;
import com.app.doublers.ChessboardWriter;

import java.io.IOException;
import java.util.List;

class ChessboardEditor {
    private final AttackCounter attackCounter;
    private final AttackCalculator attackCalculator;
    private final ChessboardWriter chessboardWriter;
    private final ChessboardReader chessboardReader;

    private Chessboard currentBoard;

    public ChessboardEditor(AttackCounter counter, AttackCalculator calculator,
                            ChessboardWriter writer, ChessboardReader reader) {
        this.attackCounter = counter;
        this.attackCalculator = calculator;
        this.chessboardWriter = writer;
        this.chessboardReader = reader;
    }

    public void createNewBoard() {
        this.currentBoard = new Chessboard();
        System.out.println("Inicjalizacja nowej, czystej planszy.");
    }

    public void loadBoard(String path) throws IOException {
        System.out.println("Próba odczytu planszy z pliku: " + path);
        this.currentBoard = chessboardReader.load(path);
        System.out.println("Sukces odczytu! " + currentBoard);
    }

    public void placePiece(int row, int col, String pieceType) {
        if (currentBoard == null) return;
        try {
            Position pos = new Position(row, col);
            currentBoard.addPiece(pos, pieceType);
            System.out.println("Dodano: " + pieceType + " na " + pos);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("BLAD WALIDACJI " + e.getMessage());
        }
    }

    public void executeAnalysis() {
        if (currentBoard == null) return;
        System.out.println("Wywoływanie algorytmów analitycznych...");
        int count = attackCounter.count(currentBoard);
        List<Position> fields = attackCalculator.calculateAttack(currentBoard);
        System.out.println("Liczba atakowanych pól: " + count + " | Lokalizacje: " + fields);
    }

    public void saveBoard(String path) throws IOException {
        if (currentBoard == null) return;
        System.out.println("Próba zapisu planszy pod ścieżkę: " + path);
        chessboardWriter.save(currentBoard, path);
        System.out.println("Sukces zapisu!");
    }
}