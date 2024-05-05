package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

public class Message {
    private User user;
    private String message;

    public Message(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
