package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Broadcaster;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class BroadcasterImpl implements Broadcaster {
    private BlockingQueue<Message> messages;
    private List<User> users;
    private final ChatServer server;
    private final TextIO io;

    public BroadcasterImpl(BlockingQueue<Message> messages, ChatServer server, TextIO io) {
            this.messages = messages;
            this.server = server;
            this.users = server.getUsers();
            this.io = io;

    }

    @Override
    public void run() {
        try {
            while (messages != null) {
                if (messages.isEmpty()) {
                    continue;
                }
                Message message = messages.take();

                //evt whileLoop
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
        }catch (IOException e) {

            //Make more specific, fx which user caused the connection error?, maybe do try/catch inside for loop
            //deklarer variabel øverst
            io.put("Connection error" + e.getMessage());
        }
    }


}
