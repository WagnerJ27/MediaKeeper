package de.mediatracker;

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

        // Contains the buttons used for deleting and navigating.
        VBox navigation = new VBox();

        Button deleteEntry = new Button("Eintrag löschen");
        Button backToStart = new Button("Zurück zur Startseite");

        // Configure the navigation container.
        navigation.setSpacing(20);
        navigation.getChildren().addAll(deleteEntry, backToStart);
        navigation.setAlignment(Pos.CENTER);

        // Configure the navigation button width.
        deleteEntry.setPrefWidth(mainGUI.buttonWidth);
        backToStart.setPrefWidth(mainGUI.buttonWidth);

        // Title of the "Delete Entry" scene.
        Label title = new Label("Eintrag löschen");

        // Labels and input field for the entry.
        Label typeLabel = new Label("Medientyp:");
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        // Add the available media types to the ComboBox.
        mediaType.getItems().addAll(
            "Spiel",
            "Buch",
            "Film",
            "Serie"
        );

        // Select games as the default media type.
        mediaType.setValue("Spiel");

        // Apply CSS classes to the title.
        title.getStyleClass().add("smalltitle");

        // Apply the shared button style.
        deleteEntry.getStyleClass().add("small-button");
        backToStart.getStyleClass().add("small-button");

        // Apply the shared input field style.
        mediaType.getStyleClass().add("input-field");
        nameField.getStyleClass().add("input-field");

        // Apply the shared label style.
        typeLabel.getStyleClass().add("form-label");
        nameLabel.getStyleClass().add("form-label");

        // Add the input fields to the form.
        form.add(typeLabel, 0, 0);
        form.add(mediaType, 1, 0);

        form.add(nameLabel, 0, 1);
        form.add(nameField, 1, 1);

        // Configure spacing between form elements.
        form.setHgap(20);
        form.setVgap(15);

        // Center the form.
        form.setAlignment(Pos.CENTER);

        // Place the title, form and navigation in the BorderPane.
        root.setTop(title);
        root.setCenter(form);
        root.setBottom(navigation);

        // Center the elements inside their BorderPane areas.
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(form, Pos.CENTER);
        BorderPane.setAlignment(navigation, Pos.CENTER);

        // Handle the delete button.
        deleteEntry.setOnAction(event -> {

            String inputName = nameField.getText();
            String inputType = mediaType.getValue();

            // Check whether a name was entered.
            if (inputName.isBlank()) {

                mainGUI.showError(
                    stage,
                    "Bitte geben Sie einen Namen ein!",
                    deleteEntryScene
                );

            } else {

                // Try to delete the selected entry.
                boolean wasDeleted =
                    controller.deleteEntry(inputName, inputType);

                if (wasDeleted) {

                    try {

                        // Save the updated media list.
                        controller.saveMedia();

                        // Show a success message.
                        mainGUI.showSuccessMessage(
                            stage,
                            "Eintrag erfolgreich gelöscht"
                        );

                    } catch (IOException e) {

                        // Show an error if saving failed.
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

        // Load the application stylesheet.
        deleteEntryScene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        // Display the scene.
        stage.setScene(deleteEntryScene);
    }
}