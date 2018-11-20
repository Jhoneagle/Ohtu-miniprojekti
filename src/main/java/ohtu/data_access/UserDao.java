package ohtu.data_access;

import java.sql.SQLException;
import java.util.List;
import ohtu.domain.User;

public class UserDao implements AccountDao<User, Integer> {
    private final Database database;
    
    public UserDao(Database database) {
        this.database = database;
    }        

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User findByName(String name) {
        for (User user : findAll()) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }
    
    @Override
    public User findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public void add(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
