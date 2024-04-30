package dk.cphbusiness.vp.f2024.Eksamen.client;

import dk.cphbusiness.vp.f2024.Eksamen.textio.Textio;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextioImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConsole implements Client {
    private final String ip;
    private final int port;
    private Textio io;
    private String name;
    public ClientConsole(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.io = new TextioImpl();
        this.name = name;
    }
    @Override
    public void run() {
        try(Socket socket = new Socket(ip, port);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()))
        {
            while(true) {
                String message = io.get();
                outputStream.writeUTF(name + " Sent: " + message);
                String serverInput = inputStream.readUTF();
                if(serverInput != null) {
                    System.out.println(serverInput);
                }
            }


        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @Override
    public void sendMessage(String message) {

    }
}
