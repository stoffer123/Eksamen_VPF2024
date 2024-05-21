package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.util.ArrayList;
import java.util.List;

import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.ServerLogger.logger;


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
        logger.info(user.getName() + " added to user list");
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public synchronized void removeUser(User user) {
        users.remove(user);
        logger.info(user.getName() + " removed from user list");
    }

    @Override
    public synchronized void sendAll(Message message) {

        for(User user : users) {
            String msgToSend = message.getComposedMessage();
            if (user == message.getSender() /*&& !user.getName().equalsIgnoreCase("SERVER")*/) {
                continue;
            }
            user.sendMessage(msgToSend);
        }
        logger.info("ALL <- " + message.getComposedMessage());
    }

    @Override
    public synchronized void clear() {
        for(User user : users) {
            user.close();
        }
        users.clear();
        logger.info("clear() All users cleared!");
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
