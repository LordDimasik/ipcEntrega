import DBAccess.Connect4DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Player;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.Constants.Screens.MAIN;

public class ProfileController implements Initializable {
    @FXML
    public Label errorMessage;
    @FXML
    public ImageView avatar;
    @FXML
    public Button uploadImageButton;
    @FXML
    private TextField userName;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private DatePicker birthdate;
    @FXML
    private Button saveChanges;
    @FXML
    private Button back;

    public GlobalGame globalGame = GlobalGame.getInstance();
    public PlayerService playerService = new PlayerService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back.setOnAction(this::viewMain);
        saveChanges.setOnAction(this::saveChanges);
        uploadImageButton.setOnAction(this::uploadImage);
        playerService.birthdayDayPicker(birthdate);
    }

    public void setTextFields() {
        if (!globalGame.getPlayers().isEmpty()) {
            Player player = globalGame.getPlayers().get(0);
            userName.setText(player.getNickName());
            email.setText(player.getEmail());
            avatar.setImage(player.getAvatar());
        }
    }

    public void viewMain(ActionEvent actionEvent) {
        globalGame.activate(MAIN);
    }

    public void saveChanges(ActionEvent actionEvent) {
        final String errorMessageValidation =
                FormValidator.validateRegistration(userName.getText(), email.getText(), password.getText(), birthdate.getValue());
        if (errorMessageValidation.equals(PlayerService.SUCCESS_MESSAGE)) {
            if (globalGame.getPlayers() != null) {
                Player player = globalGame.getPlayers().get(0);
                try {
                    player.setEmail(email.getText());
                    player.setAvatar(avatar.getImage());
                    player.setPassword(password.getText());
                    player.setBirthdate(birthdate.getValue());
                } catch (Connect4DAOException exception) {
                    exception.printStackTrace();
                }
                globalGame.activate(MAIN);
            }
        } else {
            errorMessage.setText(errorMessageValidation);
        }
    }

    @FXML
    public void uploadImage(ActionEvent actionEvent) {
        playerService.uploadImage(avatar);
    }
}
