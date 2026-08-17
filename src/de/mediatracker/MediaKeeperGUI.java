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
import java.io.IOException;
import javafx.scene.Cursor;

// Main JavaFX application class and entry point for the GUI.
public class MediaKeeperGUI extends Application {

    // Main controller shared by the different GUI components.
    private final MediaController controller = new MediaController();

    // Stores the main application scene for returning to the start screen.
    private Scene mainScene;

    // Standard padding used for GUI elements.
    private final Insets padding = new Insets(20);

    // Padding used to create additional space above titles.
    private final Insets paddingTop = new Insets(50, 0, 0, 0);

    // Preferred width used for the main menu buttons.
    double buttonWidth = 250;


    // Starts the JavaFX application and creates the main menu.
    @Override
    public void start(Stage stage) {

        // Set the title of the application window.
        stage.setTitle("MediaKeeper");

        // Load all previously saved media entries.
        controller.loadData();

        // Create the root layout for the main scene.
        BorderPane root = new BorderPane();

        // Arrange the main menu buttons vertically.
        VBox buttonBox = new VBox();

        // Create the title of the start screen.
        Label title = new Label("Willkommen bei MediaKeeper!");

        // Create the buttons of the main menu.
        Button addEntry = new Button("Neuen Eintrag hinzufügen");
        Button deleteEntry = new Button("Einen Eintrag entfernen");
        Button showEntries = new Button("Einträge Anzeigen");
        Button exitProgramm = new Button("Speichern und Beenden");


        // Set the spacing between the menu buttons.
        buttonBox.setSpacing(15);

        // Add all menu buttons to the VBox.
        buttonBox.getChildren().addAll(
            addEntry,
            deleteEntry,
            showEntries,
            exitProgramm
        );


        // Place the title at the top of the layout.
        root.setTop(title);

        // Place the menu buttons in the center of the layout.
        root.setCenter(buttonBox);

        // Center the buttons inside the VBox.
        buttonBox.setAlignment(Pos.CENTER);

        // Set the preferred width of all menu buttons.
        addEntry.setPrefWidth(buttonWidth);
        deleteEntry.setPrefWidth(buttonWidth);
        showEntries.setPrefWidth(buttonWidth);
        exitProgramm.setPrefWidth(buttonWidth);


        // Apply padding to the title and button area.
        title.setPadding(paddingTop);
        buttonBox.setPadding(paddingTop);

        // Apply padding to all menu buttons.
        addEntry.setPadding(padding);
        deleteEntry.setPadding(padding);
        showEntries.setPadding(padding);
        exitProgramm.setPadding(padding);


        // Center the title and button area inside their BorderPane regions.
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);


        // Save all media and close the application when the exit button is clicked.
        exitProgramm.setOnAction(event -> {

            System.out.println("Programm wird geschlossen!");

            try {

                controller.saveMedia();
                stage.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        });


        // Open the GUI for creating a new media entry.
        addEntry.setOnAction(event -> {

            System.out.println("Scene wird gewechselt!");

            AddEntryGUI addEntryGUI = new AddEntryGUI(controller, this);
            addEntryGUI.show(stage);
        });


        // Open the GUI for deleting a media entry.
        deleteEntry.setOnAction(event -> {

            DeleteEntryGUI deleteGUI = new DeleteEntryGUI(controller, this);
            deleteGUI.show(stage);
        });


        // Open the GUI for displaying media entries.
        showEntries.setOnAction(event -> {

            ShowEntriesGUI showEntriesGUI = new ShowEntriesGUI(controller, this);
            showEntriesGUI.show(stage);
        });


        // Create the main application scene.
        mainScene = new Scene(root, 800, 600);

        // Set the main scene on the application window.
        stage.setScene(mainScene);

        // Display the application window.
        stage.show();
    }


    // Displays a message after an action has been completed successfully.
    public void showSuccessMessage(Stage mainStage, String message) {

        Stage successStage = new Stage();
        VBox content = new VBox();

        successStage.setTitle("Aktion erfolgreich durchgeführt!");

        BorderPane root = new BorderPane();

        Button ok = new Button("OK");
        Label success = new Label(message);

        // Add the message and confirmation button to the dialog.
        content.getChildren().addAll(success, ok);

        root.setCenter(content);

        // Close the dialog and return to the main scene.
        ok.setOnAction(event -> {

            successStage.close();
            mainStage.setScene(mainScene);

            // Reset the cursor after returning to the main scene.
            mainStage.getScene().setCursor(Cursor.DEFAULT);
        });

        content.setAlignment(Pos.CENTER);
        content.setSpacing(15);

        Scene scene = new Scene(root, 200, 200);

        successStage.setScene(scene);
        successStage.show();
    }


    // Displays an error message and returns to the specified scene.
    public void showError(Stage mainStage, String error, Scene returnScene) {

        Stage errorStage = new Stage();
        VBox content = new VBox();

        BorderPane root = new BorderPane();

        Button ok = new Button("OK");
        Label errorMessage = new Label(error);

        // Add the error message and confirmation button to the dialog.
        content.getChildren().addAll(errorMessage, ok);

        root.setCenter(content);

        // Close the dialog and return to the previous scene.
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


    // Switches the main application window back to the start screen.
    public void showMainScene(Stage stage) {
        stage.setScene(mainScene);
    }
}