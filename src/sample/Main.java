package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.xml.soap.Text;

import java.net.URI;


public class Main extends Application {

    int margin = 25;

    @Override
    public void start(Stage primaryStage) {
        game(25, 15, 20, primaryStage, 1024, 768);
    }

    public void newGameMenu(Stage primaryStage) {
        VBox vbox = new VBox();

        TextField columns = new TextField();
        TextField rows = new TextField();
        TextField mines = new TextField();
        Button go = new Button("GO");

        columns.setPromptText("Columns");
        rows.setPromptText("Rows");
        mines.setPromptText("Mines");

        go.setMaxWidth(Double.MAX_VALUE);

        go.setOnMouseClicked( e ->
                    game(Integer.parseInt(columns.getText()), Integer.parseInt(rows.getText()),  Integer.parseInt(mines.getText()),
                            primaryStage, 1024, 768)
        );

        vbox.getChildren().addAll(columns, rows, mines, go);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(20.0);

        Scene scene = new Scene(vbox, 180, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void game(int columns, int rows, int mines, Stage primaryStage, double windowWidth, double windowHeight) {

        VBox vbox = new VBox();
        HBox hbox = new HBox();

        GridPane grid = new GridPane();

        Button[][] button = new Button[columns][rows];

        grid.setPrefSize(Region.USE_COMPUTED_SIZE, 1000);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


        hbox.setPadding(new Insets(15));

        Image bomb = new Image("mine.png", 1000 * Math.pow((columns + rows) / 2, -1.2),
                1000 * Math.pow((columns + rows) / 2, -1.2), false, false);
        Image smiley1 = new Image("smiley1.png", 70, 70, false, false);
        Image smiley2 = new Image("smiley2.png", 70, 70, false, false);
        Image dead = new Image("dead.png", 70, 70, false, false);
        Image won = new Image("won.png", 70, 70, false, false);
        Image flag = new Image("flag.png", 800 * Math.pow((columns + rows) / 2, -1.2),
                800 * Math.pow((columns + rows) / 2, -1.2), false, false);
        Spielfeld game = new Spielfeld(columns, rows, mines);
        Button smiley = new Button();

        HBox hbox2 = new HBox();
        Region region1 = new Region();
        Region region2 = new Region();

        int smileySize = 40;

        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);

        hbox2.setPrefHeight(smileySize);
        hbox2.setMaxHeight(smileySize);
        hbox2.setPadding(new Insets(margin));

        hbox2.getChildren().addAll(region1, smiley, region2);

        smiley.setPrefSize(smileySize, smileySize);
        smiley.setMaxSize(smileySize, smileySize);
        smiley.setAlignment(Pos.CENTER);

        smiley.setGraphic(new ImageView(smiley1));

        MenuBar menubar = new MenuBar();
        Menu Game = new Menu("Game");
        MenuItem New = new MenuItem("New");
        MenuItem Reset = new MenuItem("Reset");
        MenuItem Exit = new MenuItem("Exit");
        Menu Help = new Menu("Help");
        MenuItem HowToPlay = new MenuItem("How To Play Minesweeper?");
        Game.getItems().addAll(New, Reset, Exit);
        Help.getItems().add(HowToPlay);
        menubar.getMenus().addAll(Game, Help);


        HBox.setHgrow(grid, Priority.ALWAYS);
        VBox.setVgrow(hbox, Priority.ALWAYS);
        VBox.setVgrow(grid, Priority.ALWAYS);
        hbox.setAlignment(Pos.BASELINE_LEFT);
        hbox.getChildren().addAll(grid);
        vbox.getChildren().addAll(menubar, hbox2, hbox);

        Scene scene = new Scene(vbox, windowWidth, windowHeight, Color.WHITE);

        Stage secondaryStage = new Stage();

        HowToPlay.setOnAction( e ->
                openURL("http://bfy.tw/8uhR")
        );

        New.setOnAction( e ->
            newGameMenu(secondaryStage)
        );

        Reset.setOnAction( e ->
                reset(game, columns, rows, mines, bomb, smiley1, smiley2, dead, won, flag, button, smiley, vbox,
                        primaryStage, scene)
        );

        Exit.setOnAction( e ->
                System.exit(0)
        );

        smiley.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                MouseButton btn = event.getButton();
                if(btn==MouseButton.PRIMARY){
                    reset(game, columns, rows, mines, bomb, smiley1, smiley2, dead, won, flag, button, smiley, vbox,
                            primaryStage, scene);
                }
            }
        });

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                button[i][j] = new Button();
                grid.add(button[i][j], i, j);
                button[i][j].setMinSize(24, 24);
                button[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                HBox.setHgrow(button[i][j], Priority.ALWAYS);
                VBox.setVgrow(button[i][j], Priority.ALWAYS);
                grid.setHgrow(button[i][j], Priority.ALWAYS);
                grid.setVgrow(button[i][j], Priority.ALWAYS);
                button[i][j].setStyle("-fx-font-size: 10; -fx-font-weight: bold; -fx-base: #6E6E6E;");
                final int x = i;
                final int y = j;

                if ((scene.getWidth() - 2 * margin) / columns > 24) {
                    button[x][y].setMinWidth((scene.getWidth() - 2 * margin) / columns);
                }
                if ((scene.getHeight() - 90 - 4 * margin) / rows > 24) {
                    button[x][y].setMinHeight((scene.getHeight() - 90 - 4 * margin) / rows);
                }

                scene.widthProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                        double minWidth = 24;
                        if ((scene.getWidth() - 1.5 * margin) / columns > 24) {
                            minWidth = (scene.getWidth() - 1.7 * margin) / columns;
                        }

                        button[x][y].setMinWidth(minWidth);
                    }
                });

                scene.heightProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                        double minHeight = 24;
                        if ((scene.getHeight() - 90 - 4 * margin) / rows > 24) {
                            minHeight = (scene.getHeight() - 90 - 4 * margin) / rows;
                        }
                        button[x][y].setMinHeight(minHeight);
                    }
                });

                button[i][j].addEventHandler(MouseEvent.MOUSE_PRESSED,
                        event -> {
                            if (!game.lost && ! game.won) {
                                smiley.setGraphic(new ImageView(smiley2));
                            }
                        });
                button[i][j].addEventHandler(MouseEvent.MOUSE_RELEASED,
                        event -> {
                            smiley.setGraphic(new ImageView(smiley1));
                        });
                button[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton btn = event.getButton();
                        if(btn==MouseButton.PRIMARY){
                            smiley.setGraphic(new ImageView(smiley1));
                            game.linksKlick(x, y, columns, rows, mines);
                            for (int k = 0; k < columns; k++) {
                                for (int l = 0; l < rows; l++) {
                                    if (game.spielfeld[k][l].opened) {
                                        button[k][l].setGraphic(new ImageView());
                                        if (game.spielfeld[k][l].neighboredMines != 0) {
                                            button[k][l].setText(String.valueOf(game.spielfeld[k][l].neighboredMines));
                                        }
                                        if (game.spielfeld[k][l].neighboredMines == 1) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD; -fx-text-fill: blue;");
                                        } else if (game.spielfeld[k][l].neighboredMines == 2) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD; -fx-text-fill: green;");
                                        } else if (game.spielfeld[k][l].neighboredMines == 3) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD; -fx-text-fill: red;");
                                        } else if (game.spielfeld[k][l].neighboredMines == 4) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD; -fx-text-fill: purple;");
                                        } else if (game.spielfeld[k][l].neighboredMines == 5) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD; -fx-text-fill: maroon;");
                                        } else if (game.spielfeld[k][l].neighboredMines == 6) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD; -fx-text-fill: turquoise;");
                                        } else if (game.spielfeld[k][l].neighboredMines == 7) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD; -fx-text-fill: black;");
                                        } else if (game.spielfeld[k][l].neighboredMines == 8) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD; -fx-text-fill: gray;");
                                        }
                                        else {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD;");
                                        }
                                    }
                                }
                            }

                            if (game.lost) {
                                for (int k = 0; k < columns; k++) {
                                    for (int l = 0; l < rows; l++) {
                                        if (game.spielfeld[k][l].isMine) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD;");
                                            button[k][l].setGraphic(new ImageView(bomb));
                                        }
                                    }
                                }
                                button[game.xcoor][game.ycoor].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                        "-fx-base: red;");
                                smiley.setGraphic(new ImageView(dead));
                            }

                            if (game.won) {
                                for (int k = 0; k < columns; k++) {
                                    for (int l = 0; l < rows; l++) {
                                        if (game.spielfeld[k][l].isMine) {
                                            button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; " +
                                                    "-fx-base: #BDBDBD;");
                                            button[k][l].setGraphic(new ImageView(bomb));
                                        }
                                    }
                                }
                                smiley.setGraphic(new ImageView(won));
                            }
                        } else if (btn==MouseButton.SECONDARY) {
                            if (!game.spielfeld[x][y].opened) {
                                game.spielfeld[x][y].flag = !game.spielfeld[x][y].flag;
                                if (game.spielfeld[x][y].flag) {
                                    button[x][y].setGraphic(new ImageView(flag));
                                } else {
                                    button[x][y].setGraphic(new ImageView());
                                }
                            }
                        }
                    }
                });

            }
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void reset(Spielfeld game, int columns, int rows, int mines, Image bomb, Image smiley1, Image smiley2, Image dead,
               Image won, Image flag, Button[][] button, Button smiley, VBox layout, Stage arg0, Scene scene) {
        smiley.setGraphic(new ImageView(smiley1));
        game.reset(columns, rows, mines);
        game(columns, rows, mines, arg0, scene.getWidth(), scene.getHeight());
        for (int k = 0; k < columns; k++) {
            for (int l = 0; l < rows; l++) {
                button[k][l].setText("");
                button[k][l].setStyle("-fx-font-size: 10; -fx-font-weight: bold; -fx-base: #6E6E6E;");
            }
        }
    }

    void openURL(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
