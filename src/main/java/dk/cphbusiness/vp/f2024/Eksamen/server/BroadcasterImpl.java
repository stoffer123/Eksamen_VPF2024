package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Broadcaster;
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
        try {
            while (messages != null) {
                if (messages.isEmpty()) {
                    continue;
                }
                Message message = messages.take();

                for (User user : users) {
                    if (user == message.getUser()) {
                        continue;
                    }
                    String msgToSend = "[" + message.getUser().getName() + "] " + message.getMessage();
                    user.sendMessage(msgToSend);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println("Connection error" + e.getMessage());
        }
    }


}
