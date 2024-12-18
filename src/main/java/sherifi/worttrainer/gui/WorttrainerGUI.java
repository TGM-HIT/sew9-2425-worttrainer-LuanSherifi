package sherifi.worttrainer.gui;

import org.json.JSONException;
import sherifi.worttrainer.model.WortBildPaar;
import sherifi.worttrainer.service.WorttrainerService;

import javax.swing.*;
import java.net.URL;

/**
 * Verwaltet die Benutzeroberfläche des Worttrainers.
 */
public class WorttrainerGUI {
    private WorttrainerService worttrainerService;

    /**
     * Konstruktor, der eine WorttrainerService verwendet.
     *
     * @param worttrainerService das WorttrainerService
     */
    public WorttrainerGUI(WorttrainerService worttrainerService) {
        if (worttrainerService == null) {
            throw new IllegalArgumentException("WorttrainerService darf nicht null sein.");
        }
        this.worttrainerService = worttrainerService;
    }

    /**
     * Startet den Worttrainer und lässt den User beginnen
     *
     * @throws JSONException
     */
    public void starten() throws JSONException {
        boolean weitermachen = true;
        while (weitermachen) {
            // Wähle zufälliges WortBildPaar
            worttrainerService.waehleZufaelligesPaar();
            WortBildPaar aktuellesPaar = worttrainerService.getAktuellesPaar();

            boolean ersterVersuch = true;
            boolean korrekt = false;

            while (!korrekt) {
                String nachricht = erstelleNachricht(ersterVersuch);
                String eingabe = zeigeEingabeDialog(nachricht, aktuellesPaar.getBildUrl());
                if (eingabe == null || eingabe.trim().isEmpty()) {
                    // Benutzer hat abgebrochen oder leere Eingabe
                    weitermachen = false;
                    break;
                }

                korrekt = worttrainerService.rateWort(eingabe);
                ersterVersuch = false;
                if (korrekt) {
                    JOptionPane.showMessageDialog(null, "Richtig!", "Ergebnis", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Falsch! Versuche es noch einmal.", "Ergebnis", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (!weitermachen) {
                break;
            }
        }

        // Speichere den aktuellen Zustand
        worttrainerService.speichern();
    }

    /**
     * Zeigt die Statistiken an
     *
     * @param ersterVersuch die Richtigkeit des Versuches
     * @return
     */
    private String erstelleNachricht(boolean ersterVersuch) {
        StringBuilder sb = new StringBuilder();
        sb.append("Statistik:\n");
        sb.append("Gesamtversuche: ").append(worttrainerService.getStatistik().getGesamtVersuche()).append("\n");
        sb.append("Richtige Versuche: ").append(worttrainerService.getStatistik().getRichtigeVersuche()).append("\n");
        sb.append("Falsche Versuche: ").append(worttrainerService.getStatistik().getFalscheVersuche()).append("\n\n");
        if (!ersterVersuch) {
            if (worttrainerService.isLetzterVersuchRichtig()) {
                sb.append("Der letzte Versuch war richtig.\n\n");
            } else {
                sb.append("Der letzte Versuch war falsch.\n\n");
            }
        }
        sb.append("Bitte gib das Wort zum angezeigten Bild ein:");
        return sb.toString();
    }

    private String zeigeEingabeDialog(String nachricht, URL bildUrl) {
        ImageIcon icon = ladeBild(bildUrl);
        String eingabe = (String) JOptionPane.showInputDialog(null, nachricht, "Worttrainer",
                JOptionPane.PLAIN_MESSAGE, icon, null, "");
        return eingabe;
    }

    private ImageIcon ladeBild(URL bildUrl) {
        try {
            return new ImageIcon(bildUrl);
        } catch (Exception e) {
            e.printStackTrace();
            // Wenn das Bild nicht geladen werden kann, kein Icon verwenden
            return null;
        }
    }
}
