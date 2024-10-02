package sherifi.worttrainer.persistence;

import org.json.JSONException;
import sherifi.worttrainer.model.Statistik;
import sherifi.worttrainer.model.WortBildPaar;
import org.json.JSONArray;
import org.json.JSONObject;
import sherifi.worttrainer.service.WorttrainerService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementiert den PersistenzService mit JSON als Speicherformat.
 */
public class JsonPersistenzService implements PersistenzService {
    private String dateiPfad;

    public JsonPersistenzService(String dateiPfad) {
        if (dateiPfad == null || dateiPfad.trim().isEmpty()) {
            throw new IllegalArgumentException("Der Dateipfad darf nicht null oder leer sein.");
        }
        this.dateiPfad = dateiPfad;
    }

    @Override
    public void speichern(WorttrainerService worttrainerService) throws JSONException {
        JSONObject jsonObj = new JSONObject();

        // Speichere WortBildPaare
        JSONArray paareArray = new JSONArray();
        for (WortBildPaar paar : worttrainerService.getWortBildPaare()) {
            JSONObject paarObj = new JSONObject();
            paarObj.put("wort", paar.getWort());
            paarObj.put("bildUrl", paar.getBildUrl().toString());
            paareArray.put(paarObj);
        }
        jsonObj.put("wortBildPaare", paareArray);

        // Speichere aktuellesPaar
        WortBildPaar aktuellesPaar = worttrainerService.getAktuellesPaar();
        if (aktuellesPaar != null) {
            JSONObject aktuellesPaarObj = new JSONObject();
            aktuellesPaarObj.put("wort", aktuellesPaar.getWort());
            aktuellesPaarObj.put("bildUrl", aktuellesPaar.getBildUrl().toString());
            jsonObj.put("aktuellesPaar", aktuellesPaarObj);
        }

        // Speichere Statistik
        Statistik statistik = worttrainerService.getStatistik();
        JSONObject statistikObj = new JSONObject();
        statistikObj.put("gesamtVersuche", statistik.getGesamtVersuche());
        statistikObj.put("richtigeVersuche", statistik.getRichtigeVersuche());
        statistikObj.put("falscheVersuche", statistik.getFalscheVersuche());
        jsonObj.put("statistik", statistikObj);

        // Speichere letzterVersuchRichtig
        jsonObj.put("letzterVersuchRichtig", worttrainerService.isLetzterVersuchRichtig());

        try (FileWriter file = new FileWriter(dateiPfad)) {
            file.write(jsonObj.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
            // Fehlerbehandlung
        }
    }

    /**
     * Lädt den Worttrainer mit den Daten vom JSON File
     * @return Die Daten im File
     * @throws JSONException
     */
    @Override
    public WorttrainerService laden() throws JSONException {
        File file = new File(dateiPfad);
        if (!file.exists()) {
            return null; // Keine persistierten Daten vorhanden
        }

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder jsonStr = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonStr.append(scanner.nextLine());
            }
            JSONObject jsonObj = new JSONObject(jsonStr.toString());

            WorttrainerService worttrainerService = new WorttrainerService();

            // Lade WortBildPaare
            List<WortBildPaar> paareList = new ArrayList<>();
            JSONArray paareArray = jsonObj.getJSONArray("wortBildPaare");
            for (int i = 0; i < paareArray.length(); i++) {
                JSONObject paarObj = paareArray.getJSONObject(i);
                String wort = paarObj.getString("wort");
                String bildUrl = paarObj.getString("bildUrl");
                WortBildPaar paar = new WortBildPaar(wort, bildUrl);
                paareList.add(paar);
            }
            worttrainerService.setWortBildPaare(paareList);

            // Lade aktuellesPaar
            if (jsonObj.has("aktuellesPaar")) {
                JSONObject aktuellesPaarObj = jsonObj.getJSONObject("aktuellesPaar");
                String wort = aktuellesPaarObj.getString("wort");
                String bildUrl = aktuellesPaarObj.getString("bildUrl");
                WortBildPaar aktuellesPaar = new WortBildPaar(wort, bildUrl);
                worttrainerService.setAktuellesPaar(aktuellesPaar);
            }

            // Lade Statistik
            JSONObject statistikObj = jsonObj.getJSONObject("statistik");
            Statistik statistik = new Statistik();
            statistik.setGesamtVersuche(statistikObj.getInt("gesamtVersuche"));
            statistik.setRichtigeVersuche(statistikObj.getInt("richtigeVersuche"));
            statistik.setFalscheVersuche(statistikObj.getInt("falscheVersuche"));
            worttrainerService.setStatistik(statistik);

            // Lade letzterVersuchRichtig
            if (jsonObj.has("letzterVersuchRichtig")) {
                boolean letzterVersuchRichtig = jsonObj.getBoolean("letzterVersuchRichtig");
                worttrainerService.setLetzterVersuchRichtig(letzterVersuchRichtig);
            }

            return worttrainerService;
        } catch (IOException e) {
            e.printStackTrace();
            // Fehlerbehandlung
            return null;
        }
    }
}
