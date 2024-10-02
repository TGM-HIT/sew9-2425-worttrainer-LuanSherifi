package sherifi.worttrainer.persistence;

import org.json.JSONException;
import sherifi.worttrainer.service.WorttrainerService;

/**
 * Interface für den Persistenz-Service.
 * Die Speichern Methode ist dafür da, den aktuellen Worttrainer in einem JSON zu speichern.
 * Die Laden Methode ist dafür da, den Worttrainer aus der JSON File zu laden.
 */
public interface PersistenzService {
    void speichern(WorttrainerService worttrainerService) throws JSONException;

    WorttrainerService laden() throws JSONException;
}
