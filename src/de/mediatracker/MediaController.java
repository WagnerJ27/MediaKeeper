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
	
	
	public boolean mediaExists(String name, String type) {
		boolean exists =false;
		String newType="";
		
		switch(type) {
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
		
		for(Media media : allEntries) {
			if(media.getName().equals(name) && media.getClass().getSimpleName().equals(newType)) {
				exists = true;
			}
		}
		return exists;
	}
	
	public boolean deleteEntry(String name, String type) {
		
		boolean entryIsDeleted = false;
		switch(type) {
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
		
		for(int i =0; i<allEntries.size();i++) {
			if(allEntries.get(i).getName().equals(name) && allEntries.get(i).getClass().getSimpleName().equals(type)) {
				allEntries.remove(i);
				entryIsDeleted = true;
				break;
			}
		}
		return entryIsDeleted;
	}
	
	
	public ArrayList<Game> getGames(){
		ArrayList<Game> gamesList = new ArrayList<>();
			for(Media media : allEntries) {
				if(media instanceof Game) {
					Game game = (Game) media;
					gamesList.add(game);
				}
			}
			return gamesList;
	}
	
	public ArrayList<Book> getBooks(){
		ArrayList<Book> booksList = new ArrayList<>();
		for(Media media : allEntries) {
			if(media instanceof Book) {
				Book book = (Book) media;
				booksList.add(book);
			}
		}
		return booksList;
	}
	
	public ArrayList<Movie> getMovies(){
		ArrayList<Movie> moviesList = new ArrayList<>();
		for(Media media : allEntries) {
			if(media instanceof Movie) {
				Movie movie = (Movie) media;
				moviesList.add(movie);
			}
		}
		return moviesList;
	}
	public ArrayList<Series> getSeries(){
		ArrayList<Series> seriesList = new ArrayList<>();
		for(Media media : allEntries) {
			if(media instanceof Series) {
				Series series = (Series) media;
				seriesList.add(series);
			}
		}
		return seriesList;
	}
	
	public void saveMedia()throws IOException {
		    fileManager.saveMedia(allEntries);
		    
	}

	





	


	
	

}
