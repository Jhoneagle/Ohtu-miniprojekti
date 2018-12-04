package ohtu.authentication;

import ohtu.data_access.AccountDao;
import ohtu.domain.User;
import ohtu.util.CreationStatus;

import java.sql.SQLException;

public class AuthenticationService {

    private AccountDao userDao;

    public AuthenticationService(AccountDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) throws SQLException {
        for (Object obj : userDao.findAll()) {
            if (obj.getClass() == User.class) {
                User user = (User) obj;

                if (user.getUsername().equals(username)
                        && user.getPassword().equals(password)) {
                    return true;
                }
            }
        }

        return false;
    }

    public CreationStatus createUser(String username, String password, String passwordConfirmation) {
        CreationStatus status = new CreationStatus();

        if (userDao.findByName(username) != null) {
            status.addError("username is already taken");
        }

        if (username.length() < 3) {
            status.addError("username should have at least 3 characters");
        }

        if (!username.matches("[a-zA-Z]+")) {
            status.addError("username should have only letter characters");
        }

        if (password.length() < 8) {
            status.addError("password should have at least 8 characters");
        }

        char[] specialCh = {'!', '@', ']', '#', '$', '%', '^', '&', '*', '+', '-'};
        boolean noSpecialCh = true;

        for (char c : specialCh) {
            String s = "" + c;

            if (password.contains(s)) {
                noSpecialCh = false;
            }
        }

        if (noSpecialCh && !password.matches(".*\\d+.*")) {
            status.addError("password should contain special characters or numbers");
        }

        if (!password.equals(passwordConfirmation)) {
            status.addError("password and password confirmation do not match");
        }

        if (status.isOk()) {
            userDao.add(new User(-1, username, password));
        }

        return status;
    }
}
