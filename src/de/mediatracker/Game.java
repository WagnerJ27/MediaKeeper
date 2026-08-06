package de.mediatracker;

public class Game extends Media{
	private String platform;
	private boolean hundredPercentCompletion;
	public Game(String name, int year, String platform, boolean hundredPercentCompletion) {
	    super(name, year);
	    this.platform = platform;
	    this.hundredPercentCompletion = hundredPercentCompletion;
	}
	public String getPlatform() {
		return platform;
	}
	
	public boolean getHundredPercentCompletion(){
		return hundredPercentCompletion;
	}
}
