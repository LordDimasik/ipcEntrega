import DBAccess.Connect4DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import model.Connect4;
import model.Player;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static constants.Constants.Const.COMPUTER;
import static constants.Constants.Const.SAME_PC;
import static constants.Constants.Screens.*;

public class PlaySamePCLoginController implements Initializable {
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button backButton;
    @FXML
    private Button playButton;
    @FXML
    private Label errorMessage;
    @FXML
    private ImageView avatar1;
    @FXML
    private Label slotLabel1;
    @FXML
    private ImageView avatar2;
    @FXML
    private Label slotLabel2;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Button removePlayerButton;
    @FXML
    private RadioButton playerRadio1;
    @FXML
    private RadioButton playerRadio2;

    @FXML
    private ToggleGroup player;

    public GlobalGame globalGame = GlobalGame.getInstance();
    PlayerService playerService = new PlayerService();
    GameService gameService = new GameService();
    public Connect4 model;

    {
        try {
            model = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addPlayerButton.setOnAction(this::addPlayer);
        removePlayerButton.setOnAction(this::removePlayer);
        backButton.setOnAction(this::viewMain);
        playButton.setOnAction(this::validatePlay);
    }

    @FXML
    private void viewMain(ActionEvent actionEvent) {
        globalGame.activate(MAIN);
    }

    @FXML
    private void validatePlay(ActionEvent actionEvent) {
        globalGame.setGameMode(SAME_PC);
        gameService.initBoard();
        globalGame.activate(GAME);
    }

    @FXML
    private void addPlayer(ActionEvent actionEvent) {
        int playerNum = getToggleRadio();
        if(playerNum != -1) {
            Pair<String, Player> pairErrorPlayer = playerService.loginPlayer
                    (userName.getText(), password.getText(), playerNum);
            Player currentPlayer = pairErrorPlayer.getValue();
            String currentError = pairErrorPlayer.getKey();
            errorMessage.setText(currentError);
            if (currentError.equals(PlayerService.SUCCESS_MESSAGE)) {
                setSlotAndAvatar(currentPlayer.getNickName(), currentPlayer.getAvatar(), playerNum);
                userName.setText("");
                password.setText("");
            }
        }
    }

    @FXML
    private void removePlayer(ActionEvent actionEvent) {
        int playerNum = getToggleRadio();
        if (playerNum != -1) {
            Player playerRemoved = playerService.logoutPlayerSamePC(playerNum);
            setSlotAndAvatar("Slot " + playerNum, new Image(Objects.requireNonNull
                    (Player.class.getResourceAsStream("/avatars/default.png"))), playerNum);
        }
    }

    private void setSlotAndAvatar(String slotLabel, Image avatar, int playerNum) {
        switch (playerNum) {
            case 1:
                slotLabel1.setText(slotLabel);
                avatar1.setImage(avatar);
                break;
            case 2:
                slotLabel2.setText(slotLabel);
                avatar2.setImage(avatar);
                break;
        }
    }

    //the way to extract the number of toggle is to print all of its properties and get it from the id
    private int getToggleRadio() {
        if (player.getSelectedToggle() != null) {
            String playerToggleRaw = player.getSelectedToggle().toString();
            int playerNumPos = playerToggleRaw.indexOf(", "); //this way we extract the id
            return Integer.parseInt(playerToggleRaw.substring(playerNumPos - 1, playerNumPos));
        }
        return -1;
    }
}
