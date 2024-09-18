package org.example.persistence;

import org.example.model.SpellTrainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Verantwortlich für das Speichern und Laden des SpellTrainer-Zustands.
 */
public class PersistenceManager {
    private static final String FILE_PATH = "worttrainer.json";
    private Gson gson;

    public PersistenceManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Speichert den aktuellen Zustand des SpellTrainers in eine JSON-Datei.
     *
     * @param trainer der zu speichernde SpellTrainer
     * @throws IOException bei Schreibfehlern
     */
    public void save(SpellTrainer trainer) throws IOException {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(trainer, writer);
        }
    }

    /**
     * Lädt den SpellTrainer-Zustand aus einer JSON-Datei.
     *
     * @return der geladene SpellTrainer oder null, wenn keine Datei existiert
     * @throws IOException bei Lesefehlern
     */
    public SpellTrainer load() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return null;
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, SpellTrainer.class);
        }
    }
}
