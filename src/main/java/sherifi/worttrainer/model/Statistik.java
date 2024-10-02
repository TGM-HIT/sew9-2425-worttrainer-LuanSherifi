package sherifi.worttrainer.model;

/**
 * Hält die Statistik über die geratenen Wörter.
 */
public class Statistik {
    private int gesamtVersuche;
    private int richtigeVersuche;
    private int falscheVersuche;

    public Statistik() {
        this.gesamtVersuche = 0;
        this.richtigeVersuche = 0;
        this.falscheVersuche = 0;
    }

    public int getGesamtVersuche() {
        return gesamtVersuche;
    }

    public int getRichtigeVersuche() {
        return richtigeVersuche;
    }

    public int getFalscheVersuche() {
        return falscheVersuche;
    }

    public void erhoeheGesamtVersuche() {
        this.gesamtVersuche++;
    }

    public void erhoeheRichtigeVersuche() {
        this.richtigeVersuche++;
    }

    public void erhoeheFalscheVersuche() {
        this.falscheVersuche++;
    }

    public void setGesamtVersuche(int gesamtVersuche) {
        if (gesamtVersuche < 0) {
            throw new IllegalArgumentException("Gesamtversuche dürfen nicht negativ sein.");
        }
        this.gesamtVersuche = gesamtVersuche;
    }

    public void setRichtigeVersuche(int richtigeVersuche) {
        if (richtigeVersuche < 0) {
            throw new IllegalArgumentException("Richtige Versuche dürfen nicht negativ sein.");
        }
        this.richtigeVersuche = richtigeVersuche;
    }

    public void setFalscheVersuche(int falscheVersuche) {
        if (falscheVersuche < 0) {
            throw new IllegalArgumentException("Falsche Versuche dürfen nicht negativ sein.");
        }
        this.falscheVersuche = falscheVersuche;
    }

    @Override
    public String toString() {
        return "Statistik{" +
                "gesamtVersuche=" + gesamtVersuche +
                ", richtigeVersuche=" + richtigeVersuche +
                ", falscheVersuche=" + falscheVersuche +
                '}';
    }
}
