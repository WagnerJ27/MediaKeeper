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

    // Controller used to delete and save media entries.
    private final MediaController controller;

    // Reference to the main GUI for navigation and displaying messages.
    private final MediaKeeperGUI mainGUI;

    // Standard padding used for GUI elements.
    private final Insets padding = new Insets(20);

    // Padding used for titles at the top of a scene.
    private final Insets paddingTop = new Insets(50, 0, 0, 0);

    // Scene used for deleting a media entry.
    private Scene deleteEntryScene;

    public DeleteEntryGUI(MediaController controller, MediaKeeperGUI mainGUI) {
        this.controller = controller;
        this.mainGUI = mainGUI;
    }

    public void show(Stage stage) {

        // Root container of the "Delete Entry" scene.
        BorderPane root = new BorderPane();

        // GridPane containing the input fields.
        GridPane form = new GridPane();

        // ComboBox used to select the media type.
        ComboBox<String> mediaType = new ComboBox<>();

        // Contains the buttons used for navigation and deleting an entry.
        VBox navigation = new VBox();

        Button deleteEntry = new Button("Eintrag löschen");
        Button backToStart = new Button("Zurück zur Startseite");

        navigation.setSpacing(20);

        // Add the available navigation buttons to the VBox.
        navigation.getChildren().addAll(deleteEntry, backToStart);

        backToStart.setPrefWidth(mainGUI.buttonWidth);

        navigation.setAlignment(Pos.CENTER);

        // Title of the "Delete Entry" scene.
        Label title = new Label("Eintrag löschen");

        Label typeLabel = new Label("Medientyp: ");
        Label nameLabel = new Label("Name: ");

        TextField nameField = new TextField();

        // Add the available media types to the ComboBox.
        mediaType.getItems().addAll("Spiel", "Buch", "Film", "Serie");

        // Select games as the default media type.
        mediaType.setValue("Spiel");

        // Add the input fields to the form.
        form.add(typeLabel, 0, 0);
        form.add(mediaType, 1, 0);
        form.add(nameLabel, 0, 1);
        form.add(nameField, 1, 1);

        // Add space above the title.
        title.setPadding(paddingTop);

        // Set horizontal and vertical spacing between form elements.
        form.setHgap(20);
        form.setVgap(15);

        // Center the form.
        form.setAlignment(Pos.CENTER);

        // Place the different GUI elements inside the BorderPane.
        root.setTop(title);
        root.setCenter(form);
        root.setBottom(navigation);

        // Center the elements inside their BorderPane areas.
        BorderPane.setAlignment(form, Pos.CENTER);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(navigation, Pos.CENTER);

        // Try to delete the selected media entry when the button is clicked.
        deleteEntry.setOnAction(event -> {

            String inputName = nameField.getText();
            String inputType = mediaType.getValue();

            // Check whether the user entered a name.
            if (inputName.isBlank()) {

                mainGUI.showError(
                    stage,
                    "Bitte geben Sie einen Namen ein!",
                    deleteEntryScene
                );

            } else {

                // Try to delete an entry matching the name and media type.
                boolean wasDeleted = controller.deleteEntry(
                    inputName,
                    inputType
                );

                if (wasDeleted) {

                    try {

                        // Save the updated media list after successful deletion.
                        controller.saveMedia();

                        // Show a success message after saving.
                        mainGUI.showSuccessMessage(
                            stage,
                            "Eintrag erfolgreich gelöscht"
                        );

                    } catch (IOException e) {

                        // Show an error if saving the deletion failed.
                        mainGUI.showError(
                            stage,
                            "Fehler beim Speichern in der Datei!",
                            deleteEntryScene
                        );

                        return;
                    }

                } else {

                    // Show an error if no matching entry was found.
                    mainGUI.showError(
                        stage,
                        "Kein passenden Eintrag gefunden",
                        deleteEntryScene
                    );
                }
            }
        });

        // Return to the main menu without deleting an entry.
        backToStart.setOnAction(event -> {
            mainGUI.showMainScene(stage);
        });

        // Create the scene.
        deleteEntryScene = new Scene(root, 800, 600);

        // Display the scene.
        stage.setScene(deleteEntryScene);
    }
}