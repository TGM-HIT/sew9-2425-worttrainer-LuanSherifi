package sherifi.worttrainer;

import org.json.JSONException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sherifi.worttrainer.gui.WorttrainerGUI;
import sherifi.worttrainer.model.WortBildPaar;
import sherifi.worttrainer.persistence.JsonPersistenzService;
import sherifi.worttrainer.persistence.PersistenzService;
import sherifi.worttrainer.service.WorttrainerService;

/**
 * Startet die Anwendung.
 */
@SpringBootApplication
public class WorttrainerApplication {
	public static void main(String[] args) throws JSONException {
		// Initialisiere das PersistenzService
		PersistenzService persistenzService = new JsonPersistenzService("worttrainer.json");

		// Lade den WorttrainerService aus dem PersistenzService
		WorttrainerService worttrainerService = persistenzService.laden();

		if (worttrainerService == null) {
			// Wenn keine gespeicherten Daten vorhanden sind, erstelle einen neuen Service
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

		// Setze das PersistenzService im WorttrainerService
		worttrainerService.setPersistenzService(persistenzService);

		// Erstelle die GUI und starte sie
		WorttrainerGUI gui = new WorttrainerGUI(worttrainerService);
		gui.starten();
	}
}
