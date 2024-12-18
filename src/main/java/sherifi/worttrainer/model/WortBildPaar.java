    package sherifi.worttrainer.model;

    import java.net.MalformedURLException;
    import java.net.URL;

    /**
     * Repräsentiert ein Paar aus Wort und zugehöriger Bild-URL.
     */
    public class WortBildPaar {
        private String wort;
        private URL bildUrl;

        public WortBildPaar(String wort, String bildUrlString) throws MalformedURLException {
            setWort(wort);
            setBildUrl(bildUrlString);
        }

        public String getWort() {
            return wort;
        }

        public void setWort(String wort) {
            if (wort == null || wort.trim().isEmpty()) {
                throw new IllegalArgumentException("Das Wort darf nicht null oder leer sein.");
            }
            this.wort = wort;
        }

        public URL getBildUrl() {
            return bildUrl;
        }

        public void setBildUrl(String bildUrlString) throws MalformedURLException {
            if (bildUrlString == null || bildUrlString.trim().isEmpty()) {
                throw new IllegalArgumentException("Die Bild-URL darf nicht null oder leer sein.");
            }
            this.bildUrl = new URL(bildUrlString);
        }

        @Override
        public String toString() {
            return "WortBildPaar{" +
                    "wort='" + wort + '\'' +
                    ", bildUrl=" + bildUrl +
                    '}';
        }
    }
