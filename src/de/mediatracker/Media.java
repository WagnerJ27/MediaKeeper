package de.mediatracker;


//Represents a single media entry stored by the application.
public class Media {
	
	// Type of the media (e.g. Book, Movie, Game, Series).	
	private MediaType type;
	
	// Name of the media.
	private String name;

	// Year in which the media was finished
	private int year;
	// Creates a new media object with the given type and name.
	public Media(MediaType type, String name, int year) {
		this.type = type;
		this.name = name;
		this.year = year;
	}

	// Returns the name of the media.
	public String getName() {
		return name;
	}
	// Returns the media type.
	public MediaType getType() {
		return type;
	}
	public int getYear() {
		return year;
	}
}
