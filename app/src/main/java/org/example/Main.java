package org.example;

import org.example.model.SpellTrainer;
import org.example.model.WordImagePair;
import org.example.persistence.PersistenceManager;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Hauptklasse für den Worttrainer.
 */
public class Main {
    public static void main(String[] args) {
        PersistenceManager persistenceManager = new PersistenceManager();
        SpellTrainer trainer = null;

        // Laden der persistierten Daten
        try {
            trainer = persistenceManager.load();
            if (trainer == null) {
                trainer = createDefaultTrainer();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Daten: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
            trainer = createDefaultTrainer();
        }

        boolean continueTraining = true;
        boolean lastAttemptCorrect = false;

        while (continueTraining) {
            WordImagePair pair = null;
            try {
                pair = trainer.selectRandomPair();
            } catch (IllegalStateException e) {
                JOptionPane.showMessageDialog(null, "Keine weiteren Wort-Bild-Paare zum Trainieren verfügbar.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            String message = trainer.getStatistics().toString() + "\n" +
                    "Bild URL: " + pair.getImageUrl() + "\n";

            if (trainer.getStatistics().getTotal() > 0) {
                message += "Letzter Versuch war " + (lastAttemptCorrect ? "richtig." : "falsch.") + "\n";
            }

            String input = JOptionPane.showInputDialog(null, message + "Bitte gib das Wort ein:",
                    "Worttrainer", JOptionPane.QUESTION_MESSAGE);

            if (input == null || input.trim().isEmpty()) {
                continueTraining = false;
                break;
            }

            boolean isCorrect;
            try {
                isCorrect = trainer.checkAnswer(input);
                lastAttemptCorrect = isCorrect;
                if (isCorrect) {
                    JOptionPane.showMessageDialog(null, "Richtig!",
                            "Ergebnis", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Falsch! Versuch es noch einmal.",
                            "Ergebnis", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler: " + e.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Speichern des aktuellen Zustands
        try {
            persistenceManager.save(trainer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern der Daten: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Erstellt einen SpellTrainer mit einigen fixen Wort-Bild-Paaren.
     *
     * @return der erstellte SpellTrainer
     */
    private static SpellTrainer createDefaultTrainer() {
        SpellTrainer trainer = new SpellTrainer();
        try {
            trainer.addWordImagePair(new WordImagePair("Hund", "https://example.com/hund.png"));
            trainer.addWordImagePair(new WordImagePair("Katze", "https://example.com/katze.png"));
            trainer.addWordImagePair(new WordImagePair("Haus", "https://example.com/haus.png"));
            // Füge weitere Paare nach Bedarf hinzu
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Erstellen der Standard-Paare: " + e.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
        return trainer;
    }
}
