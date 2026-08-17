
package de.mediatracker;

import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    /*
     * Determines the folder in which MediaKeeper stores its data.
     *
     * On Windows this will normally be:
     *
     * C:\Users\<Benutzer>\AppData\Local\MediaKeeper
     *
     * This keeps user data separate from the installed application.
     */
    private static final String APP_FOLDER = "MediaKeeper";

    // Name of the folder containing the save file.
    private static final String SAVE_FOLDER = "Save";

    // Name of the file containing the media entries.
    private static final String SAVE_FILE = "media.csv";

    /*
     * Complete path to the MediaKeeper application folder.
     *
     * user.home points to the current Windows user's home directory.
     */
    private static final File APP_DIRECTORY =
        new File(
            System.getProperty("user.home"),
            "AppData" + File.separator + "Local"
        );

    /*
     * Folder in which MediaKeeper stores its save data.
     */
    private static final File SAVE_DIRECTORY =
        new File(APP_DIRECTORY, APP_FOLDER + File.separator + SAVE_FOLDER);

    /*
     * File containing all saved media entries.
     */
    private static final File SAVE_FILE_PATH =
        new File(SAVE_DIRECTORY, SAVE_FILE);


    // Saves all media entries to the CSV file.
    public void saveMedia(ArrayList<Media> allEntries) throws IOException {

        /*
         * Create the complete save directory if it does not exist yet.
         *
         * mkdirs() creates all missing parent folders as well.
         */
        if (!SAVE_DIRECTORY.exists()) {
            SAVE_DIRECTORY.mkdirs();
        }

        /*
         * Open the save file for writing.
         *
         * try-with-resources automatically closes the writer afterwards.
         */

        try (BufferedWriter writer =
                 new BufferedWriter(new FileWriter(SAVE_FILE_PATH))) {

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

                        /*
                         * Movies and series only require the attributes
                         * defined in the Media class.
                         */
                        writer.write(
                            media.getClass().getSimpleName()
                            + ";" + media.getName()
                            + ";" + media.getYear()
                        );

                        writer.newLine();

                        break;


                    default:

                        // Ignore unsupported media types.
                        break;
                }
        
            }
        
        }
        
    }


    // Loads all media entries from the save file.
    public ArrayList<Media> loadMedia() {

        ArrayList<Media> allEntries = new ArrayList<>();

        /*
         * Only try to load the file if it already exists.
         *
         * On the first application start there will not be a save file yet.
         */
        if (SAVE_FILE_PATH.exists()) {

            String line;

            /*
             * Open the save file for reading.
             *
             * try-with-resources automatically closes the reader.
             */
            try (BufferedReader reader =
                     new BufferedReader(new FileReader(SAVE_FILE_PATH))) {

                // Read the file line by line.
                while ((line = reader.readLine()) != null) {

                    /*
                     * Split the CSV line into its individual values.
                     */
                    String[] parts = line.split(";");

                    String type = parts[0];

                    int year;

                    Media obj;


                    /*
                     * Recreate the correct media object based on
                     * the stored media type.
                     */
                    switch (type) {

                        case "Game":

                            year = Integer.parseInt(parts[2]);

                            boolean completed =
                                Boolean.valueOf(parts[4]);

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

                            // Ignore unknown media types.
                            System.out.println(
                                "Unbekannter Medientyp beim Laden."
                            );

                            continue;
                    }

                    // Add the reconstructed media object to the list.
                    allEntries.add(obj);
                }

            } catch (IOException e) {

                System.out.println("Laden fehlgeschlagen.");
                e.printStackTrace();
            }
        }

        return allEntries;
    }
}
