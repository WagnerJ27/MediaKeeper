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
import javafx.scene.Cursor;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
public class ShowEntriesGUI {
	
	
    private final MediaController controller;
    private final MediaKeeperGUI mainGUI;
    
    private final Insets padding = new Insets(20);
    private final Insets paddingTop = new Insets(50, 0, 0, 0);


    public ShowEntriesGUI(MediaController controller, MediaKeeperGUI mainGUI) {
        this.controller = controller;
        this.mainGUI = mainGUI;
    }
    
    protected void show(Stage stage) {

    	
    	BorderPane root = new BorderPane();
    	VBox content = new VBox();
    	Label title = new Label("Einträge anzeigen");
    	
    	Button showGamesButton = new Button("Spiele anzeigen");
    	Button showBooksButton = new Button("Bücher anzeigen");
    	Button showMoviesButton = new Button("Filme anzeigen");
    	Button showSeriesButton = new Button("Serien anzeigen");

    	showGamesButton.setPrefWidth(mainGUI.buttonWidth);
    	showBooksButton.setPrefWidth(mainGUI.buttonWidth);
    	showMoviesButton.setPrefWidth(mainGUI.buttonWidth);
    	showSeriesButton.setPrefWidth(mainGUI.buttonWidth);
    	
    	showGamesButton.setPadding(padding);
    	showBooksButton.setPadding(padding);
    	showMoviesButton.setPadding(padding);
    	showSeriesButton.setPadding(padding);
    	
    	Button toHomeScreen = new Button("Zurück zur Startseite");
    	
    	content.setAlignment(Pos.CENTER);
    	
    	content.getChildren().add(showGamesButton);
    	content.getChildren().add(showBooksButton);
    	content.getChildren().add(showMoviesButton);
    	content.getChildren().add(showSeriesButton);
    	
    	
    	
    	root.setTop(title);
    	root.setCenter(content);
    	root.setBottom(toHomeScreen);
    	
    	content.setSpacing(15);
    	title.setPadding(paddingTop);
    	
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(content, Pos.CENTER);
    	BorderPane.setAlignment(toHomeScreen, Pos.CENTER);
    	
    	showGamesButton.setOnAction(event ->{
    		showGames(stage);
    	});
    	
    	showBooksButton.setOnAction(even ->{
    		showBooks(stage);
    	});
    	
    	showMoviesButton.setOnAction(event ->{
    		showMovies(stage);
    	});
    	
    	showSeriesButton.setOnAction(event -> {
    		showSeries(stage);
    	});
    	toHomeScreen.setOnAction(event ->{
    		mainGUI.showMainScene(stage);
    	});
    	
    	Scene showEntriesScene = new Scene(root,800,600);
    	
    	stage.setScene(showEntriesScene);

    }
    
    private void showGames(Stage stage) {
    	ArrayList<Game> games = controller.getGames();
    	ObservableList<Game> gameData = FXCollections.observableArrayList(games);
    	
    	BorderPane root = new BorderPane();
    	Label title = new Label("Spiele");
    	HBox navigation = new HBox();
    	Button backToOverview = new Button("Zurück zur Auswahl");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
    	TableView<Game> gameTable = new TableView<>();
    	
    	gameTable.setItems(gameData);	
    	
    	TableColumn<Game, String> nameColumn = new TableColumn<>("Name");
    	nameColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getName())
    		);
    	gameTable.getColumns().add(nameColumn);
    	
    	TableColumn<Game, Number> yearColumn = new TableColumn<>("Jahr");
    	yearColumn.setCellValueFactory(
    		    data -> new SimpleIntegerProperty(data.getValue().getYear())
    		);
    	gameTable.getColumns().add(yearColumn);
    	
    	TableColumn<Game, String> platformColumn = new TableColumn<>("Plattform");
    	platformColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getPlatform())
    		);
    	gameTable.getColumns().add(platformColumn);
    	
    	
    	
    	TableColumn<Game, Boolean> completionColumn = new TableColumn<>("100%");
    	completionColumn.setCellValueFactory(
    	        data -> new SimpleBooleanProperty(data.getValue().getHundredPercentCompletion())
    	);

    	completionColumn.setCellFactory(column -> new TableCell<Game, Boolean>() {

    	    @Override
    	    protected void updateItem(Boolean item, boolean empty) {
    	        super.updateItem(item, empty);

    	        if (empty) {
    	            setText(null);
    	        } else {
    	            setText(item ? "Ja" : "Nein");
    	        }
    	    }
    	});

    	gameTable.getColumns().add(completionColumn);
    	
    	

    	
    	backToOverview.setPrefWidth(mainGUI.buttonWidth);
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	navigation.getChildren().add(backToStart);
    	navigation.getChildren().add(backToOverview);
    	navigation.setAlignment(Pos.CENTER);
    	
    	root.setTop(title);
    	root.setCenter(gameTable);
    	root.setBottom(navigation);
    	
    	title.setPadding(paddingTop);
    	
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(gameTable, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	Scene scene = new Scene(root,800,600);
    	stage.setScene(scene);
    	
    	backToOverview.setOnAction(event ->{
    		show(stage);
    	});
    	
    	backToStart.setOnAction(event ->{
    		mainGUI.showMainScene(stage);
    	});
    }
 
    private void showBooks(Stage stage) {
    	ArrayList<Book> books = controller.getBooks();
    	ObservableList<Book> bookData = FXCollections.observableArrayList(books);
    	
    	BorderPane root = new BorderPane();
    	Label title = new Label("Bücher");
    	HBox navigation = new HBox();
    	Button backToOverview = new Button("Zurück zur Auswahl");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
    	TableView<Book> bookTable = new TableView<>();
    	
    	bookTable.setItems(bookData);	
    	
    	TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
    	nameColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getName())
    		);
    	bookTable.getColumns().add(nameColumn);
    	
    	TableColumn<Book, Number> yearColumn = new TableColumn<>("Jahr");
    	yearColumn.setCellValueFactory(
    		    data -> new SimpleIntegerProperty(data.getValue().getYear())
    		);
    	bookTable.getColumns().add(yearColumn);
    	
    	TableColumn<Book, String> authorColumn = new TableColumn<>("Autor");
    	authorColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getAuthor())
    		);
    	bookTable.getColumns().add(authorColumn);
    	
	
    	backToOverview.setPrefWidth(mainGUI.buttonWidth);
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	navigation.getChildren().add(backToStart);
    	navigation.getChildren().add(backToOverview);
    	
    	navigation.setAlignment(Pos.CENTER);
    	
    	root.setTop(title);
    	root.setCenter(bookTable);
    	root.setBottom(navigation);
    	
    	title.setPadding(paddingTop);
    	
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(bookTable, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	Scene scene = new Scene(root,800,600);
    	stage.setScene(scene);
    	
    	backToOverview.setOnAction(event ->{
    		show(stage);
    	});
    	
    	backToStart.setOnAction(event -> {
    		mainGUI.showMainScene(stage);
    	});
    }
    
    private void showMovies(Stage stage) {
    	ArrayList<Movie> movies = controller.getMovies();
    	ObservableList<Movie> movieData = FXCollections.observableArrayList(movies);
    	
    	BorderPane root = new BorderPane();
    	Label title = new Label("Filme");
    	HBox navigation = new HBox();
    	Button backToOverview = new Button("Zurück zur Auswahl");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
    	TableView<Movie> movieTable = new TableView<>();
    	
    	movieTable.setItems(movieData);	
    	
    	TableColumn<Movie, String> nameColumn = new TableColumn<>("Name");
    	nameColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getName())
    		);
    	movieTable.getColumns().add(nameColumn);
    	
    	TableColumn<Movie, Number> yearColumn = new TableColumn<>("Jahr");
    	yearColumn.setCellValueFactory(
    		    data -> new SimpleIntegerProperty(data.getValue().getYear())
    		);
    	movieTable.getColumns().add(yearColumn);
    	
    	
	
    	backToOverview.setPrefWidth(mainGUI.buttonWidth);
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	navigation.getChildren().add(backToStart);
    	navigation.getChildren().add(backToOverview);
    	navigation.setAlignment(Pos.CENTER);
    	
    	root.setTop(title);
    	root.setCenter(movieTable);
    	root.setBottom(navigation);
    	
    	title.setPadding(paddingTop);
    	
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(movieTable, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	Scene scene = new Scene(root,800,600);
    	stage.setScene(scene);
    	
    	backToOverview.setOnAction(event ->{
    		show(stage);
    	});
    }
    
    private void showSeries(Stage stage) {
    	ArrayList<Series> series = controller.getSeries();
    	ObservableList<Series> seriesData = FXCollections.observableArrayList(series);
    	
    	BorderPane root = new BorderPane();
    	Label title = new Label("Serien");
    	HBox navigation = new HBox();
    	Button backToOverview = new Button("Zurück zur Auswahl");
    	Button backToStart = new Button("Zurück zur Startseite");
    	
    	TableView<Series> seriesTable = new TableView<>();
    	
    	seriesTable.setItems(seriesData);	
    	
    	TableColumn<Series, String> nameColumn = new TableColumn<>("Name");
    	nameColumn.setCellValueFactory(
    		    data -> new SimpleStringProperty(data.getValue().getName())
    		);
    	seriesTable.getColumns().add(nameColumn);
    	
    	TableColumn<Series, Number> yearColumn = new TableColumn<>("Jahr");
    	yearColumn.setCellValueFactory(
    		    data -> new SimpleIntegerProperty(data.getValue().getYear())
    		);
    	seriesTable.getColumns().add(yearColumn);
    	
    	
	
    	backToOverview.setPrefWidth(mainGUI.buttonWidth);
    	backToStart.setPrefWidth(mainGUI.buttonWidth);
    	
    	navigation.getChildren().add(backToStart);
    	navigation.getChildren().add(backToOverview);
    	navigation.setAlignment(Pos.CENTER);
    	
    	root.setTop(title);
    	root.setCenter(seriesTable);
    	root.setBottom(navigation);
    	
    	title.setPadding(paddingTop);
    	
    	BorderPane.setAlignment(title, Pos.CENTER);
    	BorderPane.setAlignment(seriesTable, Pos.CENTER);
    	BorderPane.setAlignment(navigation, Pos.CENTER);
    	
    	Scene scene = new Scene(root,800,600);
    	stage.setScene(scene);
    	
    	backToOverview.setOnAction(event ->{
    		show(stage);
    	});
    
    	backToStart.setOnAction(event ->{
    		mainGUI.showMainScene(stage);
    	});
    }
}
