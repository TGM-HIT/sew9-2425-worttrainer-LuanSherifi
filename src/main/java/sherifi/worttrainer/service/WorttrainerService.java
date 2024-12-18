package sherifi.worttrainer.service;

import sherifi.worttrainer.model.Statistik;
import sherifi.worttrainer.model.WortBildPaar;
import sherifi.worttrainer.persistence.PersistenzService;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Verwaltet die Logik des Worttrainers.
 */
public class WorttrainerService {
    private List<WortBildPaar> wortBildPaare;
    private WortBildPaar aktuellesPaar;
    private Statistik statistik;
    private boolean letzterVersuchRichtig;
    private PersistenzService persistenzService;

    /**
     * Standardkonstruktor.
     */
    public WorttrainerService() {
        this.wortBildPaare = new ArrayList<>();
        this.aktuellesPaar = null;
        this.statistik = new Statistik();
        this.letzterVersuchRichtig = false;
        this.persistenzService = null;
    }

    /**
     * Setter für PersistenzService.
     *
     * @param persistenzService das PersistenzService
     */
    public void setPersistenzService(PersistenzService persistenzService) {
        if (persistenzService == null) {
            throw new IllegalArgumentException("PersistenzService darf nicht null sein.");
        }
        this.persistenzService = persistenzService;
    }

    /**
     * Speichert den aktuellen Zustand des Worttrainers.
     *
     * @throws JSONException falls ein JSON-Fehler auftritt
     */
    public void speichern() throws JSONException {
        if (persistenzService == null) {
            throw new IllegalStateException("PersistenzService ist nicht gesetzt.");
        }
        persistenzService.speichern(this);
    }

    // Bestehende Methoden bleiben unverändert

    public void addWortBildPaar(WortBildPaar paar) {
        if (paar == null) {
            throw new IllegalArgumentException("Das Wort-Bild-Paar darf nicht null sein.");
        }
        wortBildPaare.add(paar);
    }

    public void waehlePaar(int index) {
        if (index < 0 || index >= wortBildPaare.size()) {
            throw new IndexOutOfBoundsException("Index außerhalb des gültigen Bereichs.");
        }
        aktuellesPaar = wortBildPaare.get(index);
        letzterVersuchRichtig = false;
    }

    public void waehleZufaelligesPaar() {
        Random rand = new Random();
        int index = rand.nextInt(wortBildPaare.size());
        aktuellesPaar = wortBildPaare.get(index);
        letzterVersuchRichtig = false;
    }

    public WortBildPaar getAktuellesPaar() {
        return aktuellesPaar;
    }

    public boolean rateWort(String eingabe) {
        if (aktuellesPaar == null) {
            throw new IllegalStateException("Kein aktuelles Wort-Bild-Paar ausgewählt.");
        }
        if (eingabe == null) {
            throw new IllegalArgumentException("Eingabe darf nicht null sein.");
        }
        statistik.erhoeheGesamtVersuche();
        if (aktuellesPaar.getWort().equalsIgnoreCase(eingabe.trim())) {
            statistik.erhoeheRichtigeVersuche();
            aktuellesPaar = null;
            letzterVersuchRichtig = true;
            return true;
        } else {
            statistik.erhoeheFalscheVersuche();
            letzterVersuchRichtig = false;
            return false;
        }
    }

    public Statistik getStatistik() {
        return statistik;
    }

    public boolean isLetzterVersuchRichtig() {
        return letzterVersuchRichtig;
    }

    public void setLetzterVersuchRichtig(boolean letzterVersuchRichtig) {
        this.letzterVersuchRichtig = letzterVersuchRichtig;
    }

    public boolean istPaarAusgewaehlt() {
        return aktuellesPaar != null;
    }

    public List<WortBildPaar> getWortBildPaare() {
        return new ArrayList<>(wortBildPaare);
    }

    public void setWortBildPaare(List<WortBildPaar> paare) {
        if (paare == null) {
            throw new IllegalArgumentException("Die Liste der Wort-Bild-Paare darf nicht null sein.");
        }
        this.wortBildPaare = new ArrayList<>(paare);
    }

    public void setAktuellesPaar(WortBildPaar paar) {
        this.aktuellesPaar = paar;
    }

    public void setStatistik(Statistik statistik) {
        if (statistik == null) {
            throw new IllegalArgumentException("Die Statistik darf nicht null sein.");
        }
        this.statistik = statistik;
    }
}
