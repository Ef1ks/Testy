package com.app;

import java.io.IOException;

public class Workflow {
    public static void executeWorkflow(int scenario, ChessboardEditor editor) throws IOException {
        if (scenario == 7) {
            editor.loadBoard("/konfiguracje/partia_arcymistrzowska.json");
        } else {
            editor.createNewBoard();
        }

        switch (scenario) {
            case 1:
                System.out.println("[Test] Pozostawiamy planszę pustą.");
                break;
            case 2:
                editor.placePiece(2, 4, "White Pawn");
                break;
            case 3:
                editor.placePiece(1, 4, "White Knight"); // Brzeg
                break;
            case 4:
                editor.placePiece(4, 4, "White Queen");  // Centrum
                break;
            case 5:
                editor.placePiece(1, 1, "White Rook");
                editor.placePiece(1, 4, "White Pawn");   // Zasłania wieżę
                editor.placePiece(1, 8, "Black King");   // Nie powinien być atakowany bezpośrednio
                break;
            case 6:
                editor.placePiece(5, 5, "Black Bishop");
                break;
            case 7:
                System.out.println("Figury zostały zaimportowane z pliku zewnętrznego.");
                break;
        }

        editor.executeAnalysis();

        if (scenario == 6) {
            editor.saveBoard("/błędna_ścieżka/zapis.json"); //  Wyjątek z Mockito
        } else if (scenario != 7) {
            editor.saveBoard("/pliki/szachownica_wynik_" + scenario + ".json");
        }
    }
}
