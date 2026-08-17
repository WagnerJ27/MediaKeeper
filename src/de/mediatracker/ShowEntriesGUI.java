package de.mediatracker;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
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

    // Controller used to retrieve media entries.
    private final MediaController controller;

    // Reference to the main GUI for navigation.
    private final MediaKeeperGUI mainGUI;

    // Standard padding used for GUI elements.
    private final Insets padding = new Insets(20);

    // Padding used for titles at the top of a scene.
    private final Insets paddingTop = new Insets(50, 0, 0, 0);

    public ShowEntriesGUI(MediaController controller, MediaKeeperGUI mainGUI) {
        this.controller = controller;
        this.mainGUI = mainGUI;
    }

    protected void show(Stage stage) {

        // Root container of the media selection scene.
        BorderPane root = new BorderPane();

        // Contains the buttons for selecting a media type.
        VBox content = new VBox();

        // Title of the scene.
        Label title = new Label("Einträge anzeigen");

        // Buttons for selecting the media type.
        Button showGamesButton = new Button("Spiele anzeigen");
        Button showBooksButton = new Button("Bücher anzeigen");
        Button showMoviesButton = new Button("Filme anzeigen");
        Button showSeriesButton = new Button("Serien anzeigen");

        // Set the width of the media selection buttons.
        showGamesButton.setPrefWidth(mainGUI.buttonWidth);
        showBooksButton.setPrefWidth(mainGUI.buttonWidth);
        showMoviesButton.setPrefWidth(mainGUI.buttonWidth);
        showSeriesButton.setPrefWidth(mainGUI.buttonWidth);

        // Apply the shared button style.
        showGamesButton.getStyleClass().add("small-button");
        showBooksButton.getStyleClass().add("small-button");
        showMoviesButton.getStyleClass().add("small-button");
        showSeriesButton.getStyleClass().add("small-button");

        // Button used to return to the main menu.
        Button toHomeScreen = new Button("Zurück zur Startseite");

        // Apply the shared button style.
        toHomeScreen.getStyleClass().add("small-button");

        // Apply the title style.
        title.getStyleClass().add("smalltitle");

        // Center the media selection buttons.
        content.setAlignment(Pos.CENTER);

        // Add the buttons to the container.
        content.getChildren().addAll(
            showGamesButton,
            showBooksButton,
            showMoviesButton,
            showSeriesButton
        );

        // Configure spacing between the buttons.
        content.setSpacing(15);

        // Place the elements inside the BorderPane.
        root.setTop(title);
        root.setCenter(content);
        root.setBottom(toHomeScreen);

        // Center the elements inside their BorderPane areas.
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(content, Pos.CENTER);
        BorderPane.setAlignment(toHomeScreen, Pos.CENTER);

        // Show the games table.
        showGamesButton.setOnAction(event -> {
            showGames(stage);
        });

        // Show the books table.
        showBooksButton.setOnAction(event -> {
            showBooks(stage);
        });

        // Show the movies table.
        showMoviesButton.setOnAction(event -> {
            showMovies(stage);
        });

        // Show the series table.
        showSeriesButton.setOnAction(event -> {
            showSeries(stage);
        });

        // Return to the main menu.
        toHomeScreen.setOnAction(event -> {
            mainGUI.showMainScene(stage);
        });

        // Create the scene.
        Scene showEntriesScene = new Scene(root, 800, 600);

        // Load the application stylesheet.
        showEntriesScene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        // Display the scene.
        stage.setScene(showEntriesScene);
    }

    private void showGames(Stage stage) {

        // Retrieve all games from the controller.
        ArrayList<Game> games = controller.getGames();

        // Convert the list into an observable list for the TableView.
        ObservableList<Game> gameData =
            FXCollections.observableArrayList(games);

        BorderPane root = new BorderPane();

        Label title = new Label("Spiele");

        HBox navigation = new HBox();

        Button backToOverview = new Button("Zurück zur Auswahl");
        Button backToStart = new Button("Zurück zur Startseite");

        // Apply the shared title style.
        title.getStyleClass().add("smalltitle");

        // Apply the shared button style.
        backToOverview.getStyleClass().add("small-button");
        backToStart.getStyleClass().add("small-button");

        TableView<Game> gameTable = new TableView<>();
        gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the data displayed by the table.
        gameTable.setItems(gameData);

        // Apply the table style.
        gameTable.getStyleClass().add("media-table");

        // Name column.
        TableColumn<Game, String> nameColumn =
            new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
            data -> new SimpleStringProperty(
                data.getValue().getName()
            )
        );

        gameTable.getColumns().add(nameColumn);

        // Year column.
        TableColumn<Game, Number> yearColumn =
            new TableColumn<>("Jahr");

        yearColumn.setCellValueFactory(
            data -> new SimpleIntegerProperty(
                data.getValue().getYear()
            )
        );

        gameTable.getColumns().add(yearColumn);

        // Platform column.
        TableColumn<Game, String> platformColumn =
            new TableColumn<>("Plattform");

        platformColumn.setCellValueFactory(
            data -> new SimpleStringProperty(
                data.getValue().getPlatform()
            )
        );

        gameTable.getColumns().add(platformColumn);

        // Completion column.
        TableColumn<Game, Boolean> completionColumn =
            new TableColumn<>("100%");

        completionColumn.setCellValueFactory(
            data -> new SimpleBooleanProperty(
                data.getValue().getHundredPercentCompletion()
            )
        );

        // Display "Ja" or "Nein" instead of true or false.
        completionColumn.setCellFactory(
            column -> new TableCell<Game, Boolean>() {

                @Override
                protected void updateItem(Boolean item, boolean empty) {

                    super.updateItem(item, empty);

                    if (empty) {
                        setText(null);
                    } else {
                        setText(item ? "Ja" : "Nein");
                    }
                }
            }
        );

        gameTable.getColumns().add(completionColumn);

        // Configure navigation buttons.
        backToOverview.setPrefWidth(mainGUI.buttonWidth);
        backToStart.setPrefWidth(mainGUI.buttonWidth);

        navigation.getChildren().addAll(
            backToStart,
            backToOverview
        );

        navigation.setAlignment(Pos.CENTER);

        // Place elements inside the BorderPane.
        root.setTop(title);
        root.setCenter(gameTable);
        root.setBottom(navigation);

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(gameTable, Pos.CENTER);
        BorderPane.setAlignment(navigation, Pos.CENTER);

        // Create and style the scene.
        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        stage.setScene(scene);

        // Return to the media selection screen.
        backToOverview.setOnAction(event -> {
            show(stage);
        });

        // Return to the main menu.
        backToStart.setOnAction(event -> {
            mainGUI.showMainScene(stage);
        });
    }

    private void showBooks(Stage stage) {

        // Retrieve all books from the controller.
        ArrayList<Book> books = controller.getBooks();

        // Convert the list into an observable list for the TableView.
        ObservableList<Book> bookData =
            FXCollections.observableArrayList(books);

        BorderPane root = new BorderPane();

        Label title = new Label("Bücher");

        HBox navigation = new HBox();

        Button backToOverview = new Button("Zurück zur Auswahl");
        Button backToStart = new Button("Zurück zur Startseite");

        // Apply the shared styles.
        title.getStyleClass().add("smalltitle");
        backToOverview.getStyleClass().add("small-button");
        backToStart.getStyleClass().add("small-button");

        TableView<Book> bookTable = new TableView<>();
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the table data.
        bookTable.setItems(bookData);

        // Apply the table style.
        bookTable.getStyleClass().add("media-table");

        // Name column.
        TableColumn<Book, String> nameColumn =
            new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
            data -> new SimpleStringProperty(
                data.getValue().getName()
            )
        );

        bookTable.getColumns().add(nameColumn);

        // Year column.
        TableColumn<Book, Number> yearColumn =
            new TableColumn<>("Jahr");

        yearColumn.setCellValueFactory(
            data -> new SimpleIntegerProperty(
                data.getValue().getYear()
            )
        );

        bookTable.getColumns().add(yearColumn);

        // Author column.
        TableColumn<Book, String> authorColumn =
            new TableColumn<>("Autor");

        authorColumn.setCellValueFactory(
            data -> new SimpleStringProperty(
                data.getValue().getAuthor()
            )
        );

        bookTable.getColumns().add(authorColumn);

        // Configure navigation.
        backToOverview.setPrefWidth(mainGUI.buttonWidth);
        backToStart.setPrefWidth(mainGUI.buttonWidth);

        navigation.getChildren().addAll(
            backToStart,
            backToOverview
        );

        navigation.setAlignment(Pos.CENTER);

        root.setTop(title);
        root.setCenter(bookTable);
        root.setBottom(navigation);

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(bookTable, Pos.CENTER);
        BorderPane.setAlignment(navigation, Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        stage.setScene(scene);

        backToOverview.setOnAction(event -> {
            show(stage);
        });

        backToStart.setOnAction(event -> {
            mainGUI.showMainScene(stage);
        });
    }

    private void showMovies(Stage stage) {

        // Retrieve all movies from the controller.
        ArrayList<Movie> movies = controller.getMovies();

        // Convert the list into an observable list for the TableView.
        ObservableList<Movie> movieData =
            FXCollections.observableArrayList(movies);

        BorderPane root = new BorderPane();

        Label title = new Label("Filme");

        HBox navigation = new HBox();

        Button backToOverview = new Button("Zurück zur Auswahl");
        Button backToStart = new Button("Zurück zur Startseite");

        // Apply the shared styles.
        title.getStyleClass().add("smalltitle");
        backToOverview.getStyleClass().add("small-button");
        backToStart.getStyleClass().add("small-button");

        TableView<Movie> movieTable = new TableView<>();
        movieTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the table data.
        movieTable.setItems(movieData);

        // Apply the table style.
        movieTable.getStyleClass().add("media-table");

        // Name column.
        TableColumn<Movie, String> nameColumn =
            new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
            data -> new SimpleStringProperty(
                data.getValue().getName()
            )
        );

        movieTable.getColumns().add(nameColumn);

        // Year column.
        TableColumn<Movie, Number> yearColumn =
            new TableColumn<>("Jahr");

        yearColumn.setCellValueFactory(
            data -> new SimpleIntegerProperty(
                data.getValue().getYear()
            )
        );

        movieTable.getColumns().add(yearColumn);

        // Configure navigation.
        backToOverview.setPrefWidth(mainGUI.buttonWidth);
        backToStart.setPrefWidth(mainGUI.buttonWidth);

        navigation.getChildren().addAll(
            backToStart,
            backToOverview
        );

        navigation.setAlignment(Pos.CENTER);

        root.setTop(title);
        root.setCenter(movieTable);
        root.setBottom(navigation);

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(movieTable, Pos.CENTER);
        BorderPane.setAlignment(navigation, Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        stage.setScene(scene);

        backToOverview.setOnAction(event -> {
            show(stage);
        });

        backToStart.setOnAction(event -> {
            mainGUI.showMainScene(stage);
        });
    }

    private void showSeries(Stage stage) {

        // Retrieve all series from the controller.
        ArrayList<Series> series = controller.getSeries();

        // Convert the list into an observable list for the TableView.
        ObservableList<Series> seriesData =
            FXCollections.observableArrayList(series);

        BorderPane root = new BorderPane();

        Label title = new Label("Serien");

        HBox navigation = new HBox();

        Button backToOverview = new Button("Zurück zur Auswahl");
        Button backToStart = new Button("Zurück zur Startseite");

        // Apply the shared styles.
        title.getStyleClass().add("smalltitle");
        backToOverview.getStyleClass().add("small-button");
        backToStart.getStyleClass().add("small-button");

        TableView<Series> seriesTable = new TableView<>();
        seriesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the table data.
        seriesTable.setItems(seriesData);

        // Apply the table style.
        seriesTable.getStyleClass().add("media-table");

        // Name column.
        TableColumn<Series, String> nameColumn =
            new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
            data -> new SimpleStringProperty(
                data.getValue().getName()
            )
        );

        seriesTable.getColumns().add(nameColumn);

        // Year column.
        TableColumn<Series, Number> yearColumn =
            new TableColumn<>("Jahr");

        yearColumn.setCellValueFactory(
            data -> new SimpleIntegerProperty(
                data.getValue().getYear()
            )
        );

        seriesTable.getColumns().add(yearColumn);

        // Configure navigation.
        backToOverview.setPrefWidth(mainGUI.buttonWidth);
        backToStart.setPrefWidth(mainGUI.buttonWidth);

        navigation.getChildren().addAll(
            backToStart,
            backToOverview
        );

        navigation.setAlignment(Pos.CENTER);

        root.setTop(title);
        root.setCenter(seriesTable);
        root.setBottom(navigation);

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(seriesTable, Pos.CENTER);
        BorderPane.setAlignment(navigation, Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        stage.setScene(scene);

        backToOverview.setOnAction(event -> {
            show(stage);
        });

        backToStart.setOnAction(event -> {
            mainGUI.showMainScene(stage);
        });
    }
}