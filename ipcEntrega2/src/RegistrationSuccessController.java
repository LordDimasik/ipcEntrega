import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.Constants.Screens.MAIN;

public class RegistrationSuccessController implements Initializable {

    public GlobalGame globalGame = GlobalGame.getInstance();

    @FXML
    private Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back.setOnAction(this::viewMain);
    }

    public void viewMain(ActionEvent actionEvent) {
        globalGame.activate(MAIN);
    }
}
