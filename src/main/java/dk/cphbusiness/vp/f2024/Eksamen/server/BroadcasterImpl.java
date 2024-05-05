package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Broadcaster;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class BroadcasterImpl implements Broadcaster {
    BlockingQueue<Message> messages;
    List<User> users;

    public BroadcasterImpl(BlockingQueue<Message> messages, List<User> users) {
            this.messages = messages;
            this.users = users;

    }

    @Override
    public void run() {
            while (true) {
                if(messages.size() < 1) {
                    continue;
                }

                for(Message m : messages) {
                    for(User u : users) {
                        if(u != m.getUser()) {
                            continue;
                        }
                        u.receiveMessage(m.getText());
                    }
                }
            }

    }

    @Override
    public void broadcast() {

    }

}
