package de.mediatracker;
import java.util.ArrayList;
import java.util.Scanner;



//Controls the complete application flow including user input,
//media management and program navigation.
public class MediaController {
	
	// Scanner used to read user input from the console.
	private final Scanner in = new Scanner(System.in);
	
	// Stores all created media entries during runtime.
	private ArrayList<Media> allEntries= new ArrayList<Media>();

	private final FileManager fileManager = new FileManager();
	// Determines whether the application should continue running.
	private boolean exit = false;
	

	// Main application loop.
	// Repeats until the user chooses to exit the program.
	public void programLoop() {
		allEntries= fileManager.loadMedia();
		while(!exit) {
		int action =mainMenu();
		
		switch (action) {
			case 1:
				addMediaAction();
				break;
			case 2:
				deleteMediaAction();
				break;
			case 3:
				whatToShow();
				break;
			case 4:
				endProgram();
				break;
		}
		

	}
}
	
	// Displays the main menu and returns the selected menu option.
	public int mainMenu() {
		//Welcoming Message when starting the programm
		System.out.println("\nWas möchten Sie tun?");
		System.out.println("");
		System.out.println("1 - Neues Eintrag erstellen");
		System.out.println("2 - Eintrag löschen");
		System.out.println("3 - Alle Einträge auflisten");
		System.out.println("4 - Programm beenden");
		
		int userInput = in.nextInt();
		in.nextLine();
		
		// Keep asking until a valid menu option is entered.
		while(userInput !=1 && userInput!=2 && userInput!=3 && userInput!=4) {
			System.out.println("Bitte geben Sie eine der passenden Optionen ein!");
			userInput = in.nextInt();
		}
		
		return userInput;
	}
	
	
	// Creates a new media entry based on user input
	// and adds it to the media collection.
	public void addMediaAction() {
		System.out.println("\n\n\n");
		System.out.println("Hinzufügen eines Mediums:");

		Media entry;
		String inputType = askForMediaType();
			switch(inputType) {
			case "game":
				entry = addGame();
				break;
			case "book":
				entry = addBook();
				break;
			case "movie":
				entry = addMovie();
				break;
			case "series":
				entry = addSeries();
				break;
			default:
				System.out.println("Ungültiger Medientyp");
				return;
			}
		


		// Create and store the new media entry.
		allEntries.add(entry);
		
		System.out.println("Eintrag wurde angelegt!");
		System.out.println("\n");
	}

	private String askForMediaType() {
	    while (true) {

	        System.out.println("Bitte geben Sie den Medientyp ein:");
	        System.out.println("Spiel | Buch | Film | Serie");

	        String input = in.nextLine();

	        switch (input.toLowerCase()) {

	            case "spiel":
	                return "game";
	                
	            case "buch":
	                return "book";
	                
	            case "film":
	                return "movie";

	            case "serie":
	                return "series";

	            default:
	                System.out.println("Ungültiger Medientyp.");
	        }

	    }

	}
	

	
	public String askName() {
		System.out.println("Bitte geben Sie nun den Namen des den Eintrag ein.");
		String name = in.nextLine();
		return name;
	}
	
	public int askYear() {
		System.out.println("Bitte geben Sie nun das Jahr ein, ich welches das Medium abgeschlossen wurde!");
		int year = in.nextInt();
		in.nextLine();
		return year;
	}
	
		public String checkIfNameExists(String name, String type) {
		    String fixedName = name;
	
		    boolean nameExists;
	
		    do {
		        nameExists = false;
	
		        for (Media media : allEntries) {
		            if (media.getName().equals(fixedName)
		                    && media.getClass().getSimpleName().equals(type)) {
	
		                System.out.println("Es gibt bereits einen "
		                        + media.getClass().getSimpleName()
		                        + " Eintrag mit diesem Namen.");
	
		                System.out.println("Bitte geben Sie einen anderen Namen für den Eintrag an.");
	
		                fixedName = in.nextLine();
		                nameExists = true;
		                break;
		            }
		        }
	
		    } while (nameExists);
	
		    return fixedName;
		}
	
	public boolean askYesNo() {
		boolean validInput = false;
		boolean yesOrNo= false;
		while(!validInput) {
	
		System.out.println("(Ja/Nein)");
		String hundredPercent = in.nextLine();
		switch(hundredPercent.toLowerCase()) {
		case "ja":
			yesOrNo = true;
			validInput = true;
			break;
		case "nein":
			yesOrNo =false;
			validInput =true;
			break;
		default:
			System.out.println("Ungültige Eingabe!");
		}
	}
		return yesOrNo;
	}
		
	public Media addGame() {
		String name = askName();
		String type = "Game";
		
		String fixedName = checkIfNameExists(name, type);
		
		int year = askYear();

		boolean completed =false;
		System.out.println("Bitt geben geben Sie an, auf welcher Plattform das Spiel gespielt wurde:");
		String platform = in.nextLine();

		
		
		System.out.println("\nHaben Sie das Spiel zu 100% durchgespielt?");
		completed = askYesNo();
		

		Media game = new Game(fixedName,year,platform,completed);
		return game;
	}
	
	
	public Media addBook() {
		String name = askName();
		String type = "Book";
		
		String fixedName = checkIfNameExists(name, type);
		
		int year = askYear();
		boolean validInput = false;
		boolean authorWanted =false;
		String author;
		Media book;
		

			System.out.println("\nMöchten Sie einen Autor angeben?");
			authorWanted = askYesNo();
		
		if(authorWanted) {
			System.out.println("\nBitte geben Sie den Namen des Autors ein:");
			author = in.nextLine();
			 book = new Book(fixedName,year,author);
		}else {
			 book = new Book(fixedName,year);
		}
		
		return book;

	}
	
	
	public Media addMovie() {
		String name = askName();
		String type = "Movie";
		
		String fixedName = checkIfNameExists(name, type);
		int year = askYear();
		
		Media movie = new Movie(fixedName,year);
		return movie;
	}
	
	public Media addSeries() {
		String name = askName();
		String type = "Series";
		
		String fixedName = checkIfNameExists(name, type);
		int year = askYear();
		
		Media series = new Series(fixedName,year);
		return series;
	}
	
	// Removes a media entry from the collection by its name.
	public void deleteMediaAction() {
		if(allEntries.isEmpty()) {
			System.out.println("Es sind noch keine Einträge vorhanden!");
		}	else {
			
		System.out.println("Was für eine Art Eintrag möchten Sie entfernen?");
		boolean entryFound = false;
		String type = askForMediaType();
		
		System.out.println("Hier ist eine Liste mit allen \""+type + "\" Einträgen");
		
		for(Media media: allEntries) {
			if(media.getClass().getSimpleName().toLowerCase().equals(type)) {
				System.out.println("\n"+type+ " | "+media.getName());
			}
	}
		
		System.out.println("Geben Sie bitte nun den Namen des zu löschenden Eintrags an");
		String deleteName = in.nextLine();
			for(int i = 0; i<allEntries.size();i++) {
				if(deleteName.equals(allEntries.get(i).getName()) && allEntries.get(i).getClass().getSimpleName().toLowerCase().equals(type)){
					allEntries.remove(i);
					System.out.println("Der gewünschte Eintrag wurde gelöscht");
					entryFound = true;
					break;
				}
			}
			if(!entryFound) {
				System.out.println("Es wurde kein Eintrag mit diesem Namen gefunden!");
			}
		}
		// Check whether there are any entries to delete.

}
	public int askWhatToShow() {
		System.out.println("Was möchten Sie angezeigt bekommen?");
		System.out.println("\n1 - Alle Einträge");
		System.out.println("2 - Alle Games Einträge");
		System.out.println("3 - Alle Books Einträge");
		System.out.println("4 - Alle Movie Einträge");
		System.out.println("5 - Alle Series Einträge");
		int input = in.nextInt();
		
		while(input <1 || input >5) {
			System.out.println("Bitte geben Sie einen gültigen Wert an!");
			input = in.nextInt();
		}
		
		return input;
	}
	
	public void showEntries(int input) {
		System.out.println("Hier ist eine Liste aller Einträge.");
		// Print every stored media entry.
		
		
		switch(input) {
			case 1:
				for(Media media : allEntries) {
					if(media instanceof Game) {
						Game game = (Game) media;
						System.out.println("\n Typ:"+media.getClass().getSimpleName()+" | Name: "+media.getName()+" | Beendet in: "+ media.getYear()+" | Plattform: "+game.getPlatform()+" | 100%: "+game.getHundredPercentCompletion());
					}
					else if(media instanceof Book) {
						Book book = (Book) media;
						System.out.println("\n Typ:"+media.getClass().getSimpleName()+" | Name: "+media.getName()+" | Beendet in: "+ media.getYear()+" | Author: " + book.getAuthor());
					}
					else if(media instanceof Movie) {
						System.out.println("\n Typ:"+media.getClass().getSimpleName()+" | Name: "+media.getName()+" | Beendet in: "+ media.getYear());
					}
					else if(media instanceof Series) {
						System.out.println("\n Typ:"+media.getClass().getSimpleName()+" | Name: "+media.getName()+" | Beendet in: "+ media.getYear());
					}
				}
				break;
		
			case 2:
				for(Media media : allEntries) {
					if(media instanceof Game) {
						Game game = (Game) media;
						System.out.println("\n Typ:"+media.getClass().getSimpleName()+" | Name: "+media.getName()+" | Beendet in: "+ media.getYear()+" | Plattform: "+game.getPlatform()+" | 100%: "+game.getHundredPercentCompletion());
					}
				}
				break;
				
			case 3:
				for(Media media : allEntries) {

					if(media instanceof Book) {
						Book book = (Book) media;
						System.out.println("\n Typ:"+media.getClass().getSimpleName()+" | Name: "+media.getName()+" | Beendet in: "+ media.getYear()+" | Author: " + book.getAuthor());
					}
				}
				break;
				
			case 4:
				for(Media media : allEntries) {

					if(media instanceof Movie) {
						System.out.println("\n Typ:"+media.getClass().getSimpleName()+" | Name: "+media.getName()+" | Beendet in: "+ media.getYear());
					}
				}
				break;
				
			case 5:
				for(Media media : allEntries) {
					if(media instanceof Series) {
						System.out.println("\n Typ:"+media.getClass().getSimpleName()+" | Name: "+media.getName()+" | Beendet in: "+ media.getYear());
					}
				}
				break;
		}				
	}
	
	
	// Displays all stored media entries.
	public void whatToShow() {
		String type;
		if(allEntries.isEmpty()) {
			System.out.println("Noch kein Eintrag vorhanden!");
		} 
		
		else {
			int input = askWhatToShow();
			showEntries(input);

			

		}
}
	
	// Terminates the application.
	public void endProgram() {
		System.out.println("Vielen Dank für die Nutzung von MediaKeeper!");
		System.out.println("Ihre Änderungen wurden gespeichert");
		fileManager.saveMedia(allEntries);
		System.out.println("Bis zum Nächsten Mal!");
		
		exit = true;
	}
}
