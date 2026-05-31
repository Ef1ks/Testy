package com.app;

import com.app.doublers.StubAttackCalculator;
import com.app.doublers.StubAttackCounter;
import com.app.doublers.FakeChessboard;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainCustomDoubles {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ZAAWANSOWANY POTOK TESTOWY: WŁASNE DUBLERY (ANALOGICZNY) ===");

        // Uruchamiamy automatycznie 7 zróżnicowanych scenariuszy testowych
        for (int scenario = 1; scenario <= 7; scenario++) {
            System.out.println("\n----------------------------------------------------------------");
            System.out.println(">>> URUCHAMIANIE SCENARIUSZA " + scenario + " <<<");
            System.out.println("----------------------------------------------------------------");

            // Izolacja: Nowe instancje własnych dublerów zamiast mock()
            StubAttackCounter customCounter = new StubAttackCounter();
            StubAttackCalculator customCalculator = new StubAttackCalculator();
            FakeChessboard customStorage = new FakeChessboard();

            // Konfiguracja specyficznych danych wyjściowych dla algorytmów i I/O przez dedykowany mechanizm
            configureAdvancedScenarios(scenario, customCounter, customCalculator, customStorage);

            // Wstrzyknięcie dublerów do komponentu biznesowego (Storage obsługuje zapis i odczyt)
            ChessboardEditor editor = new ChessboardEditor(customCounter, customCalculator, customStorage, customStorage);

            try {
                executeWorkflow(scenario, editor);
            } catch (IOException e) {
                System.out.println("[PRZECHWYCONY BŁĄD SYSTEMU PLIKÓW W MAIN] " + e.getMessage());
            }

            Thread.sleep(1000);
        }
        System.out.println("\n=== PIPELINE ZAKOŃCZONY: PRZETESTOWANO 7 KOMPLETNYCH SCENARIUSZY ===");
    }

    private static void configureAdvancedScenarios(int scenario, StubAttackCounter counter, StubAttackCalculator calculator,
                                                   FakeChessboard storage) {
        switch (scenario) {
            case 1:
                System.out.println("[Konfiguracja Double] Analiza pustej planszy.");
                counter.stubValue(0);
                calculator.stubPositions(Collections.emptyList());
                break;

            case 2:
                System.out.println("[Konfiguracja Double] Analiza pojedynczego pionka.");
                counter.stubValue(2);
                List<Position> pawnAttacks = Arrays.asList(new Position(3, 3), new Position(3, 5));
                calculator.stubPositions(pawnAttacks);
                break;

            case 3:
                System.out.println("[Konfiguracja Double] Analiza skoczka na krawędzi (ograniczony zasięg).");
                counter.stubValue(4);
                List<Position> knightAttacks = Arrays.asList(
                        new Position(3, 2), new Position(4, 3),
                        new Position(4, 5), new Position(3, 6)
                );
                calculator.stubPositions(knightAttacks);
                break;

            case 4:
                System.out.println("[Konfiguracja Double] Analiza centralnie ustawionego Hetmana (Maksymalny zasięg).");
                counter.stubValue(27);
                List<Position> queenAttacks = Arrays.asList(
                        new Position(4, 1), new Position(4, 2), new Position(4, 3),
                        new Position(1, 4), new Position(2, 4), new Position(3, 4),
                        new Position(1, 1), new Position(2, 2), new Position(3, 3)
                );
                calculator.stubPositions(queenAttacks);
                break;

            case 5:
                System.out.println("[Konfiguracja Double] Analiza blokowania linii strzału (figura zasłonięta).");
                counter.stubValue(5);
                List<Position> blockedAttacks = Arrays.asList(
                        new Position(1, 1), new Position(1, 2), new Position(1, 3)
                );
                calculator.stubPositions(blockedAttacks);
                break;

            case 6:
                System.out.println("[Konfiguracja Double] Poprawna analiza, ale wymuszenie błędu zapisu pliku.");
                counter.stubValue(12);
                calculator.stubPositions(List.of(new Position(5, 5)));

                // Symulacja doThrow przez konfigurację zachowania Fake'a
                storage.failOnWrite("/błędna_ścieżka/zapis.json",
                        new IOException("Krytyczny błąd zapisu: Niepoprawna ścieżka systemowa!"));
                break;

            case 7:
                System.out.println("[Konfiguracja Double] Odczyt z pliku zakończony sukcesem.");
                Chessboard loadedBoard = new Chessboard();
                loadedBoard.addPiece(new Position(8, 8), "Black King");

                // Wstrzyknięcie gotowej planszy do pamięci wirtualnej
                storage.addPredefinedBoard("/konfiguracje/partia_arcymistrzowska.json", loadedBoard);
                counter.stubValue(42);
                calculator.stubPositions(Arrays.asList(new Position(7, 7), new Position(7, 8)));
                break;
        }
    }

    private static void executeWorkflow(int scenario, ChessboardEditor editor) throws IOException {
        // KROK 1: Inicjalizacja stanu szachownicy
        if (scenario == 7) {
            editor.loadBoard("/konfiguracje/partia_arcymistrzowska.json");
        } else {
            editor.createNewBoard();
        }

        // KROK 2: Rozstawienie figur adekwatnie do testowanego scenariusza obliczeniowego
        switch (scenario) {
            case 1:
                System.out.println("[Test] Pozostawiamy planszę pustą.");
                break;
            case 2:
                editor.placePiece(2, 4, "White Pawn");
                break;
            case 3:
                editor.placePiece(1, 4, "White Knight");
                break;
            case 4:
                editor.placePiece(4, 4, "White Queen");
                break;
            case 5:
                editor.placePiece(1, 1, "White Rook");
                editor.placePiece(1, 4, "White Pawn");
                editor.placePiece(1, 8, "Black King");
                break;
            case 6:
                editor.placePiece(5, 5, "Black Bishop");
                break;
            case 7:
                System.out.println("[Test] Figury zostały zaimportowane z pliku zewnętrznego.");
                break;
        }

        // KROK 3: Wywołanie i weryfikacja logiki analitycznej (Count & Calculate)
        editor.executeAnalysis();

        // KROK 4: Persystencja stanu końcowego
        if (scenario == 6) {
            editor.saveBoard("/błędna_ścieżka/zapis.json");
        } else if (scenario != 7) {
            editor.saveBoard("/pliki/szachownica_wynik_" + scenario + ".json");
        }
    }
}