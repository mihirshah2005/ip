package shahzam;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Shahzam shahzam;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image ShahzamImage = new Image(this.getClass().getResourceAsStream("/images/DaShahzam.png"));

    @FXML
    public void initialize() {

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getShahzamDialog("The word was spoken, SHAHZAM awakens.\n What can I do for you today?", ShahzamImage)
        );
    }

    /** Injects the Shahzam instance */
    public void setShahzam(Shahzam s) {

        shahzam = s;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Shahzam's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        // Obtain and display response to user input
        String response = shahzam.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getShahzamDialog(response, ShahzamImage)
        );

        // Remove previous input
        userInput.clear();

        // Shutdown upon receiving exit message
        if (response.equals("Thunder quiets. SHAHZAM signing off, until next time.")) {
            // Disable all mode of input
            userInput.setDisable(true);
            sendButton.setDisable(true);

        }

    }
}
