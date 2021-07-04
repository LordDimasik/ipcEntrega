import DBAccess.Connect4DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Connect4;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    public GlobalGame globalGame = GlobalGame.getInstance();
    PlayerService playerService = new PlayerService();
    public Connect4 model;

    {
        try {
            model = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException exception) {
            exception.printStackTrace();
        }
    }

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
    private Button createAccountButton;
    @FXML
    private Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back.setOnAction(this::viewMain);
        createAccountButton.setOnAction(this::createAccount);
        uploadImageButton.setOnAction(this::uploadImage);
        playerService.birthdayDayPicker(birthdate);
    }

    @FXML
    private void viewMain(ActionEvent actionEvent) {
        globalGame.activate("main");
    }

    @FXML
    private void createAccount(ActionEvent actionEvent) {
        try {
            final String errorMessageValidation =
                    FormValidator.validateRegistration(userName.getText(), email.getText(), password.getText(), birthdate.getValue());
            if (errorMessageValidation.equals(PlayerService.SUCCESS_MESSAGE)) {
                model.registerPlayer(userName.getText(), email.getText(), password.getText(), avatar.getImage(), birthdate.getValue(), 0);
                globalGame.activate("registrationSuccess");
            } else {
                errorMessage.setText(errorMessageValidation);
            }
        } catch (Connect4DAOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void uploadImage(ActionEvent actionEvent) {
        playerService.uploadImage(avatar);
    }
}
