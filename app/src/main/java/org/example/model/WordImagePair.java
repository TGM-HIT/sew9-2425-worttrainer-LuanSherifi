package org.example.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Repräsentiert ein Wort-Bild-Paar.
 */
public class WordImagePair {
    private String word;
    private String imageUrl;

    /**
     * Konstruktor für WordImagePair.
     *
     * @param word     das zugehörige Wort
     * @param imageUrl die URL des Bildes
     * @throws IllegalArgumentException bei ungültigen Eingaben
     */
    public WordImagePair(String word, String imageUrl) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Wort darf nicht null oder leer sein.");
        }
        if (!isValidURL(imageUrl)) {
            throw new IllegalArgumentException("Ungültige Bild-URL.");
        }
        this.word = word;
        this.imageUrl = imageUrl;
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public String getWord() {
        return word;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // equals und hashCode für korrekte Vergleiche und Collections

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordImagePair that = (WordImagePair) o;

        if (!word.equals(that.word)) return false;
        return imageUrl.equals(that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, imageUrl);
    }

    @Override
    public String toString() {
        return "WordImagePair{" +
                "word='" + word + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
