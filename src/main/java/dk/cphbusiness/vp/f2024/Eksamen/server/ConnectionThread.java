package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.textio.Textio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionThread implements Runnable {
    private final Socket socket;
    private final Textio io;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public ConnectionThread(Socket socket, Textio io) throws IOException {
        this.socket = socket;
        this.io = io;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

    }
    @Override
    public void run() {
        try {
            while (true) {
                String clientInput = inputStream.readUTF();
                System.out.println(clientInput);
                switch (clientInput.toLowerCase()) {
                    case "/exit":
                        outputStream.writeUTF("Client requested EXIT, closing socket now");
                        socket.close();
                        break;
                    default:
                        continue;

                }

            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
