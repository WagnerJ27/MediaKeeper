package de.mediatracker;

public class Media {
private String typ;
private String name;

	public Media(String typ, String name) {
		this.typ = typ;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getTyp() {
		return typ;
	}
}
