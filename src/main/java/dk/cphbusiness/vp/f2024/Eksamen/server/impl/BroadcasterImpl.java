package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.*;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class BroadcasterImpl implements Broadcaster {
    private BlockingQueue<Message> messages;
    private UserList users;
    private final ChatServer server;
    private final TextIO io;

    public BroadcasterImpl(BlockingQueue<Message> messages, ChatServer server, TextIO io, UserList users) {
            this.messages = messages;
            this.server = server;
            this.users = users;
            this.io = io;

    }

    @Override
    public void run() {
        try {
            while (server.isOnline()) {
                if(messages.isEmpty()) {
                    continue;
                }

                users.sendAll(messages.take());

            }
        } catch (InterruptedException e) {
            io.put("Broadcaster was interrupted while taking message from queue");
            io.put(e.getMessage());
        }
    }


}
