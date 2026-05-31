package com.app;

import com.app.doublers.AttackCalculator;
import com.app.doublers.AttackCounter;
import com.app.doublers.ChessboardReader;
import com.app.doublers.ChessboardWriter;
import org.mockito.Mockito;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ZAAWANSOWANY POTOK TESTOWY: REJESTR SCENARIUSZY ANALITYCZNYCH ===");

        // Uruchamiamy automatycznie 7 zróżnicowanych scenariuszy testowych
        for (int scenario = 1; scenario <= 7; scenario++) {
            System.out.println("\n----------------------------------------------------------------");
            System.out.println(">>> URUCHAMIANIE SCENARIUSZA " + scenario + " <<<");
            System.out.println("----------------------------------------------------------------");

            // Izolacja: Nowe makiety dla każdego przebiegu pętli
            AttackCounter mockCounter = mock(AttackCounter.class);
            AttackCalculator mockCalculator = mock(AttackCalculator.class);
            ChessboardWriter mockWriter = mock(ChessboardWriter.class);
            ChessboardReader mockReader = mock(ChessboardReader.class);

            // Konfiguracja specyficznych danych wyjściowych dla algorytmów i I/O
            configureAdvancedScenarios(scenario, mockCounter, mockCalculator, mockWriter, mockReader);

            // Wstrzyknięcie dublerów do komponentu biznesowego
            ChessboardEditor editor = new ChessboardEditor(mockCounter, mockCalculator, mockWriter, mockReader);

            try {
                executeWorkflow(scenario, editor);
            } catch (IOException e) {
                System.out.println("[PRZECHWYCONY BŁĄD SYSTEMU PLIKÓW W MAIN] " + e.getMessage());
            }

            Thread.sleep(1000);

        }
        System.out.println("\n=== PIPELINE ZAKOŃCZONY: PRZETESTOWANO 7 KOMPLETNYCH SCENARIUSZY ===");
    }

    private static void configureAdvancedScenarios(int scenario, AttackCounter counter, AttackCalculator calculator,
                                                   ChessboardWriter writer, ChessboardReader reader) {
        try {
            switch (scenario) {
                case 1:
                    // SCENARIUSZ 1: Pusta szachownica (Minimalny stan graniczny)
                    System.out.println("[Konfiguracja Mock] Analiza pustej planszy.");
                    when(counter.count(any(Chessboard.class))).thenReturn(0);
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(Collections.emptyList());
                    break;

                case 2:
                    // SCENARIUSZ 2: Pojedynczy pionek (Atak po przekątnej)
                    System.out.println("[Konfiguracja Mock] Analiza pojedynczego pionka.");
                    when(counter.count(any(Chessboard.class))).thenReturn(2);
                    List<Position> pawnAttacks = Arrays.asList(new Position(3, 3), new Position(3, 5));
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(pawnAttacks);
                    break;

                case 3:
                    // SCENARIUSZ 3: Skoczek (Knight) na brzegu szachownicy
                    System.out.println("[Konfiguracja Mock] Analiza skoczka na krawędzi (ograniczony zasięg).");
                    when(counter.count(any(Chessboard.class))).thenReturn(4);
                    List<Position> knightAttacks = Arrays.asList(
                            new Position(3, 2), new Position(4, 3),
                            new Position(4, 5), new Position(3, 6)
                    );
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(knightAttacks);
                    break;

                case 4:
                    // SCENARIUSZ 4: Potężny zasięg Hetmana (Queen) w centrum szachownicy
                    System.out.println("[Konfiguracja Mock] Analiza centralnie ustawionego Hetmana (Maksymalny zasięg).");
                    when(counter.count(any(Chessboard.class))).thenReturn(27);
                    List<Position> queenAttacks = Arrays.asList(
                            new Position(4, 1), new Position(4, 2), new Position(4, 3), // Poziom
                            new Position(1, 4), new Position(2, 4), new Position(3, 4), // Pion
                            new Position(1, 1), new Position(2, 2), new Position(3, 3)  // Skosy (skrócona lista dla czytelności)
                    );
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(queenAttacks);
                    break;

                case 5:
                    // SCENARIUSZ 5: Kolizja i blokowanie linii ataku przez inne figury
                    System.out.println("[Konfiguracja Mock] Analiza blokowania linii strzału (figura zasłonięta).");
                    when(counter.count(any(Chessboard.class))).thenReturn(5);
                    List<Position> blockedAttacks = Arrays.asList(
                            new Position(1, 1), new Position(1, 2), new Position(1, 3)
                    );
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(blockedAttacks);
                    break;

                case 6:
                    // SCENARIUSZ 6: Błąd zapisu I/O (Zła ścieżka) przy poprawnym wyniku analizy
                    System.out.println("[Konfiguracja Mock] Poprawna analiza, ale wymuszenie błędu zapisu pliku.");
                    when(counter.count(any(Chessboard.class))).thenReturn(12);
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(List.of(new Position(5, 5)));

                    doThrow(new IOException("Krytyczny błąd zapisu: Niepoprawna ścieżka systemowa!"))
                            .when(writer).save(any(Chessboard.class), eq("/błędna_ścieżka/zapis.json"));
                    break;

                case 7:
                    // SCENARIUSZ 7: Załaduj gotowy układ z pliku z unikalnymi danymi analitycznymi
                    System.out.println("[Konfiguracja Mock] Odczyt z pliku zakończony sukcesem.");
                    Chessboard loadedBoard = new Chessboard();
                    loadedBoard.addPiece(new Position(8, 8), "Black King");

                    when(reader.load("/konfiguracje/partia_arcymistrzowska.json")).thenReturn(loadedBoard);
                    when(counter.count(loadedBoard)).thenReturn(42); // Unikalny wynik dla tej konkretnej planszy
                    when(calculator.calculateAttack(loadedBoard)).thenReturn(Arrays.asList(new Position(7, 7), new Position(7, 8)));
                    break;
            }
        } catch (IOException e) {
            // Ignorowane podczas konfiguracji definicji zachowań makiet
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
                editor.placePiece(1, 4, "White Knight"); // Brzeg szachownicy
                break;
            case 4:
                editor.placePiece(4, 4, "White Queen");  // Centrum szachownicy
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
                System.out.println("[Test] Figury zostały zaimportowane z pliku zewnętrznego.");
                break;
        }

        // KROK 3: Wywołanie i weryfikacja logiki analitycznej (Count & Calculate)
        editor.executeAnalysis();

        // KROK 4: Persystencja stanu końcowego
        if (scenario == 6) {
            editor.saveBoard("/błędna_ścieżka/zapis.json"); // Wywoła rzucenie wyjątku z Mockito
        } else if (scenario != 7) {
            editor.saveBoard("/pliki/szachownica_wynik_" + scenario + ".json");
        }
    }
}