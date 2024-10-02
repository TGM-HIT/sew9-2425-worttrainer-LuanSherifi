package sherifi.worttrainer;

import org.json.JSONException;
import sherifi.worttrainer.model.Statistik;
import sherifi.worttrainer.model.WortBildPaar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sherifi.worttrainer.service.WorttrainerService;
import sherifi.worttrainer.persistence.PersistenzService;
import sherifi.worttrainer.persistence.JsonPersistenzService;

import java.io.File;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für den Worttrainer.
 */
public class WorttrainerApplicationTests {

	private WorttrainerService worttrainerService;

	/**
	 * Wird vor jedem Test ausgeführt und initialisiert den WorttrainerService mit einigen Wort-Bild-Paaren.
	 */
	@BeforeEach
	public void setUp() throws MalformedURLException {
		worttrainerService = new WorttrainerService();
		worttrainerService.addWortBildPaar(new WortBildPaar("Hund", "https://via.placeholder.com/150?text=Hund"));
		worttrainerService.addWortBildPaar(new WortBildPaar("Katze", "https://via.placeholder.com/150?text=Katze"));
		worttrainerService.addWortBildPaar(new WortBildPaar("Auto", "https://via.placeholder.com/150?text=Auto"));
	}

	/**
	 * Testet die Erstellung eines gültigen WortBildPaar-Objekts.
	 */
	@Test
	public void testWortBildPaarValid() throws MalformedURLException {
		WortBildPaar paar = new WortBildPaar("Baum", "https://via.placeholder.com/150?text=Baum");
		assertEquals("Baum", paar.getWort());
		assertEquals("https://via.placeholder.com/150?text=Baum", paar.getBildUrl().toString());
	}

	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn das Wort null ist.
	 */
	@Test
	public void testWortBildPaarInvalidWort() {
		assertThrows(IllegalArgumentException.class, () -> {
			new WortBildPaar(null, "https://via.placeholder.com/150?text=Baum");
		});
	}

	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn die Bild-URL null ist.
	 */
	@Test
	public void testWortBildPaarInvalidUrl() {
		assertThrows(IllegalArgumentException.class, () -> {
			new WortBildPaar("Baum", null);
		});
	}

	/**
	 * Testet, ob eine MalformedURLException geworfen wird, wenn die Bild-URL ungültig ist.
	 */
	@Test
	public void testWortBildPaarMalformedUrl() {
		assertThrows(MalformedURLException.class, () -> {
			new WortBildPaar("Baum", "invalid_url");
		});
	}

	/**
	 * Testet, ob die Statistik initial korrekt mit Nullwerten gesetzt ist.
	 */
	@Test
	public void testStatistikInitialValues() {
		Statistik statistik = new Statistik();
		assertEquals(0, statistik.getGesamtVersuche());
		assertEquals(0, statistik.getRichtigeVersuche());
		assertEquals(0, statistik.getFalscheVersuche());
	}

	/**
	 * Testet die Inkrementierungsfunktionen der Statistikklasse.
	 */
	@Test
	public void testStatistikIncrement() {
		Statistik statistik = new Statistik();
		statistik.erhoeheGesamtVersuche();
		statistik.erhoeheRichtigeVersuche();
		statistik.erhoeheFalscheVersuche();
		assertEquals(1, statistik.getGesamtVersuche());
		assertEquals(1, statistik.getRichtigeVersuche());
		assertEquals(1, statistik.getFalscheVersuche());
	}

	/**
	 * Testet das Hinzufügen eines neuen Wort-Bild-Paares zum WorttrainerService.
	 */
	@Test
	public void testWorttrainerServiceAddPaar() throws MalformedURLException {
		int initialSize = worttrainerService.getWortBildPaare().size();
		worttrainerService.addWortBildPaar(new WortBildPaar("Blume", "https://via.placeholder.com/150?text=Blume"));
		assertEquals(initialSize + 1, worttrainerService.getWortBildPaare().size());
	}

	/**
	 * Testet die Funktion zum Auswählen eines bestimmten Wort-Bild-Paares anhand des Index.
	 */
	@Test
	public void testWorttrainerServiceWaehlePaar() {
		worttrainerService.waehlePaar(0);
		assertNotNull(worttrainerService.getAktuellesPaar());
		assertEquals("Hund", worttrainerService.getAktuellesPaar().getWort());
	}

	/**
	 * Testet, ob eine IndexOutOfBoundsException geworfen wird, wenn ein ungültiger Index verwendet wird.
	 */
	@Test
	public void testWorttrainerServiceWaehlePaarInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			worttrainerService.waehlePaar(-1);
		});
	}

	/**
	 * Testet die Funktion zum zufälligen Auswählen eines Wort-Bild-Paares.
	 */
	@Test
	public void testWorttrainerServiceWaehleZufaelligesPaar() {
		worttrainerService.waehleZufaelligesPaar();
		assertNotNull(worttrainerService.getAktuellesPaar());
	}

	/**
	 * Testet, ob ein korrekt geratenes Wort als richtig erkannt wird und die Statistik aktualisiert wird.
	 */
	@Test
	public void testWorttrainerServiceRateWortRichtig() {
		worttrainerService.waehlePaar(0);
		boolean korrekt = worttrainerService.rateWort("Hund");
		assertTrue(korrekt);
		assertNull(worttrainerService.getAktuellesPaar());
		assertEquals(1, worttrainerService.getStatistik().getGesamtVersuche());
		assertEquals(1, worttrainerService.getStatistik().getRichtigeVersuche());
	}

	/**
	 * Testet, ob ein falsch geratenes Wort als falsch erkannt wird und die Statistik aktualisiert wird.
	 */
	@Test
	public void testWorttrainerServiceRateWortFalsch() {
		worttrainerService.waehlePaar(0);
		boolean korrekt = worttrainerService.rateWort("Katze");
		assertFalse(korrekt);
		assertNotNull(worttrainerService.getAktuellesPaar());
		assertEquals(1, worttrainerService.getStatistik().getGesamtVersuche());
		assertEquals(1, worttrainerService.getStatistik().getFalscheVersuche());
	}

	/**
	 * Testet, ob eine IllegalStateException geworfen wird, wenn versucht wird, ein Wort zu raten, ohne dass ein Paar ausgewählt wurde.
	 */
	@Test
	public void testWorttrainerServiceRateWortOhneAuswahl() {
		assertThrows(IllegalStateException.class, () -> {
			worttrainerService.rateWort("Hund");
		});
	}

	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn die Eingabe null ist.
	 */
	@Test
	public void testWorttrainerServiceRateWortNullEingabe() {
		worttrainerService.waehlePaar(0);
		assertThrows(IllegalArgumentException.class, () -> {
			worttrainerService.rateWort(null);
		});
	}

	/**
	 * Testet das Speichern und Laden des WorttrainerService-Zustands mittels JsonPersistenzService.
	 */
	@Test
	public void testPersistenzServiceSpeichernUndLaden() throws MalformedURLException, JSONException {
		// Erstelle ein neues WorttrainerService-Objekt
		WorttrainerService originalService = new WorttrainerService();
		originalService.addWortBildPaar(new WortBildPaar("Baum", "https://via.placeholder.com/150?text=Baum"));
		originalService.addWortBildPaar(new WortBildPaar("Haus", "https://via.placeholder.com/150?text=Haus"));
		originalService.waehlePaar(1);
		originalService.rateWort("Haus"); // Richtige Antwort
		originalService.waehlePaar(0);
		originalService.rateWort("Hause"); // Falsche Antwort

		// Speichere den Zustand
		PersistenzService persistenzService = new JsonPersistenzService("test_worttrainer.json");
		persistenzService.speichern(originalService);

		// Lade den Zustand
		WorttrainerService geladenerService = persistenzService.laden();

		// Vergleiche die geladenen Daten mit den Originaldaten
		assertNotNull(geladenerService);
		assertEquals(originalService.getWortBildPaare().size(), geladenerService.getWortBildPaare().size());
		assertEquals(originalService.getStatistik().getGesamtVersuche(), geladenerService.getStatistik().getGesamtVersuche());
		assertEquals(originalService.getStatistik().getRichtigeVersuche(), geladenerService.getStatistik().getRichtigeVersuche());
		assertEquals(originalService.getStatistik().getFalscheVersuche(), geladenerService.getStatistik().getFalscheVersuche());

		// Bereinige die Testdatei
		new File("test_worttrainer.json").delete();
	}
}
