package dk.cphbusiness.vp.f2024.Eksamen.server.interfaces;

import java.util.List;

public interface UserList {
    void addUser(User user);
    List<User> getUsers();
    void removeUser(User user);
    void sendAll(Message message);
    void clear();
    boolean nameExists(String name);
}
