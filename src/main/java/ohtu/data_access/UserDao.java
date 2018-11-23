package ohtu.data_access;

import ohtu.domain.User;

import java.util.List;

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
    public User findOne(Integer key) {
        return null; 
    }
    
    @Override
    public void add(User user) {
        
    }

    @Override
    public void delete(Integer key) {
        
    }
}
