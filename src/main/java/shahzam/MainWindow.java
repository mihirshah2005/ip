package shahzam;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;

//improved with ChatGPT 5
public class MainWindow extends AnchorPane {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private Shahzam shahzam;

    private final Image userImage = new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image shahzamImage = new Image(getClass().getResourceAsStream("/images/DaShahzam.png"));

    private final BooleanProperty busy = new SimpleBooleanProperty(false);
    private static final String EXIT_LINE = "Thunder quiets. SHAHZAM signing off, until next time.";

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane.setFitToWidth(true);
        dialogContainer.setFillWidth(true);

        userInput.setPromptText("Type a spell…  (Shift+Enter for newline)");
        sendButton.disableProperty().bind(busy.or(userInput.textProperty().isEmpty()));
        userInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && !e.isShiftDown()) {
                e.consume();
                handleUserInput();
            }
        });

        dialogContainer.getStyleClass().add("chat-root");

        dialogContainer.getChildren().add(
                DialogBox.getShahzamDialog(
                        "The word was spoken, SHAHZAM awakens.\nWhat can I do for you today?",
                        shahzamImage
                )
        );
    }

    /** Injects the Shahzam instance */
    public void setShahzam(Shahzam s) {
        this.shahzam = s;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty() || shahzam == null) return;

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
        userInput.clear();

        busy.set(true);
        userInput.setDisable(true);

        Task<String> respondTask = new Task<>() {
            @Override protected String call() {
                return shahzam.getResponse(input);
            }
        };

        respondTask.setOnSucceeded(evt -> {
            String response = respondTask.getValue();
            dialogContainer.getChildren().add(DialogBox.getShahzamDialog(response, shahzamImage));

            if (EXIT_LINE.equals(response)) {
                userInput.setDisable(true);
                sendButton.setDisable(true);
            } else {
                userInput.setDisable(false);
                userInput.requestFocus();
            }
            busy.set(false);

        });

        respondTask.setOnFailed(evt -> {
            dialogContainer.getChildren().add(
                    DialogBox.getShahzamDialog("⚠️ Oops, something went wrong—try again.", shahzamImage)
            );
            busy.set(false);
            userInput.setDisable(false);
        });

        Thread t = new Thread(respondTask);
        t.setDaemon(true);
        t.start();
    }
}
