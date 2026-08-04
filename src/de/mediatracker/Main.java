package de.mediatracker;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	
	public static Media addMedia(String typ, String name) {
		Media entry = new Media(typ,name);
		return entry;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Scanner for userinput
		Scanner in = new Scanner(System.in);
		ArrayList<Media> allEntries= new ArrayList<Media>();
		//variable to exit programm loop later on
		boolean exit = false;
		

		System.out.println("Wilkommen bei MediaKeeper!");
		
		while(exit==false) {

			//Welcoming Message when starting the programm
			System.out.println("\nWas möchten Sie tun?");
			System.out.println("");
			System.out.println("1 - Neues Eintrag erstellen");
			System.out.println("2 - Eintrag löschen");
			System.out.println("3 - Alle Einträge auflisten");
			System.out.println("4 - Programm beenden");
			
			//variable inputed by user to determine what they want to do
			int userInput = in.nextInt();
			in.nextLine();
			
			//loop until one of the given choices is selected by the user
			while(userInput !=1 && userInput!=2 && userInput!=3 && userInput!=4) {
				System.out.println("Bitte geben Sie eine der passenden Optionen ein!");
				userInput = in.nextInt();
			}
			
			//User wants to add an Entry
			if(userInput==1) {
				System.out.println("\n\n\n");
				System.out.println("Hinzufügen eines Mediums:");
				System.out.println("\nBitte geben Sie an ob es sich um ein Spiel, ein Buch, einen Film oder eine Serie handelt");
				String typ = in.nextLine();
	
				System.out.println("Bitte geben Sie nun den Namen für den Eintrag ein.");
				String name = in.nextLine();
				Media entry = addMedia(typ,name);
				allEntries.add(entry);
				System.out.println("Eintrag wurde angelegt!");
				System.out.println("\n");
				continue;
			}
			
			
			
			//User wants to delete an Entry
			else if(userInput==2) {
				boolean entryFound = false;
				System.out.println("Geben Sie bitte den Namen des Eintrags ein, den Sie entfernen möchten.");
				String deleteName = in.nextLine();
					for(int i =0;i<allEntries.size();i++) {
						if(deleteName.equals(allEntries.get(i).getName())) {
							allEntries.remove(i);
							System.out.println("Das gewünschte Eintrag wurde gelöscht");
							entryFound = true;
						}
						if(entryFound==false) {	
						System.out.println("Es wurde leider kein Eintrag mit diesem Namen gefunden!");
						}
					}
					continue;
			}
			
			//User wants to see all Entries
			else if(userInput==3) {
				if(allEntries.size()==0) {
					System.out.println("Noch kein Eintrag vorhanden!");
					continue;
				}
				System.out.println("Hier ist eine Liste aller Entries");
				for(int i =0;i<allEntries.size();i++) {
					System.out.println("Name: "+allEntries.get(i).getName() + "| Medium: "+allEntries.get(i).getTyp());
					continue;
				}
			}else {
				System.out.println("Vielen Dank für die Nutzung von MediaKeeper, wir wünschen Ihnen noch einen schönen Tag!");
				exit = true;
			}

		}
											
	}

}
