package solitaire.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import solitaire.model.GameMode;
import javafx.util.Duration;

public class SolitaireApp extends Application {

    private static final Duration AUTOPLAY_DELAY = Duration.millis(400);

    private final GameController controller = new GameController();

    private ToggleGroup typeGroup;
    private ToggleGroup modeGroup;
    private TextField sizeField;
    private Button randomizeButton;
    private Label statusLabel;
    private BoardView boardView;
    private Timeline autoplay;

    @Override
    public void start(Stage stage) {
        boardView = new BoardView(position -> {
            controller.handleClick(position);
            refresh();
        });
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px;");

        autoplay = new Timeline(new KeyFrame(AUTOPLAY_DELAY, event -> onAutoPlayTick()));
        autoplay.setCycleCount(Animation.INDEFINITE);

        typeGroup = new ToggleGroup();
        modeGroup = new ToggleGroup();
        VBox choosers = new VBox(20,
                buildChooser("Board Type", typeGroup, BoardType.values(), BoardType.ENGLISH),
                buildChooser("Game Mode", modeGroup, GameMode.values(), GameMode.MANUAL));

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
        root.setLeft(choosers);
        root.setRight(buildSizeAndButtons());
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

    private <E extends Enum<E>> VBox buildChooser(String title, ToggleGroup group, E[] values, E selected) {
        VBox box = new VBox(8, new Label(title));
        for (E value : values) {
            RadioButton button = new RadioButton(displayName(value));
            button.setUserData(value);
            button.setToggleGroup(group);
            button.setSelected(value == selected);
            box.getChildren().add(button);
        }
        return box;
    }

    private VBox buildSizeAndButtons() {
        sizeField = new TextField(String.valueOf(Board.DEFAULT_SIZE));
        sizeField.setPrefColumnCount(3);
        sizeField.setOnAction(event -> onNewGame());

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> onNewGame());

        randomizeButton = new Button("Randomize");
        randomizeButton.setOnAction(event -> {
            controller.randomize();
            refresh();
        });

        return new VBox(8, new Label("Board size"), sizeField, newGameButton, randomizeButton);
    }

    private void onNewGame() {
        autoplay.stop();

        BoardType type = (BoardType) typeGroup.getSelectedToggle().getUserData();
        GameMode mode = (GameMode) modeGroup.getSelectedToggle().getUserData();
        controller.newGame(type, sizeField.getText(), mode);

        if (controller.getMode() == GameMode.AUTOMATED && !controller.getGame().isGameOver()) {
            autoplay.play();
        }
        refresh();
    }

    private void onAutoPlayTick() {
        controller.autoStep();
        if (controller.getGame().isGameOver()) {
            autoplay.stop();
        }
        refresh();
    }

    private void refresh() {
        boardView.render(controller.getGame().getBoard(), controller.getSelected());
        String message = controller.getMessage();
        statusLabel.setText(message.isEmpty()
                ? "Pegs left: " + controller.getGame().getPegsLeft()
                : message);
        randomizeButton.setDisable(!controller.canRandomize());

    }

    private static String displayName(Enum<?> value) {
        String name = value.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}