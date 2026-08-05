package de.mediatracker;


//Represents a single media entry stored by the application.
public class Media {
	
	// Type of the media (e.g. Book, Movie, Game, Series).	
	private String typ;
	
	// Name of the media.
	private String name;

	// Creates a new media object with the given type and name.
	public Media(String typ, String name) {
		this.typ = typ;
		this.name = name;
	}

	// Returns the name of the media.
	public String getName() {
		return name;
	}
	// Returns the media type.
	public String getType() {
		return typ;
	}
}
