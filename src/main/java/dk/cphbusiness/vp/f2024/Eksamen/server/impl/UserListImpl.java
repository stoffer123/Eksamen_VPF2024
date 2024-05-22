package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.util.ArrayList;
import java.util.List;

import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.ChatLogger.chatLogger;
import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.SystemLogger.systemLogger;


public class UserListImpl implements UserList {
    private final List<User> users;
    private final ChatServer server;
    private final TextIO io;

    public UserListImpl(ChatServer server, TextIO io) {
        this.server = server;
        this.io = io;
        users = new ArrayList<>();
    }



    @Override
    public synchronized void addUser(User user) {
        users.add(user);
    }


    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }


    @Override
    public synchronized void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public synchronized void sendAll(Message message) {

        for(User user : users) {
            String msgToSend = message.getComposedMessage();
            if (user == message.getSender()) {
                continue;
            }
            user.sendMessage(msgToSend);
        }
            chatLogger.info("[ALL] <- " + message.getComposedMessage());
        //logger.info("ALL <- " + message.getComposedMessage()); - Replace with a chatLogger

    }

    @Override
    public synchronized void clear() {
        for(User user : users) {
            user.close();
        }
        users.clear();
        systemLogger.info("clear() All users cleared!");
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
