package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.textio.Textio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionThread implements Runnable {
    private final Socket socket;
    private final Textio io;

    public ConnectionThread(Socket socket, Textio io) {
        this.socket = socket;
        this.io = io;


    }
    @Override
    public void run() {
        try (
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
        ) {
            while (true) {

                System.out.println(inputStream.readUTF());

            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
