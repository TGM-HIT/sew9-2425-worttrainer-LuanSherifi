package sherifi.worttrainer.persistence;

import sherifi.worttrainer.service.WorttrainerService;

/**
 * Interface für den Persistenz-Service.
 */
public interface PersistenzService {
    void speichern(WorttrainerService worttrainerService);

    WorttrainerService laden();
}
