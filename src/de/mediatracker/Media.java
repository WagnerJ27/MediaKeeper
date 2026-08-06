package de.mediatracker;


//Represents a single media entry stored by the application.
public class Media {
	

	// Name of the media.
	private String name;

	// Year in which the media was finished
	private int year;
	
	// Creates a new media object with the given type and name.
	public Media(String name, int year) {
		this.name = name;
		this.year = year;
	}

	// Returns the name of the media.
	public String getName() {
		return name;
	}
	// Returns the media type.

	public int getYear() {
		return year;
	}
}
