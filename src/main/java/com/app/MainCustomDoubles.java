package com.app;

import com.app.doublers.StubAttackCalculator;
import com.app.doublers.StubAttackCounter;
import com.app.doublers.FakeChessboard;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.app.Workflow.executeWorkflow;

public class MainCustomDoubles {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ZAAWANSOWANY POTOK TESTOWY: WŁASNE DUBLERY (ANALOGICZNY) ===");

        ListOfScenarios list = new ListOfScenarios(Arrays.asList(1, 2, 3, 4, 5, 6, 7));

        // Za pomocą własnych dublerów tworzymy dynamiczne makiety dla wszystkich zależności, które chcemy kontrolować i iterujemy po nich
        for (int scenario : list.getScenarios()) {
            System.out.println("");
            System.out.println("=== SCENARIUSZ " + scenario + " ===");
            System.out.println("");

            StubAttackCounter customCounter = new StubAttackCounter();
            StubAttackCalculator customCalculator = new StubAttackCalculator();
            FakeChessboard customStorage = new FakeChessboard();

            configureAdvancedScenarios(scenario, customCounter, customCalculator, customStorage);

            ChessboardEditor editor = new ChessboardEditor(customCounter, customCalculator, customStorage, customStorage);

            try {
                executeWorkflow(scenario, editor);
            } catch (IOException e) {
                System.out.println("BLAD W MAIN " + e.getMessage());
            }

            Thread.sleep(1000);
        }
        System.out.println("\n=== PRZETESTOWANO WSZYSTKIE SCENARIUSZE ===");
    }

    private static void configureAdvancedScenarios(int scenario, StubAttackCounter counter, StubAttackCalculator calculator,
                                                   FakeChessboard storage) {
        switch (scenario) {
            case 1:
                // SCENARIUSZ 1: Pusta szachownica
                System.out.println("Test pustej tablicy.");
                counter.stubValue(0);
                calculator.stubPositions(Collections.emptyList());
                break;

            case 2:
                // SCENARIUSZ 2: Pojedynczy pionek na szachownicy
                System.out.println("Test pojedynczego pionka.");
                counter.stubValue(2);
                List<Position> pawnAttacks = Arrays.asList(new Position(3, 3), new Position(3, 5));
                calculator.stubPositions(pawnAttacks);
                break;

            case 3:
                // SCENARIUSZ 3: Skoczek na brzegu szachownicy
                System.out.println("Test skoczka na krawędzi.");
                counter.stubValue(4);
                List<Position> knightAttacks = Arrays.asList(
                        new Position(3, 2), new Position(4, 3),
                        new Position(4, 5), new Position(3, 6)
                );
                calculator.stubPositions(knightAttacks);
                break;

            case 4:
                // SCENARIUSZ 4: Potężny zasięg Hetmana w centrum szachownicy
                System.out.println("Test centralnie ustawionego hetmana");
                counter.stubValue(27);
                List<Position> queenAttacks = Arrays.asList(
                        new Position(4, 1), new Position(4, 2), new Position(4, 3),
                        new Position(1, 4), new Position(2, 4), new Position(3, 4),
                        new Position(1, 1), new Position(2, 2), new Position(3, 3)
                );
                calculator.stubPositions(queenAttacks);
                break;

            case 5:
                // SCENARIUSZ 5: Kolizja i blokowanie linii ataku przez inne figury
                System.out.println("Test blokowania linii.");
                counter.stubValue(5);
                List<Position> blockedAttacks = Arrays.asList(
                        new Position(1, 1), new Position(1, 2), new Position(1, 3)
                );
                calculator.stubPositions(blockedAttacks);
                break;

            case 6:
                // SCENARIUSZ 6: Błąd zapisu przy poprawnym wyniku analizy
                System.out.println("Poprawny test ruchu, ale wymuszenie błędu zapisu pliku.");
                counter.stubValue(12);
                calculator.stubPositions(List.of(new Position(5, 5)));
                storage.failOnWrite("/błędna_ścieżka/zapis.json",
                        new IOException("Krytyczny błąd zapisu: Niepoprawna ścieżka systemowa!"));
                break;

            case 7:
                // SCENARIUSZ 7: Załaduj gotowy układ z pliku z unikalnymi danymi analitycznymi
                System.out.println("Odczyt z pliku zakończony sukcesem.");
                Chessboard loadedBoard = new Chessboard();
                loadedBoard.addPiece(new Position(8, 8), "Black King");


                storage.addPredefinedBoard("/konfiguracje/partia_arcymistrzowska.json", loadedBoard);
                counter.stubValue(42);
                calculator.stubPositions(Arrays.asList(new Position(7, 7), new Position(7, 8)));
                break;
        }
    }
}