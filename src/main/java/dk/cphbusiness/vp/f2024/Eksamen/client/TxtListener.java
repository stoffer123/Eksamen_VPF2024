package dk.cphbusiness.vp.f2024.Eksamen.client;

import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class TxtListener implements Runnable {
    private final Socket socket;
    private final DataInputStream input;
    private final TextIO io;
    private final Client client;

    public TxtListener(Client client, Socket socket, DataInputStream input, TextIO io) {
        this.client = client;
        this.socket = socket;
        this.input = input;
        this.io = io;
    }

    @Override
    public void run() {
        try {
            while(client.isRunning()) {
                String message = input.readUTF();
                client.receiveMessage(message);
            }
        } catch (IOException e) {
            if(client.isRunning()){
            io.put("Lost connection to the server");
            client.stop();
            }
        }
    }
}
