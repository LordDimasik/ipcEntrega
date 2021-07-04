import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Player;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static constants.Constants.Const.COMPUTER;
import static constants.Constants.Screens.*;

public class MainController implements Initializable {

    @FXML
    private Button playVsComputer;
    @FXML
    private Label userName;
    @FXML
    private ImageView userAvatar;
    @FXML
    private Button signOutButton;
    @FXML
    private Button playSamePCButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button registrationButton;
    @FXML
    private Button profile;

    public PlayerService playerService = new PlayerService();
    GlobalGame globalGame = GlobalGame.getInstance();
    GameService gameService = new GameService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registrationButton.setOnAction(this::viewRegistration);
        loginButton.setOnAction(this::viewLogin);
        playSamePCButton.setOnAction(this::viewPlaySamePCLogin);
        signOutButton.setOnAction(this::signOut);
        profile.setOnAction(this::viewProfile);
    }

    @FXML
    private void viewRegistration(ActionEvent actionEvent) {
        globalGame.activate(REGISTRATION);
    }

    @FXML
    private void viewLogin(ActionEvent actionEvent) {
        globalGame.activate(LOGIN);
    }

    @FXML
    private void viewPlaySamePCLogin(ActionEvent actionEvent) {
        globalGame.activate(PLAY_SAME_PC_LOGIN);
    }

    @FXML
    public void viewProfile(ActionEvent actionEvent) {
        globalGame.activate(PROFILE);
        ProfileController profileController = globalGame.getCurrentXmlLoader().getController();
        profileController.setTextFields();
    }

    public void viewPlayVsComputer(ActionEvent actionEvent) {
        globalGame.setGameMode(COMPUTER);
        gameService.initBoard();
        globalGame.activate(GAME);
    }

    public void signOut(ActionEvent actionEvent) {
        playerService.logoutAllPlayers();
        globalGame.isLoggedIn().set(Boolean.FALSE);
        userName.setText("Guest");
        userAvatar.setImage(new Image(Objects.requireNonNull
                (Player.class.getResourceAsStream("/avatars/default.png"))));
    }

    public void setUserNameAvatar(boolean newValue) {
        if (newValue) {
            Player player = globalGame.getPlayers().get(0);
            userAvatar.setImage(player.getAvatar());
            userName.setText(player.getNickName());
        }
    }
}
