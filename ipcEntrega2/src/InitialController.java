import constants.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialController implements Initializable {

    @FXML
    private GridPane screenId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenId.setOnMouseClicked(this::viewMain);
    }

    public void viewMain(MouseEvent mouseEvent) {
        GlobalGame globalGame = GlobalGame.getInstance();
        globalGame.activate(Constants.Screens.MAIN);
    }
}
