package de.mediatracker;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;


// Manages media entries and provides the main operations used by the GUI.
public class MediaController {

    // Stores all media entries during runtime.
    private ArrayList<Media> allEntries = new ArrayList<>();

    // Handles loading and saving the media data.
    private final FileManager fileManager = new FileManager();


    // Loads all saved media entries when the application starts.
    public void loadData() {
        allEntries = fileManager.loadMedia();
    }


    // Adds a media object to the list of all entries.
    public void addMedia(Media media) {
        allEntries.add(media);
    }


    // Creates and returns a new Game object.
    public Media addGame(String name, int year, String platform, boolean completed) {

        Game game = new Game(name, year, platform, completed);

        return game;
    }


    // Creates and returns a new Book object without an author.
    public Media addBook(String name, int year) {

        Book book = new Book(name, year);

        return book;
    }


    // Creates and returns a new Book object with an author.
    public Media addBook(String name, int year, String author) {

        Book book = new Book(name, year, author);

        return book;
    }


    // Creates and returns a new Movie object.
    public Media addMovie(String name, int year) {

        Movie movie = new Movie(name, year);

        return movie;
    }


    // Creates and returns a new Series object.
    public Media addSeries(String name, int year) {

        Series series = new Series(name, year);

        return series;
    }


    // Checks whether a media entry with the same name and type already exists.
    public boolean mediaExists(String name, String type) {

        boolean exists = false;
        String newType = "";

        // Convert the GUI's German media type to the corresponding class name.
        switch (type) {

            case "Spiel":
                newType = "Game";
                break;

            case "Buch":
                newType = "Book";
                break;

            case "Film":
                newType = "Movie";
                break;

            case "Serie":
                newType = "Series";
                break;
        }

        // Check all existing entries for a matching name and media type.
        for (Media media : allEntries) {

            if (media.getName().equals(name)
                    && media.getClass().getSimpleName().equals(newType)) {

                exists = true;
            }
        }

        return exists;
    }


    // Deletes the first entry matching the given name and media type.
    public boolean deleteEntry(String name, String type) {

        boolean entryIsDeleted = false;

        // Convert the GUI's German media type to the corresponding class name.
        switch (type) {

            case "Spiel":
                type = "Game";
                break;

            case "Buch":
                type = "Book";
                break;

            case "Film":
                type = "Movie";
                break;

            case "Serie":
                type = "Series";
                break;
        }

        // Search for the matching entry and remove it from the list.
        for (int i = 0; i < allEntries.size(); i++) {

            if (allEntries.get(i).getName().equals(name)
                    && allEntries.get(i).getClass().getSimpleName().equals(type)) {

                allEntries.remove(i);
                entryIsDeleted = true;

                break;
            }
        }

        return entryIsDeleted;
    }


    // Returns all Game entries from the complete media list.
    public ArrayList<Game> getGames() {

        ArrayList<Game> gamesList = new ArrayList<>();

        // Filter the complete media list for Game objects.
        for (Media media : allEntries) {

            if (media instanceof Game) {

                Game game = (Game) media;
                gamesList.add(game);
            }
        }

        return gamesList;
    }


    // Returns all Book entries from the complete media list.
    public ArrayList<Book> getBooks() {

        ArrayList<Book> booksList = new ArrayList<>();

        // Filter the complete media list for Book objects.
        for (Media media : allEntries) {

            if (media instanceof Book) {

                Book book = (Book) media;
                booksList.add(book);
            }
        }

        return booksList;
    }


    // Returns all Movie entries from the complete media list.
    public ArrayList<Movie> getMovies() {

        ArrayList<Movie> moviesList = new ArrayList<>();

        // Filter the complete media list for Movie objects.
        for (Media media : allEntries) {

            if (media instanceof Movie) {

                Movie movie = (Movie) media;
                moviesList.add(movie);
            }
        }

        return moviesList;
    }


    // Returns all Series entries from the complete media list.
    public ArrayList<Series> getSeries() {

        ArrayList<Series> seriesList = new ArrayList<>();

        // Filter the complete media list for Series objects.
        for (Media media : allEntries) {

            if (media instanceof Series) {

                Series series = (Series) media;
                seriesList.add(series);
            }
        }

        return seriesList;
    }


    // Saves all current media entries using the FileManager.
    public void saveMedia() throws IOException {
        fileManager.saveMedia(allEntries);
    }
}