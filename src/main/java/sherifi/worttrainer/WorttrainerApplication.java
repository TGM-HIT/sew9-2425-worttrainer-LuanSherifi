package sherifi.worttrainer;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sherifi.worttrainer.gui.WorttrainerGUI;

@SpringBootApplication
/**
 * Startet die Anwendung.
 */
public class WorttrainerApplication {
	public static void main(String[] args) throws JSONException {
		WorttrainerGUI gui = new WorttrainerGUI();
		gui.starten();
	}
}

