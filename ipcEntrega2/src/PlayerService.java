import DBAccess.Connect4DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.Connect4;
import model.Player;

import static constants.Constants.Screens.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerService {
    public final static String SUCCESS_MESSAGE = "success!";
    GlobalGame globalGame = GlobalGame.getInstance();

    public Connect4 model;

    {
        try {
            model = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException exception) {
            exception.printStackTrace();
        }
    }

    public Pair<String, Player> loginPlayer(String userName, String password, int playerNum) {
        GlobalGame globalGame = GlobalGame.getInstance();
        String errorMessageValidatorFormValidator = FormValidator.validateLogin(userName, password);
        if (errorMessageValidatorFormValidator.equals(SUCCESS_MESSAGE)) {
            Player player = model.loginPlayer(userName, password);
            if (player != null) {
                ArrayList<Player> players = globalGame.getPlayers();
                players.add(playerNum - 1, player);
                globalGame.setPlayers(players);
                return new Pair<>(SUCCESS_MESSAGE, player);
            }
        }
        return new Pair<>(errorMessageValidatorFormValidator, null);
    }

    public Player logoutPlayerSamePC(int playerNum) {
        GlobalGame globalGame = GlobalGame.getInstance();
        ArrayList<Player> players = globalGame.getPlayers();
        Player playerRemoved = players.remove(playerNum - 1);
        globalGame.setPlayers(players);
        return playerRemoved;
    }

    public void logoutAllPlayers() {
        GlobalGame globalGame = GlobalGame.getInstance();
        globalGame.isLoggedIn().set(Boolean.FALSE);
        globalGame.getPlayers().removeAll(globalGame.getPlayers());
    }

    public void addCircle(Cell cell) {
        Pane paneCell = getPaneFromGrid(cell);
        ImageView circle = new ImageView(new Image(Objects.requireNonNull
                (Player.class.getResourceAsStream(circleImageColor()))));
        circle.fitWidthProperty().bind(paneCell.widthProperty());
        circle.fitHeightProperty().bind(paneCell.heightProperty());
        cell.setImageView(circle);
        paneCell.getChildren().add(circle);
    }

    public void removeCircle(Cell cell, Pane paneCell) {
        cell.setNumber(0);
        cell.setImageView(null);
        ((ImageView) paneCell.getChildren().get(0)).setImage(null);
    }

    public Pane getPaneFromGrid(Cell cell) {
        HBox mainHBox = globalGame.getScreenMap().get(GAME).getValue().getRoot();
        GridPane mainGrid = (GridPane) mainHBox.getChildren().get(0);
        return (Pane) mainGrid.getChildren().get(cell.getNodeNum());
    }

    public String circleImageColor() {
        int playerNum = globalGame.getCurrentPlayer();
        switch (playerNum) {
            case 1:
                return "/avatars/BlueCircle.png";
            case 2:
                return "/avatars/RedCircle.png";
            case 3:
                return "/avatars/YellowCircle.png";
            case 4:
                return "/avatars/GreenCircle.png";
            default:
                return "error circleImageColor";
        }
    }

    public void birthdayDayPicker(DatePicker birthdate) {
        birthdate.setDayCellFactory((
                DatePicker picker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now().minusYears(12)) > 0);
            }
        });
    }

    public void uploadImage(ImageView avatar) {
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.setTitle("Elige el avatar a subir");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = fileChooser.showOpenDialog(globalGame.getMain().getWindow());
            avatar.setImage(new Image(file.toURI().toURL().toExternalForm()));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
