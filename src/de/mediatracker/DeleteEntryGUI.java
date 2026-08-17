package de.mediatracker;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.scene.Cursor;

public class DeleteEntryGUI {
    private final MediaController controller;
    private final MediaKeeperGUI mainGUI;

    private final Insets padding = new Insets(20);
    private final Insets paddingTop = new Insets(50, 0, 0, 0);

    private Scene deleteEntryScene;

    public DeleteEntryGUI(MediaController controller, MediaKeeperGUI mainGUI) {
        this.controller = controller;
        this.mainGUI = mainGUI;
    }
public void show(Stage stage) {
    	
    	BorderPane root = new BorderPane();
    	GridPane form = new GridPane();
    	ComboBox<String> mediaType = new ComboBox<>();
    	
    	VBox navigation = new VBox();
    	Button deleteEntry = new Button("Eintrag löschen");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
        navigation.setSpacing(20);
        navigation.getChildren().add(deleteEntry);
        navigation.getChildren().add(backToStart);
        
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	navigation.setAlignment(Pos.CENTER);
    	
    	
    	
    	Label title = new Label("Eintrag löschen");
    	Label typeLabel = new Label("Medientyp: "); 
    	
    	Label nameLabel = new Label("Name: ");
    	TextField nameField = new TextField();
    	
    	mediaType.getItems().add("Spiel");
    	mediaType.getItems().add("Buch");
    	mediaType.getItems().add("Film");
    	mediaType.getItems().add("Serie");

    	mediaType.setValue("Spiel");
    	
    	form.add(typeLabel, 0, 0);
    	form.add(mediaType, 1, 0);
    	form.add(nameLabel, 0, 1);
    	form.add(nameField, 1, 1);
    	
    	title.setPadding(paddingTop);
    	
    	
        form.setHgap(20);
        form.setVgap(15);
        
        form.setAlignment(Pos.CENTER);

    	root.setTop(title);
    	root.setCenter(form);
    	root.setBottom(navigation);
    	BorderPane.setAlignment(form, Pos.CENTER);
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	
    	
    	deleteEntry.setOnAction(event ->{
    		String inputName = nameField.getText();
    		String inputType = mediaType.getValue();
    			if(inputName.isBlank()) {
    				mainGUI.showError(stage,"Bitte geben Sie einen Namen ein!",deleteEntryScene);
    				
    			}else {
    				boolean wasDeleted = controller.deleteEntry(inputName, inputType);
    					if(wasDeleted) {
    						try {
    							mainGUI.showSuccessMessage(stage, "Eintrag erfolgreich gelöscht");
    							controller.saveMedia();
    						}catch (IOException e){
    							mainGUI.showError(stage, "Fehler beim Speichern in der Datei!",deleteEntryScene);
    			        	    return;
    						}
    						
    						
    					}else {
    						mainGUI.showError(stage,"Kein passenden Eintrag gefunden",deleteEntryScene);
    					}
    				
    			}
    			
    	});
    	
        backToStart.setOnAction(event ->{
        	mainGUI.showMainScene(stage);
        });
    	
       deleteEntryScene = new Scene(root, 800, 600);
               
       stage.setScene(deleteEntryScene);
    }
    
}
