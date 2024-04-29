package dk.cphbusiness.vp.f2024.Eksamen.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConsole implements Client {
    private final String ip;
    private final int port;

    public ClientConsole(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    @Override
    public void run() {
        try(Socket socket = new Socket(ip, port);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()))
        {
            outputStream.writeUTF("Connected to " + ip + ":" + port);


        } catch (IOException e) {

        }

    }

    @Override
    public void sendMessage(String message) {

    }
}
