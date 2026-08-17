package de.mediatracker;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;

public class ShowEntriesGUI {
	
    // References to the controller and main GUI used for navigation and data access.
    private final MediaController controller;
    private final MediaKeeperGUI mainGUI;
    
    // Standard padding values used throughout the GUI.
    private final Insets padding = new Insets(20);
    private final Insets paddingTop = new Insets(50, 0, 0, 0);


    // Receives the controller and main GUI needed by this class.
    public ShowEntriesGUI(MediaController controller, MediaKeeperGUI mainGUI) {
        this.controller = controller;
        this.mainGUI = mainGUI;
    }
    
    // Displays the media type selection screen.
    protected void show(Stage stage) {

    	// Create the root layout and container for the selection buttons.
    	BorderPane root = new BorderPane();
    	VBox content = new VBox();
    	Label title = new Label("Einträge anzeigen");
    	
    	// Create buttons for each available media type.
    	Button showGamesButton = new Button("Spiele anzeigen");
    	Button showBooksButton = new Button("Bücher anzeigen");
    	Button showMoviesButton = new Button("Filme anzeigen");
    	Button showSeriesButton = new Button("Serien anzeigen");

    	// Set the preferred width of all media type buttons.
    	showGamesButton.setPrefWidth(mainGUI.buttonWidth);
    	showBooksButton.setPrefWidth(mainGUI.buttonWidth);
    	showMoviesButton.setPrefWidth(mainGUI.buttonWidth);
    	showSeriesButton.setPrefWidth(mainGUI.buttonWidth);
    	
    	// Set the padding of all media type buttons.
    	showGamesButton.setPadding(padding);
    	showBooksButton.setPadding(padding);
    	showMoviesButton.setPadding(padding);
    	showSeriesButton.setPadding(padding);
    	
    	// Create the button used to return to the main screen.
    	Button toHomeScreen = new Button("Zurück zur Startseite");
    	
    	// Center the selection buttons inside the VBox.
    	content.setAlignment(Pos.CENTER);
    	
    	// Add all media type buttons to the VBox.
    	content.getChildren().add(showGamesButton);
    	content.getChildren().add(showBooksButton);
    	content.getChildren().add(showMoviesButton);
    	content.getChildren().add(showSeriesButton);
    	
    	// Place the title, buttons and navigation button in the BorderPane.
    	root.setTop(title);
    	root.setCenter(content);
    	root.setBottom(toHomeScreen);
    	
    	// Set spacing and title padding.
    	content.setSpacing(15);
    	title.setPadding(paddingTop);
    	
    	// Center the elements inside their BorderPane areas.
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(content, Pos.CENTER);
    	BorderPane.setAlignment(toHomeScreen, Pos.CENTER);
    	
    	// Open the game table when the game button is clicked.
    	showGamesButton.setOnAction(event ->{
    		showGames(stage);
    	});
    	
    	// Open the book table when the book button is clicked.
    	showBooksButton.setOnAction(even ->{
    		showBooks(stage);
    	});
    	
    	// Open the movie table when the movie button is clicked.
    	showMoviesButton.setOnAction(event ->{
    		showMovies(stage);
    	});
    	
    	// Open the series table when the series button is clicked.
    	showSeriesButton.setOnAction(event -> {
    		showSeries(stage);
    	});
    	
    	// Return to the main screen when the home button is clicked.
    	toHomeScreen.setOnAction(event ->{
    		mainGUI.showMainScene(stage);
    	});
    	
    	// Create and display the selection scene.
    	Scene showEntriesScene = new Scene(root,800,600);
    	stage.setScene(showEntriesScene);
    }
    
    // Displays all games in a TableView.
    private void showGames(Stage stage) {
    	// Get all games from the controller and convert them into an observable list.
    	ArrayList<Game> games = controller.getGames();
    	ObservableList<Game> gameData = FXCollections.observableArrayList(games);
    	
    	// Create the basic layout and navigation elements.
    	BorderPane root = new BorderPane();
    	Label title = new Label("Spiele");
    	HBox navigation = new HBox();
    	Button backToOverview = new Button("Zurück zur Auswahl");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
    	// Create the table that displays the games.
    	TableView<Game> gameTable = new TableView<>();
    	
    	// Set the data displayed by the table.
    	gameTable.setItems(gameData);	
    	
    	// Create and configure the name column.
    	TableColumn<Game, String> nameColumn = new TableColumn<>("Name");
    	nameColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getName())
    		);
    	gameTable.getColumns().add(nameColumn);
    	
    	// Create and configure the year column.
    	TableColumn<Game, Number> yearColumn = new TableColumn<>("Jahr");
    	yearColumn.setCellValueFactory(
    		    data -> new SimpleIntegerProperty(data.getValue().getYear())
    		);
    	gameTable.getColumns().add(yearColumn);
    	
    	// Create and configure the platform column.
    	TableColumn<Game, String> platformColumn = new TableColumn<>("Plattform");
    	platformColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getPlatform())
    		);
    	gameTable.getColumns().add(platformColumn);
    	
    	// Create and configure the completion column.
    	TableColumn<Game, Boolean> completionColumn = new TableColumn<>("100%");
    	completionColumn.setCellValueFactory(
    	        data -> new SimpleBooleanProperty(data.getValue().getHundredPercentCompletion())
    	);

    	// Display "Ja" or "Nein" instead of true or false.
    	completionColumn.setCellFactory(column -> new TableCell<Game, Boolean>() {

    	    @Override
    	    protected void updateItem(Boolean item, boolean empty) {
    	        super.updateItem(item, empty);

    	        // Clear the cell if it does not contain an item.
    	        if (empty) {
    	            setText(null);
    	        } else {
    	            // Convert the boolean value into a readable German text.
    	            setText(item ? "Ja" : "Nein");
    	        }
    	    }
    	});

    	gameTable.getColumns().add(completionColumn);
    	
    	// Set the preferred width of the navigation buttons.
    	backToOverview.setPrefWidth(mainGUI.buttonWidth);
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	// Add the navigation buttons to the HBox.
    	navigation.getChildren().add(backToStart);
    	navigation.getChildren().add(backToOverview);
    	navigation.setAlignment(Pos.CENTER);
    	
    	// Place the elements in the BorderPane.
    	root.setTop(title);
    	root.setCenter(gameTable);
    	root.setBottom(navigation);
    	
    	title.setPadding(paddingTop);
    	
    	// Center the elements inside their BorderPane areas.
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(gameTable, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	// Create and display the game scene.
    	Scene scene = new Scene(root,800,600);
    	stage.setScene(scene);
    	
    	// Return to the media type selection.
    	backToOverview.setOnAction(event ->{
    		show(stage);
    	});
    	
    	// Return to the main screen.
    	backToStart.setOnAction(event ->{
    		mainGUI.showMainScene(stage);
    	});
    }
 
    // Displays all books in a TableView.
    private void showBooks(Stage stage) {
    	// Get all books from the controller and convert them into an observable list.
    	ArrayList<Book> books = controller.getBooks();
    	ObservableList<Book> bookData = FXCollections.observableArrayList(books);
    	
    	// Create the basic layout and navigation elements.
    	BorderPane root = new BorderPane();
    	Label title = new Label("Bücher");
    	HBox navigation = new HBox();
    	Button backToOverview = new Button("Zurück zur Auswahl");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
    	// Create the table and set its data.
    	TableView<Book> bookTable = new TableView<>();
    	bookTable.setItems(bookData);	
    	
    	// Create and configure the name column.
    	TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
    	nameColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getName())
    		);
    	bookTable.getColumns().add(nameColumn);
    	
    	// Create and configure the year column.
    	TableColumn<Book, Number> yearColumn = new TableColumn<>("Jahr");
    	yearColumn.setCellValueFactory(
    		    data -> new SimpleIntegerProperty(data.getValue().getYear())
    		);
    	bookTable.getColumns().add(yearColumn);
    	
    	// Create and configure the author column.
    	TableColumn<Book, String> authorColumn = new TableColumn<>("Autor");
    	authorColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getAuthor())
    		);
    	bookTable.getColumns().add(authorColumn);
    	
    	// Set the preferred width of the navigation buttons.
    	backToOverview.setPrefWidth(mainGUI.buttonWidth);
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	// Add the navigation buttons to the HBox.
    	navigation.getChildren().add(backToStart);
    	navigation.getChildren().add(backToOverview);
    	
    	navigation.setAlignment(Pos.CENTER);
    	
    	// Place the elements in the BorderPane.
    	root.setTop(title);
    	root.setCenter(bookTable);
    	root.setBottom(navigation);
    	
    	title.setPadding(paddingTop);
    	
    	// Center the elements inside their BorderPane areas.
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(bookTable, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	// Create and display the book scene.
    	Scene scene = new Scene(root,800,600);
    	stage.setScene(scene);
    	
    	// Return to the media type selection.
    	backToOverview.setOnAction(event ->{
    		show(stage);
    	});
    	
    	// Return to the main screen.
    	backToStart.setOnAction(event -> {
    		mainGUI.showMainScene(stage);
    	});
    }
    
    // Displays all movies in a TableView.
    private void showMovies(Stage stage) {
    	// Get all movies from the controller and convert them into an observable list.
    	ArrayList<Movie> movies = controller.getMovies();
    	ObservableList<Movie> movieData = FXCollections.observableArrayList(movies);
    	
    	// Create the basic layout and navigation elements.
    	BorderPane root = new BorderPane();
    	Label title = new Label("Filme");
    	HBox navigation = new HBox();
    	Button backToOverview = new Button("Zurück zur Auswahl");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
    	// Create the table and set its data.
    	TableView<Movie> movieTable = new TableView<>();
    	movieTable.setItems(movieData);	
    	
    	// Create and configure the name column.
    	TableColumn<Movie, String> nameColumn = new TableColumn<>("Name");
    	nameColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getName())
    		);
    	movieTable.getColumns().add(nameColumn);
    	
    	// Create and configure the year column.
    	TableColumn<Movie, Number> yearColumn = new TableColumn<>("Jahr");
    	yearColumn.setCellValueFactory(
    		    data -> new SimpleIntegerProperty(data.getValue().getYear())
    		);
    	movieTable.getColumns().add(yearColumn);
    	
    	// Set the preferred width of the navigation buttons.
    	backToOverview.setPrefWidth(mainGUI.buttonWidth);
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	// Add the navigation buttons to the HBox.
    	navigation.getChildren().add(backToStart);
    	navigation.getChildren().add(backToOverview);
    	navigation.setAlignment(Pos.CENTER);
    	
    	// Place the elements in the BorderPane.
    	root.setTop(title);
    	root.setCenter(movieTable);
    	root.setBottom(navigation);
    	
    	title.setPadding(paddingTop);
    	
    	// Center the elements inside their BorderPane areas.
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(movieTable, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	// Create and display the movie scene.
    	Scene scene = new Scene(root,800,600);
    	stage.setScene(scene);
    	
    	// Return to the media type selection.
    	backToOverview.setOnAction(event ->{
    		show(stage);
    	});
    	
    	// Return to the main screen.
    	backToStart.setOnAction(event ->{
    		mainGUI.showMainScene(stage);
    	});
    }
    
    // Displays all series in a TableView.
    private void showSeries(Stage stage) {
    	// Get all series from the controller and convert them into an observable list.
    	ArrayList<Series> series = controller.getSeries();
    	ObservableList<Series> seriesData = FXCollections.observableArrayList(series);
    	
    	// Create the basic layout and navigation elements.
    	BorderPane root = new BorderPane();
    	Label title = new Label("Serien");
    	HBox navigation = new HBox();
    	Button backToOverview = new Button("Zurück zur Auswahl");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
    	// Create the table and set its data.
    	TableView<Series> seriesTable = new TableView<>();
    	seriesTable.setItems(seriesData);	
    	
    	// Create and configure the name column.
    	TableColumn<Series, String> nameColumn = new TableColumn<>("Name");
    	nameColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getName())
    		);
    	seriesTable.getColumns().add(nameColumn);
    	
    	// Create and configure the year column.
    	TableColumn<Series, Number> yearColumn = new TableColumn<>("Jahr");
    	yearColumn.setCellValueFactory(
    		    data -> new SimpleIntegerProperty(data.getValue().getYear())
    		);
    	seriesTable.getColumns().add(yearColumn);
    	
    	// Set the preferred width of the navigation buttons.
    	backToOverview.setPrefWidth(mainGUI.buttonWidth);
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	// Add the navigation buttons to the HBox.
    	navigation.getChildren().add(backToStart);
    	navigation.getChildren().add(backToOverview);
    	navigation.setAlignment(Pos.CENTER);
    	
    	// Place the elements in the BorderPane.
    	root.setTop(title);
    	root.setCenter(seriesTable);
    	root.setBottom(navigation);
    	
    	title.setPadding(paddingTop);
    	
    	// Center the elements inside their BorderPane areas.
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(seriesTable, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	// Create and display the series scene.
    	Scene scene = new Scene(root,800,600);
    	stage.setScene(scene);
    	
    	// Return to the media type selection.
    	backToOverview.setOnAction(event ->{
    		show(stage);
    	});
    
    	// Return to the main screen.
    	backToStart.setOnAction(event ->{
    		mainGUI.showMainScene(stage);
    	});
    }
}