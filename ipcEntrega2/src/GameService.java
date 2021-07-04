import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.Optional;

import static constants.Constants.Const.COLUMNS;
import static constants.Constants.Const.ROWS;
import static constants.Constants.Screens.MAIN;

public class GameService {
    GlobalGame globalGame = GlobalGame.getInstance();
    PlayerService playerService = new PlayerService();

    public void initBoard() {
        if (globalGame.getPlayers() != null) {
            globalGame.setCurrentPlayer(1);
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Cell cell = globalGame.getBoard()[i][j];
                if(cell == null) {
                    globalGame.getBoard()[i][j] = new Cell(j, i, j + i * COLUMNS);
                } else {
                    Pane paneCell = playerService.getPaneFromGrid(cell);
                    if (!paneCell.getChildren().isEmpty()) {
                        playerService.removeCircle(cell, paneCell);
                    }
                }
            }
        }
    }

    public boolean isGameOver() {
        if (horizontalCheck() || verticalCheck() || diagonalCheck() || antiDiagonalCheck()) {
            createDialog(String.format("Felicidades al jugador %d!", globalGame.getCurrentPlayer()),
                    "Enhorabunena!", String.format("Ha ganado el jugador %d!", globalGame.getCurrentPlayer()));
            return true;
        }
        if (checkDraw()) {
            createDialog("Empate!", "Ha sido un empate!", "");
            return true;
        }
        return false;
    }

    private void createDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            globalGame.activate(MAIN);
            initBoard();
        }
    }

    private boolean checkDraw() {
        Cell[][] board = globalGame.getBoard();
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .noneMatch(cell -> cell.getNumber() == 0);
    }

    private boolean horizontalCheck() {
        Cell[][] board = globalGame.getBoard();
        int player = globalGame.getCurrentPlayer();
        for (int j = 0; j < COLUMNS - 3; j++) {
            for (int i = 0; i < ROWS; i++) {
                if (board[i][j].getNumber() == player && board[i][j + 1].getNumber() == player
                        && board[i][j + 2].getNumber() == player && board[i][j + 3].getNumber() == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticalCheck() {
        Cell[][] board = globalGame.getBoard();
        int player = globalGame.getCurrentPlayer();
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j].getNumber() == player && board[i + 1][j].getNumber() == player
                        && board[i + 2][j].getNumber() == player && board[i + 3][j].getNumber() == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean diagonalCheck() {
        Cell[][] board = globalGame.getBoard();
        int player = globalGame.getCurrentPlayer();
        for (int i = 3; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if (board[i][j].getNumber() == player && board[i - 1][j + 1].getNumber() == player
                        && board[i - 2][j + 2].getNumber() == player && board[i - 3][j + 3].getNumber() == player)
                    return true;
            }
        }
        return false;
    }

    private boolean antiDiagonalCheck() {
        Cell[][] board = globalGame.getBoard();
        int player = globalGame.getCurrentPlayer();
        for (int i = 3; i < ROWS; i++) {
            for (int j = 3; j < COLUMNS; j++) {
                if (board[i][j].getNumber() == player && board[i - 1][j - 1].getNumber() == player
                        && board[i - 2][j - 2].getNumber() == player && board[i - 3][j - 3].getNumber() == player)
                    return true;
            }
        }
        return false;
    }
}
