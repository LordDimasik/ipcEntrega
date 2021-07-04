import DBAccess.Connect4DAOException;
import model.Connect4;
import model.Player;

import java.time.LocalDate;

public class FormValidator {

    public static String validateRegistration(String userName, String email,
                                              String password, LocalDate birthdate) {
        try {
            final Connect4 model = Connect4.getSingletonConnect4();
            if (userName.isEmpty() || email.isEmpty() ||
                    password.isEmpty() || birthdate == null) {
                return "Falta algun valor obligatorio!";
            }
            if (model.exitsNickName(userName)) {
                return "El nombre de usuario ya está en uso!";
            }
            if (!Player.checkEmail(email)) {
                return "El email no es válido!";
            }
            if (!Player.checkNickName(userName)) {
                return "El nombre de usuario no es válido!";
            }
            if (!Player.checkPassword(password)) {
                return "La contraseña no es válida!";
            }
            return PlayerService.SUCCESS_MESSAGE;
        } catch (Connect4DAOException exception){
            exception.printStackTrace();
            return exception.toString();
        }
    }

    public static String validateLogin(String userName, String password) {
        final Connect4 model;
        try {
            model = Connect4.getSingletonConnect4();
            if (userName.isEmpty() || password.isEmpty()) {
                return "Falta algun valor obligatorio!";
            }
            if (!model.exitsNickName(userName)) {
                return "Este usuario no existe!";
            }
            return PlayerService.SUCCESS_MESSAGE;
        } catch (Connect4DAOException exception) {
            exception.printStackTrace();
            return exception.toString();
        }
    }
}
