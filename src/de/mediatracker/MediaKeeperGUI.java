package de.mediatracker;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.scene.Cursor;
/*
 * Main class of the JavaFX GUI for MediaKeeper.
 *
 * By extending Application, this class can be used as the
 * entry point for the JavaFX application.
 */
public class MediaKeeperGUI extends Application {
	private final MediaController controller = new MediaController();
    
	private Scene mainScene;
	
	
	private Scene deleteEntryScene;
	
     // Standard padding that can be reused throughout the GUI.     
    private final Insets padding = new Insets(20);
    
    //Padding used for elements that need a larger distance from the top, such as the title.
    private final Insets paddingTop = new Insets(50, 0, 0, 0);

    private final Insets paddingBot = new Insets(0,0,50,0);
    
    double buttonWidth = 250;

     //Main entry point of the JavaFX application.

    @Override
    public void start(Stage stage) {

        // Set the title of the application window.
        stage.setTitle("MediaKeeper");
        controller.loadData();


         //BorderPane is the root container of the main scene.
        BorderPane root = new BorderPane();

        //used here to arrange the four menu buttons underneath each other.
        VBox buttonBox = new VBox();


        // Main title of the start page.
        Label title = new Label("Willkommen bei MediaKeeper!");

        // Buttons of the main menu.
        Button addEntry = new Button("Neuen Eintrag hinzufügen");
        Button deleteEntry = new Button("Einen Eintrag entfernen");
        Button showEntries = new Button("Einträge Anzeigen");
        Button exitProgramm = new Button("Speichern und Beenden");



        //Set the vertical spacing between the buttons.
        buttonBox.setSpacing(15);


        
        //Add the buttons to the VBox.
        buttonBox.getChildren().add(addEntry);
        buttonBox.getChildren().add(deleteEntry);
        buttonBox.getChildren().add(showEntries);
        buttonBox.getChildren().add(exitProgramm);


        
        //Place the title in the top area of the BorderPane.
        root.setTop(title);


        
        //Place the VBox containing the buttons in the center area of the BorderPane. 
        root.setCenter(buttonBox);



        //Center the buttons inside the VBox.
        buttonBox.setAlignment(Pos.CENTER);



        //Set the preferred width of all buttons.
        addEntry.setPrefWidth(buttonWidth);
        deleteEntry.setPrefWidth(buttonWidth);
        showEntries.setPrefWidth(buttonWidth);
        exitProgramm.setPrefWidth(buttonWidth);


        
        //Set padding for the different elements.     
        title.setPadding(paddingTop);
        buttonBox.setPadding(paddingTop);

        addEntry.setPadding(padding);
        deleteEntry.setPadding(padding);
        showEntries.setPadding(padding);
        exitProgramm.setPadding(padding);


        
        //Center the nodes inside their respective BorderPane areas.
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);


        /*
         * Event handler for the "Save and Exit" button.
         *
         * The lambda expression is executed whenever the button
         * is clicked.
         *
         * At the moment, the Stage is simply closed.
         * Later, the actual saving functionality will be connected here.
         */
        exitProgramm.setOnAction(event -> {

            System.out.println("Programm wird geschlossen!");

            // Close the application window.
            try {
                controller.saveMedia();
                stage.close();
            } catch (IOException e) {
                
            }
        });


        /*
         * Event handler for the "Add Entry" button.
         *
         * When the button is clicked, the addEntryScene() method
         * is called and the Scene inside the existing Stage is changed.
         */
        addEntry.setOnAction(event -> {

            System.out.println("Scene wird gewechselt!");

            AddEntryGUI addEntryGUI = new AddEntryGUI(controller, this);
            addEntryGUI.show(stage);
        });

        deleteEntry.setOnAction(event -> {
            DeleteEntryGUI deleteGUI = new DeleteEntryGUI(controller, this);
            deleteGUI.show(stage);
        });
        
        //Create the Scene using the root node.
        mainScene = new Scene(root, 800, 600);


        showEntries.setOnAction(event ->{
        	ShowEntriesGUI showEntriesGUI = new ShowEntriesGUI(controller,this);
        	showEntriesGUI.show(stage);
        });

        //Set the Scene on the Stage.
        stage.setScene(mainScene);


        
		//Make the Stage visible.
        stage.show();
    }


 


    
    
    
    public void showSuccessMessage(Stage mainStage, String message) {
        Stage successStage = new Stage();
        VBox content = new VBox();
        successStage.setTitle("Aktion erfolgreich durchgeführt!");

        BorderPane root = new BorderPane();
        Button ok = new Button("OK");
        Label success = new Label(message);

        content.getChildren().add(success);
        content.getChildren().add(ok);
        
        root.setCenter(content);
        
        ok.setOnAction(event -> {
            successStage.close();
            mainStage.setScene(mainScene);
            mainStage.getScene().setCursor(Cursor.DEFAULT);
        });
        
        content.setAlignment(Pos.CENTER);
        content.setSpacing(15);
        
        Scene scene = new Scene(root, 200, 200);
        successStage.setScene(scene);

        successStage.show();
    }


    
    public void showError(Stage mainStage, String error, Scene returnScene) {
        Stage errorStage = new Stage();
        VBox content = new VBox();

        BorderPane root = new BorderPane();
        Button ok = new Button("OK");
        Label errorMessage = new Label(error);

        content.getChildren().add(errorMessage);
        content.getChildren().add(ok);

        root.setCenter(content);

        ok.setOnAction(event -> {
            errorStage.close();
            mainStage.setScene(returnScene);
        });

        content.setAlignment(Pos.CENTER);
        content.setSpacing(15);

        Scene scene = new Scene(root, 200, 200);
        errorStage.setScene(scene);

        errorStage.show();
    }
    public void showMainScene(Stage stage) {
        stage.setScene(mainScene);
    }

    
}