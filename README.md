# MediaKeeper

MediaKeeper ist eine JavaFX-Anwendung zur Verwaltung und Organisation einer persönlichen Mediensammlung. Mit der Anwendung können abgeschlossene **Filme, Serien, Bücher und Spiele** erfasst, angezeigt und wieder gelöscht werden.

Das Projekt wurde als Java-Anwendung mit einer grafischen Benutzeroberfläche umgesetzt. Die Mediendaten werden lokal gespeichert, sodass die Einträge beim nächsten Start der Anwendung wieder geladen werden können.

## Funktionen

### Medien verwalten

MediaKeeper bietet grundlegende Funktionen zur Verwaltung der persönlichen Mediensammlung:

* **Medien hinzufügen**

  * Filme
  * Serien
  * Bücher
  * Spiele
* **Medien anzeigen**

  * Separate Übersicht für jede Medienart
  * Tabellarische Darstellung der gespeicherten Einträge
* **Medien löschen**

  * Auswahl der Medienart
  * Suche nach dem Namen des Eintrags
* **Automatische Speicherung**

  * Änderungen werden beim Hinzufügen oder Löschen gespeichert
  * Gespeicherte Einträge werden beim Start der Anwendung automatisch geladen

### Spezifische Informationen

Je nach Medienart können unterschiedliche Informationen gespeichert werden.

**Spiele**

* Name
* Erscheinungsjahr
* Plattform
* 100%-Abschluss

**Bücher**

* Name
* Erscheinungsjahr
* Autor

**Filme**

* Name
* Erscheinungsjahr

**Serien**

* Name
* Erscheinungsjahr

## Benutzeroberfläche

Die Anwendung verfügt über eine grafische Benutzeroberfläche, die mit **JavaFX** entwickelt wurde.

Das Hauptmenü bietet direkten Zugriff auf die wichtigsten Funktionen:

1. Neuen Eintrag hinzufügen
2. Einen Eintrag entfernen
3. Einträge anzeigen
4. Speichern und Beenden

Die verschiedenen Medienarten werden in eigenen Tabellen dargestellt. Dadurch können die gespeicherten Einträge übersichtlich betrachtet werden.

Das Erscheinungsbild der Anwendung wird zusätzlich über eine **CSS-Datei** gestaltet.

## Speicherung der Daten

Die Mediendaten werden lokal auf dem Computer gespeichert.

MediaKeeper verwendet dafür eine CSV-Datei. Die Anwendung erstellt bei Bedarf automatisch den benötigten Speicherordner.

Unter Windows werden die Daten standardmäßig im lokalen Benutzerverzeichnis gespeichert:

```text
C:\Users\<Benutzer>\AppData\Local\MediaKeeper\Save\media.csv
```

Dadurch befinden sich die persönlichen Mediendaten getrennt von den eigentlichen Programmdateien.

Beim Start der Anwendung werden vorhandene Einträge aus der CSV-Datei geladen. Beim Hinzufügen oder Löschen von Einträgen wird die Datei entsprechend aktualisiert.

## Projektstruktur

Das Projekt ist in mehrere Klassen aufgeteilt, die unterschiedliche Aufgaben übernehmen.

```text
MediaTracker/
│
├── src/
│   └── de/
│       └── mediatracker/
│           ├── Main.java
│           ├── MediaKeeperGUI.java
│           ├── MediaController.java
│           ├── FileManager.java
│           ├── AddEntryGUI.java
│           ├── DeleteEntryGUI.java
│           ├── ShowEntriesGUI.java
│           ├── Media.java
│           ├── Game.java
│           ├── Book.java
│           ├── Movie.java
│           └── Series.java
│
├── resources/
│   ├── style.css
│   └── mediakeeper.png
│
├── release/
│   └── ...
│
├── .gitignore
├── .classpath
├── .project
└── README.md
```

### Aufgaben der wichtigsten Klassen

**`Main.java`**
Startpunkt der Anwendung. Von hier aus wird die JavaFX-Anwendung gestartet.

**`MediaKeeperGUI.java`**
Erstellt das Hauptfenster und das Hauptmenü der Anwendung. Außerdem werden allgemeine Erfolgs- und Fehlermeldungen verwaltet.

**`MediaController.java`**
Übernimmt die zentrale Verwaltung der Medieneinträge und verbindet die Benutzeroberfläche mit der Datenverwaltung.

**`FileManager.java`**
Kümmert sich um das Speichern und Laden der Mediendaten.

**`AddEntryGUI.java`**
Stellt die Oberfläche zum Hinzufügen neuer Medieneinträge bereit.

**`DeleteEntryGUI.java`**
Ermöglicht das Löschen vorhandener Medieneinträge.

**`ShowEntriesGUI.java`**
Zeigt die gespeicherten Medien in übersichtlichen Tabellen an.

**`Media.java`**
Basisklasse für die verschiedenen Medienarten.

**`Game.java`, `Book.java`, `Movie.java` und `Series.java`**
Repräsentieren die jeweiligen konkreten Medienarten und erweitern die Basisklasse `Media`.

## Technologien

Für die Entwicklung von MediaKeeper wurden folgende Technologien verwendet:

* **Java**
* **JavaFX** für die grafische Benutzeroberfläche
* **CSS** für das Styling der Benutzeroberfläche
* **Git** für die Versionsverwaltung
* **GitHub** zur Speicherung und Verwaltung des Quellcodes
* **Eclipse** als Entwicklungsumgebung

## Voraussetzungen

Für die Entwicklung bzw. das Starten aus dem Quellcode werden entsprechende Java- und JavaFX-Komponenten benötigt.

Empfohlen wird eine aktuelle Java-Version sowie eine passende JavaFX-Version.

Wenn das Projekt über eine fertige Anwendung bzw. einen Installer bereitgestellt wird, ist keine manuelle Einrichtung der Entwicklungsumgebung erforderlich.

## Installation

### Verwendung des Installers

Falls eine fertige Installer-Version im Bereich `release` bereitgestellt wird, kann MediaKeeper direkt über den Installer installiert werden.

Nach der Installation kann die Anwendung wie ein normales Windows-Programm gestartet werden.

### Ausführen aus dem Quellcode

Alternativ kann das Projekt in eine Java-Entwicklungsumgebung wie Eclipse importiert und von dort ausgeführt werden.

Dabei müssen Java und JavaFX entsprechend eingerichtet sein.

## Validierung der Eingaben

MediaKeeper überprüft verschiedene Eingaben, bevor ein neuer Eintrag gespeichert wird.

Unter anderem wird geprüft:

* ob alle benötigten Pflichtfelder ausgefüllt wurden
* ob das eingegebene Jahr eine gültige Zahl ist
* ob ein Eintrag mit gleichem Namen und gleicher Medienart bereits vorhanden ist
* ob bei einem Spiel eine Plattform angegeben wurde

Dadurch sollen fehlerhafte oder doppelte Einträge möglichst verhindert werden.

## Architektur

Die Anwendung ist in mehrere Verantwortungsbereiche aufgeteilt.

Vereinfacht lässt sich der Ablauf folgendermaßen darstellen:

```text
Benutzer
   │
   ▼
JavaFX-GUI
   │
   ▼
MediaController
   │
   ├──► Media / Game / Book / Movie / Series
   │
   ▼
FileManager
   │
   ▼
media.csv
```

Die GUI übernimmt hauptsächlich die Darstellung und Interaktion mit dem Benutzer. Der `MediaController` übernimmt die Verwaltung der Daten und der `FileManager` ist für die dauerhafte Speicherung zuständig.

## Datenmodell

Alle Medien basieren auf der gemeinsamen Klasse `Media`.

```text
Media
├── Game
├── Book
├── Movie
└── Series
```

Die gemeinsame Basisklasse stellt grundlegende Informationen wie Name und Erscheinungsjahr bereit.

Spezifische Medienarten können zusätzliche Eigenschaften besitzen. Ein `Game` enthält beispielsweise zusätzlich die Plattform und die Information, ob das Spiel zu 100 % abgeschlossen wurde.

## Versionsverwaltung

Das Projekt wird mit **Git** versioniert und auf **GitHub** verwaltet.

Dadurch können Änderungen am Projekt nachvollzogen und frühere Versionen wiederhergestellt werden.

Das Repository enthält den Quellcode sowie die für das Projekt benötigten Ressourcen und Projektdokumentationen.

## Ziel des Projekts

MediaKeeper wurde als eigenständiges Java-Projekt entwickelt, um eine einfache und übersichtliche Möglichkeit zur Verwaltung einer persönlichen Sammlung abgeschlossener Medien bereitzustellen.

Gleichzeitig dient das Projekt dazu, praktische Erfahrungen in folgenden Bereichen zu sammeln:

* objektorientierte Programmierung mit Java
* Entwicklung grafischer Benutzeroberflächen mit JavaFX
* Trennung von GUI, Logik und Datenverwaltung
* Datei- und CSV-Verarbeitung
* CSS-Styling
* Versionsverwaltung mit Git
* Verwaltung eines Projekts über GitHub
* Erstellung und Bereitstellung einer ausführbaren Anwendung

## Status

**Projektstatus: Fertig**

Die grundlegenden Funktionen zur Verwaltung, Anzeige, Speicherung und Löschung von Medien sind implementiert.

Weitere Funktionen können in zukünftigen Versionen ergänzt werden.
