package de.mediatracker;

public class Book extends Media{
	private String author;
	
	
	public Book(String name,int year) {
		super(name, year);
		this.author ="Keine Angabe";
	}
	
	public Book(String name, int year, String author) {
		super(name,year);
		this.author = author;
	}
	
	public String getAuthor() {
		return author;
	}
}
