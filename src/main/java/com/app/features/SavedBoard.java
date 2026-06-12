package com.app.features;

import com.app.*;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
public class SavedBoard {
    Map<String, Board> savedBoards;

    public SavedBoard() {
        this.savedBoards = new HashMap<>();

        Board startingBoard = new Board();

        startingBoard.setPieceTo(15, new Piece(PieceColor.WHITE, PieceType.KNIGHT));
        startingBoard.setPieceTo(30, new Piece(PieceColor.WHITE, PieceType.KNIGHT));

        this.savedBoards.put("tablice/pierwsza.tablica", startingBoard);
    }

    public void saveBoard(String name, Board board) {
        this.savedBoards.put(name, board);
    }

    public Board loadBoard(String name) {
        if (!this.savedBoards.containsKey(name)) {
            System.out.println("⚠️ BŁĄD: Nie znaleziono zapisu o nazwie: " + name);
            return null;
        }
        return this.savedBoards.get(name);
    }

    public void printAllSavedBoards() {
        // Sprawdzamy, czy mapa nie jest pusta
        if (this.savedBoards.isEmpty()) {
            System.out.println("📭 Brak zapisanych plansz w pamięci.");
            return;
        }

        System.out.println("💾 --- LISTA ZAPISANYCH PLANSZ ---");
        // Iterujemy po wszystkich kluczach (nazwach zapisów) w mapie
        for (String boardName : this.savedBoards.keySet()) {
            System.out.printf("- %s%n", boardName);
        }
        System.out.println("---------------------------------");
    }

}
