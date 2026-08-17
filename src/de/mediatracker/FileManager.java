package de.mediatracker;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    // Path to the file used for storing all media entries.
    private static final String OUTPUT_FILE = "Save/media.csv";

    // File object representing the save file.
    File saveFile = new File(OUTPUT_FILE);

    // Folder containing the save file.
    File saveFolder = new File("Save");

    // Saves all media entries to the CSV file.
    public void saveMedia(ArrayList<Media> allEntries) throws IOException {

        // Create the save folder if it does not exist yet.
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
        }

        // Open the save file for writing and close it automatically afterwards.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {

            // Write every media entry to the file.
            for (Media media : allEntries) {

                String type = media.getClass().getSimpleName();

                // Handle each media type according to its specific attributes.
                switch (type) {

                    case "Game":

                        Game game = (Game) media;

                        writer.write(
                            game.getClass().getSimpleName()
                            + ";" + game.getName()
                            + ";" + game.getYear()
                            + ";" + game.getPlatform()
                            + ";" + game.getHundredPercentCompletion()
                        );

                        writer.newLine();

                        break;

                    case "Book":

                        Book book = (Book) media;

                        writer.write(
                            book.getClass().getSimpleName()
                            + ";" + book.getName()
                            + ";" + book.getYear()
                            + ";" + book.getAuthor()
                        );

                        writer.newLine();

                        break;

                    case "Movie":

                    case "Series":

                        // Movies and series only require the attributes defined in Media.
                        writer.write(
                            media.getClass().getSimpleName()
                            + ";" + media.getName()
                            + ";" + media.getYear()
                        );

                        writer.newLine();

                        break;

                    default:

                        // Ignore media types that are not supported by the file format.
                        System.out.println(
                            "Unbekannter Medientyp konnte nicht gespeichert werden."
                        );

                        break;
                }
            }
        }
    }

    // Loads all media entries from the save file.
    public ArrayList<Media> loadMedia() {

        ArrayList<Media> allEntries = new ArrayList<>();

        // Only try to load the file if it already exists.
        if (saveFile.exists()) {

            String line;

            // Open the save file for reading and close it automatically afterwards.
            try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {

                // Read the file line by line.
                while ((line = reader.readLine()) != null) {

                    // Split the CSV line into its individual values.
                    String[] parts = line.split(";");

                    String type = parts[0];

                    int year;

                    Media obj;

                    // Recreate the correct media object based on its stored type.
                    switch (type) {

                        case "Game":

                            year = Integer.parseInt(parts[2]);

                            boolean completed = Boolean.valueOf(parts[4]);

                            obj = new Game(
                                parts[1],
                                year,
                                parts[3],
                                completed
                            );

                            break;

                        case "Book":

                            year = Integer.parseInt(parts[2]);

                            obj = new Book(
                                parts[1],
                                year,
                                parts[3]
                            );

                            break;

                        case "Movie":

                            year = Integer.parseInt(parts[2]);

                            obj = new Movie(
                                parts[1],
                                year
                            );

                            break;

                        case "Series":

                            year = Integer.parseInt(parts[2]);

                            obj = new Series(
                                parts[1],
                                year
                            );

                            break;

                        default:

                            // Ignore lines containing an unknown media type.
                            System.out.println("Fehler beim Laden");

                            continue;
                    }

                    // Add the reconstructed media object to the list.
                    allEntries.add(obj);
                }

            } catch (IOException e) {

                // Print the error if the file could not be read.
                System.out.println("Laden fehlgeschlagen.");
                e.printStackTrace();
            }
        }

        return allEntries;
    }
}