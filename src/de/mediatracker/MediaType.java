package de.mediatracker;

public enum MediaType {
	GAME("Spiel"),
	BOOK("Buch"),
	MOVIE("Film"),
	SERIES("Serie");
	
    private final String displayName;

    MediaType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
