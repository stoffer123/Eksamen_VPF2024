package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.textio.Textio;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextioImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private final int port;
    private final Textio io;

    public Server(int port) {
        this.port = port;
        this.io = new TextioImpl();
    }
    @Override
    public void run() {
       try(ServerSocket serverSocket = new ServerSocket(port)) {
           while(true) {
               Socket socket = serverSocket.accept();
               new Thread(new ConnectionThread(socket, io)).start();
               System.out.println("Connected to: " + socket.getRemoteSocketAddress());

           }

        } catch (IOException e) {
           System.out.println(e);
        }
    }
}
