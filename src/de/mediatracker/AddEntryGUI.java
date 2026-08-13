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

    private final MediaController controller;
    private final MediaKeeperGUI mainGUI;

    private final Insets padding = new Insets(20);
    private final Insets paddingTop = new Insets(50, 0, 0, 0);

    private Scene addEntryScene;

    public AddEntryGUI(MediaController controller, MediaKeeperGUI mainGUI) {
        this.controller = controller;
        this.mainGUI = mainGUI;
    }
	
    public void show(Stage stage) {

        // Root node of the "Add Entry" Scene.
        BorderPane root = new BorderPane();
        Button addEntry = new Button("Eintrag erstellen");

        // Title of the "Add Entry" Scene.
        Label title = new Label("Neuen Eintrag hinzufügen");

        /*
         * Main GridPane containing the general input fields.
         */
        GridPane form = new GridPane();

        /*
         * Separate GridPane for fields that depend on the
         * selected media type.
         */
        GridPane dynamicFields = new GridPane();

        // ---------- GAME FIELDS ----------

        Label platform = new Label("Plattform: ");
        Label completion = new Label("100%: ");

        TextField platformField = new TextField();

        ComboBox<String> yesOrNo = new ComboBox<>();

        yesOrNo.getItems().add("Ja");
        yesOrNo.getItems().add("Nein");

        yesOrNo.setValue("Nein");

        // ---------- BOOK FIELDS ----------

        Label author = new Label("Autor: ");
        TextField authorField = new TextField();

        // Place the title in the top area.
        root.setTop(title);

        // Place the main form in the center.
        root.setCenter(form);

        root.setBottom(addEntry);

        // ---------- MEDIA TYPE ----------

        ComboBox<String> mediaType = new ComboBox<>();

        mediaType.getItems().add("Spiel");
        mediaType.getItems().add("Buch");
        mediaType.getItems().add("Film");
        mediaType.getItems().add("Serie");

        mediaType.setValue("Spiel");

        // ---------- GENERAL FIELDS ----------

        Label type = new Label("Medientyp: ");
        Label name = new Label("Name: ");
        Label year = new Label("Jahr: ");

        TextField nameField = new TextField();
        TextField yearField = new TextField();

        // Add the general fields to the main GridPane.
        form.add(type, 0, 0);
        form.add(mediaType, 1, 0);

        form.add(name, 0, 1);
        form.add(year, 0, 2);

        form.add(nameField, 1, 1);
        form.add(yearField, 1, 2);

        // Set spacing.
        form.setHgap(20);
        form.setVgap(15);

        dynamicFields.setHgap(20);
        dynamicFields.setVgap(15);

        // Initially display the fields for a game.
        dynamicFields.add(platform, 0, 0);
        dynamicFields.add(platformField, 1, 0);

        dynamicFields.add(completion, 0, 1);
        dynamicFields.add(yesOrNo, 1, 1);

        // Add dynamic fields to the form.
        form.add(dynamicFields, 0, 3, 2, 1);

        // Center the form.
        form.setAlignment(Pos.CENTER);

        title.setPadding(paddingTop);

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(form, Pos.CENTER);
        BorderPane.setAlignment(addEntry, Pos.CENTER);

        // ---------- MEDIA TYPE LISTENER ----------

        mediaType.valueProperty().addListener((observable, oldValue, newValue) -> {

            switch (newValue) {

                case "Spiel":

                    dynamicFields.getChildren().clear();

                    dynamicFields.add(platform, 0, 0);
                    dynamicFields.add(platformField, 1, 0);

                    dynamicFields.add(completion, 0, 1);
                    dynamicFields.add(yesOrNo, 1, 1);

                    break;

                case "Buch":

                    dynamicFields.getChildren().clear();

                    dynamicFields.add(author, 0, 0);
                    dynamicFields.add(authorField, 1, 0);

                    break;

                case "Film":

                    dynamicFields.getChildren().clear();

                    break;

                case "Serie":

                    dynamicFields.getChildren().clear();

                    break;

                default:

                    System.out.println("Es kam zu einem unerwarteten Fehler");
            }
        });

        // ---------- ADD ENTRY BUTTON ----------

        addEntry.setOnAction(event -> {

            String mediaT = mediaType.getValue();
            String mediaName = nameField.getText();
            String yearInput = yearField.getText();

            int mediaYear;

            if (mediaName.isBlank() || yearInput.isBlank()) {

                mainGUI.showError(
                    stage,
                    "Bitte füllen Sie alle Pflichtfelder aus!",
                    addEntryScene
                );

                return;
            }

            try {

                mediaYear = Integer.parseInt(yearInput);

            } catch (NumberFormatException e) {

                mainGUI.showError(
                    stage,
                    "Bitte geben Sie eine Zahl an!",
                    addEntryScene
                );

                return;
            }

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

            switch (mediaT) {

                case "Spiel":

                    String mediaPlatform = platformField.getText();
                    boolean mediaCompleted = yesOrNo.getValue().equals("Ja");

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

                    System.out.println("Fehlermeldung");
                    return;
            }

            controller.addMedia(media);

            try {

                controller.saveMedia();

                mainGUI.showSuccessMessage(
                    stage,
                    "Eintrag wurde hinzugefügt"
                );

            } catch (IOException e) {

                mainGUI.showError(
                    stage,
                    "Fehler beim Speichern in der Datei!",
                    addEntryScene
                );

                return;
            }
        });

        // Create the Scene.
        addEntryScene = new Scene(root, 800, 600);

        // Display the Scene.
        stage.setScene(addEntryScene);
    }
}
