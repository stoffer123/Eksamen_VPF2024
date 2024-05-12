package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class UserImpl implements User {
    private final ChatServer server;
    private final Socket socket;
    private String name;
    private final DataInputStream input;
    private final DataOutputStream output;


    public UserImpl(ChatServer server, Socket socket) throws IOException {

        this.server = server;
        this.socket = socket;
        this.name = "FUNGUS";
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

    }

    @Override
    public void run() {
        try {
            init();
            while (socket != null) {
                String message = input.readUTF();
                server.addMessageToQueue(new Message(this, message));

            }
        } catch (IOException e) {
            close();
            System.out.println("[SERVER] " + name + " Disconnected!");
        }

    }

    @Override
    public void init() {
        try {
            sendMessage("Connected to " + socket.getRemoteSocketAddress());
            sendMessage("Please enter your name: ");
            while(true) {
                String temp = input.readUTF();
                final List<User> users = server.getUsers();
                boolean nameExists = false;

                for(User user : users) {
                    if(user.getName().toLowerCase().equals(temp.toLowerCase())) {
                        nameExists = true;
                    }
                }

                if(nameExists) {
                        sendMessage("Not a valid name, try again!");
                        continue;
                }else {
                    setName(temp);
                    sendMessage("Welcome " + name + "!");
                    break;

                }

            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close(){
        try {
            input.close();
            output.close();
            socket.close();
            server.removeUser(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void sendMessage(String message) throws IOException {
            output.writeUTF(message);
    }
}
