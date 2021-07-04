import DBAccess.Connect4DAOException;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import model.Connect4;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static constants.Constants.Const.*;

public class GameController implements Initializable {

    public Connect4 model;
    public GlobalGame globalGame = GlobalGame.getInstance();
    public PlayerService playerService = new PlayerService();
    public GameService gameService = new GameService();

    {
        try {
            model = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Cell makeMove(int column, int currentPlayer) {
        Cell[][] board = globalGame.getBoard();
        Cell cell = null;
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column].getNumber() == 0) {
                board[i][column].setNumber(currentPlayer);
                cell = board[i][column];
                break;
            }
        }
        return cell;
    }

    public void drawMove(Cell cell) {
        if (cell != null) {
            playerService.addCircle(cell);
            gameService.isGameOver();
        }
    }

    public void makeYourMove(int column) {
        Cell cell = makeMove(column, globalGame.getCurrentPlayer());
        if (cell != null) {
            drawMove(cell);
            changePlayer();
        }
    }

    public void enemyCPUMove() {
        Cell[][] board = globalGame.getBoard();
        Cell cell = null;
        Random rand = new Random();
        int column = rand.nextInt(7);
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column].getNumber() == 0) {
                board[i][column].setNumber(globalGame.getCurrentPlayer());
                cell = board[i][column];
                drawMove(cell);
                break;
            }
        }
        if (cell == null) {
            enemyCPUMove();
        }
    }

    public void changePlayer() {
        if (globalGame.getCurrentPlayer() == 1) {
            globalGame.setCurrentPlayer(2);
        } else {
            globalGame.setCurrentPlayer(1);
        }
    }

    public void column0(MouseEvent mouseEvent) {
        makeYourMove(0);
        if (!gameService.isGameOver() && globalGame.getGameMode().equals(COMPUTER)) {
            enemyCPUMove();
            changePlayer();
        }
    }

    public void column1(MouseEvent mouseEvent) {
        makeYourMove(1);
        if (!gameService.isGameOver() && globalGame.getGameMode().equals(COMPUTER)) {
            enemyCPUMove();
            changePlayer();
        }
    }

    public void column2(MouseEvent mouseEvent) {
        makeYourMove(2);
        if (!gameService.isGameOver() && globalGame.getGameMode().equals(COMPUTER)) {
            enemyCPUMove();
            changePlayer();
        }
    }

    public void column3(MouseEvent mouseEvent) {
        makeYourMove(3);
        if (!gameService.isGameOver() && globalGame.getGameMode().equals(COMPUTER)) {
            enemyCPUMove();
            changePlayer();
        }
    }

    public void column4(MouseEvent mouseEvent) {
        makeYourMove(4);
        if (!gameService.isGameOver() && globalGame.getGameMode().equals(COMPUTER)) {
            enemyCPUMove();
            changePlayer();
        }
    }

    public void column5(MouseEvent mouseEvent) {
        makeYourMove(5);
        if (!gameService.isGameOver() && globalGame.getGameMode().equals(COMPUTER)) {
            enemyCPUMove();
            changePlayer();
        }
    }

    public void column6(MouseEvent mouseEvent) {
        makeYourMove(6);
        if (!gameService.isGameOver() && globalGame.getGameMode().equals(COMPUTER)) {
            enemyCPUMove();
            changePlayer();
        }
    }

    public void column7(MouseEvent mouseEvent) {
        makeYourMove(7);
        if (!gameService.isGameOver() && globalGame.getGameMode().equals(COMPUTER)) {
            enemyCPUMove();
            changePlayer();
        }
    }
}
