package sherifi.worttrainer.persistence;

import org.json.JSONException;
import sherifi.worttrainer.service.WorttrainerService;

/**
 * Interface für den Persistenz-Service.
 */
public interface PersistenzService {
    void speichern(WorttrainerService worttrainerService) throws JSONException;

    WorttrainerService laden() throws JSONException;
}
