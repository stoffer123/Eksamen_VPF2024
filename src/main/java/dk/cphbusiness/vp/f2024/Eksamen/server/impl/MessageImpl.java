package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

public class MessageImpl implements Message {
    private User user;
    private String text;

    public MessageImpl(User user, String text) {
        this.user = user;
        this.text = text;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getText() {
        return text;
    }
}
