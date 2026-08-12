package de.mediatracker;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;


//Controls the complete application flow including user input,
//media management and program navigation.
public class MediaController {
	

	// Stores all created media entries during runtime.
	private ArrayList<Media> allEntries = new ArrayList<>();

	private final FileManager fileManager = new FileManager();
	// Determines whether the application should continue running.

	// Main application loop.
	// Repeats until the user chooses to exit the program.
	public void loadData() {
		allEntries= fileManager.loadMedia();

	}

	

	



	public void addMedia(Media media) {
		allEntries.add(media);
	}

	

	public Media addGame(String name, int year, String platform, boolean completed) {


		Game game = new Game(name,year,platform,completed);
		return game;
	}
	
	
	public Media addBook(String name, int year) {
		Book book = new Book(name,year);
		return book;
	}
	
	public Media addBook(String name, int year, String author) {
		Book book = new Book(name,year,author);
		return book;
	}
	
	public Media addMovie(String name, int year) {

		Movie movie = new Movie(name,year);
		return movie;
	}
	
	public Media addSeries(String name, int year) {

		Series series = new Series(name,year);
		return series;
	}
	
	public void saveMedia()throws IOException {
		    fileManager.saveMedia(allEntries);
		    
	}

	





	


	
	

}
