package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UserImpl implements User {
    private final ChatServer server;
    private final User serverUser;
    private final Socket socket;
    private String name;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final TextIO io;
    private boolean isRunning;
    private final UserList users;


    public UserImpl(ChatServer server, User serverUser, Socket socket, TextIO io, UserList users) throws IOException {

        this.server = server;
        this.serverUser = serverUser;
        this.socket = socket;
        this.io = io;
        this.name = "NewUser";
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        isRunning = true;
        this.users = users;
    }

    @Override
    public void run() {
        init();

        try {
            while (isRunning) {
                String text = input.readUTF();
                server.addMessageToQueue(new MessageImpl(this, text));

            }

        } catch (IOException e) {
            io.putError("Lost connection to: " + name + " with message: " + e.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public void init() {
        try {
            sendMessage("Connected to " + socket.getRemoteSocketAddress());
            sendMessage("Please enter your name: ");

            //Check if name is already in use
            while (name.equalsIgnoreCase("NewUser")) {
                String temp = input.readUTF().trim();

                if (users.nameExists(temp)) {
                    sendMessage("Not a valid name, try again!");

                } else {
                    setName(temp);
                    server.addMessageToQueue(new MessageImpl(serverUser, "Welcome " + name + "!"));
                    break;

                }

            }
        } catch (IOException e) {
            io.putError(e.getMessage() + " for user: " + name + " in run()");
            close();
        }

    }

    @Override
    public void close(){
        isRunning = false;
        //close resources individually to make sure everything gets closed.
        //[SUGGESTION] add null check?? no point in trying to close something that is null
        try{
            if(input != null) {
            input.close();
            }

        }catch(IOException e) {
            io.putError(name + " Failed to close input " + e.getMessage());
        }

        try{
            if(output != null) {
            output.close();
            }

        }catch(IOException e) {
            io.putError(name + " Failed to close output " + e.getMessage());
        }

        try{
            if(socket != null) {
            socket.close();
            }

        }catch(IOException e) {
            io.putError(name + " Failed to close socket " + e.getMessage());
        }
        server.addMessageToQueue(new MessageImpl(serverUser, name + " Disconnected!"));
        server.removeUser(this);

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
    public void sendMessage(String text) {
        try {
            output.writeUTF(text);
        }catch (IOException e) {
            io.putError("sendMessage() failed for user: " + name + " with message: " + e.getMessage());
        }
    }

}
