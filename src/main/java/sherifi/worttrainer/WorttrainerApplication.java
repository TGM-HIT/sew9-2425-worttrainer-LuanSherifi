package sherifi.worttrainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sherifi.worttrainer.gui.WorttrainerGUI;

@SpringBootApplication
/**
 * Startet die Anwendung.
 */
public class WorttrainerApplication {
	public static void main(String[] args) {
		WorttrainerGUI gui = new WorttrainerGUI();
		gui.starten();
	}
}

