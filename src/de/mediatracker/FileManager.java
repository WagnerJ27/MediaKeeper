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
			 writer.write(media.getType()+";"+media.getName()+";"+media.getYear());
			 writer.newLine();
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
					MediaType type = MediaType.valueOf(parts[0]);
					int year = Integer.parseInt(parts[2]);
					Media obj = new Media(type,parts[1],year);
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
