import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Objects;

import static constants.Constants.Screens.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        GlobalGame globalGame = GlobalGame.getInstance();
        FXMLLoader mainLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(VIEWS + MAIN + FXML)));
        FXMLLoader registrationLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(VIEWS + REGISTRATION + FXML)));
        FXMLLoader registrationSuccessLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(VIEWS + REGISTRATION_SUCCESS + FXML)));
        FXMLLoader loginLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(VIEWS + LOGIN + FXML)));
        FXMLLoader playSamePCLoginLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(VIEWS + PLAY_SAME_PC_LOGIN + FXML)));
        FXMLLoader gameLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(VIEWS + GAME + FXML)));
        FXMLLoader profileLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(VIEWS + PROFILE + FXML)));
        globalGame.addScreen(MAIN, new Pair<>(mainLoader.load(), mainLoader));
        globalGame.addScreen(REGISTRATION, new Pair<>(registrationLoader.load(), registrationLoader));
        globalGame.addScreen(REGISTRATION_SUCCESS, new Pair<>(registrationSuccessLoader.load(), registrationLoader));
        globalGame.addScreen(LOGIN, new Pair<>(loginLoader.load(), loginLoader));
        globalGame.addScreen(PLAY_SAME_PC_LOGIN, new Pair<>(playSamePCLoginLoader.load(), playSamePCLoginLoader));
        globalGame.addScreen(GAME, new Pair<>(gameLoader.load(), gameLoader));
        globalGame.addScreen(PROFILE, new Pair<>(profileLoader.load(), profileLoader));
        primaryStage.setScene(globalGame.getMain());
        primaryStage.setTitle("Cuatro en Raya");
        globalGame.isLoggedIn().addListener((observable, oldValue, newValue) -> {
            globalGame.activate(MAIN);
            MainController mainController = globalGame.getCurrentXmlLoader().getController();
            mainController.setUserNameAvatar(newValue);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
