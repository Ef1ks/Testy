package com.app;

import com.app.doublers.AttackCalculator;
import com.app.doublers.AttackCounter;
import com.app.doublers.ChessboardReader;
import com.app.doublers.ChessboardWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.app.Workflow.executeWorkflow;
import static org.mockito.Mockito.*;

public class MainOld {

    static void main(String[] args) throws InterruptedException {

        ListOfScenarios list = new ListOfScenarios(Arrays.asList(1, 2, 3, 4, 5, 6, 7));

        // Za pomocą Mockito tworzymy dynamiczne makiety dla wszystkich zależności, które chcemy kontrolować i iterujemy po nich

        for (int scenario : list.getScenarios()) {

            System.out.println("");
            System.out.println("=== SCENARIUSZ " + scenario + " ===");
            System.out.println("");

            AttackCounter mockCounter = mock(AttackCounter.class);
            AttackCalculator mockCalculator = mock(AttackCalculator.class);
            ChessboardWriter mockWriter = mock(ChessboardWriter.class);
            ChessboardReader mockReader = mock(ChessboardReader.class);

            ChessboardEditor editor = new ChessboardEditor(mockCounter, mockCalculator, mockWriter, mockReader);
            try {
                executeWorkflow(scenario, editor);
            } catch (IOException e) {
                System.out.println("BLAD W MAIN" + e.getMessage());
            }
            configureAdvancedScenarios(scenario, mockCounter, mockCalculator, mockWriter, mockReader);
            Thread.sleep(2000);

        }
        System.out.println("\n=== PRZETESTOWANO 7 KOMPLETNYCH SCENARIUSZY ===");
    }

    private static void configureAdvancedScenarios(int scenario, AttackCounter counter, AttackCalculator calculator,
                                                   ChessboardWriter writer, ChessboardReader reader) {
        try {
            switch (scenario) {
                case 1:
                    // SCENARIUSZ 1: Pusta szachownica
                    System.out.println("Test pustej tablicy.");
                    when(counter.count(any(Chessboard.class))).thenReturn(0);
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(Collections.emptyList());
                    break;

                case 2:
                    // SCENARIUSZ 2: Pojedynczy pionek na szachownicy
                    System.out.println("Test pojedynczego pionka.");
                    when(counter.count(any(Chessboard.class))).thenReturn(2);
                    List<Position> pawnAttacks = Arrays.asList(new Position(3, 3), new Position(3, 5));
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(pawnAttacks);
                    break;

                case 3:
                    // SCENARIUSZ 3: Skoczek na brzegu szachownicy
                    System.out.println("Test skoczka na krawędzi.");
                    when(counter.count(any(Chessboard.class))).thenReturn(4);
                    List<Position> knightAttacks = Arrays.asList(
                            new Position(3, 2), new Position(4, 3),
                            new Position(4, 5), new Position(3, 6)
                    );
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(knightAttacks);
                    break;

                case 4:
                    // SCENARIUSZ 4: Potężny zasięg Hetmana w centrum szachownicy
                    System.out.println("Test centralnie ustawionego hetmana");
                    when(counter.count(any(Chessboard.class))).thenReturn(27);
                    List<Position> queenAttacks = Arrays.asList(
                            new Position(4, 1), new Position(4, 2), new Position(4, 3), // Poziom
                            new Position(1, 4), new Position(2, 4), new Position(3, 4), // Pion
                            new Position(1, 1), new Position(2, 2), new Position(3, 3)  // Skosy
                    );
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(queenAttacks);
                    break;

                case 5:
                    // SCENARIUSZ 5: Kolizja i blokowanie linii ataku przez inne figury
                    System.out.println("Test blokowania linii.");
                    when(counter.count(any(Chessboard.class))).thenReturn(5);
                    List<Position> blockedAttacks = Arrays.asList(
                            new Position(1, 1), new Position(1, 2), new Position(1, 3)
                    );
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(blockedAttacks);
                    break;

                case 6:
                    // SCENARIUSZ 6: Błąd zapisu przy poprawnym wyniku analizy
                    System.out.println("Poprawny test ruchu, ale wymuszenie błędu zapisu pliku.");
                    when(counter.count(any(Chessboard.class))).thenReturn(12);
                    when(calculator.calculateAttack(any(Chessboard.class))).thenReturn(List.of(new Position(5, 5)));

                    doThrow(new IOException("Krytyczny błąd zapisu: Niepoprawna ścieżka systemowa!"))
                            .when(writer).save(any(Chessboard.class), eq("/błędna_ścieżka/zapis.json"));
                    break;

                case 7:
                    // SCENARIUSZ 7: Załaduj gotowy układ z pliku z unikalnymi danymi analitycznymi
                    System.out.println("Odczyt z pliku zakończony sukcesem.");
                    Chessboard loadedBoard = new Chessboard();
                    loadedBoard.addPiece(new Position(8, 8), "Black King");

                    when(reader.load("/konfiguracje/partia_arcymistrzowska.json")).thenReturn(loadedBoard);
                    when(counter.count(loadedBoard)).thenReturn(42); // Unikalny wynik dla tej konkretnej planszy
                    when(calculator.calculateAttack(loadedBoard)).thenReturn(Arrays.asList(new Position(7, 7), new Position(7, 8)));
                    break;
            }
        } catch (IOException _) {
        }
    }
}