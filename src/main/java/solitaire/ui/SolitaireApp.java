package solitaire.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import solitaire.controller.GameController;
import solitaire.model.Board;
import solitaire.model.BoardType;

public class SolitaireApp extends Application {

    private final GameController controller = new GameController();

    private ToggleGroup typeGroup;
    private TextField sizeField;
    private Label statusLabel;
    private BoardView boardView;

    @Override
    public void start(Stage stage) {
        boardView = new BoardView(position -> {
            controller.handleClick(position);
            refresh();
        });
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px;");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
        root.setLeft(buildBoardTypeChooser());
        root.setRight(buildSizeAndNewGame());
        root.setCenter(boardView);
        root.setBottom(statusLabel);
        BorderPane.setAlignment(boardView, Pos.CENTER);
        BorderPane.setMargin(boardView, new Insets(0, 24, 0, 24));
        BorderPane.setMargin(statusLabel, new Insets(12, 0, 0, 0));

        refresh();
        stage.setScene(new Scene(root));
        stage.setTitle("Peg Solitaire");
        stage.show();
    }

    private VBox buildBoardTypeChooser() {
        typeGroup = new ToggleGroup();
        VBox box = new VBox(8, new Label("Board Type"));
        for (BoardType type : BoardType.values()) {
            RadioButton button = new RadioButton(displayName(type));
            button.setUserData(type);
            button.setToggleGroup(typeGroup);
            button.setSelected(type == BoardType.ENGLISH);
            box.getChildren().add(button);
        }
        return box;
    }

    private VBox buildSizeAndNewGame() {
        sizeField = new TextField(String.valueOf(Board.DEFAULT_SIZE));
        sizeField.setPrefColumnCount(3);
        sizeField.setOnAction(event -> onNewGame());

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> onNewGame());

        return new VBox(8, new Label("Board size"), sizeField, newGameButton);
    }

    private void onNewGame() {
        BoardType type = (BoardType) typeGroup.getSelectedToggle().getUserData();
        controller.newGame(type, sizeField.getText());
        refresh();
    }

    private void refresh() {
        boardView.render(controller.getGame().getBoard(), controller.getSelected());
        String message = controller.getMessage();
        statusLabel.setText(message.isEmpty()
                ? "Pegs left: " + controller.getGame().getPegsLeft()
                : message);
    }

    private static String displayName(BoardType type) {
        String name = type.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}