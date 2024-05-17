package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListImpl implements UserList {
    private List<User> users;
    private ChatServer server;
    private TextIO io;

    public UserListImpl(ChatServer server, TextIO io) {
        this.server = server;
        this.io = io;
        users = new ArrayList<User>();
    }



    @Override
    public synchronized void addUser(User user) {
        users.add(user);
    }

    @Override
    public synchronized void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public synchronized void sendAll(Message message) {
        for(User user : users) {
            if (user == message.getUser() && !user.getName().equalsIgnoreCase("SERVER")) {
                continue;
            }

            String msgToSend = "[" + message.getUser().getName() + "] " + message.getText();
            user.sendMessage(msgToSend);
        }
    }

    @Override
    public synchronized void clear() {
        for(User user : users) {
            user.close();
        }
        users.clear();
    }

    @Override
    public synchronized boolean nameExists(String name) {
        for(User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
