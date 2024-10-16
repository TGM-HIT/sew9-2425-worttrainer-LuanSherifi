package sherifi.worttrainer.gui;

import org.json.JSONException;
import sherifi.worttrainer.model.WortBildPaar;
import sherifi.worttrainer.persistence.JsonPersistenzService;
import sherifi.worttrainer.persistence.PersistenzService;
import sherifi.worttrainer.service.WorttrainerService;

import javax.swing.*;
import java.net.URL;

/**
 * Verwaltet die Benutzeroberfläche des Worttrainers.
 */
public class WorttrainerGUI {
    private WorttrainerService worttrainerService;
    private PersistenzService persistenzService;

    /**
     * GUI Methode, startet das Programm
     * @throws JSONException
     */
    public WorttrainerGUI() throws JSONException {
        persistenzService = new JsonPersistenzService("worttrainer.json");
        worttrainerService = persistenzService.laden();
        if (worttrainerService == null) {
            worttrainerService = new WorttrainerService();
            // Füge einige vordefinierte Wort-Bild-Paare hinzu
            try {
                worttrainerService.addWortBildPaar(new WortBildPaar("Hund", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgQmEm06BSRd0EopLiNYprCoHc15FkT37RBQ&s"));
                worttrainerService.addWortBildPaar(new WortBildPaar("Katze", "https://media.os.fressnapf.com/cms/2021/05/katze_gefunde_zugelaufen_1200x527.jpg?t=seoimgsqr_527"));
                worttrainerService.addWortBildPaar(new WortBildPaar("Baum", "https://nikinclothing.com/cdn/shop/articles/der-baum-ein-meisterwerk-der-natur-356835.jpg?v=1713177674"));
                worttrainerService.addWortBildPaar(new WortBildPaar("Auto", "https://www.autoscout24.at/cms-content-assets/4mpSnBCf7cpBksE9w2nKmf-7d11ffa37ae4a3d95ae4823031e282f9-5kK7vUN4DMykm4fhsi0Pc-b1070949849a06dd22109fab7a99b8a2-lamborghini-huracan-tecnica-front-1100-1100.jpeg"));
                worttrainerService.addWortBildPaar(new WortBildPaar("SEW", "https://miro.medium.com/v2/resize:fit:1400/1*3OWWk9BUargTyvFGQpBsOA.png"));
                worttrainerService.addWortBildPaar(new WortBildPaar("Test", "https://www.anti-bias.eu/wp-content/uploads/2015/01/shutterstock_92612287-e1420280083718.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Startet den Worttrainer und lässt den User beginnen
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
        persistenzService.speichern(worttrainerService);
    }

    /**
     * Zeigt die Statistiken an
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
