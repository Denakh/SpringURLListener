package mainpackage.users;

import mainpackage.users.CustomUser;

public interface UserService {
    CustomUser getUserByLogin(String login);
    boolean existsByLogin(String login);
    void addUser(CustomUser customUser);
    void updateUser(CustomUser customUser);
}
