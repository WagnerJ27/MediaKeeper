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

public class AddEntryGUI {

    // Controller used to create, check and save media entries.
    private final MediaController controller;

    // Reference to the main GUI for navigation and displaying messages.
    private final MediaKeeperGUI mainGUI;

    // Standard padding used for GUI elements.
    private final Insets padding = new Insets(20);

    // Padding used for titles at the top of a scene.
    private final Insets paddingTop = new Insets(50, 0, 0, 0);

    // Scene used for creating a new media entry.
    private Scene addEntryScene;

    public AddEntryGUI(MediaController controller, MediaKeeperGUI mainGUI) {
        this.controller = controller;
        this.mainGUI = mainGUI;
    }

    public void show(Stage stage) {

        // Root node of the "Add Entry" scene.
        BorderPane root = new BorderPane();

        Button addEntry = new Button("Eintrag erstellen");
        Button backToStart = new Button("Zurück zur Startseite");

        // Contains the buttons used for navigating within the scene.
        VBox navigation = new VBox();

        navigation.setSpacing(20);
        navigation.getChildren().add(addEntry);
        navigation.getChildren().add(backToStart);

        backToStart.setPrefWidth(mainGUI.buttonWidth);

        navigation.setAlignment(Pos.CENTER);

        // Title of the "Add Entry" scene.
        Label title = new Label("Neuen Eintrag hinzufügen");

        // Main GridPane containing all input fields.
        GridPane form = new GridPane();

        // ---------- GAME FIELDS ----------

        Label platform = new Label("Plattform: ");
        Label completion = new Label("100%: ");

        TextField platformField = new TextField();

        // ComboBox used to select whether the game was completed 100%.
        ComboBox<String> yesOrNo = new ComboBox<>();

        yesOrNo.getItems().add("Ja");
        yesOrNo.getItems().add("Nein");

        yesOrNo.setValue("Nein");

        // ---------- BOOK FIELDS ----------

        Label author = new Label("Autor: ");
        TextField authorField = new TextField();

        // ---------- MEDIA TYPE ----------

        // ComboBox used to select the type of media being created.
        ComboBox<String> mediaType = new ComboBox<>();

        mediaType.getItems().add("Spiel");
        mediaType.getItems().add("Buch");
        mediaType.getItems().add("Film");
        mediaType.getItems().add("Serie");

        // Select games as the default media type.
        mediaType.setValue("Spiel");

        // ---------- GENERAL FIELDS ----------

        Label type = new Label("Medientyp: ");
        Label name = new Label("Name: ");
        Label year = new Label("Jahr: ");

        TextField nameField = new TextField();
        TextField yearField = new TextField();

        // Apply CSS classes to the scene elements.
        title.getStyleClass().add("smalltitle");

        addEntry.getStyleClass().add("small-button");
        backToStart.getStyleClass().add("small-button");

        nameField.getStyleClass().add("input-field");
        yearField.getStyleClass().add("input-field");
        platformField.getStyleClass().add("input-field");
        authorField.getStyleClass().add("input-field");

        mediaType.getStyleClass().add("input-field");
        yesOrNo.getStyleClass().add("input-field");

        type.getStyleClass().add("form-label");
        name.getStyleClass().add("form-label");
        year.getStyleClass().add("form-label");
        platform.getStyleClass().add("form-label");
        completion.getStyleClass().add("form-label");
        author.getStyleClass().add("form-label");

        // Place the title in the top area.
        root.setTop(title);

        // Place the form in the center.
        root.setCenter(form);

        // Place the navigation buttons at the bottom.
        root.setBottom(navigation);

        // ---------- FORM FIELDS ----------

        // Add the general fields to the form.
        form.add(type, 0, 0);
        form.add(mediaType, 1, 0);

        form.add(name, 0, 1);
        form.add(nameField, 1, 1);

        form.add(year, 0, 2);
        form.add(yearField, 1, 2);

        // Add the game-specific fields initially.
        form.add(platform, 0, 3);
        form.add(platformField, 1, 3);

        form.add(completion, 0, 4);
        form.add(yesOrNo, 1, 4);

        // Set horizontal and vertical spacing between form elements.
        form.setHgap(20);
        form.setVgap(15);

        // Center the form.
        form.setAlignment(Pos.CENTER);

        // Add space above the title.
        title.setPadding(paddingTop);

        // Center the elements inside their BorderPane areas.
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(form, Pos.CENTER);
        BorderPane.setAlignment(navigation, Pos.CENTER);

        // ---------- MEDIA TYPE LISTENER ----------

        // Update the displayed fields whenever the selected media type changes.
        mediaType.valueProperty().addListener((observable, oldValue, newValue) -> {

            // Remove all media-specific fields before adding the required ones.
            form.getChildren().removeAll(
                platform,
                platformField,
                completion,
                yesOrNo,
                author,
                authorField
            );

            switch (newValue) {

                case "Spiel":

                    // Add the fields required for games.
                    form.add(platform, 0, 3);
                    form.add(platformField, 1, 3);

                    form.add(completion, 0, 4);
                    form.add(yesOrNo, 1, 4);

                    break;

                case "Buch":

                    // Add the field required for books.
                    form.add(author, 0, 3);
                    form.add(authorField, 1, 3);

                    break;

                case "Film":

                    // Movies do not require additional fields.
                    break;

                case "Serie":

                    // Series do not require additional fields.
                    break;

                default:

                    // Handle an unexpected media type.
            }
        });

        // ---------- ADD ENTRY BUTTON ----------

        // Validate the input, create the media object and save it.
        addEntry.setOnAction(event -> {

            String mediaT = mediaType.getValue();
            String mediaName = nameField.getText();
            String yearInput = yearField.getText();

            int mediaYear;

            // Check whether all required fields have been filled in.
            if (mediaName.isBlank() || yearInput.isBlank()) {

                mainGUI.showError(
                    stage,
                    "Bitte füllen Sie alle Pflichtfelder aus!",
                    addEntryScene
                );

                return;
            }

            // Convert the entered year from String to int.
            try {

                mediaYear = Integer.parseInt(yearInput);

            } catch (NumberFormatException e) {

                // Show an error if the entered year is not a valid number.
                mainGUI.showError(
                    stage,
                    "Bitte geben Sie eine Zahl an!",
                    addEntryScene
                );

                return;
            }

            // Check whether an entry with the same name and media type already exists.
            boolean nameExists = controller.mediaExists(mediaName, mediaT);

            if (nameExists) {

                mainGUI.showError(
                    stage,
                    "Es gibt bereits einen Eintrag mit diesem Namen!",
                    addEntryScene
                );

                return;
            }

            Media media;

            // Create the appropriate media object based on the selected type.
            switch (mediaT) {

                case "Spiel":

                    String mediaPlatform = platformField.getText();
                    boolean mediaCompleted = yesOrNo.getValue().equals("Ja");

                    // A platform is required for games.
                    if (mediaPlatform.isBlank()) {

                        mainGUI.showError(
                            stage,
                            "Bitte geben Sie zusätzlich die Plattform an!",
                            addEntryScene
                        );

                        return;
                    }

                    media = controller.addGame(
                        mediaName,
                        mediaYear,
                        mediaPlatform,
                        mediaCompleted
                    );

                    break;

                case "Buch":

                    String mediaAuthor = authorField.getText();

                    // Create a book with or without an author.
                    if (!mediaAuthor.isBlank()) {

                        media = controller.addBook(
                            mediaName,
                            mediaYear,
                            mediaAuthor
                        );

                    } else {

                        media = controller.addBook(
                            mediaName,
                            mediaYear
                        );
                    }

                    break;

                case "Film":

                    media = controller.addMovie(
                        mediaName,
                        mediaYear
                    );

                    break;

                case "Serie":

                    media = controller.addSeries(
                        mediaName,
                        mediaYear
                    );

                    break;

                default:

                    // Handle an unexpected media type.
                   
                    return;
            }

            // Add the newly created media object to the controller.
            controller.addMedia(media);

            // Save the updated media list to the CSV file.
            try {

                controller.saveMedia();

                // Show a success message after the entry was saved successfully.
                mainGUI.showSuccessMessage(
                    stage,
                    "Eintrag wurde hinzugefügt"
                );

            } catch (IOException e) {

                // Show an error if saving the entry failed.
                mainGUI.showError(
                    stage,
                    "Fehler beim Speichern in der Datei!",
                    addEntryScene
                );

                return;
            }
        });

        // Return to the main menu without creating an entry.
        backToStart.setOnAction(event -> {
            mainGUI.showMainScene(stage);
        });

        // Create the scene.
        addEntryScene = new Scene(root, 800, 600);

        // Load the application stylesheet.
        addEntryScene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        // Display the scene.
        stage.setScene(addEntryScene);
    }
}