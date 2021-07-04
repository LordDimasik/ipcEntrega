import DBAccess.Connect4DAOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;
import model.Connect4;
import model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static constants.Constants.Screens.*;

public class GlobalGame {

    public Connect4 model;
    private final HashMap<String, Pair<Parent, FXMLLoader>> screenMap = new HashMap<>();
    private static GlobalGame INSTANCE;
    private final Scene main;
    private BooleanProperty isLoggedIn;
    private ArrayList<Player> players;
    private FXMLLoader currentXmlLoader;
    private Scene currentScene;
    private Cell[][] board = new Cell[7][8];
    private int currentPlayer;
    private String gameMode;

    {
        try {
            model = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException exception) {
            exception.printStackTrace();
        }
    }

    private GlobalGame() throws IOException, Connect4DAOException {
        this.currentXmlLoader = new FXMLLoader(getClass().getResource(VIEWS + INITIAL + FXML));
        Parent root = currentXmlLoader.load();
        screenMap.put(INITIAL, new Pair<>(root, currentXmlLoader));
        this.main = new Scene(root, 1000, 800);
        this.players = new ArrayList<>(4);
        this.isLoggedIn = new SimpleBooleanProperty(Boolean.FALSE);
        model.createDemoData(10, 0, 0);
    }

    public static GlobalGame getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new GlobalGame();
            } catch (IOException | Connect4DAOException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

    public void addScreen(String name, Pair<Parent, FXMLLoader> pairParentLoader) {
        screenMap.put(name, pairParentLoader);
    }

    public void activate(String name) {
        main.setRoot(screenMap.get(name).getKey());
        currentXmlLoader = screenMap.get(name).getValue();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Scene getMain() {
        return main;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public BooleanProperty isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(BooleanProperty loggedIn) {
        isLoggedIn = loggedIn;
    }

    public FXMLLoader getCurrentXmlLoader() {
        return currentXmlLoader;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public HashMap<String, Pair<Parent, FXMLLoader>> getScreenMap() {
        return screenMap;
    }
}
