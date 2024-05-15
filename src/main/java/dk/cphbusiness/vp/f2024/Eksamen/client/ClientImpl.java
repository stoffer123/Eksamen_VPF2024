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
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    public ClientImpl(String ip, int port, TextIO io) throws IOException {
        this.ip = ip;
        this.port = port;
        this.io = io;
        this.socket = new Socket(ip, port);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        new Thread(new TxtListener(this, socket, input, io)).start();
        while(socket.isConnected()) {
            String message = io.get();
            sendMessage(message);
        }
    }

    @Override
    public void stop() {
        //close socket and streams
        io.put("Shutting down");
        System.exit(0);

    }

    @Override
    public void sendMessage(String message) {
        try {
        output.writeUTF(message);

        }catch(IOException e) {
            io.put(e.getMessage());
        }
    }

    @Override
    public void receiveMessage(String message) {
        io.put(message);
    }


}
