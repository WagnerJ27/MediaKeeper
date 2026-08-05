package de.mediatracker;
import java.util.ArrayList;
import java.util.Scanner;



//Controls the complete application flow including user input,
//media management and program navigation.
public class MediaController {
	
	// Scanner used to read user input from the console.
	private Scanner in = new Scanner(System.in);
	
	// Stores all created media entries during runtime.
	private ArrayList<Media> allEntries= new ArrayList<Media>();
	
	// Determines whether the application should continue running.
	private boolean exit = false;
	
	
	
	// Main application loop.
	// Repeats until the user chooses to exit the program.
	public void programLoop() {
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
				showAllEntriesAction();
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
		System.out.println("\nBitte geben Sie an ob es sich um ein Spiel, ein Buch, einen Film oder eine Serie handelt");
		String type = in.nextLine();
			
		// Validate the entered media type.
			while(!type.equals("Filme") && !type.equals("Spiel") && !type.equals("Buch") && !type.equals("Serie")) {
				System.out.println("Bitte geben Sie einen gültigen Typen für den neuen Eintrag ein.");
				type = in.nextLine();
			}
			
		System.out.println("Bitte geben Sie nun den Namen für den Eintrag ein.");
		String name = in.nextLine();
		
		// Create and store the new media entry.
		Media entry = new Media(type,name);
		allEntries.add(entry);
		
		System.out.println("Eintrag wurde angelegt!");
		System.out.println("\n");
	}

	
	// Removes a media entry from the collection by its name.
	public void deleteMediaAction() {
		boolean entryFound = false;
		
		// Check whether there are any entries to delete.
		if(allEntries.isEmpty()) {
			System.out.println("Es sind noch keine Einträge vorhanden!");
		}
		
		else {
		System.out.println("Geben Sie bitte den Namen des Eintrags ein, den Sie entfernen möchten.");
		String deleteName = in.nextLine();
		
			// Search for the requested entry.
			for(int i =0;i<allEntries.size();i++) {
				if(deleteName.equals(allEntries.get(i).getName())) {
					allEntries.remove(i);
					System.out.println("Das gewünschte Eintrag wurde gelöscht");
					entryFound = true;
				}

			}						if(entryFound==false) {	
				System.out.println("Es wurde leider kein Eintrag mit diesem Namen gefunden!");
				}
			
	}
}
	
	// Displays all stored media entries.
	public void showAllEntriesAction() {
		if(allEntries.isEmpty()) {
			System.out.println("Noch kein Eintrag vorhanden!");
		} 
		
		else {
			System.out.println("Hier ist eine Liste aller Entries");
			
			// Print every stored media entry.
			for(int i =0;i<allEntries.size();i++) {
				System.out.println("Name: "+allEntries.get(i).getName() + "| Medium: "+allEntries.get(i).getType());
				continue;
			}
		}
}
	
	// Terminates the application.
	public void endProgram() {
		System.out.println("Vielen Dank für die Nutzung von MediaKeeper!");
		System.out.println("Bis zum Nächsten Mal!");
		exit = true;
	}
}
