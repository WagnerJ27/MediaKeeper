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


/*
 * Main class of the JavaFX GUI for MediaKeeper.
 *
 * By extending Application, this class can be used as the
 * entry point for the JavaFX application.
 */
public class MediaKeeperGUI extends Application {
	private final MediaController controller = new MediaController();
    
	private Scene mainScene;
     // Standard padding that can be reused throughout the GUI.     
    private final Insets padding = new Insets(20);
    
    //Padding used for elements that need a larger distance from the top, such as the title.
    private final Insets paddingTop = new Insets(50, 0, 0, 0);

    private final Insets paddingBot = new Insets(0,0,50,0);

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
        addEntry.setPrefWidth(250);
        deleteEntry.setPrefWidth(250);
        showEntries.setPrefWidth(250);
        exitProgramm.setPrefWidth(250);


        
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
            stage.close();
        });


        /*
         * Event handler for the "Add Entry" button.
         *
         * When the button is clicked, the addEntryScene() method
         * is called and the Scene inside the existing Stage is changed.
         */
        addEntry.setOnAction(event -> {

            System.out.println("Scene wird gewechselt!");

            addEntryScene(stage);
        });


        
        //Create the Scene using the root node.
        mainScene = new Scene(root, 800, 600);



        //Set the Scene on the Stage.
        stage.setScene(mainScene);


        
		//Make the Stage visible.
        stage.show();
    }



     //Creates the Scene used to add a new media entry.
    private void addEntryScene(Stage stage) {

        
        //Root node of the "Add Entry" Scene.
        BorderPane root = new BorderPane();
        Button addEntry = new Button("Eintrag erstellen");

        
        //Title of the "Add Entry" Scene. 
        Label title = new Label("Neuen Eintrag hinzufügen");


        /*
         * Main GridPane containing the general input fields.
         *
         * These fields are:
         * - Media type
         * - Name
         * - Year
         */
        GridPane form = new GridPane();


        /*
         * Separate GridPane for fields that depend on the
         * selected media type.
         *
         * Example:
         *
         * Game   -> Platform + 100% completion
         * Book   -> Author
         * Movie  -> No additional fields
         * Series -> No additional fields
         */
        GridPane dynamicFields = new GridPane();


        /*
         * ---------- GAME FIELDS ----------
         *
         * These controls are created here but are only displayed
         * when "Spiel" is selected.
         */
        Label platform = new Label("Plattform: ");
        Label completion = new Label("100%: ");

        TextField platformField = new TextField();


        /*
         * ComboBox for selecting whether the game was completed
         * 100 percent.
         */
        ComboBox<String> yesOrNo = new ComboBox<>();

        yesOrNo.getItems().add("Ja");
        yesOrNo.getItems().add("Nein");

        yesOrNo.setValue("Nein");

        /*
         * ---------- BOOK FIELDS ----------
         */
        Label author = new Label("Autor: ");
        TextField authorField = new TextField();


        
        //Place the title in the top area of the BorderPane.       
        root.setTop(title);


        
        //Place the main form in the center area. 
        root.setCenter(form);

        root.setBottom(addEntry);
        
        //ComboBox for selecting the media type.
        ComboBox<String> mediaType = new ComboBox<>();


        //Add the available media types to the ComboBox.
        mediaType.getItems().add("Spiel");
        mediaType.getItems().add("Buch");
        mediaType.getItems().add("Film");
        mediaType.getItems().add("Serie");


        
        //Select "Spiel" by default. 
        mediaType.setValue("Spiel");


        /*
         * ---------- GENERAL FIELDS ----------
         */
        Label type = new Label("Medientyp: ");
        Label name = new Label("Name: ");
        Label year = new Label("Jahr: ");

        TextField nameField = new TextField();
        TextField yearField = new TextField();


        
        //Add the general fields to the main GridPane.
        form.add(type, 0, 0);
        form.add(mediaType, 1, 0);

        form.add(name, 0, 1);
        form.add(year, 0, 2);

        form.add(nameField, 1, 1);
        form.add(yearField, 1, 2);


        
        //Set horizontal and vertical spacing between GridPane cells.
        form.setHgap(20);
        form.setVgap(15);


        
        //Set spacing for the dynamic fields.        
        dynamicFields.setHgap(20);
        dynamicFields.setVgap(15);


        
        //Initially display the fields for a game.
        dynamicFields.add(platform, 0, 0);
        dynamicFields.add(platformField, 1, 0);

        dynamicFields.add(completion, 0, 1);
        dynamicFields.add(yesOrNo, 1, 1);


        
        //Add the dynamic GridPane to the main form.
        form.add(dynamicFields, 0, 3, 2, 1);


        
        //Center the main form.        
        form.setAlignment(Pos.CENTER);

        
        //Add space above the title.
        title.setPadding(paddingTop);


        //Center the title inside the top BorderPane area.
         
        BorderPane.setAlignment(title, Pos.CENTER);


        
        //Center the form inside the center BorderPane area.        
        BorderPane.setAlignment(form, Pos.CENTER);

        BorderPane.setAlignment(addEntry, Pos.CENTER);

        /*
         * Listener for changes to the selected media type.
         *
         * Whenever the value of the ComboBox changes, this
         * lambda expression is executed.
         *
         * newValue contains the newly selected media type.
         */
        mediaType.valueProperty().addListener((observable, oldValue, newValue) -> {

            switch (newValue) {

                
                //---------- GAME ----------

                case "Spiel":

                    
                    //Remove all currently displayed dynamic fields.
                    dynamicFields.getChildren().clear();

                    
                    //Add the game-specific fields.         
                    dynamicFields.add(platform, 0, 0);
                    dynamicFields.add(platformField, 1, 0);

                    dynamicFields.add(completion, 0, 1);
                    dynamicFields.add(yesOrNo, 1, 1);

                    break;


                
                 //---------- BOOK ----------
                 
                case "Buch":

                    
                    //Remove the fields from the previous selection.
                     
                    dynamicFields.getChildren().clear();


                    
                    //Add the book-specific fields.
                    
                    dynamicFields.add(author, 0, 0);
                    dynamicFields.add(authorField, 1, 0);

                    break;


                 //---------- MOVIE ----------

                case "Film":

                    dynamicFields.getChildren().clear();

                    break;



                 //---------- SERIES ----------

                case "Serie":

                    dynamicFields.getChildren().clear();

                    break;



                 //Handle an unexpected value.

                default:

                    System.out.println("Es kam zu einem unerwarteten Fehler");
            }
        });

        addEntry.setOnAction(event -> {
        	String mediaT = mediaType.getValue(); 
        	String mediaName = nameField.getText();
        	int mediaYear = Integer.parseInt(yearField.getText());
        	Media media;
        	switch(mediaT) {
        		case "Spiel":
        			String mediaPlatform = platformField.getText();
        			boolean mediaCompleted = yesOrNo.getValue().equals("Ja");
        			media = controller.addGame(mediaName, mediaYear, mediaPlatform, mediaCompleted);
        			break;
        			
        		case "Buch":
        			String mediaAuthor = authorField.getText();
        			
        				if(!mediaAuthor.isBlank()) {
        					media = controller.addBook(mediaName, mediaYear, mediaAuthor);
        				}else {
        				media =	controller.addBook(mediaName, mediaYear);
        				}
        			break;
        			
        		case "Film":
        			media = controller.addMovie(mediaName, mediaYear);
        			break;
        			
        		case "Serie":
        	
        			media= controller.addSeries(mediaName, mediaYear);
        			break;
        			
        		default:
        			System.out.println("Fehlermeldung");
        			return;
        	}
        	controller.addMedia(media);
        	showSuccessMessage(stage);
        });
        
        
        //Create the Scene for adding a new media entry.
        Scene addEntryScene = new Scene(root, 800, 600);


        
        //Replace the current Scene of the existing Stage.         
        stage.setScene(addEntryScene);
    }


    /*
     * Main method of the Java application.
     *
     * launch() starts the JavaFX application and eventually
     * calls the start() method.
     */
    
    public void showSuccessMessage(Stage mainStage) {
        Stage successStage = new Stage();
        VBox content = new VBox();
        successStage.setTitle("Eintrag erfolgreich angelegt!");

        BorderPane root = new BorderPane();
        Button ok = new Button("OK");
        Label success = new Label("Eintrag wurde erfolgreich erstellt");

        content.getChildren().add(success);
        content.getChildren().add(ok);
        
        root.setCenter(content);
        
        ok.setOnAction(event ->{
            successStage.close();
            mainStage.setScene(mainScene);
        });
        
        content.setAlignment(Pos.CENTER);
        content.setSpacing(15);
        
        Scene scene = new Scene(root, 200, 200);
        successStage.setScene(scene);

        successStage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}