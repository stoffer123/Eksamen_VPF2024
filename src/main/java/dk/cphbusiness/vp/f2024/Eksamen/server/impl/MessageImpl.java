package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

public class MessageImpl implements Message {
    private final User sender;
    private final String rawText;
    private final String composedMessage;

    //Add date(hh:mm:ss) to message to display it in the chat?????

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
        //Returns message as it will be displayed in the console
        return composedMessage;
    }
}
