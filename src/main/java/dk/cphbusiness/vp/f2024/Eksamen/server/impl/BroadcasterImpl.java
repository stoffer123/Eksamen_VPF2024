package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Broadcaster;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class BroadcasterImpl implements Broadcaster {
    private BlockingQueue<MessageImpl> messages;
    private List<User> users;
    private final TextIO io;

    public BroadcasterImpl(BlockingQueue<MessageImpl> messages, List<User> users, TextIO io) {
            this.messages = messages;
            this.users = users;
            this.io = io;

    }

    @Override
    public void run() {
        try {
            while (messages != null) {
                if (messages.isEmpty()) {
                    continue;
                }
                MessageImpl message = messages.take();

                for (User user : users) {
                    if (user == message.getUser()) {
                        continue;
                    }
                    String msgToSend = "[" + message.getUser().getName() + "] " + message.getText();
                    user.sendMessage(msgToSend);
                }
            }
        } catch (InterruptedException e) {
            io.put("Broadcaster was interrupted while taking message from queue");
            io.put(e.getMessage());
        }catch (IOException e){

            //Make more specific, fx which user caused the connection error?, maybe do try/catch inside for loop
            io.put("Connection error" + e.getMessage());
        }
    }


}
