package de.mediatracker;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;

public class MediaKeeperGUI extends Application {

	@Override
    public void start(Stage stage) {
        stage.setTitle("MediaKeeper");
        
        BorderPane root = new BorderPane();
        VBox buttonBox = new VBox();
        Insets padding = new Insets(20);
        Insets paddingTop = new Insets(50,0,0,0);
        
        
        Label title = new Label("Willkommen bei MediaKeeper!");      
        Button addEntry = new Button("Neuen Eintrag hinzufügen");
        Button deleteEntry = new Button("Einen Eintrag entfernen");
        Button showEntries = new Button("Einträge Anzeigen");
        Button exitProgramm = new Button("Speichern und Beenden");
                      
        buttonBox.setSpacing(15);
        buttonBox.getChildren().add(addEntry);
        buttonBox.getChildren().add(deleteEntry);
        buttonBox.getChildren().add(showEntries);
        buttonBox.getChildren().add(exitProgramm);
        
        root.setTop(title);
        root.setCenter(buttonBox);
        buttonBox.setAlignment(Pos.CENTER);

        addEntry.setPrefWidth(250);
        deleteEntry.setPrefWidth(250);
        showEntries.setPrefWidth(250);
        exitProgramm.setPrefWidth(250);
       
        title.setPadding(paddingTop);
        buttonBox.setPadding(paddingTop);
        addEntry.setPadding(padding);
        deleteEntry.setPadding(padding);
        showEntries.setPadding(padding);
        exitProgramm.setPadding(padding);
        
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);
        
        
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
    	stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
