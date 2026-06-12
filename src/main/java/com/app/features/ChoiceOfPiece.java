package com.app.features;

import com.app.Square;

import java.util.List;
import java.util.Scanner;

public class ChoiceOfPiece {
    public int choicePiece(List<Square> moves, Scanner scanner) {

        System.out.println("Wybierz, którego pionka chcesz ruszyć:");
        for (int i = 0; i < moves.size(); i++) {
            Square square = moves.get(i);
            System.out.printf("[%d] Pionek na pozycji: %s%n", (i + 1), square);
        }
        int choice = -1;
        while (choice < 1 || choice > moves.size()) {
            System.out.print("Wpisz numer pionka: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > moves.size()) {
                    System.out.println("Nieprawidłowy numer, spróbuj ponownie.");
                }
            } else {
                System.out.println("To nie jest liczba!");
                scanner.next(); // Czyszczenie błędnego tokenu
            }
        }

        return choice;
    }
}
