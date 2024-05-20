package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

public class MessageImpl implements Message {
    private User sender;
    private String rawText;
    private String composedMessage;

    public MessageImpl(User sender, String text) {
        this.sender = sender;
        this.rawText = text;
        composedMessage = "[" + sender.getName() + "] " + text;
    }

    @Override
    public User getSender() {
        return sender;
    }

    @Override
    public String getRawText() {
        return rawText;
    }

    @Override
    public String getComposedMessage() {
        return composedMessage;
    }
}
