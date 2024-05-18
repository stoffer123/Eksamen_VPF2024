package dk.cphbusiness.vp.f2024.Eksamen.client;

import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientImpl implements Client {
    private final String ip;
    private final int port;
    private final TextIO io;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean isRunning;

    public ClientImpl(String ip, int port, TextIO io) {
        this.ip = ip;
        this.port = port;
        this.io = io;
        this.isRunning = true;
        try {
            this.socket = new Socket(this.ip, this.port);
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e) {
            io.putError("Could not connect to " + ip + ":" + port);
            stop();
        }
    }

    @Override
    public void run() {
        new Thread(new TxtListener(this, socket, input, io)).start();
        while(isRunning) {
            String message = io.get();
            if(message.equalsIgnoreCase("/exit")) {
                stop();
            }

            sendMessage(message);
        }
    }

    @Override
    public void stop() {
        //close socket and streams
        io.put("Shutting down");
        isRunning = false;
        try {
            input.close();
        }catch(IOException e) {
            io.putError("Error closing input stream: " + e.getMessage());
        }

        try {
            output.close();
        }catch(IOException e) {
            io.putError("Error closing output stream: " + e.getMessage());
        }

        try {
            socket.close();
        }catch(IOException e) {
            io.putError("Error closing socket: " + e.getMessage());
        }

        System.exit(0);

    }

    @Override
    public void sendMessage(String message) {
        try {
        output.writeUTF(message);

        }catch(IOException e) {
            io.putError("Error in sendMessage(): " + e.getMessage());
        }
    }

    @Override
    public void receiveMessage(String message) {
        io.put(message);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
