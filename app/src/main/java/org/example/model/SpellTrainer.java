package org.example.model;

import java.util.*;

import org.example.worttrainer.persistence.PersistenceManager;

/**
 * Repräsentiert den Rechtschreibtrainer.
 */
public class SpellTrainer {
    private List<WordImagePair> wordImagePairs;
    private WordImagePair currentPair;
    private Statistics statistics;

    /**
     * Konstruktor für SpellTrainer.
     */
    public SpellTrainer() {
        this.wordImagePairs = new ArrayList<>();
        this.currentPair = null;
        this.statistics = new Statistics();
    }

    /**
     * Fügt ein neues Wort-Bild-Paar hinzu.
     *
     * @param pair das hinzuzufügende Paar
     */
    public void addWordImagePair(WordImagePair pair) {
        if (pair == null) {
            throw new IllegalArgumentException("Wort-Bild-Paar darf nicht null sein.");
        }
        wordImagePairs.add(pair);
    }

    /**
     * Wählt ein zufälliges Wort-Bild-Paar aus.
     *
     * @return das ausgewählte Paar
     */
    public WordImagePair selectRandomPair() {
        if (wordImagePairs.isEmpty()) {
            throw new IllegalStateException("Keine Wort-Bild-Paare verfügbar.");
        }
        Random random = new Random();
        currentPair = wordImagePairs.get(random.nextInt(wordImagePairs.size()));
        return currentPair;
    }

    /**
     * Überprüft die eingegebene Antwort.
     *
     * @param input die eingegebene Antwort
     * @return true, wenn korrekt; false sonst
     */
    public boolean checkAnswer(String input) {
        if (currentPair == null) {
            throw new IllegalStateException("Kein aktuelles Wort-Bild-Paar ausgewählt.");
        }
        if (input == null) {
            throw new IllegalArgumentException("Eingabe darf nicht null sein.");
        }

        statistics.incrementTotal();
        if (currentPair.getWord().equalsIgnoreCase(input.trim())) {
            statistics.incrementCorrect();
            wordImagePairs.remove(currentPair);
            currentPair = null;
            return true;
        } else {
            statistics.incrementIncorrect();
            return false;
        }
    }

    public List<WordImagePair> getWordImagePairs() {
        return Collections.unmodifiableList(wordImagePairs);
    }

    public WordImagePair getCurrentPair() {
        return currentPair;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Statistikklasse zur Verfolgung der Versuche.
     */
    public static class Statistics {
        private int total;
        private int correct;
        private int incorrect;

        public Statistics() {
            this.total = 0;
            this.correct = 0;
            this.incorrect = 0;
        }

        public void incrementTotal() {
            total++;
        }

        public void incrementCorrect() {
            correct++;
        }

        public void incrementIncorrect() {
            incorrect++;
        }

        public int getTotal() {
            return total;
        }

        public int getCorrect() {
            return correct;
        }

        public int getIncorrect() {
            return incorrect;
        }

        @Override
        public String toString() {
            return "Statistik: Gesamtversuche = " + total + ", Richtig = " + correct + ", Falsch = " + incorrect;
        }
    }
}
