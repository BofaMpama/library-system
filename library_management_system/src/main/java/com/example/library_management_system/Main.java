package com.example.library_management_system;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application{
   private TableView<Members> membersTableView = new TableView<>();
   private TableView<Books> booksTableView = new TableView<>();
   private TableView<IssuedBooks> issuedBooksTableView = new TableView<>();




    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane frame = new BorderPane();

        HBox topNavBar = new HBox();
        topNavBar.setStyle("-fx-background-color: linear-gradient(to right, black 10%, #437610); -fx-padding: 10px");
        Label title = new Label("Yada-LMS");
        title.setStyle("-fx-text-fill: white; -fx-font-family: 'Cambria'; -fx-font-size: 30px; -fx-font-weight: bold; -fx-padding: 5px;");
        title.setTranslateX(30);

        Label adminLabel = new Label("Hello, Admin");
        adminLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 20px 0 0 0;");
        adminLabel.setTranslateX(1000);

        topNavBar.getChildren().addAll(title, adminLabel);
        frame.setTop(topNavBar);

        //SideBar with tabs
        VBox sideBar = new VBox(15);
        sideBar.setStyle("-fx-background-color: #000000; -fx-font-weight: bold; -fx-padding: 30px 0 30px 0; -fx-pref-width: 200px; -fx-spacing: 4; -fx-border-radius: 0px 0px 0 0; ");
        sideBar.setAlignment(Pos.TOP_LEFT);
        sideBar.setPadding(new Insets(10));


        
        String buttonDesign = "-fx-background-color: rgb(10,10,10);" +
                " -fx-text-fill: green;" +
                " -fx-font-size: 20px;" +
                " -fx-font-weight: bold;" +
                "-fx-padding: 10px 10px 10px 20px;" +
                "-fx-content-display: left";


        VBox sideBarButtons = new VBox(5);
        sideBarButtons.setAlignment(Pos.CENTER_LEFT);
        sideBarButtons.setTranslateY(40);

        //icon for button to go to dashboard page
        FileInputStream dashboardIconButton = new FileInputStream("C:\\Users\\BBG\\IdeaProjects\\library_management_system\\libs\\pics\\dashboard.png");
        Image dashboardImage = new Image(dashboardIconButton);
        ImageView dashboardImageview = new ImageView(dashboardImage);
        dashboardImageview.setFitWidth(25);
        dashboardImageview.setFitHeight(25);

        //for the dashboard page
        JFXButton dashboardButton = new JFXButton(" Dashboard");
        dashboardButton.setMinWidth(200);
        dashboardButton.setAlignment(Pos.CENTER_LEFT);
        dashboardButton.setGraphic(dashboardImageview);
        dashboardButton.setStyle(buttonDesign);
        dashboardButton.setOnAction(event -> {
            try {
                frame.setCenter(dashboardPage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });


        //icon for button to go to members page
        FileInputStream membersIconButton = new FileInputStream("C:\\Users\\BBG\\IdeaProjects\\library_management_system\\libs\\pics\\team.png");
        Image membersImage = new Image(membersIconButton);
        ImageView membersImageview = new ImageView(membersImage);
        membersImageview.setFitWidth(25);
        membersImageview.setFitHeight(25);

        //for the members page
        JFXButton membersButton = new JFXButton(" Members");
        membersButton.setMinWidth(200);
        membersButton.setAlignment(Pos.CENTER_LEFT);
        membersButton.setGraphic(membersImageview);
        membersButton.setStyle(buttonDesign);
        membersButton.setOnAction(event -> {
            frame.setCenter(membersPage());
        });

        //icon for button to go to library page
        FileInputStream libraryIconButton = new FileInputStream("C:\\Users\\BBG\\IdeaProjects\\library_management_system\\libs\\pics\\stack-of-books.png");
        Image libraryImage = new Image(libraryIconButton);
        ImageView libraryImageview = new ImageView(libraryImage);
        libraryImageview.setFitWidth(25);
        libraryImageview.setFitHeight(25);

        //for the library page
        JFXButton libraryButton = new JFXButton(" Library");
        libraryButton.setMinWidth(200);
        libraryButton.setAlignment(Pos.CENTER_LEFT);
        libraryButton.setGraphic(libraryImageview);
        libraryButton.setStyle(buttonDesign);
        libraryButton.setStyle(buttonDesign);
        libraryButton.setOnAction(event -> {
           frame.setCenter(libraryPage());
        });

        //icon for button to go to borrowedBooks page
        FileInputStream borrowedBooksIconButton = new FileInputStream("C:\\Users\\BBG\\IdeaProjects\\library_management_system\\libs\\pics\\borrowedBook.png");
        Image borrowedBooksImage = new Image(borrowedBooksIconButton);
        ImageView borrowedBooksImageview = new ImageView(borrowedBooksImage);
        borrowedBooksImageview.setFitWidth(25);
        borrowedBooksImageview.setFitHeight(25);

        //for the borrowed books page
        JFXButton borrowedBooksButton = new JFXButton(" Issued Books");
        borrowedBooksButton.setMinWidth(200);
        borrowedBooksButton.setAlignment(Pos.CENTER_LEFT);
        borrowedBooksButton.setGraphic(borrowedBooksImageview);
        borrowedBooksButton.setStyle(buttonDesign);
        borrowedBooksButton.setOnAction(event -> {
            try {
                frame.setCenter(issuedBooksPage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

//
        JFXButton logoutButton = new JFXButton("<- Log Out");
        logoutButton.setAlignment(Pos.CENTER);
        logoutButton.setPrefWidth(200);
        logoutButton.setStyle(buttonDesign);
        logoutButton.setTranslateY(350);

        sideBarButtons.getChildren().addAll(dashboardButton, membersButton, libraryButton, borrowedBooksButton);
        sideBar.getChildren().addAll(sideBarButtons, logoutButton);

        frame.setLeft(sideBar);
        frame.setCenter(dashboardPage());





        //To Show the Scene
        Scene scene = new Scene(frame);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Yada LMS");
        primaryStage.show();
    }

    private StackPane dashboardPage() throws SQLException {
        String members_count_sql ="SELECT COUNT(*) FROM members";
        int membersCount = 0;
        try (Connection connection = dbConnect.connection(); PreparedStatement preparedStatement = connection.prepareStatement(members_count_sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()){
                membersCount = resultSet.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        String books_count_sql ="SELECT COUNT(*) FROM books";

        int booksCount = 0;
        try (Connection connection = dbConnect.connection(); PreparedStatement preparedStatement = connection.prepareStatement(books_count_sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()){
                booksCount = resultSet.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        String issuedBooks_count_sql ="SELECT COUNT(*) FROM issuedBooks";
        int issuedBooksCount = 0;
        try (Connection connection = dbConnect.connection(); PreparedStatement preparedStatement = connection.prepareStatement(issuedBooks_count_sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()){
                issuedBooksCount = resultSet.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }


        //main content
        StackPane mainContent = new StackPane();
        mainContent.setStyle("-fx-background-color: rgb(30,30,30); -fx-pref-width: 600px; -fx-pref-height: 400px");
        mainContent.setAlignment(Pos.TOP_LEFT);

        Text dashboardText = new Text("Dashboard");
        dashboardText.setStyle("-fx-text-fill: white; -fx-font-family: 'Roboto'; -fx-font-size: 40px; -fx-font-weight: bold; -fx-padding: 30px 0 0 100px;");
        dashboardText.setTextOrigin(VPos.TOP);
        dashboardText.setFill(Color.WHITE);
        dashboardText.setTranslateX(50);
        dashboardText.setTranslateY(60);

        Text currentTime = new Text();
        currentTime.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 20px;");
        currentTime.setFill(Color.WHITE);
        currentTime.setTranslateX(50);
        currentTime.setTranslateY(120);

        //getting the current date and time
        Timeline clock = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            LocalTime clockTime = LocalTime.now();
            LocalDate dateTime = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(" MMMM dd, yyyy | EEEE");
            currentTime.setText(dateTime.format(dateTimeFormatter) + ", " +clockTime.format(formatter)  );
        }));

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        //HBox for the rows of boxes
        HBox overview = new HBox(15);
        overview.setPadding(new Insets(50));
        overview.setLayoutX(170);
        overview.setTranslateY(120);

        String bookLabelStyle = "-fx-border-color: #437610;" +
                "-fx-padding: 15px 35px 15px 15px;" +
                "-fx-text-fill: #fff;" +
                "-fx-font-size: 22px;" +
                "-fx-border-width: 3px 0 0 0 ;" +
                "-fx-border-radius: 0px;" +
                "-fx-background-radius: 0px;" +
                "-fx-background-color: #000;";


        //creating the labels for these boxes
        Label totalBooksLabel = new Label("Total Books:   " + booksCount);
        totalBooksLabel.setStyle(bookLabelStyle);
        Label issuedBooksLabel = new Label("Issued Books:   " + issuedBooksCount);
        issuedBooksLabel.setStyle(bookLabelStyle);
        Label availableBooksLabel = new Label("Total Members:   " + membersCount);
        availableBooksLabel.setStyle(bookLabelStyle);






        //adding the labels to the Hbox
        overview.getChildren().addAll(totalBooksLabel, issuedBooksLabel, availableBooksLabel);

        mainContent.getChildren().addAll(dashboardText, currentTime, overview);

        return mainContent;
    }

    private StackPane membersPage(){
        StackPane membersContent = new StackPane();
        HBox firstRow = new HBox(0);
        firstRow.setTranslateY(60);

        membersContent.setStyle("-fx-background-color: rgb(30,30,30); -fx-pref-width: 600px; -fx-pref-height: 400px");
        membersContent.setAlignment(Pos.TOP_LEFT);


        Text text = new Text("Members");
        text.setStyle("-fx-text-fill: white; -fx-font-family: 'Roboto'; -fx-font-size: 40px; -fx-font-weight: bold; -fx-padding: 30px 0 0 100px;");
        text.setTextOrigin(VPos.TOP);
        text.setFill(Color.WHITE);
        text.setTranslateX(50);



        String buttonStyle = "-fx-pref-width: 180px; -fx-pref-height: 35px; -fx-background-color: black; -fx-background-radius: 5px; -fx-border-color: green; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 15px;";

        String membersInputStyle = "-fx-pref-width: 180px;" +
                "-fx-pref-height: 30px;" +
                "-fx-outline: none;" +
                "-fx-background-color: black;" +
                "-fx-text-fill: white;" +
                "-fx-border-width: 0 0 2px 0;" +
                "-fx-border-color: green;";

        //HBOX TO GROUP ALL THE INPUT FIELDS
        FlowPane membersInputFields = new FlowPane(0, 5);
        membersInputFields.setHgap(15);
        membersInputFields.setVgap(15);
        membersInputFields.setTranslateY(120);
        membersInputFields.setTranslateX(50);
        membersInputFields.setPrefSize(700, 700);
        membersInputFields.setMinSize(700, 700);
        membersInputFields.setMaxSize(700, 700);


        //INPUT FIELDS

        TextField membersNameField = new TextField();
        membersNameField.setPromptText("Name");
        membersNameField.setStyle(membersInputStyle);

        TextField membersEmailField = new TextField();
        membersEmailField.setPromptText("Email");
        membersEmailField.setStyle(membersInputStyle);

        TextField membersPhoneField = new TextField();
        membersPhoneField.setPromptText("Phone Number");
        membersPhoneField.setStyle(membersInputStyle);


        TextField membersDateField = new TextField();
        membersDateField.setPromptText("Date (DD-MM-YYYY)");
        membersDateField.setStyle(membersInputStyle);

        JFXButton addMember = new JFXButton("Add Member");
        addMember.setStyle(buttonStyle);
        addMember.setOnAction(event -> {

            String name = membersNameField.getText();
            String email = membersEmailField.getText();
            String phone = membersPhoneField.getText();
            String membershipDate = membersDateField.getText();

            if (!name.isEmpty()  && !email.isEmpty() && !phone.isEmpty() && !membershipDate.isEmpty()){
                yadaDAO.insertMembers(name, email, phone, membershipDate);

                membersNameField.clear();
                membersEmailField.clear();
                membersPhoneField.clear();
                membersDateField.clear();
                refreshMembersTable();
            }
        });





        //DB CONNECTIVITY AND TABLE VIEW

        dbConnect.createMembersTable();//to create the members table

        membersTableView.setTranslateY(290);
        membersTableView.setPrefSize(1200, 400);
        membersTableView.setMaxSize(1200, 400);
        membersTableView.setMinSize(1200, 400);
        membersTableView.setStyle("-fx-background-color: rgb(15,15,15); -fx-text-fill: white; -fx-border-color: transparent;");

        membersTableView.setRowFactory(membersTableView -> {
            TableRow row = new TableRow();
            row.setStyle("-fx-background-color: rgb(15,15,15); -fx-text-fill: white;");
            row.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (membersTableView.getItems().indexOf(newValue) % 2 == 0){
                    row.setStyle("-fx-background-color: rgb(15, 15, 15, 0.3); -fx-text-fill: white;");
                } else{
                    row.setStyle("-fx-background-color: rgb(15, 150, 15, 0.3); -fx-text-fill: white;");
                }
            });

            return row;
        });

        String tableColumnColor = " -fx-text-fill: white;";

        //table columns
        TableColumn<Members, Integer> membersIdColumn = new TableColumn<>("Members ID");
        membersIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        membersIdColumn.setPrefWidth(100);
        membersIdColumn.setStyle(tableColumnColor);

        TableColumn<Members, String> membersNameColumn = new TableColumn<>("Name");
        membersNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        membersNameColumn.setPrefWidth(200);
        membersNameColumn.setStyle(tableColumnColor);

        TableColumn<Members, String> membersEmailColumn = new TableColumn<>("Email");
        membersEmailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        membersEmailColumn.setPrefWidth(300);
        membersEmailColumn.setStyle(tableColumnColor);

        TableColumn<Members, String> membersPhoneColumn = new TableColumn<>("Phone Number");
        membersPhoneColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));
        membersPhoneColumn.setPrefWidth(200);
        membersPhoneColumn.setStyle(tableColumnColor);

        TableColumn<Members, String> membersDateColumn = new TableColumn<>("Registration Date");
        membersDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMembershipDate()));
        membersDateColumn.setPrefWidth(200);
        membersDateColumn.setStyle(tableColumnColor);

        TableColumn<Members, Integer> membersIssuanceNumberColumn = new TableColumn<>("Issuance Number");
        membersIssuanceNumberColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIssuanceNumber()).asObject());
        membersIssuanceNumberColumn.setPrefWidth(200);
        membersIssuanceNumberColumn.setStyle(tableColumnColor);

        membersTableView.getColumns().addAll(membersIdColumn, membersNameColumn, membersEmailColumn, membersPhoneColumn, membersDateColumn, membersIssuanceNumberColumn);
        refreshMembersTable();





        firstRow.getChildren().addAll(text);
        membersInputFields.getChildren().addAll(membersNameField, membersEmailField, membersPhoneField, membersDateField, addMember);
        membersContent.getChildren().addAll(firstRow, membersInputFields,membersTableView);




        return membersContent;
    }

    private void refreshMembersTable(){
        ObservableList<Members> members = yadaDAO.getMembers();
        membersTableView.setItems(members);
    }







    private StackPane libraryPage() {


        StackPane BooksContent = new StackPane();
        BooksContent.setStyle("-fx-background-color: rgb(30,30,30); -fx-pref-width: 600px; -fx-pref-height: 400px");
        BooksContent.setAlignment(Pos.TOP_LEFT);

        HBox firstRow = new HBox(0);
             firstRow.setTranslateY(60);




        Text text = new Text("Books");
        text.setStyle("-fx-text-fill: white; -fx-font-family: 'Roboto'; -fx-font-size: 40px; -fx-font-weight: bold; -fx-padding: 30px 0 0 100px;");
        text.setTextOrigin(VPos.TOP);
        text.setFill(Color.WHITE);
        text.setTranslateX(50);

        //FIELDS INPUT TO POPULATE THE TABLE
        String buttonStyle = "-fx-pref-width: 180px; -fx-pref-height: 35px; -fx-background-color: black; -fx-background-radius: 5px; -fx-border-color: green; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 15px;";

        String booksInputStyle = "-fx-pref-width: 180px;" +
                "-fx-pref-height: 30px;" +
                "-fx-outline: none;" +
                "-fx-background-color: black;" +
                "-fx-text-fill: white;" +
                "-fx-border-width: 0 0 2px 0;" +
                "-fx-border-color: green;";

        //HBOX TO GROUP ALL THE INPUT FIELDS
        FlowPane booksInputFields = new FlowPane(0, 5);
        booksInputFields.setHgap(15);
        booksInputFields.setVgap(15);
        booksInputFields.setTranslateY(120);
        booksInputFields.setTranslateX(50);
        booksInputFields.setPrefSize(700, 700);
        booksInputFields.setMinSize(700, 700);
        booksInputFields.setMaxSize(700, 700);


        //INPUT FIELDS
        TextField booksTitleField = new TextField();
        booksTitleField.setPromptText("Title");
        booksTitleField.setStyle(booksInputStyle);

        TextField booksAuthorField = new TextField();
        booksAuthorField.setPromptText("Author");
        booksAuthorField.setStyle(booksInputStyle);

        TextField booksGenreField = new TextField();
        booksGenreField.setPromptText("Genre");
        booksGenreField.setStyle(booksInputStyle);

        TextField booksTotalField = new TextField();
        booksTotalField.setPromptText("Total Books");
        booksTotalField.setStyle(booksInputStyle);


        TextField booksAvailableField = new TextField();
        booksAvailableField.setPromptText("Available Copies");
        booksAvailableField.setStyle(booksInputStyle);

        JFXButton addBook = new JFXButton("Add Book");
        addBook.setStyle(buttonStyle);
        addBook.setOnAction(event -> {

            String Title = booksTitleField.getText();
            String Author = booksAuthorField.getText();
            String Genre = booksGenreField.getText();
            int TotalBooks = Integer.parseInt(booksTotalField.getText());
            int AvailableBooks = Integer.parseInt(booksAvailableField.getText());

            if (!Title.isEmpty() && !Author.isEmpty() && !Genre.isEmpty()){
                try {
                    yadaDAO.insertBooks(Title, Author, Genre, TotalBooks, AvailableBooks);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                booksTitleField.clear();
                booksAuthorField.clear();
                booksGenreField.clear();
                booksTotalField.clear();
                booksAvailableField.clear();
                refreshBookTable();
            }
        });





        //DB CONNECTIVITY AND TABLE VIEW
        dbConnect.createBooksTable();

        booksTableView.setTranslateY(290);
        booksTableView.setPrefSize(1200, 400);
        booksTableView.setMinSize(1200, 400);
        booksTableView.setMaxSize(1200, 400);
        booksTableView.setStyle(" -fx-background-color: rgb(15,15,15); -fx-text-fill: white;");

        booksTableView.setRowFactory(booksTableView -> {
            TableRow row = new TableRow();
            row.setStyle("-fx-background-color: rgb(15,15,15); -fx-text-fill: white;");
            row.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (booksTableView.getItems().indexOf(newValue) % 2 == 0){
                    row.setStyle("-fx-background-color: rgb(15, 15, 15, 0.3); -fx-text-fill: white;");
                } else{
                    row.setStyle("-fx-background-color: rgb(15, 150, 15, 0.3); -fx-text-fill: white;");
                }
            });

            return row;
        });


        String tableColumnColor = " -fx-text-fill: white;";

        //books table columns
        TableColumn<Books, Integer> booksIdColumn = new TableColumn<>("Book ID");
        booksIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        booksIdColumn.setPrefWidth(100);
        booksIdColumn.setStyle(tableColumnColor);

        TableColumn<Books, String> booksTitleColumn = new TableColumn<>("Title");
        booksTitleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        booksTitleColumn.setPrefWidth(300);
        booksTitleColumn.setStyle(tableColumnColor);

        TableColumn<Books, String> booksAuthorColumn = new TableColumn<>("Author");
        booksAuthorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));
        booksAuthorColumn.setPrefWidth(200);
        booksAuthorColumn.setStyle(tableColumnColor);

        TableColumn<Books, String> booksGenreColumn = new TableColumn<>("Genre");
        booksGenreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenre()));
        booksGenreColumn.setPrefWidth(200);
        booksGenreColumn.setStyle(tableColumnColor);

        TableColumn<Books, Integer> booksTotalColumn = new TableColumn<>("Total Books:");
        booksTotalColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTotalCopies()).asObject());
        booksTotalColumn.setPrefWidth(200);
        booksTotalColumn.setStyle(tableColumnColor);

        TableColumn<Books, Integer> booksAvailableColumn = new TableColumn<>("Available Books");
        booksAvailableColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAvailableCopies()).asObject());
        booksAvailableColumn.setPrefWidth(200);
        booksAvailableColumn.setStyle(tableColumnColor);

        //to add  the columns to the table view
        booksTableView.getColumns().addAll(booksIdColumn, booksTitleColumn, booksAuthorColumn, booksGenreColumn, booksTotalColumn, booksAvailableColumn);
        refreshBookTable();


        firstRow.getChildren().addAll(text);
        booksInputFields.getChildren().addAll(booksTitleField, booksAuthorField, booksGenreField, booksTotalField, booksAvailableField, addBook);
        BooksContent.getChildren().addAll(firstRow, booksInputFields, booksTableView);
        return BooksContent;
    }
    private void refreshBookTable(){
        ObservableList<Books> books = yadaDAO.getBooks();
        booksTableView.setItems(books);
    }

    private StackPane issuedBooksPage() throws SQLException {
            StackPane issuedBooksContent = new StackPane();
            issuedBooksContent.setStyle("-fx-background-color: rgb(30,30,30); -fx-pref-width: 600px; -fx-pref-height: 400px");
            issuedBooksContent.setAlignment(Pos.TOP_LEFT);

            HBox firstRow = new HBox(0);
            firstRow.setTranslateY(60);


            Text text = new Text("Issued Books");
            text.setStyle("-fx-text-fill: white; -fx-font-family: 'Roboto'; -fx-font-size: 40px; -fx-font-weight: bold; -fx-padding: 30px 0 0 100px;");
            text.setTextOrigin(VPos.TOP);
            text.setFill(Color.WHITE);
            text.setTranslateX(50);

            //FIELDS INPUT TO ISSUE/RETURN A BOOK
            String buttonStyle = "-fx-pref-width: 180px; -fx-pref-height: 35px; -fx-background-color: black; -fx-background-radius: 5px; -fx-border-color: green; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 15px;";

            String fieldInputStyle = "-fx-pref-width: 180px;" +
                    "-fx-pref-height: 30px;" +
                    "-fx-outline: none;" +
                    "-fx-background-color: black;" +
                    "-fx-text-fill: white;" +
                    "-fx-border-width: 0 0 2px 0;" +
                    "-fx-border-color: green;";

            //HBOX TO GROUP ALL THE INPUT FIELDS
            FlowPane issuedBooksInputFields = new FlowPane(0, 5);
            issuedBooksInputFields.setHgap(15);
            issuedBooksInputFields.setVgap(15);
            issuedBooksInputFields.setTranslateY(120);
            issuedBooksInputFields.setTranslateX(50);
            issuedBooksInputFields.setPrefSize(900, 900);
            issuedBooksInputFields.setMinSize(900, 900);
            issuedBooksInputFields.setMaxSize(900, 900);

            VBox rightBlock = new VBox(15);
            rightBlock.setTranslateY(120);
            rightBlock.setTranslateX(920);
            rightBlock.setPrefSize(180, 180);
            rightBlock.setMaxSize(180, 180);
            rightBlock.setMinSize(180, 180);


        //INPUT FIELDS
        TextField memberIdTextField = new TextField();
        memberIdTextField.setPromptText("Member ID");
        memberIdTextField.setStyle(fieldInputStyle);

        TextField memberNameTextField = new TextField();
        memberNameTextField.setPromptText("Name");
        memberNameTextField.setStyle(fieldInputStyle);

        TextField bookIdTextField = new TextField();
        bookIdTextField.setPromptText("Book ID");
        bookIdTextField.setStyle(fieldInputStyle);

        TextField bookNameTextField = new TextField();
        bookNameTextField.setPromptText("Book Name");
        bookNameTextField.setStyle(fieldInputStyle);

        TextField issueDateTextField = new TextField();
        issueDateTextField.setPromptText("Issue Date");
        issueDateTextField.setStyle(fieldInputStyle);

        TextField returnDateTextField = new TextField();
        returnDateTextField.setPromptText("Due Date");
        returnDateTextField.setStyle(fieldInputStyle);

        //on-click function for the issue book button
        JFXButton issueBookButton = new JFXButton("Issue Book");

        issueBookButton.setStyle(buttonStyle);
        issueBookButton.setOnAction(event -> {

            int memberID = Integer.parseInt(memberIdTextField.getText());
            String memberName = memberNameTextField.getText();
            int bookID = Integer.parseInt(bookIdTextField.getText());
            String bookName = bookNameTextField.getText();
            String issueDate = issueDateTextField.getText();
            String returnDate = returnDateTextField.getText();

           if (!memberName.isEmpty() && !bookName.isEmpty() && !issueDate.isEmpty() && !returnDate.isEmpty()){
               try {
                   yadaDAO.insertIssuedBooks(memberID, memberName, bookID, bookName, issueDate, returnDate);
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }

               memberIdTextField.clear();
               memberNameTextField.clear();
               bookIdTextField.clear();
               bookNameTextField.clear();
               issueDateTextField.clear();
               returnDateTextField.clear();
               try {
                   refreshIssuedBooksTable();
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }
           }

            String addingIssuanceNumberToMember ="UPDATE books SET AvailableBooks = AvailableBooks - 1 WHERE bookID = ? AND AvailableBooks > 0";
            Connection connect = dbConnect.connection();
            try {
                PreparedStatement preparedStatement = connect.prepareStatement(addingIssuanceNumberToMember);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });



        //on the right side
        TextField issuanceNumberTextField = new TextField();
        issuanceNumberTextField.setPromptText("Issuance Number");
        issuanceNumberTextField.setStyle(fieldInputStyle);

        JFXButton returnBookButton = new JFXButton("Return Book");
        returnBookButton.setStyle(buttonStyle);


        //DB CONNECTIVITY AND TABLE VIEW
        dbConnect.createIssuedBooksTable();//to create the issued books table

        issuedBooksTableView.setTranslateY(290);
        issuedBooksTableView.setPrefSize(1200, 400);
        issuedBooksTableView.setMaxSize(1200, 400);
        issuedBooksTableView.setMinSize(1200, 400);
        issuedBooksTableView.setStyle("-fx-background-color: rgb(15,15,15); -fx-text-fill: white; -fx-border-color: transparent;");

       issuedBooksTableView.setRowFactory(issuedBooksTableView -> {
           TableRow row = new TableRow();
           row.setStyle("-fx-background-color: rgb(15,15,15); -fx-text-fill: white;");
           row.itemProperty().addListener((observable, oldValue, newValue) -> {
               if (issuedBooksTableView.getItems().indexOf(newValue) % 2 == 0){
                   row.setStyle("-fx-background-color: rgb(15, 15, 15, 0.3); -fx-text-fill: white;");
               } else{
                   row.setStyle("-fx-background-color: rgb(15, 150, 15, 0.3); -fx-text-fill: white;");
               }
           });

           return row;
       });

        String tableColumnColor = "-fx-text-fill: white;";

        //table columns
        TableColumn<IssuedBooks, Integer> issuanceNumberColumn = new TableColumn<>("Issuance Number");
        issuanceNumberColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIssuanceNumber()).asObject());
        issuanceNumberColumn.setPrefWidth(150);
        issuanceNumberColumn.setStyle(tableColumnColor);


        TableColumn<IssuedBooks, Integer> issuanceMemberIdColumn = new TableColumn<>("Member ID");
        issuanceMemberIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMemberID()).asObject());
        issuanceMemberIdColumn.setPrefWidth(150);
        issuanceMemberIdColumn.setStyle(tableColumnColor);

        TableColumn<IssuedBooks, String> issuanceMemberNameColumn = new TableColumn<>("Name");
        issuanceMemberNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMemberName()));
        issuanceMemberNameColumn.setPrefWidth(200);
        issuanceMemberNameColumn.setStyle(tableColumnColor);

        TableColumn<IssuedBooks, Integer> issuanceBookIdColumn = new TableColumn<>("Book ID");
        issuanceBookIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBookID()).asObject());
        issuanceBookIdColumn.setPrefWidth(100);
        issuanceBookIdColumn.setStyle(tableColumnColor);

        TableColumn<IssuedBooks, String> issuanceBookName = new TableColumn<>("Book Name");
        issuanceBookName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBookName()));
        issuanceBookName.setPrefWidth(200);
        issuanceBookName.setStyle(tableColumnColor);

        TableColumn<IssuedBooks, String> issueDateColumn = new TableColumn<>("Issue Date");
        issueDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIssueDate()));
        issueDateColumn.setPrefWidth(200);
        issueDateColumn.setStyle(tableColumnColor);

        TableColumn<IssuedBooks, String> returnDateColumn = new TableColumn<>("Due Date");
        returnDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReturnDate()));
        returnDateColumn.setPrefWidth(200);
        returnDateColumn.setStyle(tableColumnColor);

        //to add the columns to the table view
        issuedBooksTableView.getColumns().addAll(issuanceNumberColumn, issuanceMemberIdColumn, issuanceMemberNameColumn, issuanceBookIdColumn, issuanceBookName, issueDateColumn, returnDateColumn);
        refreshIssuedBooksTable();




        firstRow.getChildren().addAll(text);
        issuedBooksInputFields.getChildren().addAll(memberIdTextField, memberNameTextField,issueDateTextField, issueBookButton, bookIdTextField, bookNameTextField, returnDateTextField);
        rightBlock.getChildren().addAll( issuanceNumberTextField,returnBookButton);
        issuedBooksContent.getChildren().addAll(firstRow, rightBlock, issuedBooksInputFields, issuedBooksTableView);
        return issuedBooksContent;
    }

    private void refreshIssuedBooksTable() throws SQLException {
        ObservableList<IssuedBooks> issuedBooks = yadaDAO.getIssuedBooks();
        issuedBooksTableView.setItems(issuedBooks);
    }

    public static void main(String[] args) {
        launch(args);
    }


}