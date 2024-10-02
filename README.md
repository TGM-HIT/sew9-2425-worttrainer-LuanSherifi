# Worttrainer

**Autor: Luan Sherifi**  
**Datum: 18.09.2024**  


Ein Rechtschreibtrainer für Wörter für Volksschulkinder. Die Kinder sollen zu einem angezeigten Bild das entsprechende Wort eingeben. Das Programm überprüft die Eingabe und zeigt eine entsprechende Meldung an.

## Funktionen

- **Model**: Verwaltung von Wort-Bild-Paaren und Statistik.
- **Service**: Geschäftslogik für das Auswählen von Paaren und Auswerten der Eingaben.
- **Persistenz**: Speichern und Laden des aktuellen Zustands in einer JSON-Datei.
- **GUI**: Einfache grafische Oberfläche mit `JOptionPane`.

## Technologien

- **Java**
- **Gradle** als Build-System
- **JSON** für die Persistenz (mit der Bibliothek `org.json`)

## Anleitung

1. **Build**: Führe `gradle build` aus, um das Projekt zu bauen.
2. **Starten**: Führe `gradle bootRun` aus, um die Anwendung zu starten.
3. **Bedienung**: Folge den Anweisungen in den Dialogfenstern. Um das Programm zu beenden, lasse die Eingabe leer oder klicke auf Abbrechen.

## Hinweise

- Die Bilder werden über Platzhalter-URLs geladen (`https://via.placeholder.com/150`).
- Die Anwendung speichert den aktuellen Zustand in der Datei `worttrainer.json` im Programmverzeichnis.
