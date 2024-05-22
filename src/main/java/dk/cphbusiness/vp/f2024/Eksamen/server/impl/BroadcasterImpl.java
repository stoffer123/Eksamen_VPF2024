package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.*;
import java.util.concurrent.BlockingQueue;

import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.SystemLogger.systemLogger;

public class BroadcasterImpl implements Broadcaster {
    private final BlockingQueue<Message> messages;
    private final UserList users;
    private final ChatServer server;

    public BroadcasterImpl(BlockingQueue<Message> messages, ChatServer server, UserList users) {
            this.messages = messages;
            this.server = server;
            this.users = users;

    }

    @Override
    public void run() {
        while (server.isOnline()) {
            try {
                if (!messages.isEmpty()) {
                users.sendAll(messages.take());
                }

            } catch (InterruptedException e) {
                systemLogger.warning("Broadcaster was interrupted: " + e.getMessage());
                Thread.interrupted(); // clear interrupted status to continue normal operation
            }
        }
    }


}
