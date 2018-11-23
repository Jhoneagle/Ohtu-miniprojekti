
package ohtu;

import ohtu.data_access.AccountDao;
import ohtu.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoForTests implements AccountDao<User, Integer> {
    private List<User> users;

    public UserDaoForTests() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public User findOne(Integer key) {
        return users.get(key);
    }

    @Override
    public void delete(Integer key) {
        users.remove((int) key);
    }
}