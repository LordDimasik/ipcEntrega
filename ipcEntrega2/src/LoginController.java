import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import model.Player;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.Constants.Screens.MAIN;

public class LoginController implements Initializable {

    @FXML
    private TextFlow notRegistered;
    @FXML
    private TextFlow noPassword;
    @FXML
    private Label errorMessage;
    @FXML
    private Button backButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public GlobalGame globalGame = GlobalGame.getInstance();
    public PlayerService playerService = new PlayerService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(this::viewMain);
        loginButton.setOnAction(this::validateLogin);
        notRegistered = new TextFlow(new Text(""));
    }

    @FXML
    private void validateLogin(ActionEvent actionEvent) {
        Pair<String, Player> pairErrorPlayer = playerService.loginPlayer(username.getText(), password.getText(), 1);
        String currentError = pairErrorPlayer.getKey();
        if (currentError.equals(PlayerService.SUCCESS_MESSAGE)) {
            globalGame.isLoggedIn().set(Boolean.TRUE);
            globalGame.activate(MAIN);
        }  else {
            errorMessage.setText(currentError);
        }
    }

    @FXML
    private void viewMain(ActionEvent actionEvent) {
        globalGame.activate(MAIN);
    }
}
