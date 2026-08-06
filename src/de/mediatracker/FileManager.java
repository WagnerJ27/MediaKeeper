package de.mediatracker;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



public class FileManager {
	private static final String OUTPUT_FILE = "Save/media.csv";
	File saveFile = new File(OUTPUT_FILE);
	File saveFolder = new File("Save");
	
	public void saveMedia(ArrayList<Media> allEntries) {
	
		

		if (!saveFolder.exists()) {
		    saveFolder.mkdir();
		}
	 try(BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
		 
		 for(Media media : allEntries) {
			 String type = media.getClass().getSimpleName();
			 
			 switch(type) {
			 	case "Game":
			 		Game game = (Game) media;
					 writer.write(game.getClass().getSimpleName()+";"+game.getName()+";"+game.getYear()+";" +game.getPlatform()+";"+game.getHundredPercentCompletion());
					 writer.newLine();
				 break;
				 
			 	case "Book":
			 		Book book = (Book) media;
					 writer.write(book.getClass().getSimpleName()+";"+book.getName()+";"+book.getYear()+";"+book.getAuthor());
					 writer.newLine();
			 		break;
			 		
			 	case "Movie":			 		
			 	case "Series":
					 writer.write(media.getClass().getSimpleName()+";"+media.getName()+";"+media.getYear());
					 writer.newLine();
			 		break;
			 	
			 	default:
			 	    System.out.println("Unbekannter Medientyp konnte nicht gespeichert werden.");
			 	    break;
			 }

		 }
		 

	 }catch (IOException e) {
		 System.out.println("Speichern fehlgeschlagen.");
    	 e.printStackTrace();

    }
 }
 
	public ArrayList<Media> loadMedia() {
		ArrayList<Media> allEntries = new ArrayList<>();
		
		
		if(saveFile.exists()) {
			String line;
		try(BufferedReader reader = new BufferedReader(new FileReader(saveFile))){	
			while((line = reader.readLine())!=null) {
					String[] parts = line.split(";");
					String type = parts[0];
					int year;
					Media obj;
					 switch(type) {
					 	case "Game":
					 		 year = Integer.parseInt(parts[2]);
					 		boolean completed = Boolean.valueOf(parts[4]);
					 		obj = new Game(parts[1],year,parts[3],completed);
					 		break;
						 	
					 	case "Book":
					 		 year = Integer.parseInt(parts[2]);
					 		 obj = new Book(parts[1],year,parts[3]);
					 		break;
					 		
					 	case "Movie":			 		
					 		year = Integer.parseInt(parts[2]);
					 		obj = new Movie(parts[1],year);
					 		break;
					 	case "Series":
					 		year = Integer.parseInt(parts[2]);
					 		obj = new Series(parts[1],year);
					 		break;
					 	
					 	default:
					 	    System.out.println("Fehler beim Laden");
					 	    continue;
					 }
					 allEntries.add(obj);

			}
		}catch(IOException  e) {
			System.out.println("Laden fehlgeschlagen");
	    	 e.printStackTrace();
		}
		
		}
		return allEntries;
	}
}
